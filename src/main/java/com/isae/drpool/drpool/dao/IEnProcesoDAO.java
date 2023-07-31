package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.EnProceso;

@Repository
public interface IEnProcesoDAO extends JpaRepository<EnProceso,Integer>{
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM enproceso WHERE idinventario = :idinventario", nativeQuery = true)
	void eliminarInventario(@Param("idinventario") int idinventario);
	
	@Query(value= "SELECT * FROM enproceso WHERE idinventario = :idinventario", nativeQuery = true)
	List<EnProceso> obtenerActual(@Param("idinventario") int idinventario);

	
}
