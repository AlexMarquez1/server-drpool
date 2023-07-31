package com.isae.drpool.drpool.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="clienteaplicacion")
@NamedQuery(name="ClienteAplicacion.findAll", query="SELECT c FROM ClienteAplicacion c")
public class ClienteAplicacion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcliente;
	
	private String cliente;
	
	private String urllogo;

	public ClienteAplicacion() {
		super();
	}

	public ClienteAplicacion(int idcliente, String cliente, String urllogo) {
		super();
		this.idcliente = idcliente;
		this.cliente = cliente;
		this.urllogo = urllogo;
	}

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getUrllogo() {
		return urllogo;
	}

	public void setUrllogo(String urllogo) {
		this.urllogo = urllogo;
	}

	@Override
	public String toString() {
		return "ClienteAplicacion [idcliente=" + idcliente + ", cliente=" + cliente + ", urllogo=" + urllogo + "]";
	}
	
	

}
