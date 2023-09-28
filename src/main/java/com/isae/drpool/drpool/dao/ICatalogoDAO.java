package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Catalogo;


@Repository
public interface ICatalogoDAO extends JpaRepository<Catalogo,Integer>{
	
	@Procedure(procedureName = "proc_registrar_catalogo_relacionado" )
	void registrarCatalogoRelacionado(@Param("intipopadre") String intipopadre, @Param("incatalogopadre") String incatalogopadre, @Param("intipohijo") String intipohijo, @Param("incatalogohijo") String incatalogohijo, @Param("inidproyecto") int inidproyecto );
	
	@Procedure(procedureName = "proc_eliminar_catalogos" )
	void eliminarCatalogos(@Param("intipocatalogo") String intipocatalogo, @Param("inidproyecto") int inidproyecto);

	@Modifying
    @Transactional
	@Query(value="DELETE FROM catalogorelacionado WHERE catalogopadre = (SELECT idcatalogo FROM catalogo WHERE catalogo = :catalogoPadre AND idproyecto = :idProyecto AND idtipo = (SELECT idtipo FROM tipocatalogo WHERE tipo = :tipoCatalogoPadre))", nativeQuery = true )
	void eliminarCatalogoRelacionado(@Param("catalogoPadre") String catalogoPadre, @Param("idProyecto") int idProyecto, @Param("tipoCatalogoPadre") String tipoCatalogoPadre);
	
	@Query(value = "SELECT * FROM catalogo INNER JOIN tipocatalogo ON tipocatalogo.idtipo = catalogo.idtipo WHERE tipocatalogo.tipo = :tipo AND idproyecto = :idproyecto", nativeQuery = true)
	List<Catalogo> obtenerDatosCatalogoProyecto(@Param("tipo") String tipo, @Param("idproyecto") int idproyecto);
	
	@Query(value = "SELECT * FROM catalogo INNER JOIN tipocatalogo ON tipocatalogo.idtipo = catalogo.idtipo WHERE tipocatalogo.tipo = :tipo AND idproyecto = :idproyecto AND idusuario IS NOT null", nativeQuery = true)
	List<Catalogo> obtenerDatosCatalogoProyectoSinUsuario(@Param("tipo") String tipo, @Param("idproyecto") int idproyecto);
	
	@Query(value = "SELECT * FROM catalogo INNER JOIN tipocatalogo ON tipocatalogo.idtipo = catalogo.idtipo WHERE tipocatalogo.tipo = :tipo AND idproyecto = :idproyecto AND idusuario=:idusuario", nativeQuery = true)
	List<Catalogo> obtenerDatosCatalogoProyectoUsuario(@Param("tipo") String tipo, @Param("idproyecto") int idproyecto, @Param("idusuario") int idusuario );
	
	@Query(value = "SELECT * FROM catalogo INNER JOIN tipocatalogo ON tipocatalogo.idtipo = catalogo.idtipo WHERE tipocatalogo.tipo = :tipo AND idproyecto = :idproyecto", nativeQuery = true)
	List<Catalogo> obtenerDatosCatalogoProyectoUsuario(@Param("tipo") String tipo, @Param("idproyecto") int idproyecto);
	
	@Query(value = "SELECT * FROM catalogo INNER JOIN tipocatalogo ON tipocatalogo.idtipo = catalogo.idtipo WHERE tipocatalogo.tipo = :tipo AND idproyecto = :idproyecto AND idusuario IS NULL", nativeQuery = true)
	List<Catalogo> obtenerDatosCatalogoProyectoUsuarioNulo(@Param("tipo") String tipo, @Param("idproyecto") int idproyecto );
	
	@Query(value = "SELECT * FROM catalogo INNER JOIN tipocatalogo ON tipocatalogo.idtipo = catalogo.idtipo WHERE tipocatalogo.tipo = :tipo AND idproyecto = :idproyecto AND idusuario=:idusuario AND catalogo.catalogo =:catalogo", nativeQuery = true)
	List<Catalogo> obtenerDatosCatalogoProyectoUsuario(@Param("tipo") String tipo, @Param("idproyecto") int idproyecto, @Param("idusuario") int idusuario, @Param("catalogo") String catalogo);
	
	@Query(value = "SELECT * FROM catalogo WHERE idproyecto = :idproyecto", nativeQuery = true)
	List<Catalogo> obtenerCatalogosPorProyecto(@Param("idproyecto") int idproyecto);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM catalogo WHERE idproyecto = :idproyecto", nativeQuery = true)
	void eliminarPorProyecto(@Param("idproyecto") int idproyecto);
	
	@Query(value="SELECT idcatalogo, catalogo, catalogo.idtipo, idproyecto FROM catalogo INNER JOIN tipocatalogo ON tipocatalogo.idtipo = catalogo.idtipo WHERE catalogo = :catalogo AND tipocatalogo.tipo = :tipo AND idproyecto = :idproyecto", nativeQuery = true)
	Catalogo obtenerCatalogoPorTipoProyecto(@Param("catalogo") String catalogo, @Param("tipo") String tipo, @Param("idproyecto") int idproyecto);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM catalogo WHERE idusuario = :idusuario", nativeQuery = true)
	void eliminarPoridUsuario(@Param("idusuario") int idusuario);
}
