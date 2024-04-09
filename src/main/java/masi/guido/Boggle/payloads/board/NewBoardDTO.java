package masi.guido.Boggle.payloads.board;

import jakarta.validation.constraints.Min;

public record NewBoardDTO(@Min(4) long size) {
}
