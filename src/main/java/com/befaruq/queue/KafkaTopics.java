package com.befaruq.queue;

import com.befaruq.constants.AppConstants;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

// Disabled for now. Will enable later after working on other things.
//@Configuration
public class KafkaTopics {
    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, AppConstants.SERVER_INFO);

        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(AppConstants.TOPIC_NAME)
                .partitions(10)
                .replicas(3)
                .compact()
                .build();
    }
}
