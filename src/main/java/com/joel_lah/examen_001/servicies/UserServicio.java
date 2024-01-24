package com.joel_lah.examen_001.servicies;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joel_lah.examen_001.models.User;
import com.joel_lah.examen_001.repositories.UserRepository;

@Service
public class UserServicio {

    @Autowired
    private UserRepository userRepo;

    // registrar el usuario y hacer Hash a su password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepo.save(user);
    }

    //encontrar usuario por su nombre
    public User findByName(String name) {
        return userRepo.findByName(name);
    }

    // encontrar un usuario por su email
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    // encontrar un usuario por su id
    public User findUserById(Long id) {
        Optional<User> u = userRepo.findById(id);

        if (u.isPresent()) {
            return u.get();
        } else {
            return null;
        }
    }

    

    // autenticar usuario
    public boolean authenticateUser(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return false;
        } else {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
}