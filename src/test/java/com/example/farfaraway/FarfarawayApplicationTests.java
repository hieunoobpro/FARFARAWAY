package com.example.farfaraway;

import com.example.farfaraway.entity.Role;
import com.example.farfaraway.entity.User;
import com.example.farfaraway.repository.RoleRepository;
import com.example.farfaraway.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class FarfarawayApplicationTests {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }
    @Test
    void save_role() {
        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleRepository.save(roleAdmin);

    }
    @Test
    void addAdmin(){
        Role roleAdmin = roleRepository.findByName("ADMIN").get();
        // create user
        User userUser = new User();
        userUser.setName("Duy Hiếu");
        userUser.setEmail("hieu185@gmail.com");
        userUser.setPassword(passwordEncoder.encode("123"));
        userUser.getRoles().add(roleAdmin);
        userRepository.save(userUser);
    }
    @Test
    void addUser(){
        Role roleUser = roleRepository.findByName("User").get();
        // create user
        User userUser = new User();
        userUser.setName("Bui Hien");
        userUser.setEmail("hien1@gmail.com");
        userUser.setPassword(passwordEncoder.encode("123"));
        userUser.getRoles().add(roleUser);
        userRepository.save(userUser);
    }

}
