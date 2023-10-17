package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Alberca;
import com.isae.drpool.drpool.entity.Sede;

@Repository
public interface ISedeDAO extends JpaRepository<Sede, Integer>{
	
	@Query(value = "SELECT * FROM sede WHERE idsede = :idsede",nativeQuery = true)
	List<Alberca> obtenerSedePorId (@Param("idsede") int idsede);

}
