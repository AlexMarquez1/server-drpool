package com.isae.drpool.drpool.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.VistaDatosISSSTE;

@Repository
public interface IVistaDatosISSSTE extends JpaRepository<VistaDatosISSSTE, Integer> {
	
	@Query(value="SELECT * FROM vista_datos_issste WHERE idinventario =:idinventario" , nativeQuery = true)
	List<VistaDatosISSSTE> obtenerDatosPorInventario(@Param("idinventario") int idInventario);
	
	@Query("SELECT v FROM VistaDatosISSSTE v where idinventario in :ids")
	List<VistaDatosISSSTE> obtenerDatosPorIdInventario (@Param("ids") List<Integer> ids);
}
