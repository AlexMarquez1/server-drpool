package com.isae.drpool.drpool.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Proyecto;



@Repository
public interface IProyectoDAO extends JpaRepository<Proyecto,Integer>{


	@Procedure(procedureName = "proc_registrar_proyecto" )
	void ingresarProyecto(@Param("proyecto") String nombreProyecto, @Param("tipoproyecto") String tipoProyecto);
	
	@Query(value="SELECT * FROM proyecto WHERE proyecto = :dato" , nativeQuery = true)
	Proyecto obtenerProyectoPorNombre(@Param("dato") String nombreProyecto);
	
	@Query(value="SELECT * FROM proyecto WHERE idproyecto =:id" , nativeQuery = true)
	Proyecto obtenerProyectoPorId(@Param("id") int id);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM proyecto WHERE idproyecto = :idproyecto", nativeQuery = true)
	void eliminarPoridProyecto(@Param("idproyecto") int idproyecto);
}
