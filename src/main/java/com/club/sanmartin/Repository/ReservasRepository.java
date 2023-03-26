package com.club.sanmartin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.club.sanmartin.Entidades.Reservas;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, String>{

}
