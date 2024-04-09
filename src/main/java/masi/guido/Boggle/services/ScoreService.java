package masi.guido.Boggle.services;

import masi.guido.Boggle.entities.Game;
import masi.guido.Boggle.entities.Score;
import masi.guido.Boggle.entities.User;
import masi.guido.Boggle.exceptions.NotFoundException;
import masi.guido.Boggle.payloads.score.NewScoreDTO;
import masi.guido.Boggle.repositories.ScoreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ScoreService {
    @Autowired
    ScoreDAO scoreDAO;

    @Autowired
    GameService gameService;

    @Autowired
    UserService userService;

    public Score saveScore (NewScoreDTO newScoreDTO) {
        Score score = new Score();
        score.setGame(gameService.findById(newScoreDTO.game_id()));
        score.setUser(userService.findById(newScoreDTO.user_id()));
        return scoreDAO.save(score);
    }

    public Page<Score> getAllScores(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return scoreDAO.findAll(pageable);
    }




}
