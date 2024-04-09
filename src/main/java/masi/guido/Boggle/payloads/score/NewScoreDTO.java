package masi.guido.Boggle.payloads.score;

import java.util.UUID;

public record NewScoreDTO(UUID user_id, UUID game_id) {
}
