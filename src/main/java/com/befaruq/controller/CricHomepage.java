package com.befaruq.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: Require some work to display the UI
 */
@RestController
public class CricHomepage {
    @GetMapping("/")
    public String getScorecard(@PathVariable String matchId, Model model) {
        model.addAttribute("matchId", matchId);
        return "index";
    }
}