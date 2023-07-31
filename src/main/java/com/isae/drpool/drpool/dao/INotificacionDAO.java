package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Notificaciones;


@Repository
public interface INotificacionDAO extends JpaRepository<Notificaciones, Integer>{
	
	@Query(value="SELECT MAX(idnotificacion) FROM notificaciones" , nativeQuery = true)
	int obtenerUltimoId();
	
	@Query(value="SELECT * FROM notificaciones WHERE idnotificacion = :idnotificacion" , nativeQuery = true)
	Notificaciones obtenerPorId(@Param("idnotificacion") int idnotificacion);
	
	@Query(value="SELECT * FROM notificaciones WHERE idusuario = :idusuario AND atendido = 'FALSE' ORDER BY notificaciones.idnotificacion DESC" , nativeQuery = true)
	List<Notificaciones> obtenerNotificacionPorUsuario(@Param("idusuario") int idusuario);
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM notificaciones WHERE idusuario = :idusuario", nativeQuery = true)
	void eliminarPoridUsuario(@Param("idusuario") int idusuario);

}
