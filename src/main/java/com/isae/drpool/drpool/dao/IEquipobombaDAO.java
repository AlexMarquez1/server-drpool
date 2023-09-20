package com.isae.drpool.drpool.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isae.drpool.drpool.entity.Equipobomba;

@Repository 
public interface IEquipobombaDAO extends JpaRepository<Equipobomba, Integer>{
	
	@Query(value = "SELECT * FROM equipobomba", nativeQuery = true)
	List<Equipobomba> obtenerEquipo();
	
	@Query(value = "SELECT * FROM equipobomba WHERE idalberca = :idalberca",nativeQuery = true)
	List<Equipobomba> obtenerEquipoBombaPorAlberca (@Param("idalberca") int idalberca);
	
}
