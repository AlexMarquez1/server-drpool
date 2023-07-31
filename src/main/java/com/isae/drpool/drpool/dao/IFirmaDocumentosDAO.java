package com.isae.drpool.drpool.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isae.drpool.drpool.entity.firmasdocumento;

public interface IFirmaDocumentosDAO extends JpaRepository<firmasdocumento,Integer>{
	
	@Query(value= "SELECT * FROM firmasdocumento WHERE idcampo = :idcampo AND idinventario = :idinventario", nativeQuery = true)
	List<firmasdocumento> obtenerFirmasDocumento(@Param("idcampo") int idcampo, @Param("idinventario") int idinventario);
	
	
	@Query(value= "SELECT * FROM firmasdocumento WHERE nombrefirma = :nombrefirma AND idcampo = :idcampo AND idinventario = :idinventario", nativeQuery = true)
	List<firmasdocumento> obtenerConcidencia(@Param("nombrefirma") String nombrefirma, @Param("idcampo") int idcampo, @Param("idinventario") int idinventario);
	
	@Query(value= "SELECT * FROM firmasdocumento WHERE idinventario = :idinventario", nativeQuery = true)
	List<firmasdocumento> obtenerFirmaPorInventario(@Param("idinventario") int idinventario);
	
	@Query(value= "SELECT * FROM firmasdocumento WHERE idinventario = :idinventario and idcampo = :idcampo", nativeQuery = true)
	List<firmasdocumento> obtenerFirmaPorInventarioCampo(@Param("idinventario") int idinventario, @Param("idcampo") int idcampo);
	
	@Modifying
    @Transactional
	@Query(value= "DELETE FROM firmasdocumento WHERE idinventario = :idinventario", nativeQuery = true)
	void eliminarPoridInventario(@Param("idinventario") int idinventario);
	
}
