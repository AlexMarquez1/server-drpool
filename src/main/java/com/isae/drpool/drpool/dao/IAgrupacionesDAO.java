package com.isae.drpool.drpool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Agrupacion;



@Repository
public interface IAgrupacionesDAO extends JpaRepository<Agrupacion, Integer> {

	@Query(value="SELECT * FROM agrupacion WHERE agrupacion =  :dato", nativeQuery = true )
	Agrupacion obtenerAgrupacionPorAgrupacion(@Param("dato") String agrupacion);
}
