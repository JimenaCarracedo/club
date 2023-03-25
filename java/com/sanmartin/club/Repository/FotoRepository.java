package com.sanmartin.club.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanmartin.club.Entidades.Foto;


@Repository
public interface FotoRepository  extends JpaRepository<Foto, String>  {

}
