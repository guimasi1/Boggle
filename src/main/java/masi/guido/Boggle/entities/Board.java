package masi.guido.Boggle.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.util.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Table(name = "boards")
public class Board {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue
    private UUID id;
    @OneToOne
    @JoinColumn(name = "game_id")
    @JsonIgnore
    @ToString.Exclude
    private Game game;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Cell> cells;


}

