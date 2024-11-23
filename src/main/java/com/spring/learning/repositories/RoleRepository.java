package com.spring.learning.repositories;

import com.spring.learning.dto.RoleType;
import com.spring.learning.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleType name);
    boolean existsByName(RoleType name);
}
