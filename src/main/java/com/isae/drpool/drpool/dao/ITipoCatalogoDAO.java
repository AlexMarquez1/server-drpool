package com.isae.drpool.drpool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Tipocatalogo;


@Repository
public interface ITipoCatalogoDAO extends JpaRepository<Tipocatalogo,Integer>{
	
	@Query(value="SELECT * FROM tipocatalogo WHERE tipo = :dato", nativeQuery = true )
	Tipocatalogo obtenerCatalogoPorDescripcion(@Param("dato") String tipo);

}
