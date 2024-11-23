package com.spring.learning.initializer;

import com.spring.learning.dto.RoleType;
import com.spring.learning.entities.Role;
import com.spring.learning.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer {
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if (!roleRepository.existsByName(RoleType.ROLE_USER)) {
            roleRepository.save(new Role(RoleType.ROLE_USER));
        }
        if (!roleRepository.existsByName(RoleType.ROLE_ADMIN)) {
            roleRepository.save(new Role(RoleType.ROLE_ADMIN));
        }
    }
}
