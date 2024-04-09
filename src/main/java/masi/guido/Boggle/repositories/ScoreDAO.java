package masi.guido.Boggle.repositories;

import masi.guido.Boggle.entities.Game;
import masi.guido.Boggle.entities.Score;
import masi.guido.Boggle.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScoreDAO extends JpaRepository<Score, UUID> {
    public Optional<Score> findByUserAndGame(User user, Game game);
    public Optional<Score> findByUser(User user);
}
