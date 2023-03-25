package com.sanmartin.club.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanmartin.club.Entidades.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> searchByName(String name);
}