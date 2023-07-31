package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Datosissste;

@Repository
public interface IDatosISSSTEDAO extends JpaRepository<Datosissste, Integer> {

	@Query(value= "SELECT * FROM datosissste WHERE nombreusuario = :nombre", nativeQuery = true)
	List<Datosissste> obtenerDatosPorNombreUsuario(@Param("nombre") String nombre);
	
	@Query(value= "SELECT nombreusuario FROM datosissste WHERE nombreusuario LIKE CONCAT('%', :nombreLowerCase, '%') OR CONCAT('%', :nombreCapitalization,'%') OR CONCAT('%', :nombreUpperCase,'%')", nativeQuery = true)
	List<String> obtenerNombresUsuario(@Param("nombreLowerCase") String nombreLowerCase, @Param("nombreCapitalization")String nombreCapitalization, @Param("nombreUpperCase")String nombreUpperCase);
	
	@Query(value= "SELECT * FROM datosissste WHERE numerousuario =:numero", nativeQuery = true)
	List<Datosissste> obtenerDatosPorNumeroUsuario(@Param("numero") int numero);
}
