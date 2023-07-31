package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isae.drpool.drpool.entity.DatosAValidar;



public interface IDatosAValidarDAO extends JpaRepository<DatosAValidar, Integer> {
	
	//SELECT * from datosavalidar where datosavalidar.dato not in (SELECT vista_datos_match_valores.dato from vista_datos_match_valores)
	@Query(value ="SELECT * from datosavalidar where datosavalidar.dato not in (SELECT vista_datos_match_valores.dato from vista_datos_match_valores)", nativeQuery = true )
	List<DatosAValidar> obtenerDatosAValidarPendientes();
	
	@Query(value ="SELECT * FROM datosavalidar WHERE estatus = 'ASIGNADO'", nativeQuery = true )
	List<DatosAValidar> obtenerDatosAValidarAsignados();
	
	@Query(value ="SELECT COUNT(*) FROM datosavalidar WHERE tipodedato =:tipo", nativeQuery = true )
	Integer obtenerTotalDatosAvalidar(@Param("tipo") String tipoDeDato);

	@Query(value ="SELECT tipodedato FROM datosavalidar GROUP BY tipodedato", nativeQuery = true )
	List<String> obtenerTiposDeDatos();
}
