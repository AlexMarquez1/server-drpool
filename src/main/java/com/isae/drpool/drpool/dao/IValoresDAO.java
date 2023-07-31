package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Usuario;
import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Valore;


@Repository
public interface IValoresDAO extends JpaRepository<Valore, Integer> {
	
	@Query(value="SELECT idvalor FROM valores WHERE idcampoproyecto = (SELECT MAX(idcampoproyecto) FROM valores)" , nativeQuery = true)
	int obtenerUltimoId();
	
	@Query(value="SELECT DISTINCT valor FROM vista_campos_busqueda WHERE idproyecto = :idproyecto" , nativeQuery = true)
	List<String> obtenerValorPorProyecto(@Param("idproyecto") int idproyecto);
	
	@Query(value="SELECT DISTINCT valor FROM vista_campos_busqueda WHERE idproyecto = :idproyecto AND campo = :campo" , nativeQuery = true)
	List<String> obtenerValorPorProyectoCampo(@Param("idproyecto") int idproyecto, @Param("campo") String campo);
	
	@Query(value= "SELECT valores.id, valores.idvalor, valores.valor, valores.idcampoproyecto, valores.idinventario FROM valores INNER JOIN camposproyecto ON camposproyecto.idcamposproyecto = idcampoproyecto INNER JOIN proyecto ON proyecto.idproyecto = camposproyecto.idproyecto WHERE valores.idinventario = :idinventario AND proyecto.idproyecto = :idProyecto", nativeQuery = true)
	List<Valore> obtenerDatosCampoPorProyecto(@Param("idinventario") int idinventario, @Param("idProyecto") int idProyecto);
	
	@Query(value= "SELECT valores.id, valores.idvalor, valores.valor, valores.idcampoproyecto, valores.idinventario FROM valores INNER JOIN camposproyecto ON camposproyecto.idcamposproyecto = idcampoproyecto INNER JOIN proyecto ON proyecto.idproyecto = camposproyecto.idproyecto WHERE proyecto.idproyecto = :idProyecto", nativeQuery = true)
	List<Valore> obtenerDatosCampoPorProyecto(@Param("idProyecto") int idProyecto);
	
	@Query("SELECT v FROM Valore v JOIN v.inventario i WHERE v.valor in :valores AND v.camposproyecto in :campos AND i.proyecto = :idproyecto AND v.inventario !=:inventario AND v.valor !='N/A' AND v.valor !='NA' AND v.valor !='-' AND v.valor !='' AND v.valor !=' '")
	List<Valore> obtenerDuplicidad(@Param("valores") List<String> valores, @Param("campos") List<Camposproyecto> camposProyecto, @Param("idproyecto") Proyecto idProyecto, @Param("inventario") Inventario ivnentario );
	
	@Modifying
	@Transactional
	@Query(value= "UPDATE valores SET valor= :valor WHERE idcampoproyecto = :idcampoproyecto AND idinventario = :idinventario", nativeQuery = true)
	void actualizarValores(@Param("valor") String valor, @Param("idcampoproyecto") int idcampoproyecto, @Param("idinventario") int idinventario );
	
	@Modifying
	@Transactional
	@Query(value= "UPDATE valores SET idvalor= :valor WHERE idcampoproyecto = :idcampoproyecto AND idinventario = :idinventario", nativeQuery = true)
	void actualizaridValorValores(@Param("valor") String valor, @Param("idcampoproyecto") int idcampoproyecto, @Param("idinventario") int idinventario );
	
	@Query(value= "SSELECT idvalor FROM valores WHERE idinventario = :idinventario AND idcampoproyecto = :idcampoproyecto", nativeQuery = true)
	String obtenerIdValorValores(@Param("idcampoproyecto") int idcampoproyecto, @Param("idinventario") int idinventario);
	
	@Query(value= "SELECT valor FROM valores WHERE idcampoproyecto = :idcampoproyecto AND idinventario = :idinventario", nativeQuery = true)
	String obtenerValorCampoPorIdCampo(@Param("idcampoproyecto") int idcampoproyecto, @Param("idinventario") int idinventario);
	
	@Query(value= "SELECT COUNT(valor) FROM valores INNER JOIN inventario ON inventario.idinventario = valores.idinventario WHERE inventario.idproyecto = :idproyecto AND idcampoproyecto = 2144 AND inventario.estatus = 'CERRADO' AND valores.valor =:dato", nativeQuery = true)
	String obtenerConteoEstadosANAM(@Param("idproyecto") int idproyecto, @Param("dato") String dato);
	
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM valores WHERE idinventario = :idinventario", nativeQuery = true)
	void eliminarPoridInventario(@Param("idinventario") int idinventario);
	
	
	@Query(value = "SELECT inventario.idinventario, inventario.folio, valores.valor, COUNT(valores.valor) AS 'repetidos' FROM valores INNER JOIN inventario ON inventario.idinventario = valores.idinventario INNER JOIN camposproyecto ON camposproyecto.idcamposproyecto = valores.idcampoproyecto WHERE inventario.idproyecto =:idproyecto AND camposproyecto.validarduplicidad = 'TRUE' AND valores.valor !='N/A' AND valores.valor != 'NA' AND valores.valor != '' AND valores.valor !='-' AND valores.valor NOT LIKE '%-%' AND valores.valor != '0' GROUP BY valores.valor HAVING repetidos >1", nativeQuery = true)
	List<Object> obtenerDuplicadosPorProyecto(@Param("idproyecto") int idProyecto);
	
	@Query(value = "SELECT valores.id, valores.idvalor, valores.valor, valores.idcampoproyecto, valores.idinventario, valores.fechasistema FROM valores INNER JOIN inventario ON inventario.idinventario = valores.idinventario WHERE valor =:dato AND inventario.idproyecto =:idproyecto AND idcampoproyecto IN (SELECT camposproyecto.idcamposproyecto FROM camposproyecto WHERE idproyecto = :idproyecto AND validarduplicidad = 'TRUE')", nativeQuery = true)
	List<Valore> obtenerValoresDuplicadosPorProyecto(@Param("dato") String dato, @Param("idproyecto") int idproyecto);
	
	
	@Query(value = "SELECT inventario.idinventario, inventario.folio, inventario.idproyecto, inventario.fechacreacion, inventario.estatus FROM valores INNER JOIN inventario ON inventario.idinventario = valores.idinventario WHERE valor LIKE CONCAT('%',:dato, '%') AND valores.idcampoproyecto =:idcampoproyecto AND valores.idinventario IN (SELECT idinventario FROM asignacionregistro WHERE idusuario =:idusuario )", nativeQuery = true)
//	@Query(value = "SELECT * FROM valores WHERE valor LIKE CONCAT('%',:dato, '%') AND valores.idcampoproyecto =:idcampoproyecto", nativeQuery = true)
	List<Object> obtenerInventarioPorValorCampo(@Param("dato") String dato, @Param("idcampoproyecto") int idcampoproyecto, @Param("idusuario") int idusuario);
	
	@Query(value = "SELECT valor FROM valores INNER JOIN inventario ON inventario.idinventario = valores.idinventario WHERE inventario.idproyecto = :idproyecto AND idcampoproyecto = :idcampoproyecto AND inventario.idinventario IN (SELECT idinventario FROM asignacionregistro WHERE idusuario =:idusuario)", nativeQuery = true)
	List<String> obtenerValoresPorCampoProyecto(@Param("idproyecto") int idproyecto, @Param("idcampoproyecto") int idcampoproyecto, @Param("idusuario") int idusuario);
	
	@Query(value = "SELECT valor FROM valores INNER JOIN inventario ON inventario.idinventario = valores.idinventario WHERE inventario.idproyecto = :idproyecto AND idcampoproyecto = :idcampoproyecto", nativeQuery = true)
	List<String> obtenerValoresPorCampoProyecto(@Param("idproyecto") int idproyecto, @Param("idcampoproyecto") int idcampoproyecto);
	
	@Query(value = "SELECT v FROM Valore v INNER JOIN v.inventario i WHERE i.proyecto=:proyecto AND v.camposproyecto =:campo AND v.inventario IN ( SELECT a.inventario FROM Asignacionregistro a WHERE a.usuario IN :usuarios)")
	List<Valore> obtenerValoresPorCampoProyecto(@Param("proyecto") Proyecto proyecto, @Param("campo") Camposproyecto campo, @Param("usuarios") List<Usuario> usuarios);
	
	@Query(value = "SELECT inventario.idinventario, inventario.folio, proyecto.idproyecto, inventario.fechacreacion, inventario.estatus FROM valores INNER JOIN inventario ON inventario.idinventario = valores.idinventario INNER JOIN proyecto ON inventario.idproyecto = proyecto.idproyecto WHERE inventario.idproyecto = :idproyecto AND idcampoproyecto = :idcampoproyecto AND valores.valor LIKE CONCAT('%',:dato,'%')  AND inventario.idinventario IN (SELECT idinventario FROM asignacionregistro WHERE idusuario =:idusuario)", nativeQuery = true)
	List<Object> obtenerRespuestaBusquedaProyecto(@Param("idproyecto") int idproyecto, @Param("idcampoproyecto") int idcampoproyecto, @Param("dato") String dato, @Param("idusuario") int idusuario);
	
	@Query(value = "SELECT inventario.idinventario, inventario.folio, proyecto.idproyecto, inventario.fechacreacion, inventario.estatus FROM valores INNER JOIN inventario ON inventario.idinventario = valores.idinventario INNER JOIN proyecto ON inventario.idproyecto = proyecto.idproyecto WHERE inventario.idproyecto = :idproyecto AND idcampoproyecto = :idcampoproyecto AND valores.valor LIKE CONCAT('%',:dato,'%')", nativeQuery = true)
	List<Object> obtenerRespuestaBusquedaProyecto(@Param("idproyecto") int idproyecto, @Param("idcampoproyecto") int idcampoproyecto, @Param("dato") String dato);
	
	@Transactional
	@Modifying
	@Query(value = "CALL proc_registrar_historico(:idcamp,:idus,:idinvent,:valorn)", nativeQuery = true )
	void actualizarValorEHistorial(@Param("idcamp") int idcampo, @Param("idus") int idusuario, @Param("idinvent") int idinventario, @Param("valorn") String valor);
	@Transactional
	@Modifying
	@Query(value = "CALL proc_registrar_historico_sin_actualizar_valor(:idcamp,:idus,:idinvent,:valorn,:valorant)", nativeQuery = true )
	void actualizarHistorial(@Param("idcamp") int idcampo, @Param("idus") int idusuario, @Param("idinvent") int idinventario, @Param("valorn") String valor, @Param("valorant")String valorant);
	
	
}
