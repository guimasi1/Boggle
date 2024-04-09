package masi.guido.Boggle.repositories;

import masi.guido.Boggle.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardDAO extends JpaRepository<Board, UUID> {

}
