package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Equipodosificador;

@Repository
public interface IEquipodosificadorDAO extends JpaRepository<Equipodosificador, Integer> {
	
	@Query(value = "SELECT * FROM equipodosificador WHERE idalberca = :idalberca",nativeQuery = true)
	List<Equipodosificador> obtenerEquipoDosificadorPorAlberca (@Param("idalberca") int idalberca);
}
