package com.samp.service.kafka;

import com.samp.dto.event.QueryEvent;
import com.samp.service.impl.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final WebSocketService webSocketService;

    public KafkaConsumerService(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.student-queries:samp.student.queries}",
            groupId = "${spring.kafka.consumer.group-id:samp-group}"
    )
    public void consumeStudentQueryEvent(QueryEvent event) {
        log.info("Kafka Consumer received student query event: Type={}, QueryID={}", 
                event.getEventType(), event.getQueryId());
        
        webSocketService.notifyQueryEvent(event);
    }

    @KafkaListener(
            topics = "${app.kafka.topics.query-replies:samp.query.replies}",
            groupId = "${spring.kafka.consumer.group-id:samp-group}"
    )
    public void consumeQueryReplyEvent(QueryEvent event) {
        log.info("Kafka Consumer received query reply event: Type={}, QueryID={}", 
                event.getEventType(), event.getQueryId());
        
        webSocketService.notifyQueryEvent(event);
    }
}
