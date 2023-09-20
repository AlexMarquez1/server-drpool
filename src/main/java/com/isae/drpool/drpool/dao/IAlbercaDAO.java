package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Alberca;

@Repository
public interface IAlbercaDAO extends JpaRepository<Alberca, Integer> {
	
	@Query(value = "SELECT * FROM alberca WHERE idsede = :idsede",nativeQuery = true)
	List<Alberca> obtenerAlbercaPorSede (@Param("idsede") int idsede);
	
}
