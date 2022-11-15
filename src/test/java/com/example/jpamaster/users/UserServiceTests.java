package com.example.jpamaster.users;

import com.example.jpamaster.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

}
