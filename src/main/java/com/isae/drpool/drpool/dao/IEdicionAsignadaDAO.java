package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.EdicionAsignada;


@Repository
public interface IEdicionAsignadaDAO extends JpaRepository<EdicionAsignada,Integer>{

	@Modifying
    @Transactional
	@Query(value= "DELETE FROM edicionasignada WHERE idusuario = :idusuario AND idinventario = :idinventario", nativeQuery = true)
	void eliminarEdicionPorUsuario(@Param("idusuario") int idusuario, @Param("idinventario") int idinventario);
	
	@Query(value= "SELECT * FROM edicionasignada WHERE idusuario = :idusuario AND idinventario = :idinventario", nativeQuery = true)
	List<EdicionAsignada> obtenerCamposAsignados(@Param("idusuario") int idusuario, @Param("idinventario") int idinventario);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM edicionasignada WHERE idinventario = :idinventario", nativeQuery = true)
	void eliminarPorInventario(@Param("idinventario") int idinventario);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM edicionasignada WHERE idinventario = :idinventario AND idcamposproyecto =:idcamposproyecto AND idusuario =:idusuario", nativeQuery = true)
	void eliminarPorInventarioIdCampo(@Param("idinventario") int idinventario, @Param("idcamposproyecto") int idcamposproyecto, @Param("idusuario") int idusuario);
	

	@Modifying
    @Transactional
	@Query(value= "DELETE FROM edicionasignada WHERE idusuario = :idusuario", nativeQuery = true)
	void eliminarPoridUsuario(@Param("idusuario") int idusuario);


}
