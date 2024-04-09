package masi.guido.Boggle.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "cells")
public class Cell {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private Character letter;
    private int row;
    private int col;
    @ManyToOne
    @JoinColumn(name = "board_id")
    @ToString.Exclude
    @JsonIgnore
    private Board board;
}
