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

import com.isae.drpool.drpool.entity.Fotosasistencia;


@Repository
public interface IFotoAsistenciaDAO extends JpaRepository<Fotosasistencia,Integer>{

	@Query(value="SELECT MAX(idinventario) FROM inventario" , nativeQuery = true)
	int obtenerUltimoId();
	
	@Query(value="SELECT MAX(idfoto) FROM fotosasistencia" , nativeQuery = true)
	int obtenerUltimoIdFoto();
	
	@Procedure(procedureName = "proc_registrar_entrada" )
	void registrarEntrada(@Param("usuario") int usuario, @Param("archivo") int archivo);
	
	@Procedure(procedureName = "proc_registrar_salida" )
	void registrarSalida(@Param("usuario") int usuario, @Param("archivo") int archivo);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value="INSERT INTO fotosasistencia(idfoto, nombrefoto, url, coordenadas, idusuario) VALUES (:idfoto, :nombrefoto, :url, :coordenadas, :idusuario)" , nativeQuery = true)
	int insertarFoto(@Param("idfoto") int idfoto, @Param("nombrefoto") String nombrefoto, @Param("url") String url, @Param("coordenadas") String coordenadas, @Param("idusuario") int idusuario);

	@Modifying
    @Transactional
	@Query(value= "DELETE FROM fotosasistencia WHERE idusuario = :idusuario", nativeQuery = true)
	void eliminarPorIdUsuario(@Param("idusuario") int idusuario);

}
