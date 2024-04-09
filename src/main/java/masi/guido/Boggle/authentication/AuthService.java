package masi.guido.Boggle.authentication;


import masi.guido.Boggle.entities.User;
import masi.guido.Boggle.enums.Role;
import masi.guido.Boggle.exceptions.BadRequestException;
import masi.guido.Boggle.exceptions.UnauthorizedException;
import masi.guido.Boggle.payloads.user.NewUserDTO;
import masi.guido.Boggle.payloads.user.UserLoginDTO;
import masi.guido.Boggle.repositories.UserDAO;
import masi.guido.Boggle.security.JWTTools;
import masi.guido.Boggle.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired

    private UserDAO userDAO;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bCrypt;



    public String authenticateUser(UserLoginDTO body) {
        User user = userService.findByEmail(body.email());
        if(bCrypt.matches(body.password(),user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Errore nelle credenziali");
        }
    }

    public User saveUser(NewUserDTO body) {
        userDAO.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("Email " + body.email() + " già in uso");
        });



        User newUser = new User();
        newUser.setEmail(body.email());
        newUser.setUsername(body.username());
        newUser.setPassword(bCrypt.encode(body.password()));
        newUser.setRole(Role.USER);
        return userDAO.save(newUser);
    }


}
