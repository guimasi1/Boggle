package masi.guido.Boggle.payloads.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(
        @NotEmpty(message = "email cannot be empty")
        @Email
        String email,
        @NotEmpty(message = "password cannot be empty")
        @Size(min = 8, max = 30, message = "password length: min 3 and max 20")
        String password) {
}
