package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.VistaRegistroBusqueda;


@Repository
public interface IVistaRegistroBusquedaDAO extends JpaRepository<VistaRegistroBusqueda, Integer> {
	
	@Query(value="SELECT idinventario,folio,fechacreacionregistro, idproyecto, valor FROM vista_registro_busqueda WHERE valor = :busqueda AND idproyecto = :idProyecto" , nativeQuery = true)
	List<VistaRegistroBusqueda> datosBusqueda(@Param("busqueda") String busqueda, @Param("idProyecto") int idProyecto);
	
}
