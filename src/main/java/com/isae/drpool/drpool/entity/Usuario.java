package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idusuario;

	@Column(name = "correo")
	private String correo;

	private String jefeinmediato;

	private String nombre;

	private String pass;

	private int passtemp;

	private String telefono;

	private String ubicacion;

	private String usuario;
	
	private String status;
	
	private String token;

	//bi-directional many-to-one association to Perfile
	@OneToOne
	@JoinColumn(name="perfil")
	private Perfile perfile;
	
	@OneToOne
	@JoinColumn(name="idcliente")
	private ClienteAplicacion clienteAplicacion;
	
	//@OneToOne(cascade=CascadeType.ALL)
	@OneToOne()
	@JoinColumn(name="vistacliente")
	private Cliente vistaCliente;

	public Usuario() {
	}
	
	public Usuario(int idusuario) {
		this.idusuario = idusuario;
	}
	
	public Usuario(int idusuario, String correo, String jefeinmediato, String nombre, String pass, int passtemp,
			String telefono, String ubicacion, String usuario, String status, String token, Perfile perfile,
			ClienteAplicacion clienteAplicacion) {
		super();
		this.idusuario = idusuario;
		this.correo = correo;
		this.jefeinmediato = jefeinmediato;
		this.nombre = nombre;
		this.pass = pass;
		this.passtemp = passtemp;
		this.telefono = telefono;
		this.ubicacion = ubicacion;
		this.usuario = usuario;
		this.status = status;
		this.token = token;
		this.perfile = perfile;
		this.clienteAplicacion = clienteAplicacion;
	}

	public Usuario(int idusuario, String correo, String jefeinmediato, String nombre, String pass, int passtemp,
			String telefono, String ubicacion, String usuario, String status, String token, Perfile perfile) {
		super();
		this.idusuario = idusuario;
		this.correo = correo;
		this.jefeinmediato = jefeinmediato;
		this.nombre = nombre;
		this.pass = pass;
		this.passtemp = passtemp;
		this.telefono = telefono;
		this.ubicacion = ubicacion;
		this.usuario = usuario;
		this.status = status;
		this.token = token;
		this.perfile = perfile;
	}

	public Usuario(int idusuario, String correo, String jefeinmediato, String nombre, String pass, int passtemp,
			String telefono, String ubicacion, String usuario, String status, String token, Perfile perfile,
			ClienteAplicacion clienteAplicacion, Cliente vistaCliente) {
		super();
		this.idusuario = idusuario;
		this.correo = correo;
		this.jefeinmediato = jefeinmediato;
		this.nombre = nombre;
		this.pass = pass;
		this.passtemp = passtemp;
		this.telefono = telefono;
		this.ubicacion = ubicacion;
		this.usuario = usuario;
		this.status = status;
		this.token = token;
		this.perfile = perfile;
		this.clienteAplicacion = clienteAplicacion;
		this.vistaCliente = vistaCliente;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getJefeinmediato() {
		return jefeinmediato;
	}

	public void setJefeinmediato(String jefeinmediato) {
		this.jefeinmediato = jefeinmediato;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getPasstemp() {
		return passtemp;
	}

	public void setPasstemp(int passtemp) {
		this.passtemp = passtemp;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Perfile getPerfile() {
		return perfile;
	}

	public void setPerfile(Perfile perfile) {
		this.perfile = perfile;
	}

	public ClienteAplicacion getClienteAplicacion() {
		return clienteAplicacion;
	}

	public void setClienteAplicacion(ClienteAplicacion clienteAplicacion) {
		this.clienteAplicacion = clienteAplicacion;
	}

	public Cliente getVistaCliente() {
		return vistaCliente;
	}

	public void setVistaCliente(Cliente vistaCliente) {
		this.vistaCliente = vistaCliente;
	}

	@Override
	public String toString() {
		return "Usuario [idusuario=" + idusuario + ", correo=" + correo + ", jefeinmediato=" + jefeinmediato
				+ ", nombre=" + nombre + ", pass=" + pass + ", passtemp=" + passtemp + ", telefono=" + telefono
				+ ", ubicacion=" + ubicacion + ", usuario=" + usuario + ", status=" + status + ", token=" + token
				+ ", perfile=" + perfile + ", clienteAplicacion=" + clienteAplicacion + ", vistaCliente=" + vistaCliente
				+ "]";
	}

}