package masi.guido.Boggle.services;

import jakarta.transaction.Transactional;
import masi.guido.Boggle.entities.Board;
import masi.guido.Boggle.entities.Cell;
import masi.guido.Boggle.exceptions.NotFoundException;
import masi.guido.Boggle.payloads.board.NewBoardDTO;
import masi.guido.Boggle.repositories.BoardDAO;
import masi.guido.Boggle.repositories.CellDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoardService {
    @Autowired
    BoardDAO boardDAO;

    @Autowired
    CellDAO cellDAO;

    public static List<String> dice = List.of("TACGIP","HNSRIE","TLSPUE","NUEOTC","CPVAGS","TAEOAI","OUGENL"
            ,"FEHSIE","SAIOMR","ZQSBAF","RFPIGA","BFLOEN","QOMBAO","OIABCM","DLMCOI","RBTLIA","INEGTV",
            "ESACLR","MIROCL","IAOCFR","ZNVDEA","ELROIU","TESOND","DVOMTI","MADECP");

    public Board findById(UUID id) {
        return boardDAO.findById(id).orElseThrow(() -> new NotFoundException("Board with id " + id + " not found"));
    }

    public Board saveBoard(NewBoardDTO newBoardDTO) {
       // Collections.shuffle(dice);
        Board board = new Board();
        UUID id = boardDAO.save(board).getId();
        Board board2 = this.findById(id);
        List<Cell> cells = new ArrayList<>();
        for(int row = 1; row < newBoardDTO.size() + 1; row++) {
            for(int col = 1; col < newBoardDTO.size() + 1; col++) {
                Cell cell = new Cell();
                cell.setRow(row);
                cell.setCol(col);
                Random random = new Random();
                String currentDice = dice.get(random.nextInt(0,dice.size()));
                cell.setLetter(currentDice.charAt(random.nextInt(0,currentDice.length())));
                cell.setBoard(board2);
                cells.add(cellDAO.save(cell));
            }
        }
        board2.setCells(cells);
        return board2;
    }

    public Page<Board> getAllBoards(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return boardDAO.findAll(pageable);
    }

    public void deleteById(UUID id) {
        Board board = this.findById(id);
        boardDAO.delete(board);
    }


}


