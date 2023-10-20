package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Actividadimagenes;

@Repository
public interface IActividadimagenesDAO extends JpaRepository<Actividadimagenes, Integer>{
	
	@Query(value="SELECT * FROM actividadimagenes WHERE idactividades =:idactividades", nativeQuery = true)
	List<Actividadimagenes> getActividadImagenesID(@Param("idactividades") int idactividades);


}
