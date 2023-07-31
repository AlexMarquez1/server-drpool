package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.ImagenesNotificacion;


@Repository
public interface IImagenNotificacionDAO extends JpaRepository<ImagenesNotificacion, Integer>{

	@Query(value="SELECT * FROM imagenesnotificacion WHERE idnotificacion = :idnotificacion" , nativeQuery = true)
	List<ImagenesNotificacion> obtenerPorIdNotificacion(@Param("idnotificacion") int idnotificacion);

}
