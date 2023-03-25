package com.sanmartin.club.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sanmartin.club.Entidades.Taller;


@Repository
public interface TallerRepository extends JpaRepository<Taller, String> {

	@Query("SELECT t FROM Taller t WHERE t.nombre LIKE :nombre")
	public List<Taller> buscarTallerNombre(@Param("nombre") String nombre);

	@Query("SELECT t FROM Taller t WHERE t.codigo = :codigo")
	public List<Taller> buscarOSCodigo(@Param("codigo") Integer codigo);

	@Query("SELECT t FROM Taller t WHERE t.telefono = :telefono")
	public List<Taller> buscarOSTelefono(@Param("telefono") Integer telefono);

	@Query("SELECT t FROM Taller t WHERE t.direccion LIKE :direccion")
	public List<Taller> buscarOSDireccion(@Param("direccion") String direccion);

	@Query("SELECT t FROM Taller t WHERE t.arancel = :arancel")
	public List<Taller> buscarOSArancel(@Param("arancel") Double arancel);

	@Query("SELECT t FROM Taller t WHERE t.mail LIKE :mail")
	public List<Taller> buscarOSMail(@Param("mail") String mail);

	@Query(value = "SELECT t FROM Taller t JOIN Socio_taller uos JOIN Usuario u WHERE t.id LIKE uos.taller_id AND "
			+ "uos.socio_id LIKE u.id AND WHERE u.dni LIKE :dni", nativeQuery = true)
	public List<Taller> buscarOSUsuarioDni(@Param("dni") Integer dni);

}
