package masi.guido.Boggle.payloads.game;

import java.util.List;
import java.util.UUID;

public record NewGameDTO(UUID board_id, String[] players_ids) {
}
