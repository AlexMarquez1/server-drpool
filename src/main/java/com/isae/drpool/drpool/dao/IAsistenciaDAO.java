package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isae.drpool.drpool.entity.Asistencia;



public interface IAsistenciaDAO extends JpaRepository<Asistencia, Integer>{
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM asistencia WHERE idusuario = :idusuario", nativeQuery = true)
	void eliminarPoridUsuario(@Param("idusuario") int idusuario);
	
	@Query(value="SELECT idasistencia FROM asistencia WHERE idusuario = :idusuario" , nativeQuery = true)
	List<Integer> obtenerIdPorIdUsuario(@Param("idusuario") int idusuario);

}
