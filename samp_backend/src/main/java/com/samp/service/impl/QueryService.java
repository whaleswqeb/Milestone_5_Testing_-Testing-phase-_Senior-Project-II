package com.samp.service.impl;

import com.samp.dto.event.QueryEvent;
import com.samp.dto.request.CreateQueryRequest;
import com.samp.dto.request.QueryMessageRequest;
import com.samp.dto.response.QueryMessageResponse;
import com.samp.dto.response.StudentQueryResponse;
import com.samp.entity.QueryMessage;
import com.samp.entity.StudentQuery;
import com.samp.entity.User;
import com.samp.enums.NotificationType;
import com.samp.enums.QueryCategory;
import com.samp.enums.QueryPriority;
import com.samp.enums.QueryStatus;
import com.samp.enums.Role;
import com.samp.exception.ResourceNotFoundException;
import com.samp.repository.QueryMessageRepository;
import com.samp.repository.StudentQueryRepository;
import com.samp.repository.UserRepository;
import com.samp.service.kafka.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryService {

    private static final Logger log = LoggerFactory.getLogger(QueryService.class);

    private final StudentQueryRepository queryRepository;
    private final QueryMessageRepository messageRepository;
    private final UserRepository userRepository;
    private final KafkaProducerService kafkaProducerService;
    private final NotificationService notificationService;
    private final WebSocketService webSocketService;

    public QueryService(StudentQueryRepository queryRepository,
                        QueryMessageRepository messageRepository,
                        UserRepository userRepository,
                        KafkaProducerService kafkaProducerService,
                        NotificationService notificationService,
                        WebSocketService webSocketService) {
        this.queryRepository = queryRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.notificationService = notificationService;
        this.webSocketService = webSocketService;
    }

    @Transactional
    public StudentQueryResponse createQuery(User student, CreateQueryRequest request) {
        User managedStudent = userRepository.findById(student.getId()).orElse(student);

        User assignedTo = null;
        if (request.getAssignedFacultyId() != null) {
            assignedTo = userRepository.findById(request.getAssignedFacultyId()).orElse(null);
        }

        StudentQuery query = StudentQuery.builder()
                .student(managedStudent)
                .assignedTo(assignedTo)
                .subject(request.getSubject())
                .category(request.getCategory())
                .priority(request.getPriority() != null ? request.getPriority() : QueryPriority.MEDIUM)
                .status(QueryStatus.OPEN)
                .build();

        StudentQuery savedQuery = queryRepository.save(query);

        QueryMessage firstMessage = QueryMessage.builder()
                .query(savedQuery)
                .sender(managedStudent)
                .message(request.getInitialMessage())
                .build();
        
        messageRepository.save(firstMessage);
        savedQuery.getMessages().add(firstMessage);

        QueryEvent event = QueryEvent.builder()
                .eventType("QUERY_CREATED")
                .queryId(savedQuery.getId())
                .studentId(managedStudent.getId())
                .studentName(managedStudent.getFullName())
                .studentEmail(managedStudent.getEmail())
                .senderId(managedStudent.getId())
                .senderEmail(managedStudent.getEmail())
                .targetUserId(assignedTo != null ? assignedTo.getId() : null)
                .subject(savedQuery.getSubject())
                .category(savedQuery.getCategory())
                .priority(savedQuery.getPriority())
                .status(savedQuery.getStatus())
                .latestMessage(request.getInitialMessage())
                .timestamp(LocalDateTime.now())
                .build();

        try {
            kafkaProducerService.sendQueryEvent(event);
            webSocketService.notifyQueryEvent(event);
        } catch (Exception e) {
            log.warn("Kafka/WebSocket dispatch warning during query create: {}", e.getMessage());
        }

        if (assignedTo != null) {
            try {
                notificationService.sendNotification(
                        assignedTo,
                        "New student query assigned to you: " + savedQuery.getSubject(),
                        NotificationType.SYSTEM
                );
            } catch (Exception e) {
                log.warn("Notification error: {}", e.getMessage());
            }
        }

        return toResponse(savedQuery);
    }

    @Transactional
    public QueryMessageResponse addMessage(Long queryId, User sender, QueryMessageRequest request) {
        StudentQuery query = queryRepository.findById(queryId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentQuery", queryId));

        User managedSender = userRepository.findById(sender.getId()).orElse(sender);

        QueryMessage message = QueryMessage.builder()
                .query(query)
                .sender(managedSender)
                .message(request.getMessage())
                .build();

        QueryMessage savedMessage = messageRepository.save(message);

        if (managedSender.getRole() != Role.STUDENT) {
            if (query.getStatus() == QueryStatus.OPEN) {
                query.setStatus(QueryStatus.IN_PROGRESS);
            }
            if (query.getAssignedTo() == null) {
                query.setAssignedTo(managedSender);
            }
            queryRepository.save(query);
        }

        QueryEvent event = QueryEvent.builder()
                .eventType("QUERY_REPLIED")
                .queryId(query.getId())
                .studentId(query.getStudent().getId())
                .studentName(query.getStudent().getFullName())
                .studentEmail(query.getStudent().getEmail())
                .senderId(managedSender.getId())
                .senderEmail(managedSender.getEmail())
                .targetUserId(query.getStudent().getId())
                .subject(query.getSubject())
                .category(query.getCategory())
                .priority(query.getPriority())
                .status(query.getStatus())
                .latestMessage(request.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        try {
            kafkaProducerService.sendQueryEvent(event);
            webSocketService.notifyQueryEvent(event);
        } catch (Exception e) {
            log.warn("Kafka/WebSocket dispatch warning during reply: {}", e.getMessage());
        }

        User targetUser = managedSender.getId().equals(query.getStudent().getId())
                ? query.getAssignedTo()
                : query.getStudent();

        if (targetUser != null) {
            try {
                notificationService.sendNotification(
                        targetUser,
                        "New reply on query #" + query.getId() + ": " + query.getSubject(),
                        NotificationType.SYSTEM
                );
            } catch (Exception e) {
                log.warn("Notification error: {}", e.getMessage());
            }
        }

        return toMessageResponse(savedMessage);
    }

    @Transactional(readOnly = true)
    public List<StudentQueryResponse> getStudentQueries(User student) {
        return queryRepository.findByStudentOrderByCreatedAtDesc(student)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentQueryResponse> getAssignedQueries(User faculty) {
        return queryRepository.findByAssignedToOrderByCreatedAtDesc(faculty)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentQueryResponse> getAllQueries() {
        return queryRepository.findAllOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentQueryResponse getQueryById(Long queryId, User currentUser) {
        StudentQuery query = queryRepository.findById(queryId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentQuery", queryId));

        return toResponse(query);
    }

    @Transactional
    public StudentQueryResponse updateStatus(Long queryId, QueryStatus status, User currentUser) {
        StudentQuery query = queryRepository.findById(queryId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentQuery", queryId));

        query.setStatus(status);
        StudentQuery updated = queryRepository.save(query);

        QueryEvent event = QueryEvent.builder()
                .eventType("STATUS_CHANGED")
                .queryId(updated.getId())
                .studentId(updated.getStudent().getId())
                .studentName(updated.getStudent().getFullName())
                .studentEmail(updated.getStudent().getEmail())
                .subject(updated.getSubject())
                .status(updated.getStatus())
                .timestamp(LocalDateTime.now())
                .build();

        try {
            kafkaProducerService.sendQueryEvent(event);
            webSocketService.notifyQueryEvent(event);
        } catch (Exception e) {
            log.warn("Kafka/WebSocket dispatch warning during status update: {}", e.getMessage());
        }

        return toResponse(updated);
    }

    public StudentQueryResponse toResponse(StudentQuery q) {
        List<QueryMessageResponse> msgResponses = q.getMessages() != null
                ? q.getMessages().stream().map(this::toMessageResponse).collect(Collectors.toList())
                : List.of();

        return StudentQueryResponse.builder()
                .id(q.getId())
                .studentId(q.getStudent().getId())
                .studentName(q.getStudent().getFullName())
                .studentEmail(q.getStudent().getEmail())
                .assignedToId(q.getAssignedTo() != null ? q.getAssignedTo().getId() : null)
                .assignedToName(q.getAssignedTo() != null ? q.getAssignedTo().getFullName() : "Unassigned / Faculty Queue")
                .subject(q.getSubject())
                .category(q.getCategory())
                .priority(q.getPriority())
                .status(q.getStatus())
                .messages(msgResponses)
                .createdAt(q.getCreatedAt())
                .updatedAt(q.getUpdatedAt())
                .build();
    }

    public QueryMessageResponse toMessageResponse(QueryMessage m) {
        return QueryMessageResponse.builder()
                .id(m.getId())
                .queryId(m.getQuery().getId())
                .senderId(m.getSender().getId())
                .senderName(m.getSender().getFullName())
                .senderRole(m.getSender().getRole().name())
                .message(m.getMessage())
                .createdAt(m.getCreatedAt())
                .build();
    }
}
