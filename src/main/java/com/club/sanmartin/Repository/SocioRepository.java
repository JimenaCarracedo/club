package com.club.sanmartin.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.club.sanmartin.Entidades.Socio;


@Repository
public interface SocioRepository extends JpaRepository<Socio, String>  {

	@Query("SELECT u FROM Socio u WHERE u.nombre = :nombre AND u.apellido = :apellido")
	public List<Socio> buscarNombre(@Param("nombre") String nombre, @Param("apellido") String apellido);

	@Query("SELECT u FROM Socio u WHERE u.dni = :dni")
	public Optional <Socio> buscarDni(@Param("dni") String dni);

	@Query(value = "SELECT u FROM Socio u JOIN Socio_taller us JOIN Taller taller WHERE taller.nombre LIKE :nombre AND "
			+ "us.taller_id LIKE taller.id AND u.id LIKE us.usuario_id", nativeQuery = true)
	public List<Socio> buscarTaller(@Param("nombre") String nombre);

	
	
}
