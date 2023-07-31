package com.isae.drpool.drpool.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="localidad")
@NamedQuery(name="Localidad.findAll", query="SELECT l FROM Localidad l")
public class Localidad {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idlocalidad;
	
	private String nombre;
	private String ubicacion;
	private String encargado;
	private String telefonocontacto;
	private String correocontacto;
	
	@OneToOne
	@JoinColumn(name="idclienteaplicacion")
	private ClienteAplicacion clienteAplicacion;
	
	public Localidad() {
		super();
	}

	public Localidad(int idlocalidad, String nombre, String ubicacion, String encargado, String telefonocontacto,
			String correocontacto, ClienteAplicacion clienteAplicacion) {
		super();
		this.idlocalidad = idlocalidad;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.encargado = encargado;
		this.telefonocontacto = telefonocontacto;
		this.correocontacto = correocontacto;
		this.clienteAplicacion = clienteAplicacion;
	}

	public int getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(int idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getEncargado() {
		return encargado;
	}

	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}

	public String getTelefonocontacto() {
		return telefonocontacto;
	}

	public void setTelefonocontacto(String telefonocontacto) {
		this.telefonocontacto = telefonocontacto;
	}

	public String getCorreocontacto() {
		return correocontacto;
	}

	public void setCorreocontacto(String correocontacto) {
		this.correocontacto = correocontacto;
	}

	public ClienteAplicacion getClienteAplicacion() {
		return clienteAplicacion;
	}

	public void setClienteAplicacion(ClienteAplicacion clienteAplicacion) {
		this.clienteAplicacion = clienteAplicacion;
	}

	@Override
	public String toString() {
		return "Localidad [idlocalidad=" + idlocalidad + ", nombre=" + nombre + ", ubicacion=" + ubicacion
				+ ", encargado=" + encargado + ", telefonocontacto=" + telefonocontacto + ", correocontacto="
				+ correocontacto + ", clienteAplicacion=" + clienteAplicacion + "]";
	}

}
