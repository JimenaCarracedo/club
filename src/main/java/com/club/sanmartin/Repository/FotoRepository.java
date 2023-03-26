package com.club.sanmartin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.club.sanmartin.Entidades.Foto;


@Repository
public interface FotoRepository  extends JpaRepository<Foto, String>  {

}
