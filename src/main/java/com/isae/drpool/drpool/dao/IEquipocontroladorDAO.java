package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Equipocontrolador;

@Repository
public interface IEquipocontroladorDAO extends JpaRepository<Equipocontrolador, Integer>{
	
	@Query(value = "SELECT * FROM equipocontrolador WHERE idalberca = :idalberca",nativeQuery = true)
	List<Equipocontrolador> obtenerEquipoControladorPorAlberca (@Param("idalberca") int idalberca);
}