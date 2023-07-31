package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.EstatusProyecto;

@Repository
public interface IEstatusProyecto extends JpaRepository<EstatusProyecto, Integer> {
	
	@Query(value ="SELECT * FROM estatusproyecto WHERE idproyecto =:idproyecto ORDER BY orden ASC", nativeQuery = true)
	List<EstatusProyecto> obtenerEstatusPorProyecto(@Param("idproyecto") int idproyecto);
	
}
