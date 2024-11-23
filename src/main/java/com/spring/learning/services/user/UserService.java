package com.spring.learning.services.user;

import com.spring.learning.dto.auth.RegisterDto;
import com.spring.learning.dto.RoleType;
import com.spring.learning.entities.Role;
import com.spring.learning.entities.UserEntity;
import com.spring.learning.repositories.RoleRepository;
import com.spring.learning.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean registerUser(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return false;
        }

        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElse(new Role(RoleType.ROLE_USER));

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singletonList(userRole));

        userRepository.save(user);
        return true;
    }
}

