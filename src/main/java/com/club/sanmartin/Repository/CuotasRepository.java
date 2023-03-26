package com.club.sanmartin.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.club.sanmartin.Entidades.Cuotas;
import com.club.sanmartin.Entidades.Socio;
import com.club.sanmartin.Entidades.Taller;

@Repository
public interface CuotasRepository extends JpaRepository<Cuotas, String>{
	@Query("SELECT cuo FROM Cuotas cuo WHERE cuo.socio LIKE :socio")

    public List<Cuotas> SearchByUser(@Param("socio") Optional<Socio> socio);

    @Query(value = "SELECT cuo FROM Cuotas cuo JOIN taller_Cuotas oo JOIN taller t"
            + "WHERE t.nombre LIKE: nombre AND cuo.id LIKE: oo.Cuotas_id ORDER BY oo.Cuotas_nombre"
            + "t.id LIKE oo.taller_id", nativeQuery = true)
    public List<Cuotas> SearchByTaller(@Param("Taller") Optional<Taller> taller);

    @Query(value = "SELECT cuo FROM Cuotas cuo WHERE cuo. fecha LIKE : fecha", nativeQuery = true)
    public List<Cuotas> SearchByDate(@Param(" fecha") Date fecha);

    @Query(value = "SELECT cuo FROM Cuotas cuo  ORDER BY cuo.fecha ASC", nativeQuery = true)
    public List<Cuotas> OrderByDateAsc(@Param(" fecha") Date fecha);

    @Query(value = "SELECT cuo FROM Cuotas cuo  ORDER BY cuo.fecha DESC", nativeQuery = true)
    public List<Cuotas> OrderByDateDesc(@Param(" fecha") Date fecha);
    
}


