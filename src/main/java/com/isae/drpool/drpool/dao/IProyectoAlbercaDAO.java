package com.isae.drpool.drpool.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.ProyectoAlberca;

@Repository
public interface IProyectoAlbercaDAO extends JpaRepository<ProyectoAlberca, Integer>{
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM proyectosede WHERE idproyecto = :idproyecto", nativeQuery = true)
	void eliminarSedePorProyecto(@Param("idproyecto") int idproyecto);

	boolean existsByNombreproyectoalberca(String nombreproyectoalberca);

}
