package com.ganesh.journalApp.service;

import com.ganesh.journalApp.dto.UserRequestDTO;
import com.ganesh.journalApp.dto.UserResponseDTO;
import com.ganesh.journalApp.entity.User;
import com.ganesh.journalApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

private final ModelMapper modelMapper;
    private final UserRepository userRepository;
private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
private static final Logger log= LoggerFactory.getLogger(UserService.class);

    public User saveUser(User user) {

        return userRepository.save(user);
    }

    public UserResponseDTO saveNewUser(UserRequestDTO userDTO) {
        try {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            User map = modelMapper.map(userDTO, User.class);
            User save = userRepository.save(map);
            return modelMapper.map(save, UserResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public User createAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<UserResponseDTO> getAll() {
        List<User> all = userRepository.findAll();
        return all.stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .toList();

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


