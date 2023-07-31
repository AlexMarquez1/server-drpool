package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Pendiente;

@Repository
public interface IPendienteDAO extends JpaRepository<Pendiente,Integer>{

	@Modifying
    @Transactional
	@Query(value= "DELETE FROM pendiente WHERE idinventario = :idinventario", nativeQuery = true)
	void eliminarInventario(@Param("idinventario") int idinventario);
	
	@Query(value= "SELECT * FROM pendiente WHERE idinventario = :idinventario", nativeQuery = true)
	List<Pendiente> obtenerActual(@Param("idinventario") int idinventario);
}
