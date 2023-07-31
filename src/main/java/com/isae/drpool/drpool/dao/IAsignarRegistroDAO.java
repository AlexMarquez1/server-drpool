package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Asignacionregistro;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Usuario;


@Repository
public interface IAsignarRegistroDAO extends JpaRepository<Asignacionregistro,Integer>{

	@Modifying
    @Transactional
	@Query(value= "DELETE FROM asignacionregistro WHERE idusuario = :idUsuario AND idinventario = :idinventario", nativeQuery = true)
	void eliminarAsignacionRegistro(@Param("idUsuario") int idUsuario, @Param("idinventario") int idinventario);
	
	@Query(value= "SELECT a.inventario FROM Asignacionregistro a WHERE a.usuario = :usuario" )
	List<Inventario> obtenerRegistrosAsignadosUsuario(@Param("usuario") Usuario usuario);
	
//	@Query(value= "SELECT inventario.idinventario, inventario.fechacreacion, inventario.folio, inventario.estatus, inventario.idproyecto FROM asignacionregistro INNER JOIN inventario ON inventario.idinventario = asignacionregistro.idinventario WHERE asignacionregistro.idusuario = :idusuario AND inventario.idproyecto = :idproyecto ORDER BY CAST(SUBSTRING_INDEX(folio, '-',1) AS UNSIGNED), CAST(SUBSTRING_INDEX(folio, '-',-1) AS UNSIGNED), inventario.folio, inventario.idinventario", nativeQuery = true)
	@Query(value= "SELECT i FROM Asignacionregistro a INNER JOIN a.inventario i WHERE a.usuario=:usuario AND i.proyecto=:proyecto ORDER BY i.folio, i.idinventario")
	List<Inventario> obtenerRegistrosAsignadosUsuarioProyecto(@Param("usuario") Usuario usaurio, @Param("proyecto") Proyecto proyecto);
	
	@Query(value= "SELECT i FROM Asignacionregistro a INNER JOIN a.inventario i WHERE a.usuario IN :usuario AND i.proyecto IN :proyecto ORDER BY i.folio, i.idinventario")
	List<Inventario> obtenerRegistrosAsignadosUsuarioProyecto(@Param("usuario")List<Usuario> usaurio, @Param("proyecto") List<Proyecto> proyecto);
	
	@Query(value="SELECT COUNT(*) FROM asignacionregistro INNER JOIN inventario ON inventario.idinventario = asignacionregistro.idinventario WHERE asignacionregistro.idusuario = :idusuario AND inventario.idproyecto = :idproyecto", nativeQuery = true)
	int obtenerUltimoIngresadoPorUsuario(@Param("idusuario") int idUsuario, @Param("idproyecto") int idProyecto);
	
	@Query(value= "SELECT DISTINCT i FROM Asignacionregistro a INNER JOIN a.inventario i WHERE i.proyecto =:proyecto")
	List<Inventario> obtenerRegistrosPorProyectos(@Param("proyecto") Proyecto proyecto);
	
	@Query(value= "SELECT u FROM Asignacionregistro a INNER JOIN a.inventario i INNER JOIN a.usuario u WHERE a.inventario =:inventario")
	Usuario obtenerUsuarioPorInventario(@Param("inventario") Inventario inventario);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM asignacionregistro WHERE idinventario = :idinventario", nativeQuery = true)
	void eliminarPoridInventario(@Param("idinventario") int idinventario);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM asignacionregistro WHERE idusuario = :idusuario", nativeQuery = true)
	void eliminarPoridUsuario(@Param("idusuario") int idusuario);
	
}
