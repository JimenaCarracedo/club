package com.sanmartin.club.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sanmartin.club.Entidades.Cuotas;
import com.sanmartin.club.Entidades.Socio;
import com.sanmartin.club.Entidades.Taller;

@Repository
public interface CuotasRepository extends JpaRepository<Cuotas, String>{
	@Query("SELECT cuo FROM Cuotas cuo WHERE cuo.socio LIKE :socio")

    public List<Cuotas> SearchByUser(@Param("socio") Socio socio);

    @Query(value = "SELECT cuo FROM Cuotas cuo JOIN taller_Cuotas oo JOIN taller t"
            + "WHERE t.nombre LIKE: nombre AND cuo.id LIKE: oo.Cuotas_id ORDER BY oo.Cuotas_nombre"
            + "t.id LIKE oo.taller_id", nativeQuery = true)
    public List<Cuotas> SearchByTaller(@Param("Taller") Taller taller);

    @Query(value = "SELECT cuo FROM Cuotas cuo WHERE cuo. fechaOrden LIKE : fechaOrden", nativeQuery = true)
    public List<Cuotas> SearchByDate(@Param(" fechaOrden") Date fechaOrden);

    @Query(value = "SELECT cuo FROM Cuotas cuo  ORDER BY cuo.fechaOrden ASC", nativeQuery = true)
    public List<Cuotas> OrderByDateAsc(@Param(" fechaOrden") Date fechaOrden);

    @Query(value = "SELECT cuo FROM Cuotas cuo  ORDER BY cuo.fechaOrden DESC", nativeQuery = true)
    public List<Cuotas> OrderByDateDesc(@Param(" fechaOrden") Date fechaOrden);
    
}


