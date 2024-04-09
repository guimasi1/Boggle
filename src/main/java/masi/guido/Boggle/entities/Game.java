package masi.guido.Boggle.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @OneToOne
    @JoinColumn(name = "board_id")
    private Board board;
    @ManyToMany(mappedBy = "games")
    @JsonIgnore
    @ToString.Exclude
    private List<User> users;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Score> scores;
}
