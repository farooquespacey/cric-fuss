package com.befaruq.service;

import com.befaruq.model.request.CricInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    ScorecardService scorecardService;
    
//    @KafkaListener(topics = AppConstants.TOPIC_NAME, groupId = AppConstants.GROUP_ID)
    public void consume(CricInfoRequest cir)
    {
        System.out.println("Consuming from kafka!");
        scorecardService.save(cir);
    }
}
