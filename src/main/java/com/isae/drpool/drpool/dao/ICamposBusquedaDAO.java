package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.CamposBusqueda;



@Repository
public interface ICamposBusquedaDAO extends JpaRepository<CamposBusqueda, Integer>{
	

	@Query(value = "SELECT * FROM camposbusqueda cb INNER JOIN camposproyecto cp ON cb.idcamposproyecto = cp.idcamposproyecto WHERE cp.idproyecto = :idproyecto AND cb.idcamposproyecto =:campoproyecto OR cb.idagrupacion LIKE CONCAT(:idagrupacion,'%')", nativeQuery = true)
	List<CamposBusqueda> obtenerCamposBusquedaPorCampoProyecto(@Param("idproyecto") int idproyecto, @Param("campoproyecto") int campoproyecto, @Param("idagrupacion") String idagrupacion);
	
	@Query(value = "SELECT * FROM camposbusqueda cb INNER JOIN camposproyecto cp ON cb.idcamposproyecto = cp.idcamposproyecto WHERE cp.idproyecto = :idproyecto", nativeQuery = true)
	List<CamposBusqueda> obtenerCamposBusquedaPorProyecto(@Param("idproyecto") int idproyecto);
	
	
}
