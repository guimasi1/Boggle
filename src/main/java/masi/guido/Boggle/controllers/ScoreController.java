package masi.guido.Boggle.controllers;

import masi.guido.Boggle.entities.Score;
import masi.guido.Boggle.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

   @GetMapping
   @ResponseStatus(HttpStatus.OK)
    public Page<Score> getAllScores(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String orderBy) {
       return scoreService.getAllScores(page,size,orderBy);
   }

}
