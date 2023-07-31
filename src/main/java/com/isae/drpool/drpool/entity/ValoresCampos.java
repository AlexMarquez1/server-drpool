package com.isae.drpool.drpool.entity;

public class ValoresCampos {

	private String idvalor;
    private int idcampoproyecto;
    private int idinventario;
    private String valor;
    
    
    
	public ValoresCampos() {
	}



	public ValoresCampos(String idvalor, int idcampoproyecto, int idinventario, String valor) {
		this.idvalor = idvalor;
		this.idcampoproyecto = idcampoproyecto;
		this.idinventario = idinventario;
		this.valor = valor;
	}



	public String getIdvalor() {
		return idvalor;
	}



	public void setIdvalor(String idvalor) {
		this.idvalor = idvalor;
	}



	public int getIdcampoproyecto() {
		return idcampoproyecto;
	}



	public void setIdcampoproyecto(int idcampoproyecto) {
		this.idcampoproyecto = idcampoproyecto;
	}



	public int getIdinventario() {
		return idinventario;
	}



	public void setIdinventario(int idinventario) {
		this.idinventario = idinventario;
	}



	public String getValor() {
		return valor;
	}



	public void setValor(String valor) {
		this.valor = valor;
	}



	@Override
	public String toString() {
		return "ValoresCampos [idvalor=" + idvalor + ", idcampoproyecto=" + idcampoproyecto + ", idinventario="
				+ idinventario + ", valor=" + valor + "]";
	}
    
    
}
