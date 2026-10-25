package com.samp.service.kafka;

import com.samp.dto.event.QueryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.student-queries:samp.student.queries}")
    private String studentQueriesTopic;

    @Value("${app.kafka.topics.query-replies:samp.query.replies}")
    private String queryRepliesTopic;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendQueryEvent(QueryEvent event) {
        try {
            String topic = "QUERY_REPLIED".equalsIgnoreCase(event.getEventType()) 
                    ? queryRepliesTopic 
                    : studentQueriesTopic;
                    
            log.info("Publishing Kafka event [{}] for Query ID {} to topic {}", 
                    event.getEventType(), event.getQueryId(), topic);
                    
            kafkaTemplate.send(topic, String.valueOf(event.getQueryId()), event);
        } catch (Exception e) {
            log.warn("Could not publish event to Kafka cluster: {}", e.getMessage());
        }
    }
}
