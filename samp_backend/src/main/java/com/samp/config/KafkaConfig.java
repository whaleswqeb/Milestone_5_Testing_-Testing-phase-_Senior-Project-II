package com.samp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${app.kafka.topics.student-queries:samp.student.queries}")
    private String studentQueriesTopic;

    @Value("${app.kafka.topics.query-replies:samp.query.replies}")
    private String queryRepliesTopic;

    @Value("${app.kafka.topics.notifications:samp.system.notifications}")
    private String systemNotificationsTopic;

    @Bean
    public NewTopic studentQueriesTopic() {
        return TopicBuilder.name(studentQueriesTopic)
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic queryRepliesTopic() {
        return TopicBuilder.name(queryRepliesTopic)
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic systemNotificationsTopic() {
        return TopicBuilder.name(systemNotificationsTopic)
                .partitions(3)
                .replicas(3)
                .build();
    }
}
