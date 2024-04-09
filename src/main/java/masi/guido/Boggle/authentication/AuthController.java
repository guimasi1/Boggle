package masi.guido.Boggle.authentication;


import masi.guido.Boggle.entities.User;
import masi.guido.Boggle.exceptions.BadRequestException;
import masi.guido.Boggle.payloads.user.NewUserDTO;
import masi.guido.Boggle.payloads.user.NewUserResponseDTO;
import masi.guido.Boggle.payloads.user.UserLoginDTO;
import masi.guido.Boggle.payloads.user.UserLoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        String token = authService.authenticateUser(body);
        return new UserLoginResponseDTO(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO createUser(@RequestBody @Validated NewUserDTO newPhysiotherapistPayload, BindingResult validation) {
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            User user = authService.saveUser(newPhysiotherapistPayload);
            return new NewUserResponseDTO(user.getId());
        }
    }
}
