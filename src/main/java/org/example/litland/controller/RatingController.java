package org.example.litland.controller;

import org.example.litland.response.SaveMarkResponse;
import org.example.litland.response.UserMarkResponse;
import org.example.litland.service.RatingService;
import org.example.litland.shell.GenreShell;
import org.example.litland.shell.MarkShell;
import org.example.litland.shell.UserMarkShell;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/info")
    @CrossOrigin(origins = "http://localhost:3000")
    public UserMarkResponse getUserMark(@RequestBody UserMarkShell userMarkShell) {
        return ratingService.getUserMark(userMarkShell.getUserId(), userMarkShell.getBookId());
    }

    @PostMapping("/mark")
    @CrossOrigin(origins = "http://localhost:3000")
    public SaveMarkResponse saveMark(@RequestBody MarkShell mark) {
        return ratingService.saveMark(mark.getUserId(), mark.getBookId(), mark.getMark());
    }
}
