package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Equipofiltrado;

@Repository
public interface IEquipofiltradoDAO extends JpaRepository<Equipofiltrado, Integer>{

	@Query(value = "SELECT * FROM equipofiltrado WHERE idalberca = :idalberca",nativeQuery = true)
	List<Equipofiltrado> obtenerEquipoFiltradoPorAlberca (@Param("idalberca") int idalberca);
}
