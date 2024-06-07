package com.befaruq.controller;

import com.befaruq.model.request.CricInfoRequest;
import com.befaruq.model.response.BadResponse;
import com.befaruq.model.response.mapper.Scorecard;
import com.befaruq.service.ScorecardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cric-info")
public class ScorecardController {

    @Autowired
    private ScorecardService scorecardService;
    
    @GetMapping("/getMatchInfo/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Scorecard scorecard = scorecardService.getScorecard(id);
            return new ResponseEntity<>(scorecard, HttpStatus.OK);
        } catch (Exception e) {
            BadResponse br = new BadResponse("failure", e.getMessage());
            return new ResponseEntity<>(br, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/addMatchInfo")
    public ResponseEntity<?> create(@RequestBody CricInfoRequest cricInfoRequest) {
        try {
            Scorecard scorecard = scorecardService.save(cricInfoRequest);
            return new ResponseEntity<>(scorecard, HttpStatus.CREATED);
        } catch (Exception e) {
            BadResponse br = new BadResponse("failure", e.getMessage());
            return new ResponseEntity<>(br, HttpStatus.CONFLICT);
        }
//        return kafkaProducerService.push(cricInfoRequest);
    }

}
