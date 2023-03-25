package com.sanmartin.club.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanmartin.club.Entidades.Reservas;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, String>{

}
