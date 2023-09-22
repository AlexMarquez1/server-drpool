package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Fotoevidencia;
import com.isae.drpool.drpool.entity.Inventario;


@Repository
public interface IFotoEvidenciaDAO extends JpaRepository<Fotoevidencia,Integer>{

	@Query(value= "SELECT * FROM fotoevidencia WHERE nombrefoto = :nombrefoto AND idcampo = :idcampo AND idinventario = :idinventario AND idusuario = :idusuario", nativeQuery = true)
	List<Fotoevidencia> obtenerConcidencia(@Param("nombrefoto") String nombreFoto, @Param("idcampo") int idCampo, @Param("idinventario") int idInventario, @Param("idusuario") int idUsuario);
	
	@Query(value= "SELECT * FROM fotoevidencia WHERE idcampo = :idcampo AND idusuario = :idusuario AND idinventario = :idinventario", nativeQuery = true)
	List<Fotoevidencia> obtenerEvidencia(@Param("idcampo") int idcampo, @Param("idusuario") int idusuario, @Param("idinventario") int idinventario);
	
	@Query(value= "SELECT * FROM fotoevidencia WHERE idinventario = :idinventario", nativeQuery = true)
	List<Fotoevidencia> obtenerFotoPorInventario(@Param("idinventario") int idInventario);
	
	@Query(value= "SELECT * FROM fotoevidencia WHERE idinventario = :idinventario and idcampo = :idcampo", nativeQuery = true)
	List<Fotoevidencia> obtenerFotoPorInventarioCampo(@Param("idinventario") int idInventario, @Param("idcampo") int idcampo);
	
	@Query(value= "SELECT * FROM fotoevidencia WHERE nombrefoto = :nombrefoto AND idcampo = :idcampo AND idinventario = :idinventario", nativeQuery = true)
	List<Fotoevidencia> obtenerConcidencia(@Param("nombrefoto") String nombreFoto, @Param("idcampo") int idcampo, @Param("idinventario") int idinventario);

	
	@Query(value="SELECT f FROM Fotoevidencia f INNER JOIN f.campoProyecto c WHERE f.inventario IN :inventario AND c.campo like %:actividad%")
	List<Fotoevidencia> obtenerImagenes(@Param("inventario") List<Inventario> inventario, @Param("actividad") String actividad);
	
	//SELECT fotoevidencia.* FROM `fotoevidencia` inner join camposproyecto on fotoevidencia.idcampo = camposproyecto.idcamposproyecto 
	//WHERE idinventario IN (SELECT valores.idinventario FROM `valores`  WHERE idinventario = 153764 ) AND camposproyecto.campo LIKE '%LIMPIEZA DE TRAMPAS%'

	@Modifying
    @Transactional
	@Query(value= "DELETE FROM fotoevidencia WHERE idinventario = :idinventario", nativeQuery = true)
	void eliminarPoridInventario(@Param("idinventario") int idinventario);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM fotoevidencia WHERE idinventario = :idinventario AND idcampo =:idcampo", nativeQuery = true)
	void eliminarPoridInventarioIdCampo(@Param("idinventario") int idinventario, @Param("idcampo") int idcampo);
	
	@Query(value= "SELECT idfoto, nombrefoto, idcampo, url, coordenadas, idusuario, fotoevidencia.idinventario FROM fotoevidencia INNER JOIN inventario ON fotoevidencia.idinventario = inventario.idinventario INNER JOIN proyecto ON inventario.idproyecto = proyecto.idproyecto WHERE inventario.idproyecto = :idproyecto AND fotoevidencia.idinventario = :idinventario AND fotoevidencia.idusuario = :idusuario", nativeQuery = true)
	List<Fotoevidencia> obtenerFitroProyectoInventarioUsuario(@Param("idproyecto") int idproyecto, @Param("idinventario") int idinventario, @Param("idusuario") int idusuario);
	
	@Query(value= "SELECT idfoto, nombrefoto, idcampo, url, coordenadas, idusuario, fotoevidencia.idinventario FROM fotoevidencia INNER JOIN inventario ON fotoevidencia.idinventario = inventario.idinventario INNER JOIN proyecto ON inventario.idproyecto = proyecto.idproyecto WHERE inventario.idproyecto = :idproyecto", nativeQuery = true)
	List<Fotoevidencia> obtenerFitroProyecto(@Param("idproyecto") int idproyecto);
	
	@Query(value= "SELECT idfoto, nombrefoto, idcampo, url, coordenadas, idusuario, fotoevidencia.idinventario FROM fotoevidencia INNER JOIN inventario ON fotoevidencia.idinventario = inventario.idinventario INNER JOIN proyecto ON inventario.idproyecto = proyecto.idproyecto WHERE fotoevidencia.idinventario = :idinventario", nativeQuery = true)
	List<Fotoevidencia> obtenerFitroInventario(@Param("idinventario") int idinventario);
	
	@Query(value= "SELECT idfoto, nombrefoto, idcampo, url, coordenadas, idusuario, fotoevidencia.idinventario FROM fotoevidencia INNER JOIN inventario ON fotoevidencia.idinventario = inventario.idinventario INNER JOIN proyecto ON inventario.idproyecto = proyecto.idproyecto WHERE fotoevidencia.idusuario = :idusuario", nativeQuery = true)
	List<Fotoevidencia> obtenerFitroUsuario(@Param("idusuario") int idusuario);
	
	@Query(value= "SELECT idfoto, nombrefoto, idcampo, url, coordenadas, idusuario, fotoevidencia.idinventario FROM fotoevidencia INNER JOIN inventario ON fotoevidencia.idinventario = inventario.idinventario INNER JOIN proyecto ON inventario.idproyecto = proyecto.idproyecto WHERE inventario.idproyecto = :idproyecto AND fotoevidencia.idinventario = :idinventario", nativeQuery = true)
	List<Fotoevidencia> obtenerFitroProyectoInventario(@Param("idproyecto") int idproyecto, @Param("idinventario") int idinventario);
	
	@Query(value= "SELECT idfoto, nombrefoto, idcampo, url, coordenadas, idusuario, fotoevidencia.idinventario FROM fotoevidencia INNER JOIN inventario ON fotoevidencia.idinventario = inventario.idinventario INNER JOIN proyecto ON inventario.idproyecto = proyecto.idproyecto WHERE inventario.idproyecto = :idproyecto AND fotoevidencia.idusuario = :idusuario", nativeQuery = true)
	List<Fotoevidencia> obtenerFitroProyectoUsuario(@Param("idproyecto") int idproyecto, @Param("idusuario") int idusuario);
	
	@Query(value= "SELECT idfoto, nombrefoto, idcampo, url, coordenadas, idusuario, fotoevidencia.idinventario FROM fotoevidencia INNER JOIN inventario ON fotoevidencia.idinventario = inventario.idinventario INNER JOIN proyecto ON inventario.idproyecto = proyecto.idproyecto WHERE fotoevidencia.idinventario = :idinventario AND fotoevidencia.idusuario = :idusuario", nativeQuery = true)
	List<Fotoevidencia> obtenerFitroInventarioUsuario(@Param("idinventario") int idinventario, @Param("idusuario") int idusuario);
	
	@Query(value= "SELECT f FROM Fotoevidencia f WHERE idinventario in :listainventarios ")
	List<Fotoevidencia> obtenerfotoPoridInventario(@Param("listainventarios")  List<Integer> listainventarios);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM fotoevidencia WHERE idinventario = :idinventario AND idusuario =:idusuario", nativeQuery = true)
	void eliminarPoridInventarioIdUsuario(@Param("idinventario") int idinventario, @Param("idusuario") int idUsuario);
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM fotoevidencia WHERE idusuario = :idusuario", nativeQuery = true)
	void eliminarPoridUsuario(@Param("idusuario") int idusuario);
}
