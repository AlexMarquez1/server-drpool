package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the catalogo database table.
 * 
 */
@Entity
@Table(name="catalogo")
@NamedQuery(name="Catalogo.findAll", query="SELECT c FROM Catalogo c")
public class Catalogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcatalogo;

	private String catalogo;

	//bi-directional many-to-one association to Tipocatalogo
	@ManyToOne
	@JoinColumn(name="idtipo")
	private Tipocatalogo tipocatalogoBean;

	//bi-directional many-to-one association to Proyecto
	@ManyToOne
	@JoinColumn(name="idproyecto")
	private Proyecto proyecto;
	
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;


	public Catalogo() {
	}
	

	public Catalogo(int idcatalogo, String catalogo, Tipocatalogo tipocatalogoBean, Proyecto proyecto, Usuario usuario) {
		this.idcatalogo = idcatalogo;
		this.catalogo = catalogo;
		this.tipocatalogoBean = tipocatalogoBean;
		this.proyecto = proyecto;
		this.usuario = usuario;
	}



	public int getIdcatalogo() {
		return this.idcatalogo;
	}

	public void setIdcatalogo(int idcatalogo) {
		this.idcatalogo = idcatalogo;
	}

	public String getCatalogo() {
		return this.catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}

	public Tipocatalogo getTipocatalogoBean() {
		return this.tipocatalogoBean;
	}

	public void setTipocatalogoBean(Tipocatalogo tipocatalogoBean) {
		this.tipocatalogoBean = tipocatalogoBean;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	@Override
	public String toString() {
		return "Catalogo [idcatalogo=" + idcatalogo + ", catalogo=" + catalogo + ", tipocatalogoBean="
				+ tipocatalogoBean + ", proyecto=" + proyecto + "]";
	}


	
}