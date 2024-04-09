package masi.guido.Boggle.services;

import masi.guido.Boggle.entities.User;
import masi.guido.Boggle.exceptions.NotFoundException;
import masi.guido.Boggle.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public User findById (UUID id) {
     return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail (String email ) {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found!"));
    }

    public User saveUser (User user) {
        return userDAO.save(user);
    }

    public Page<User> getAllUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }
}
