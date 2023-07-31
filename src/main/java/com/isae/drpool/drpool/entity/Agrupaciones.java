package com.isae.drpool.drpool.entity;

import java.util.List;

public class Agrupaciones {

		private int idAgrupacion;
		private String agrupacion;
		private int idInventario;
		private List<Campos> campos;
		
		public Agrupaciones(int idAgrupacion, String agrupacion, List<Campos> campos) {
			this.idAgrupacion = idAgrupacion;
			this.agrupacion = agrupacion;
			this.campos = campos;
		}

		public Agrupaciones(int idAgrupacion, String agrupacion, int idInventario, List<Campos> campos) {
			this.idAgrupacion = idAgrupacion;
			this.agrupacion = agrupacion;
			this.idInventario = idInventario;
			this.campos = campos;
		}



		public Agrupaciones() {
		}

		public int getIdAgrupacion() {
			return idAgrupacion;
		}

		public void setIdAgrupacion(int idAgrupacion) {
			this.idAgrupacion = idAgrupacion;
		}

		public int getIdInventario() {
			return idInventario;
		}

		public void setIdInventario(int idInventario) {
			this.idInventario = idInventario;
		}

		public String getAgrupacion() {
			return agrupacion;
		}

		public void setAgrupacion(String agrupacion) {
			this.agrupacion = agrupacion;
		}

		public List<Campos> getCampos() {
			return campos;
		}

		public void setCampos(List<Campos> campos) {
			this.campos = campos;
		}

		@Override
		public String toString() {
			return "Agrupaciones [idAgrupacion=" + idAgrupacion + ", agrupacion=" + agrupacion + ", idInventario="
					+ idInventario + ", campos=" + campos + "]";
		}
}
