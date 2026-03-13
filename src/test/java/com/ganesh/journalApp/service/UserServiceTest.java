package com.ganesh.journalApp.service;

import com.ganesh.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
   public void findUserName(){
       assertNotNull(userRepository.findByUserName("Ganesh"));

   }

}
