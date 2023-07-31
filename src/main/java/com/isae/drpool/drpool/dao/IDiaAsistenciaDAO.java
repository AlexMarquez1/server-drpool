package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Diasasistencia;


@Repository
public interface IDiaAsistenciaDAO  extends JpaRepository<Diasasistencia,Integer>{

	
	@Query(value= "SELECT diasasistencia.iddias, diasasistencia.iddia, diasasistencia.idasistencia, diasasistencia.asistenciacompleta FROM diasasistencia INNER JOIN asistencia ON asistencia.idasistencia = diasasistencia.idasistencia INNER JOIN dias ON dias.iddias = diasasistencia.iddia WHERE idusuario = :idusuario AND dias.dia = CURRENT_DATE() AND asistenciacompleta = 0", nativeQuery = true)
	List<Diasasistencia> obtenerAsistenciaPorDia(@Param("idusuario") int idusuario);
	
	@Query(value= "SELECT DISTINCT usuario.idusuario FROM diasasistencia INNER JOIN dias ON dias.iddias = iddia INNER JOIN asistencia ON asistencia.idasistencia = diasasistencia.idasistencia INNER JOIN usuario ON usuario.idusuario = asistencia.idusuario WHERE dias.dia BETWEEN :fechaInicio AND :fechaFin", nativeQuery = true)
	List<Integer> obtenerUsuariosAsistencia(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);
	
	@Query(value= "SELECT diasasistencia.iddias, diasasistencia.iddia, diasasistencia.idasistencia, diasasistencia.asistenciacompleta FROM diasasistencia INNER JOIN asistencia ON asistencia.idasistencia = diasasistencia.idasistencia INNER JOIN dias ON dias.iddias = diasasistencia.iddia INNER JOIN usuario ON usuario.idusuario = asistencia.idusuario WHERE dias.dia BETWEEN :fechainicio AND :fechafin AND usuario.idusuario = :idusuario ORDER BY usuario.nombre ASC, dias.dia", nativeQuery = true)
	List<Diasasistencia> obtenerAsistenciaUsuario(@Param("idusuario") int idusuario, @Param("fechainicio") String fechaInicio, @Param("fechafin") String fechafin);
	
	@Query(value= "SELECT diasasistencia.iddias, diasasistencia.iddia, diasasistencia.idasistencia, diasasistencia.asistenciacompleta FROM diasasistencia INNER JOIN asistencia ON asistencia.idasistencia = diasasistencia.idasistencia INNER JOIN dias ON dias.iddias = diasasistencia.iddia INNER JOIN usuario ON usuario.idusuario = asistencia.idusuario WHERE dias.dia BETWEEN :fechaInicio AND :fechaFinal ORDER BY usuario.nombre ASC, dias.dia", nativeQuery = true)
	List<Diasasistencia> obtenerAsistencia(@Param("fechaInicio") String fechaInicio, @Param("fechaFinal") String fechaFinal);
	
	@Query(value= "SELECT diasasistencia.iddias, diasasistencia.iddia, diasasistencia.idasistencia, diasasistencia.asistenciacompleta FROM diasasistencia INNER JOIN asistencia ON asistencia.idasistencia = diasasistencia.idasistencia INNER JOIN dias ON dias.iddias = diasasistencia.iddia INNER JOIN usuario ON usuario.idusuario = asistencia.idusuario WHERE dias.dia BETWEEN :fechaInicio AND :fechaFinal ORDER BY usuario.nombre ASC, dias.dia", nativeQuery = true)
	List<Diasasistencia> obtenerAsistenciaReporte(@Param("fechaInicio") String fechaInicio, @Param("fechaFinal") String fechaFinal);
	
	
	@Query("SELECT d FROM Diasasistencia d where idasistencia in :idasistencia")
	List<Diasasistencia> obtenerIdDiasPorIdAsistencia(@Param("idasistencia") List<Integer> idsAsistencia);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM diasasistencia WHERE idusuario = :idusuario", nativeQuery = true)
	void eliminarPorIdUsuario(@Param("idusuario") int idusuario);
}
