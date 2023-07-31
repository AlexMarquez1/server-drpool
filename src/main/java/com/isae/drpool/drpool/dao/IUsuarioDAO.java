package com.isae.drpool.drpool.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Usuario;

@Repository
public interface IUsuarioDAO extends JpaRepository<Usuario ,Integer>{

	@Modifying
    @Transactional
	@Query(value= "DELETE FROM usuario WHERE usuario = :usuario", nativeQuery = true)
	void eliminarPorUsuario(@Param("usuario") String usuario);
	
	@Modifying
	@Transactional
	@Query(value= "UPDATE usuario SET pass = :password, passtemp = 0 WHERE usuario = :usuario", nativeQuery = true)
	void asignarPassword(@Param("password") String password, @Param("usuario") String usuario );
	
	@Query(value= "SELECT * FROM usuario WHERE usuario = :usuario AND pass = :pass", nativeQuery = true)
	List<Usuario> login(@Param("usuario") String usuario, @Param("pass") String pass);
	
	@Query(value= "SELECT * FROM usuario ORDER BY usuario.usuario ASC", nativeQuery = true)
	List<Usuario> listarUsuariosOrdenados();
	
	@Query(value= "SELECT * FROM usuario WHERE usuario = :usuario AND correo = :correo", nativeQuery = true)
	List<Usuario> obtenerUsuarioPorCorreo(@Param("usuario") String usuario, @Param("correo") String correo);
	
	@Query(value= "SELECT * FROM usuario WHERE nombre =:nombre", nativeQuery = true)
	Usuario obtenerUsuarioPorNombre(@Param("nombre") String nombre);
	
	@Query(value= "SELECT DISTINCT d_estado FROM tbl_localidades", nativeQuery = true)
	List<String> obtenerEstados();
	
	@Query(value= "SELECT * FROM usuario WHERE idusuario =:idusuario", nativeQuery = true)
	Usuario obtenerUsuarioPorId(@Param("idusuario") int idusuario);
	
	@Query(value= "SELECT * FROM usuario WHERE token != ''", nativeQuery = true)
	List<Usuario> obtenerUsuariosConToken();
}