package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Equipocalentamiento;

@Repository
public interface IEquipocalentamientoDAO extends JpaRepository<Equipocalentamiento, Integer> {
	
	@Query(value = "SELECT * FROM equipocalentamiento", nativeQuery = true)
	List<Equipocalentamiento> obtenerEquipo();

	@Query(value = "SELECT * FROM equipocalentamiento WHERE idalberca = :idalberca",nativeQuery = true)
	List<Equipocalentamiento> obtenerEquipoCalentamientoPorAlberca (@Param("idalberca") int idalberca);
}
