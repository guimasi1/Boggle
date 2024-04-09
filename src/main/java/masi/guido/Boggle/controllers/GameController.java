package masi.guido.Boggle.controllers;

import jakarta.persistence.PreUpdate;
import jakarta.websocket.server.PathParam;
import masi.guido.Boggle.entities.Game;
import masi.guido.Boggle.entities.Score;
import masi.guido.Boggle.exceptions.BadRequestException;
import masi.guido.Boggle.payloads.game.GameResponseDTO;
import masi.guido.Boggle.payloads.game.NewGameDTO;
import masi.guido.Boggle.services.Dictionary;
import masi.guido.Boggle.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    GameService gameService;



    @GetMapping
    public Page<Game> getAllGames(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
        return gameService.getAllGames(page,size,orderBy);
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable UUID id) { return gameService.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody NewGameDTO game, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong with the payload");
        } else {
            return gameService.saveGame(game);
        }
    }



    @GetMapping("/handleWord")
    public int handleWord (@RequestParam String word, @RequestParam UUID game_id, @RequestParam UUID player_id) {
        return gameService.handleWord(word.toUpperCase(), game_id, player_id);
    }

}
