package com.isae.drpool.drpool.entity;

import javax.persistence.CascadeType;
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
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="iddireccion")
	private DireccionSede direccion;
	
	private String encargadosede;
	private String telefono;
	private String correo;
	private String estatus;
	
	@OneToOne
	@JoinColumn(name="coordinador")
	private Usuario coordinador;
	
	@OneToOne
	@JoinColumn(name="operador")
	private Usuario operador;
	
	@OneToOne
	@JoinColumn(name="idcliente")
	private Cliente cliente;
	
	public Sede() {
		super();
	}
	

	public Sede(int idsede) {
		super();
		this.idsede = idsede;
	}

	public Sede(int idsede, String nombre, DireccionSede direccion, String encargadosede, String telefono,
			String correo, String estatus, Usuario coordinador, Usuario operador, Cliente cliente) {
		super();
		this.idsede = idsede;
		this.nombre = nombre;
		this.direccion = direccion;
		this.encargadosede = encargadosede;
		this.telefono = telefono;
		this.correo = correo;
		this.estatus = estatus;
		this.coordinador = coordinador;
		this.operador = operador;
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

	public DireccionSede getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionSede direccion) {
		this.direccion = direccion;
	}

	public String getEncargadosede() {
		return encargadosede;
	}

	public void setEncargadosede(String encargadosede) {
		this.encargadosede = encargadosede;
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

	public Usuario getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Usuario coordinador) {
		this.coordinador = coordinador;
	}

	public Usuario getOperador() {
		return operador;
	}

	public void setOperador(Usuario operador) {
		this.operador = operador;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Sede [idsede=" + idsede + ", nombre=" + nombre + ", direccion=" + direccion + ", encargadosede="
				+ encargadosede + ", telefono=" + telefono + ", correo=" + correo + ", estatus=" + estatus
				+ ", coordinador=" + coordinador + ", operador=" + operador + ", cliente=" + cliente + "]";
	}

}
