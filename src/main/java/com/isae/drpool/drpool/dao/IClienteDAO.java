package com.isae.drpool.drpool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Cliente;


@Repository
public interface IClienteDAO extends JpaRepository<Cliente, Integer> {
	
	@Query(value= "SELECT * FROM cliente WHERE idclienteaplicacion=:clienteapp", nativeQuery = true)
	List<Cliente> obtenerClientesPorUsuario(@Param("clienteapp") int clienteapp);
}
