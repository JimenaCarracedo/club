package com.sanmartin.club.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sanmartin.club.Entidades.Eventos;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, String> {

	@Query("SELECT e FROM Eventos e WHERE e.nombre LIKE :nombre")
	public List<Eventos> searchByName(@Param("nombre") String nombre);

	@Query("SELECT e FROM Eventos e WHERE e.valorentrada LIKE :valorentrada")
	public List<Eventos> searchByUd(@Param("valorentrada") Integer valorentrada);

	@Query("SELECT e FROM Eventos e JOIN Taller t WHERE e.taller.id LIKE id AND nombre LIKE :nombre")
	public List<Eventos> searchByTaller (@Param("nombre") String nombre);

}
