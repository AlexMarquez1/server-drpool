package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Usuario;



@Repository
public interface IInventarioDAO extends JpaRepository<Inventario,Integer>{

	@Query(value="SELECT folio FROM inventario WHERE folio= :folio AND idproyecto= :idProyecto" , nativeQuery = true)
	List<String> comprobarFolio(@Param("folio") String folio, @Param("idProyecto") int idProyecto);
	
	@Query(value="SELECT MAX(idinventario) FROM inventario" , nativeQuery = true)
	int obtenerUltimoId();
	
	@Query(value="SELECT * FROM inventario WHERE idproyecto = :idProyecto" , nativeQuery = true)
	List<Inventario> obtenerPorIdProyecto(@Param("idProyecto") int idProyecto);

	@Query("SELECT i FROM Inventario i where folio in :folios")
	List<Inventario> obtenerFoliosRegistrados(@Param("folios") List<String> folios);
	
	
	@Query(value = "CALL proc_registrar_asignar_registro(:inidusuario,:inidproyecto)", nativeQuery = true )
	List<Inventario> registrarAsignarRegistro(@Param("inidusuario") int inidusuario, @Param("inidproyecto") int inidproyecto);
	
	@Modifying
	@Transactional
	@Query(value= "UPDATE inventario SET estatus = :estatus WHERE idinventario = :idinventario", nativeQuery = true)
	void cambiarEstatus(@Param("estatus") String estatus, @Param("idinventario") int idinventario );
	
	@Modifying
	@Transactional
	@Query(value= "UPDATE inventario SET folio = :folio WHERE idinventario = :idinventario", nativeQuery = true)
	void cambiarFolio(@Param("folio") String estatus, @Param("idinventario") int idinventario );
	
	@Query(value="SELECT * FROM inventario WHERE idinventario =:idinventario", nativeQuery = true)
	Inventario obtenerPorIdInventario(@Param("idinventario") int idinventario);
	
	//Datos Dashboard
	
	@Query(value="SELECT p.proyecto,COUNT(inv.idinventario) as total, p.idproyecto from inventario as inv INNER JOIN proyecto as p ON p.idproyecto = inv.idproyecto group BY p.proyecto, p.idproyecto", nativeQuery= true)
	List<Object> obtenerTotalRegistrosProyectos();
	
	@Query(value="SELECT estatus, COUNT(estatus) FROM `inventario` WHERE idproyecto =:idproyecto GROUP BY estatus", nativeQuery= true)
	List<Object> obtenerTotalEstatusProyecto(@Param("idproyecto") int idproyecto);
	
	@Query(value="SELECT i.estatus, COUNT(i.estatus) AS total FROM Asignacionregistro a INNER JOIN a.inventario i WHERE i.proyecto =:proyecto AND a.usuario =:usuario GROUP BY i.estatus ORDER BY total DESC")
	List<Object> obtenerTotalEstatusProyectoAsignados(@Param("proyecto") Proyecto idproyecto, @Param("usuario") Usuario idusuario);
	
	@Query(value="SELECT i.estatus, COUNT(i.estatus) AS total FROM Asignacionregistro a INNER JOIN a.inventario i WHERE i.proyecto =:proyecto GROUP BY i.estatus ORDER BY total DESC")
	List<Object> obtenerTotalEstatusProyecto(@Param("proyecto") Proyecto proyecto);
	
}
