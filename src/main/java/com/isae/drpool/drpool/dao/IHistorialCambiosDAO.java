package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.HistorialCambios;
import com.isae.drpool.drpool.entity.Inventario;


@Repository
public interface IHistorialCambiosDAO extends JpaRepository<HistorialCambios, Integer>{
	
	@Query(value="SELECT h FROM HistorialCambios h WHERE h.inventario =:inventario")
	List<HistorialCambios> obtenerHistorialPorInventario(@Param("inventario") Inventario inventario);
}
