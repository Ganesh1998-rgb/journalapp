package com.ganesh.journalApp.service;

import com.ganesh.journalApp.entity.User;
import com.ganesh.journalApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
private static final Logger log= LoggerFactory.getLogger(UserService.class);

    public User saveUser(User user) {

        return userRepository.save(user);
    }

    public User saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            log.info("haahahahhaaahhahhaahhah");
            log.error("fdfsfsffsfsfsfsfsffsfsf");
            log.warn("sdfdsfsdffsdgggsgsgg");
            log.trace("sdfdssgdsgsgsgsdsgsgg");
            log.debug("sfsfsffsffsfsfsdfasdf");
            throw new RuntimeException(e);
        }

    }
    public User createAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
    }

    public void deleteUser(String username) {
        userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
    }

    public User updateUser(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            User oldUser = userRepository.findByUserName(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            oldUser.setUserName(user.getUserName());
            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));;

            return userRepository.save(oldUser);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update user", e);
        }
    }

}


