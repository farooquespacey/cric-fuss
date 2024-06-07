package com.befaruq.controller;

import com.befaruq.model.response.BadResponse;
import com.befaruq.model.response.Batting;
import com.befaruq.model.response.Bowling;
import com.befaruq.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Farooque
 */
@RestController
@RequestMapping("/cric-info/stats")
public class StatsController {
    @Autowired
    private StatsService statsService;


    @GetMapping("/getTopScorer")
    public ResponseEntity<?> findTopNScorer(@RequestParam("inningId") Long inningId, @RequestParam("num") int num) {
        try {
            List<Batting> battingList = statsService.getTopNScorer(inningId, num);
            return new ResponseEntity<>(battingList, HttpStatus.OK);
        } catch (Exception e) {
            BadResponse br = new BadResponse("failure", e.getMessage());
            return new ResponseEntity<>(br, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTopBowler")
    public ResponseEntity<?> findTopNBowler(@RequestParam("inningId") Long inningId, @RequestParam("num") int num) {
        try {
            List<Bowling> bowlingList = statsService.getTopNBowler(inningId, num);
            return new ResponseEntity<>(bowlingList, HttpStatus.OK);
        } catch (Exception e) {
            BadResponse br = new BadResponse("failure", e.getMessage());
            return new ResponseEntity<>(br, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getInningData/{inningId}")
    public ResponseEntity<?> getInningData(@PathVariable Long inningId, @RequestParam("include_child") boolean includeChild) {
        try {
            /* 
            Jackson by default will call all the getters for an entity when returning ResponseEntity<..>. One way we
            could have avoided that is by annotating List<Batting> and List<Bowling> with @JsonIgnore but that would 
            also affect all other invokers who'd have wanted the default behavior (i.e., /getMatchInfo api). 
            So, I am using CustomDTO to handle "response view" just for this API call (and others to come in the future.)  
            */
            Object inning = includeChild ? statsService.getInningDataWithChild(inningId) : statsService.getInningData(inningId);
            return new ResponseEntity<>(inning, HttpStatus.OK);
        } catch (Exception e) {
            BadResponse br = new BadResponse("failure", e.getMessage());
            return new ResponseEntity<>(br, HttpStatus.BAD_REQUEST);
        }
    }
}
