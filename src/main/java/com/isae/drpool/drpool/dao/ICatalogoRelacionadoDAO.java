package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.VistaCatalogoRelacionado;


@Repository
public interface ICatalogoRelacionadoDAO extends JpaRepository<VistaCatalogoRelacionado,Integer>{

	@Query("SELECT NEW com.isae.web.entity.VistaCatalogoRelacionado(vista.id, vista.tipoPadre, vista.catalogoPadre, vista.tipoHijo, vista.catalogoHijo, vista.idproyecto) FROM VistaCatalogoRelacionado as vista WHERE vista.tipoHijo =?1 AND vista.catalogoPadre =?2 AND vista.idproyecto =?3")
	List<VistaCatalogoRelacionado> obtenerCatalogosRelacionados( String tipoHijo, String catalogoPadre, int idproyecto);
	
	@Query("SELECT NEW com.isae.web.entity.VistaCatalogoRelacionado(vista.id, vista.tipoPadre, vista.catalogoPadre, vista.tipoHijo, vista.catalogoHijo, vista.idproyecto) FROM VistaCatalogoRelacionado as vista WHERE vista.tipoPadre =?1 AND vista.catalogoPadre =?2 AND vista.idproyecto =?3 ")
	List<VistaCatalogoRelacionado> obtenerCatalogoRelacionado(String tipoCatalogoPadre, String catalogoPadre, int idproyecto);
	
	@Query("SELECT NEW com.isae.web.entity.VistaCatalogoRelacionado(vista.id, vista.tipoPadre, vista.catalogoPadre, vista.tipoHijo, vista.catalogoHijo, vista.idproyecto) FROM VistaCatalogoRelacionado as vista WHERE vista.idproyecto =?1")
	List<VistaCatalogoRelacionado> obtenerCatalogosRelacionadosPorProyecto(int idproyecto);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM catalogorelacionado WHERE catalogopadre = :catalogopadre OR catalogohijo = :catalogohijo", nativeQuery = true)
	void eliminarPorPadreOHijo(@Param("catalogopadre") int catalogopadre,@Param("catalogohijo") int catalogohijo);
	
}
