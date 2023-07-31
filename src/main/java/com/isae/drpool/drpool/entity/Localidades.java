package com.isae.drpool.drpool.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tbl_localidades")
@NamedQuery(name="Localidades.findAll", query="SELECT l FROM Localidades l")
public class Localidades {
	
	String d_cp;
	
	String d_asenta;
	
	String d_tipo_asenta;
	
	String d_mnpio_d_estado;
	
	String d_estado;
	
	String d_ciudad;
	
	String d_codigo;
	
	String c_estado;
	
	String c_oficina;
	
	String c_cp;
	
	String c_tipo_asenta;
	
	String c_mnpio;
	
	@Id
	String id_asenta_cpcons;
	
	String d_zona;
	
	String c_cve_ciudad;

	public Localidades() {
	}

	public Localidades(String d_cp, String d_asenta, String d_tipo_asenta, String d_mnpio_d_estado, String d_estado,
			String d_ciudad, String d_codigo, String c_estado, String c_oficina, String c_cp, String c_tipo_asenta,
			String c_mnpio, String id_asenta_cpcons, String d_zona, String c_cve_ciudad) {
		this.d_cp = d_cp;
		this.d_asenta = d_asenta;
		this.d_tipo_asenta = d_tipo_asenta;
		this.d_mnpio_d_estado = d_mnpio_d_estado;
		this.d_estado = d_estado;
		this.d_ciudad = d_ciudad;
		this.d_codigo = d_codigo;
		this.c_estado = c_estado;
		this.c_oficina = c_oficina;
		this.c_cp = c_cp;
		this.c_tipo_asenta = c_tipo_asenta;
		this.c_mnpio = c_mnpio;
		this.id_asenta_cpcons = id_asenta_cpcons;
		this.d_zona = d_zona;
		this.c_cve_ciudad = c_cve_ciudad;
	}

	public String getD_CP() {
		return d_cp;
	}

	public void setD_CP(String d_CP) {
		this.d_cp = d_CP;
	}

	public String getD_asenta() {
		return d_asenta;
	}

	public void setD_asenta(String d_asenta) {
		this.d_asenta = d_asenta;
	}

	public String getD_tipo_asenta() {
		return d_tipo_asenta;
	}

	public void setD_tipo_asenta(String d_tipo_asenta) {
		this.d_tipo_asenta = d_tipo_asenta;
	}

	public String getD_mnpio_d_estado() {
		return d_mnpio_d_estado;
	}

	public void setD_mnpio_d_estado(String d_mnpio_d_estado) {
		this.d_mnpio_d_estado = d_mnpio_d_estado;
	}

	public String getD_estado() {
		return d_estado;
	}

	public void setD_estado(String d_estado) {
		this.d_estado = d_estado;
	}

	public String getD_ciudad() {
		return d_ciudad;
	}

	public void setD_ciudad(String d_ciudad) {
		this.d_ciudad = d_ciudad;
	}

	public String getD_codigo() {
		return d_codigo;
	}

	public void setD_codigo(String d_codigo) {
		this.d_codigo = d_codigo;
	}

	public String getC_estado() {
		return c_estado;
	}

	public void setC_estado(String c_estado) {
		this.c_estado = c_estado;
	}

	public String getC_oficina() {
		return c_oficina;
	}

	public void setC_oficina(String c_oficina) {
		this.c_oficina = c_oficina;
	}

	public String getC_CP() {
		return c_cp;
	}

	public void setC_CP(String c_CP) {
		this.c_cp = c_CP;
	}

	public String getC_tipo_asenta() {
		return c_tipo_asenta;
	}

	public void setC_tipo_asenta(String c_tipo_asenta) {
		this.c_tipo_asenta = c_tipo_asenta;
	}

	public String getC_mnpio() {
		return c_mnpio;
	}

	public void setC_mnpio(String c_mnpio) {
		this.c_mnpio = c_mnpio;
	}

	public String getId_asenta_cpcons() {
		return id_asenta_cpcons;
	}

	public void setId_asenta_cpcons(String id_asenta_cpcons) {
		this.id_asenta_cpcons = id_asenta_cpcons;
	}

	public String getD_zona() {
		return d_zona;
	}

	public void setD_zona(String d_zona) {
		this.d_zona = d_zona;
	}

	public String getC_cve_ciudad() {
		return c_cve_ciudad;
	}

	public void setC_cve_ciudad(String c_cve_ciudad) {
		this.c_cve_ciudad = c_cve_ciudad;
	}

	@Override
	public String toString() {
		return "Localidades [d_cp=" + d_cp + ", d_asenta=" + d_asenta + ", d_tipo_asenta=" + d_tipo_asenta
				+ ", d_mnpio_d_estado=" + d_mnpio_d_estado + ", d_estado=" + d_estado + ", d_ciudad=" + d_ciudad
				+ ", d_codigo=" + d_codigo + ", c_estado=" + c_estado + ", c_oficina=" + c_oficina + ", c_cp=" + c_cp
				+ ", c_tipo_asenta=" + c_tipo_asenta + ", c_mnpio=" + c_mnpio + ", id_asenta_cpcons=" + id_asenta_cpcons
				+ ", d_zona=" + d_zona + ", c_cve_ciudad=" + c_cve_ciudad + "]";
	}

	

}
