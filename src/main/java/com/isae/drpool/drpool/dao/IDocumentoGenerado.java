package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isae.drpool.drpool.entity.Documentogenerado;

public interface IDocumentoGenerado extends JpaRepository<Documentogenerado, Integer>{
	
	@Query(value= "SELECT * FROM documentogenerado WHERE idinventario = :idinventario", nativeQuery = true)
	List<Documentogenerado> obtenerDocumento(@Param("idinventario") int idinventario);
	
	@Query(value= "SELECT url FROM documentogenerado WHERE idinventario = :idinventario", nativeQuery = true)
	String obtenerUrlPorIdInventario(@Param("idinventario") int idinventario);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM documentogenerado WHERE idinventario = :idinventario", nativeQuery = true)
	void eliminarPoridInventario(@Param("idinventario") int idinventario);
	
	
	@Query("SELECT d FROM Documentogenerado d where idinventario in :idinventarios")
	List<Documentogenerado> obtenerDocumentosPorIds(@Param("idinventarios") List<Integer> idinventario);
}
