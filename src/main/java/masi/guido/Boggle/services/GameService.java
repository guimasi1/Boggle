package masi.guido.Boggle.services;

import jakarta.transaction.Transactional;
import masi.guido.Boggle.entities.*;
import masi.guido.Boggle.exceptions.NotFoundException;
import masi.guido.Boggle.payloads.board.NewBoardDTO;
import masi.guido.Boggle.payloads.game.NewGameDTO;
import masi.guido.Boggle.payloads.game.WordHandlingResponse;
import masi.guido.Boggle.repositories.BoardDAO;
import masi.guido.Boggle.repositories.GameDAO;
import masi.guido.Boggle.repositories.ScoreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {
    @Autowired
    GameDAO gameDAO;

    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;

    @Autowired
    ScoreDAO scoreDAO;

    @Autowired
    Dictionary dictionary;

    public Page<Game> getAllGames(int page, int size,String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return gameDAO.findAll(pageable);
    }

    public Game findById (UUID id) {
        return gameDAO.findById(id).orElseThrow(() -> new NotFoundException("Game with " + id + " not found" ));
    }

    @Transactional
    public Game saveGame(NewGameDTO newGameDTO) {
        Board board = boardService.findById(newGameDTO.board_id());
        Game game = new Game();
        List<String> playersIds = Arrays.stream(newGameDTO.players_ids()).toList();
        List<User> users = playersIds.stream().map(id -> userService.findById(UUID.fromString(id))).toList();
        game.setUsers(users);
        game.setBoard(board);
        game = gameDAO.save(game);
        board.setGame(game);
        List<Score> scores = new ArrayList<>();
        for (User user : users) {
            Score score = new Score();
            score.setUser(user);
            score.setGame(game);
            score.setScore(0);
            user.getGames().add(game);
            scores.add(scoreDAO.save(score));
            userService.saveUser(user);
        }
        game.setScores(scores);

        return game;
    }

    public boolean isWordValid(UUID boardId, String word) {
        Board board = boardService.findById(boardId);
        // if the word is not present in the dictionary this line return false, else
        if(!dictionary.isWordPresent(word.toLowerCase())) return false;
        // it starts searching for the word inside the board, and if it doesn't find it, it returns false
        for (Cell cell : board.getCells()) {
            if (searchForWord(cell, board, word, 0, new HashSet<>())) {
                return true;
            }
        }
        return false;
    }

    private boolean searchForWord(Cell startCell, Board board, String word, int wordIndex, Set<Cell> visited) {
        if (wordIndex == word.length()) {
            return true;
        }

        if (startCell.getLetter() != word.charAt(wordIndex) || visited.contains(startCell)) {
            return false;
        }

        visited.add(startCell);

        List<Cell> adjacentCells = findAdjacentCells(startCell, board);
        for (Cell nextCell : adjacentCells) {
            if (searchForWord(nextCell, board, word, wordIndex + 1, new HashSet<>(visited))) {
                return true;
            }
        }

        return false;
    }

    private List<Cell> findAdjacentCells(Cell cell, Board board) {
        List<Cell> adjacentCells = new ArrayList<>();
        int[] directions = {-1, 0, 1};

        for (int dx : directions) {
            for (int dy : directions) {
                if (dx == 0 && dy == 0) continue;
                board.getCells().stream()
                        .filter(c -> c.getRow() == cell.getRow() + dx && c.getCol() == cell.getCol() + dy)
                        .findFirst().ifPresent(adjacentCells::add);
            }
        }

        return adjacentCells;
    }

    public int addPoints(Score score, int points, String word) {
           score.setScore(score.getScore() + points);
           List<String> words;
           if(score.getWords() != null) {
               words = score.getWords();
           } else {
               words = new ArrayList<>();
           }
           words.add(word);
           score.setWords(words);
           scoreDAO.save(score);
           return score.getScore() + points;
    }

    public Score findScoreByUserIdAndGameId(UUID user_id, UUID game_id) {
        User user = userService.findById(user_id);
        Game game = this.findById(game_id);
        return scoreDAO.findByUserAndGame(user, game).orElseThrow(() -> new NotFoundException("Score not found"));
    }

    public WordHandlingResponse handleWord(String word,UUID game_id, UUID player_id) {
        Game game = this.findById(game_id);
        UUID boardId = game.getBoard().getId();
        Score score = this.findScoreByUserIdAndGameId(player_id, game_id);
        if(this.isWordValid(boardId,word)) {
            if(!score.getWords().contains(word)) {
                int points = this.defineHowManyPoints(word);
                this.addPoints(score, points, word);
            } else {
                return new WordHandlingResponse("Word already added.");
            }
        } else {
            return new WordHandlingResponse("Word not valid.");
        }

        return new WordHandlingResponse("Points added: " + this.defineHowManyPoints(word) + ", Total points: " + score.getScore());
    }

    public int defineHowManyPoints(String word) {
        int points = 0;
        switch (word.length()) {
            case 3, 4:
                points = 1;
                break;
            case 5:
                points = 2;
                break;
            case 6:
                points = 3;
                break;
            case 7:
                points = 5;
                break;
            case 8:
                points = 8;
                break;
            case 9:
                points = 10;
                break;
            case 10:
                points = 12;
                break;
            case 11:
                points = 14;
                break;
            case 12:
                points = 16;
                break;
            case 13:
                points = 18;
                break;
            case 14:
                points = 20;
                break;
            case 15:
                points = 22;
                break;
            case 16:
                return 24;
        }
        return points;
    }
}








