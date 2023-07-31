package com.isae.drpool.drpool.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.isae.drpool.drpool.dao.IDiaAsistenciaDAO;
import com.isae.drpool.drpool.dao.IDiaDAO;
import com.isae.drpool.drpool.dao.IFotoAsistenciaDAO;
import com.isae.drpool.drpool.dao.IUsuarioDAO;
import com.isae.drpool.drpool.entity.AsistenciaAux;
import com.isae.drpool.drpool.entity.AsistenciaUsuario;
import com.isae.drpool.drpool.entity.Diasasistencia;
import com.isae.drpool.drpool.entity.ReporteAsistencia;
import com.isae.drpool.drpool.entity.Usuario;
import com.isae.drpool.drpool.utils.GenerarDocumentosUtils;

@RestController
//@RequestMapping("/asistencia")
public class AsistenciaRestController {

	@Autowired
	private IDiaAsistenciaDAO diaAsistencia;

	@Autowired
	private IDiaDAO dias;

	@Autowired
	private IFotoAsistenciaDAO fotoAsistencia;

	@Autowired
	private IUsuarioDAO usuario;

	@CrossOrigin(origins = "*")
	@GetMapping("/comprobar/asistencia/{idUsuario}")
	public List<String> comprobarAsistencia(@PathVariable(value = "idUsuario") String idUsuario) {
		List<String> respuesta = new ArrayList<>();
		List<Diasasistencia> lista = this.diaAsistencia.obtenerAsistenciaPorDia(Integer.parseInt(idUsuario));
		for (Diasasistencia item : lista) {
			respuesta.add(item.getAsistencia().getUsuario().getUsuario());
		}
		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/usuarios/asistencia/{fechaInicio}/{fechaFin}")
	public List<Usuario> getUsuariosAsistencia(@PathVariable(value = "fechaInicio") String fechaInicio,
			@PathVariable(value = "fechaFin") String fechaFin) {
		List<Integer> respuesta = this.diaAsistencia.obtenerUsuariosAsistencia(fechaInicio, fechaFin);
		List<Usuario> respuestaUsuarios = this.usuario.findAllById(respuesta);
		return respuestaUsuarios;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/asistencia/{idusuario}/{fechainicio}/{fechafin}")
	public List<AsistenciaAux> getAsistencia(@PathVariable(value = "idusuario") String idusuario, @PathVariable(value = "fechainicio") String fechainicio,@PathVariable(value = "fechafin") String fechafin) {
		List<AsistenciaAux> respuesta = new ArrayList<>();
		Usuario us = this.usuario.getById(Integer.parseInt(idusuario));
		
		System.out.println("Fecha Inicio: "+ fechainicio);
		System.out.println("Fecha Final: "+ fechainicio);
		System.out.println("ID Usuario: "+ us.getIdusuario());
		//TODO: Error al leer u obtener los datos de la fecha! hay que arreglarlo
		List<Diasasistencia> lista = this.diaAsistencia.obtenerAsistenciaUsuario(us.getIdusuario(),fechainicio, fechafin);
		System.out.println("Lista: "+ lista.size());
		for (Diasasistencia item : lista) {
			System.out.println("Datos Asistencia: "+ item);
			respuesta.add(new AsistenciaAux(item.getAsistencia().getUsuario(), item.getAsistencia().getHoraentrada(),
					item.getAsistencia().getHorasalida(), item.getDia().getDia().toString(),
					item.getAsistencia().getFotosasistencia().getUrl(),
					item.getAsistencia().getFotosasistencia().getCoordenadas()));
		}

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/asistencia/generarReporte/{fechaInicio}/{fechaFin}")
	public ResponseEntity<InputStreamResource> getExcelPrueba(@PathVariable(value = "fechaInicio") String fechaI,
			@PathVariable(value = "fechaFin") String fechaF) throws IOException, ParseException {
		Date fechaInicio = new Date(Integer.parseInt(fechaI.split("-")[0]), Integer.parseInt(fechaI.split("-")[1]),
				Integer.parseInt(fechaI.split("-")[2]));
		Date fechaFinal = new Date(Integer.parseInt(fechaF.split("-")[0]), Integer.parseInt(fechaF.split("-")[1]),
				Integer.parseInt(fechaF.split("-")[2]));

		System.out.println("------------------------------");
		System.out.println(fechaI);
		System.out.println(fechaInicio.toString());
		System.out.println("------------------------------");
		System.out.println("------------------------------");
		System.out.println(fechaF);
		System.out.println(fechaFinal.toString());
		System.out.println("------------------------------");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(fechaI.split("-")[0]), Integer.parseInt(fechaI.split("-")[1]),
				Integer.parseInt(fechaI.split("-")[2]));
		Calendar calendarFinal = Calendar.getInstance();
		calendarFinal.set(Integer.parseInt(fechaF.split("-")[0]), Integer.parseInt(fechaF.split("-")[1]),
				Integer.parseInt(fechaF.split("-")[2]));

		boolean mismaFecha = false;
		List<String> d = new ArrayList<String>();
		if (!fechaInicio.equals(fechaFinal)) {
			System.out.println("Es una fecha diferente");
			while (!mismaFecha) {
				System.out.println("Comparacion");
				System.out.println("anio: "+calendar.get(Calendar.YEAR));
				System.out.println("anio: "+calendarFinal.get(Calendar.YEAR));
				System.out.println("mes: "+calendar.get(Calendar.MONTH));
				System.out.println("mes: "+calendarFinal.get(Calendar.MONTH));
				System.out.println("dia: "+calendar.get(Calendar.DATE));
				System.out.println("dia: "+calendarFinal.get(Calendar.DATE));
				if (calendar.get(Calendar.YEAR) != calendarFinal.get(Calendar.YEAR)
						|| calendar.get(Calendar.MONTH) != calendarFinal.get(Calendar.MONTH)
						|| calendar.get(Calendar.DATE) != calendarFinal.get(Calendar.DATE)) {

					String mes = String.valueOf(calendar.get(Calendar.MONTH));
					String dia = String.valueOf(calendar.get(Calendar.DATE));
					d.add(calendar.get(Calendar.YEAR) + "-" + (mes.length() == 1 ? "0" + mes : mes) + "-"
							+ (dia.length() == 1 ? "0" + dia : dia));
					calendar.add(Calendar.DAY_OF_YEAR, 1);

				} else {
					String mes = String.valueOf(calendar.get(Calendar.MONTH));
					String dia = String.valueOf(calendar.get(Calendar.DATE));
					d.add(calendar.get(Calendar.YEAR) + "-" + (mes.length() == 1 ? "0" + mes : mes) + "-"
							+ (dia.length() == 1 ? "0" + dia : dia));
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					mismaFecha = true;
				}

			}
		} else {
			System.out.println("Es la misma fecha");
			String mes = String.valueOf(calendar.get(Calendar.MONTH));
			String dia = String.valueOf(calendar.get(Calendar.DATE));
			d.add(calendar.get(Calendar.YEAR) + "-" + (mes.length() == 1 ? "0" + mes : mes) + "-"
					+ (dia.length() == 1 ? "0" + dia : dia));
		}

		List<String> tituloHoras = new ArrayList();
		// d = this.dias.obtenerDias();
		System.out.println("Tamaño de la lista de dias: " + d.size());
		int column = 3;
		for (int i = 0; i < d.size(); i++) {
			tituloHoras.add("Hora de entrada");
			tituloHoras.add("Hora de salida");
		}

		List<AsistenciaUsuario> listaAsistencia = new ArrayList<AsistenciaUsuario>();
		AsistenciaUsuario asistencia = new AsistenciaUsuario();

		List<Diasasistencia> aux = this.diaAsistencia.obtenerAsistencia(d.get(0), d.get(d.size() - 1));
		List<List<String>> usuarios = new ArrayList<List<String>>();
		for (Diasasistencia item : aux) {
			List<String> a = new ArrayList<String>();
			a.add(item.getAsistencia().getUsuario().getNombre());
			a.add(item.getAsistencia().getHoraentrada());
			a.add(item.getAsistencia().getHorasalida());
			a.add(item.getDia().getDia().toString());
			a.add(item.getAsistencia().getFotosasistencia().getUrl());
			a.add(item.getAsistencia().getFotosasistencia().getCoordenadas());
			a.add(String.valueOf(item.getAsistenciacompleta()));
			boolean asistenciaRepetida = false;
			for (List<String> us : usuarios) {
				if (us.get(0).equals(item.getAsistencia().getUsuario().getNombre())
						&& us.get(3).equals(item.getDia().getDia().toString())) {
					asistenciaRepetida = true;
				}
			}
			if (!asistenciaRepetida) {
				usuarios.add(a);
			}

		}
		List<Diasasistencia> aux2 = this.diaAsistencia.obtenerAsistenciaReporte(d.get(0), d.get(d.size() - 1));
		List<ReporteAsistencia> listaRegistros = new ArrayList<ReporteAsistencia>();
		for (Diasasistencia item : aux2) {

			boolean asistenciaRepetida = false;
			for (ReporteAsistencia reporte : listaRegistros) {
				if (item.getAsistencia().getUsuario().getNombre().equals(reporte.getUsuario())
						&& item.getDia().getDia().toString().equals(reporte.getDiaAsistencia())) {
					asistenciaRepetida = true;
				}
			}

			if (!asistenciaRepetida) {
				listaRegistros.add(new ReporteAsistencia(item.getAsistencia().getUsuario().getJefeinmediato(), "",
						item.getAsistencia().getUsuario().getNombre(),
						item.getAsistencia().getUsuario().getPerfile().getPerfil(),
						item.getAsistencia().getUsuario().getUbicacion(), "", item.getDia().getDia().toString()));
			}
		}
		for (List<String> lista : usuarios) {
//			System.out.println("Usuario: " + lista.get(0));
			asistencia.setId(column);
			column++;
			asistencia.setUsuario(lista.get(0));

			for (String dia : d) {
				if (dia.equals(lista.get(3))) {
					asistencia.setDia(dia);
					asistencia.setHoraEntrada(lista.get(1));
					asistencia.setHoraSalida(lista.get(2));

				}
			}
			listaAsistencia.add(asistencia);
			asistencia = new AsistenciaUsuario();
		}

		AsistenciaUsuario as = new AsistenciaUsuario();

		List<Usuario> listaUsuarios = this.usuario.findAll();

		for (Usuario usuario : listaUsuarios) {
			boolean usuarioEncontrado = false;
			for (AsistenciaUsuario asis : listaAsistencia) {
				if (asis.getUsuario().equals(usuario.getNombre())) {
					usuarioEncontrado = true;
					break;
				}
			}
			if (!usuarioEncontrado) {
				as = new AsistenciaUsuario();
				as.setUsuario(usuario.getNombre());
				listaAsistencia.add(as);
			}

		}


		MediaType mediaType = MediaType.parseMediaType("application/vnd.ms-excel");
		GenerarDocumentosUtils generarDocumento = new GenerarDocumentosUtils();
		ByteArrayInputStream in = generarDocumento.generarExcel(d, tituloHoras, listaAsistencia, listaRegistros);
		// return IO ByteArray(in);
		HttpHeaders headers = new HttpHeaders();
		// set filename in header
		headers.add("Content-Disposition", "attachment; filename=Asistencia.csv");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Asistencia.csv")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(in));
	}
	
	
	
	//TODO: inicio de la prueba
	@CrossOrigin(origins = "*")
	@GetMapping("/prueba/generarReporte/{fechaInicio}/{fechaFin}")
	public ResponseEntity<InputStreamResource> getExcelAsistencia(@PathVariable(value = "fechaInicio") String fechaI,
			@PathVariable(value = "fechaFin") String fechaF) throws IOException, ParseException {
		Date fechaInicio = new Date(Integer.parseInt(fechaI.split("-")[0]), Integer.parseInt(fechaI.split("-")[1]),
				Integer.parseInt(fechaI.split("-")[2]));
		Date fechaFinal = new Date(Integer.parseInt(fechaF.split("-")[0]), Integer.parseInt(fechaF.split("-")[1]),
				Integer.parseInt(fechaF.split("-")[2]));

		System.out.println("------------------------------");
		System.out.println(fechaI);
		System.out.println(fechaInicio.toString());
		System.out.println("------------------------------");
		System.out.println("------------------------------");
		System.out.println(fechaF);
		System.out.println(fechaFinal.toString());
		System.out.println("------------------------------");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(fechaI.split("-")[0]), Integer.parseInt(fechaI.split("-")[1]),
				Integer.parseInt(fechaI.split("-")[2]));
		Calendar calendarFinal = Calendar.getInstance();
		calendarFinal.set(Integer.parseInt(fechaF.split("-")[0]), Integer.parseInt(fechaF.split("-")[1]),
				Integer.parseInt(fechaF.split("-")[2]));

		boolean mismaFecha = false;
		List<String> d = new ArrayList<String>();
		if (!fechaInicio.equals(fechaFinal)) {
			System.out.println("Es una fecha diferente");
			while (!mismaFecha) {
				System.out.println("Comparacion");
				System.out.println("anio: "+calendar.get(Calendar.YEAR));
				System.out.println("anio: "+calendarFinal.get(Calendar.YEAR));
				System.out.println("mes: "+calendar.get(Calendar.MONTH));
				System.out.println("mes: "+calendarFinal.get(Calendar.MONTH));
				System.out.println("dia: "+calendar.get(Calendar.DATE));
				System.out.println("dia: "+calendarFinal.get(Calendar.DATE));
				if (calendar.get(Calendar.YEAR) != calendarFinal.get(Calendar.YEAR)
						|| calendar.get(Calendar.MONTH) != calendarFinal.get(Calendar.MONTH)
						|| calendar.get(Calendar.DATE) != calendarFinal.get(Calendar.DATE)) {

					String mes = String.valueOf(calendar.get(Calendar.MONTH));
					String dia = String.valueOf(calendar.get(Calendar.DATE));
					d.add(calendar.get(Calendar.YEAR) + "-" + (mes.length() == 1 ? "0" + mes : mes) + "-"
							+ (dia.length() == 1 ? "0" + dia : dia));
					calendar.add(Calendar.DAY_OF_YEAR, 1);

				} else {
					String mes = String.valueOf(calendar.get(Calendar.MONTH));
					String dia = String.valueOf(calendar.get(Calendar.DATE));
					d.add(calendar.get(Calendar.YEAR) + "-" + (mes.length() == 1 ? "0" + mes : mes) + "-"
							+ (dia.length() == 1 ? "0" + dia : dia));
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					mismaFecha = true;
				}

			}
		} else {
			System.out.println("Es la misma fecha");
			String mes = String.valueOf(calendar.get(Calendar.MONTH));
			String dia = String.valueOf(calendar.get(Calendar.DATE));
			d.add(calendar.get(Calendar.YEAR) + "-" + (mes.length() == 1 ? "0" + mes : mes) + "-"
					+ (dia.length() == 1 ? "0" + dia : dia));
		}

		List<String> tituloHoras = new ArrayList();
		// d = this.dias.obtenerDias();
		System.out.println("Tamaño de la lista de dias: " + d.size());
		int column = 3;
		for (int i = 0; i < d.size(); i++) {
			tituloHoras.add("Hora de entrada");
			tituloHoras.add("Hora de salida");
		}

		List<AsistenciaUsuario> listaAsistencia = new ArrayList<AsistenciaUsuario>();
		AsistenciaUsuario asistencia = new AsistenciaUsuario();

		List<Diasasistencia> aux = this.diaAsistencia.obtenerAsistencia(d.get(0), d.get(d.size() - 1));
		List<List<String>> usuarios = new ArrayList<List<String>>();
		for (Diasasistencia item : aux) {
			List<String> a = new ArrayList<String>();
			a.add(item.getAsistencia().getUsuario().getNombre());
			a.add(item.getAsistencia().getHoraentrada());
			a.add(item.getAsistencia().getHorasalida());
			a.add(item.getDia().getDia().toString());
			a.add(item.getAsistencia().getFotosasistencia().getUrl());
			a.add(item.getAsistencia().getFotosasistencia().getCoordenadas());
			a.add(String.valueOf(item.getAsistenciacompleta()));
			boolean asistenciaRepetida = false;
			for (List<String> us : usuarios) {
				if (us.get(0).equals(item.getAsistencia().getUsuario().getNombre())
						&& us.get(3).equals(item.getDia().getDia().toString())) {
					asistenciaRepetida = true;
				}
			}
			if (!asistenciaRepetida) {
				usuarios.add(a);
			}

		}
		List<Diasasistencia> aux2 = this.diaAsistencia.obtenerAsistenciaReporte(d.get(0), d.get(d.size() - 1));
		List<ReporteAsistencia> listaRegistros = new ArrayList<ReporteAsistencia>();
		for (Diasasistencia item : aux2) {

			boolean asistenciaRepetida = false;
			for (ReporteAsistencia reporte : listaRegistros) {
				if (item.getAsistencia().getUsuario().getNombre().equals(reporte.getUsuario())
						&& item.getDia().getDia().toString().equals(reporte.getDiaAsistencia())) {
					asistenciaRepetida = true;
				}
			}

			if (!asistenciaRepetida) {
				listaRegistros.add(new ReporteAsistencia(item.getAsistencia().getUsuario().getJefeinmediato(), "",
						item.getAsistencia().getUsuario().getNombre(),
						item.getAsistencia().getUsuario().getPerfile().getPerfil(),
						item.getAsistencia().getUsuario().getUbicacion(), "", item.getDia().getDia().toString()));
			}
		}
		for (List<String> lista : usuarios) {
//			System.out.println("Usuario: " + lista.get(0));
			asistencia.setId(column);
			column++;
			asistencia.setUsuario(lista.get(0));

			for (String dia : d) {
				if (dia.equals(lista.get(3))) {
					asistencia.setDia(dia);
					asistencia.setHoraEntrada(lista.get(1));
					asistencia.setHoraSalida(lista.get(2));

				}
			}
			listaAsistencia.add(asistencia);
			asistencia = new AsistenciaUsuario();
		}

		AsistenciaUsuario as = new AsistenciaUsuario();

		List<Usuario> listaUsuarios = this.usuario.findAll();

		for (Usuario usuario : listaUsuarios) {
			boolean usuarioEncontrado = false;
			for (AsistenciaUsuario asis : listaAsistencia) {
				if (asis.getUsuario().equals(usuario.getNombre())) {
					usuarioEncontrado = true;
					break;
				}
			}
			if (!usuarioEncontrado) {
				as = new AsistenciaUsuario();
				as.setUsuario(usuario.getNombre());
				listaAsistencia.add(as);
			}

		}


		MediaType mediaType = MediaType.parseMediaType("application/vnd.ms-excel");
		GenerarDocumentosUtils generarDocumento = new GenerarDocumentosUtils();
		ByteArrayInputStream in = generarDocumento.generarExcelPrueba(d, tituloHoras, listaAsistencia, listaRegistros);
		// return IO ByteArray(in);
		HttpHeaders headers = new HttpHeaders();
		// set filename in header
		headers.add("Content-Disposition", "attachment; filename=Asistencia.csv");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Asistencia.csv")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(in));
	
	}
	
	
	//TODO: Fin de la prueba
	
	@CrossOrigin(origins = "*")
	@GetMapping("/asistencia/registrar/{usuario}/{archivo}/{opcion}")
	public void asistencia(@PathVariable(value = "usuario") Integer usuario,
			@PathVariable(value = "archivo") Integer archivo, @PathVariable(value = "opcion") Integer opcion) {

		String query = "";

		if (opcion == 0) {
			System.out.println("Registro de entrada");
			this.fotoAsistencia.registrarEntrada(usuario, archivo);
		} else {
			System.out.println("Registro de salida");
			this.fotoAsistencia.registrarSalida(usuario, archivo);
		}

	}

}
