package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="perfiles")
@NamedQuery(name="Perfile.findAll", query="SELECT p FROM Perfile p")
public class Perfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idperfil;

	private String perfil;

	public Perfile() {
	}
	
	public Perfile(int idperfil, String perfil) {
		this.idperfil = idperfil;
		this.perfil = perfil;
	}

	public int getIdperfil() {
		return this.idperfil;
	}

	public void setIdperfil(int idperfil) {
		this.idperfil = idperfil;
	}

	public String getPerfil() {
		return this.perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "Perfile [idperfil=" + idperfil + ", perfil=" + perfil + "]";
	}

	

}