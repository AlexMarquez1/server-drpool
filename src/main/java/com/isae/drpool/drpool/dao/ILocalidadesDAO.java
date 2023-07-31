package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isae.drpool.drpool.entity.Localidades;


public interface ILocalidadesDAO extends JpaRepository<Localidades,Integer>{
	
	@Query(value="SELECT * FROM tbl_localidades WHERE d_cp =:cp" , nativeQuery = true)
	List<Localidades> obtenerPorCP(@Param("cp") String cp);
	
	@Query(value="SELECT * FROM tbl_localidades WHERE d_cp LIKE :cp" , nativeQuery = true)
	List<Localidades> obtenerCP(@Param("cp") String cp);
	

}
