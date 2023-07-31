package com.isae.drpool.drpool.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "historialcambios")
@NamedQuery(name = "HistorialCambios.findAll", query = "SELECT h FROM HistorialCambios h")
public class HistorialCambios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idhistorial;
	
	@ManyToOne
	@JoinColumn(name = "idinventario")
	Inventario inventario;
	
	@ManyToOne
	@JoinColumn(name = "idusuario")
	Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "idcampo")
	Camposproyecto campo;
	
	String valoranterior;
	String valornuevo;
	String fechacambio;
	String horacambio;
	
	public HistorialCambios() {
		super();
	}

	public HistorialCambios(int idhistorial, Inventario inventario, Usuario usuario, Camposproyecto campo,
			String valoranterior, String valornuevo, String fechacambio, String horacambio) {
		super();
		this.idhistorial = idhistorial;
		this.inventario = inventario;
		this.usuario = usuario;
		this.campo = campo;
		this.valoranterior = valoranterior;
		this.valornuevo = valornuevo;
		this.fechacambio = fechacambio;
		this.horacambio = horacambio;
	}

	public int getIdhistorial() {
		return idhistorial;
	}

	public void setIdhistorial(int idhistorial) {
		this.idhistorial = idhistorial;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Camposproyecto getCampo() {
		return campo;
	}

	public void setCampo(Camposproyecto campo) {
		this.campo = campo;
	}

	public String getValoranterior() {
		return valoranterior;
	}

	public void setValoranterior(String valoranterior) {
		this.valoranterior = valoranterior;
	}

	public String getValornuevo() {
		return valornuevo;
	}

	public void setValornuevo(String valornuevo) {
		this.valornuevo = valornuevo;
	}

	public String getFechacambio() {
		return fechacambio;
	}

	public void setFechacambio(String fechacambio) {
		this.fechacambio = fechacambio;
	}

	public String getHoracambio() {
		return horacambio;
	}

	public void setHoracambio(String horacambio) {
		this.horacambio = horacambio;
	}

	@Override
	public String toString() {
		return "HistorialCambios [idhistorial=" + idhistorial + ", inventario=" + inventario + ", usuario=" + usuario
				+ ", campo=" + campo + ", valoranterior=" + valoranterior + ", valornuevo=" + valornuevo
				+ ", fechacambio=" + fechacambio + ", horacambio=" + horacambio + "]";
	}
}
