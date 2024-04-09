package masi.guido.Boggle.controllers;

import masi.guido.Boggle.entities.Board;
import masi.guido.Boggle.exceptions.BadRequestException;
import masi.guido.Boggle.payloads.board.NewBoardDTO;
import masi.guido.Boggle.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.UUID;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping
    public Page<Board> getBoards(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
        return boardService.getAllBoards(page, size, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Board createBoard(@RequestBody @Validated NewBoardDTO newBoardDTO, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong with the payload");
        } else {

            return boardService.saveBoard(newBoardDTO);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable UUID id) {
        boardService.deleteById(id);
    }


}
