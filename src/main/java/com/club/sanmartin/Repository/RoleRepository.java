package com.club.sanmartin.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.club.sanmartin.Entidades.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> searchByName(String name);
}