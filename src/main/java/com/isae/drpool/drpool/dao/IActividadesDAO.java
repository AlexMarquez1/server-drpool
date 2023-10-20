package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Actividades;

@Repository
public interface IActividadesDAO extends JpaRepository<Actividades, Integer>{
	
	@Query(value="SELECT * FROM actividades WHERE idreportemensual =:idreportemensual", nativeQuery = true)
	List<Actividades> getActividadesID(@Param("idreportemensual") int idreportemensual);

}
