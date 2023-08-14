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
@Table (name="sede")
@NamedQuery (name="Sede.findAll", query="SELECT s FROM Sede s")

public class Sede {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idsede;
	private String nombre;
	private String direccion;
	private String encargado;
	private String telefono;
	private String correo;
	private String estatus;
	@OneToOne
	@JoinColumn(name="idcliente")
	private Cliente cliente;
	
	public Sede() {
		super();
	}

	public Sede(int idsede, String nombre, String direccion, String encargado, String telefono, String correo,
			String estatus, Cliente cliente) {
		super();
		this.idsede = idsede;
		this.nombre = nombre;
		this.direccion = direccion;
		this.encargado = encargado;
		this.telefono = telefono;
		this.correo = correo;
		this.estatus = estatus;
		this.cliente = cliente;
	}

	public int getIdsede() {
		return idsede;
	}

	public void setIdsede(int idsede) {
		this.idsede = idsede;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEncargado() {
		return encargado;
	}

	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Sede [idsede=" + idsede + ", nombre=" + nombre + ", direccion=" + direccion + ", encargado=" + encargado
				+ ", telefono=" + telefono + ", correo=" + correo + ", estatus=" + estatus + ", cliente=" + cliente
				+ "]";
	}
	
}
