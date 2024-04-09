package masi.guido.Boggle.payloads.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotEmpty(message = "email cannot be empty")
        @Email
        String email,
        @NotEmpty(message = "password cannot be empty")
        @Size(min = 3, max = 30, message = "password must be between 3 e 30 chars")
        String password,
        @NotEmpty(message = "username cannot be empty")
        @Size(min = 3, max = 30, message = "username must be between 3 e 30 chars")
        String username) {

}
