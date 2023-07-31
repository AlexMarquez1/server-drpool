package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Proyecto;



@Repository
public interface ICamposProyectoDAO extends JpaRepository<Camposproyecto, Integer>{

	@Query(value= "SELECT * FROM camposproyecto WHERE idproyecto = :idProyecto AND tipocampo IN ('CATALOGO', 'CATALOGO-INPUT', 'FOTO' )", nativeQuery = true)
	List<Camposproyecto> obtenerCatalogoCampo(@Param("idProyecto") int idProyecto);
	
	@Query(value= "SELECT * FROM camposproyecto WHERE idproyecto = :idProyecto ORDER BY idcamposproyecto ASC", nativeQuery = true)
	List<Camposproyecto> obtenerCatalogoCampoPorProyecto(@Param("idProyecto") int idProyecto);
	
	@Query(value= "SELECT c FROM Camposproyecto c WHERE c.proyecto IN :proyectos ORDER BY c.idcamposproyecto ASC")
	List<Camposproyecto> obtenerCatalogoCampoPorProyecto(@Param("proyectos") List<Proyecto> proyectos);
	
	@Query(value= "SELECT * FROM camposproyecto WHERE idproyecto = :idProyecto AND tipocampo = 'FIRMA'", nativeQuery = true)
	List<Camposproyecto> obtenerFirmasPorProyecto(@Param("idProyecto") int idProyecto);
	
	@Query(value= "SELECT * FROM camposproyecto WHERE idproyecto = :idProyecto AND tipocampo = 'FOTO'", nativeQuery = true)
	List<Camposproyecto> obtenerFotoPorProyecto(@Param("idProyecto") int idProyecto);
	
	@Query(value= "SELECT * FROM camposproyecto WHERE idproyecto = :idProyecto AND tipocampo = 'CHECKBOX'", nativeQuery = true)
	List<Camposproyecto> obtenerCheckBoxPorProyecto(@Param("idProyecto") int idProyecto);
	
	@Query(value= "SELECT * FROM camposproyecto WHERE idproyecto = :idProyecto AND tipocampo IN ('CHECKBOX-EVIDENCIA', 'FOTO')", nativeQuery = true)
	List<Camposproyecto> obtenerCheckBoxEvidenciaPorProyecto(@Param("idProyecto") int idProyecto);
	
	@Query(value= "SELECT * FROM camposproyecto WHERE idproyecto = :idProyecto AND tipocampo = 'CHECKBOX-EVIDENCIA'", nativeQuery = true)
	List<Camposproyecto> obtenerSoloCheckBoxEvidenciaPorProyecto(@Param("idProyecto") int idProyecto);
	
	@Query(value= "SELECT * FROM camposproyecto WHERE idproyecto = :idProyecto AND campo = :campo", nativeQuery = true)
	Camposproyecto obtenerCampoProyectoPorProyecto(@Param("idProyecto") int idProyecto, @Param("campo") String campo);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM camposproyecto WHERE idproyecto = :idproyecto", nativeQuery = true)
	void eliminarPoridProyecto(@Param("idproyecto") int idproyecto);
	
}
