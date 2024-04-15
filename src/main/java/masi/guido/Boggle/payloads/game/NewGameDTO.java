package masi.guido.Boggle.payloads.game;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record NewGameDTO(
        @NotEmpty(message = "board id cannot be empty")
        UUID board_id,
        @NotEmpty(message = "players ids cannot be empty")
        String[] players_ids) {
}
