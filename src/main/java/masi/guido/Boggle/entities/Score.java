package masi.guido.Boggle.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Score {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIgnore
    @ToString.Exclude
    private Game game;
    private int score;

}
