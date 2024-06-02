package com.befaruq.service;

import com.befaruq.constants.AppConstants;
import com.befaruq.model.request.CricInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void push(CricInfoRequest cir)
    {
        System.out.println("Pushing to kafka!");
        this.kafkaTemplate.send(AppConstants.TOPIC_NAME, cir);
    }
}