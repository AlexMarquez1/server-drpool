package com.isae.drpool.drpool.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isae.drpool.drpool.dao.IAgrupacionesDAO;
import com.isae.drpool.drpool.dao.IAsignacionProyectoDAO;
import com.isae.drpool.drpool.dao.IAsignarRegistroDAO;
import com.isae.drpool.drpool.dao.ICamposProyectoDAO;
import com.isae.drpool.drpool.dao.ICatalogoDAO;
import com.isae.drpool.drpool.dao.ICatalogoRelacionadoDAO;
import com.isae.drpool.drpool.dao.IDocumentoGenerado;
import com.isae.drpool.drpool.dao.IEdicionAsignadaDAO;
import com.isae.drpool.drpool.dao.IEnProcesoDAO;
import com.isae.drpool.drpool.dao.IFirmaDocumentosDAO;
import com.isae.drpool.drpool.dao.IFotoEvidenciaDAO;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.dao.IPendienteDAO;
import com.isae.drpool.drpool.dao.IProyectoDAO;
import com.isae.drpool.drpool.dao.ITipoCatalogoDAO;
import com.isae.drpool.drpool.dao.ITipoProyectoDAO;
import com.isae.drpool.drpool.dao.IValoresDAO;
import com.isae.drpool.drpool.entity.Agrupacion;
import com.isae.drpool.drpool.entity.Agrupaciones;
import com.isae.drpool.drpool.entity.Asignacionproyecto;
import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Catalogo;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Tipocatalogo;
import com.isae.drpool.drpool.entity.Usuario;

@RestController
//@RequestMapping("/proyecto")
public class ProyectoRestController {

	@Autowired
	private IProyectoDAO proyecto;

	@Autowired
	private IInventarioDAO inventario;

	@Autowired
	private ITipoProyectoDAO tipoProyecto;

	@Autowired
	private ITipoCatalogoDAO tipoCatalogo;

	@Autowired
	private IAgrupacionesDAO agrupaciones;

	@Autowired
	private ICamposProyectoDAO camposProyecto;

	@Autowired
	private IAsignacionProyectoDAO asignacionProyecto;

	@Autowired
	private IValoresDAO valores;

	@Autowired
	private IFotoEvidenciaDAO evidencia;

	@Autowired
	private IFirmaDocumentosDAO firma;

	@Autowired
	private IPendienteDAO pendiente;

	@Autowired
	private IEnProcesoDAO enProceso;

	@Autowired
	private IEdicionAsignadaDAO edicionAsignada;

	@Autowired
	private IDocumentoGenerado documentoGenerado;

	@Autowired
	private ICatalogoDAO catalogo;

	@Autowired
	private ICatalogoRelacionadoDAO catalogoRelacionado;

	@Autowired
	private IAsignarRegistroDAO asignarRegistros;

	@CrossOrigin(origins = "*")
	@PostMapping("/crear/proyecto/{proyecto}/{tipoproyecto}")
	public String ingresarProyecto(@RequestBody List<Agrupaciones> agrupaciones,
			@PathVariable(value = "proyecto") String nombreProyecto,
			@PathVariable(value = "tipoproyecto") String tipoProyecto) {
		System.out.println("Tipo de proyecto: " + tipoProyecto);
		String respuesta = "";
		List<String> agrupacionesAux = new ArrayList<String>();
		List<String> catalogosAIngresar = new ArrayList<String>();
		List<String> listaTipo = new ArrayList<String>();
		List<Agrupacion> agrupacionesAIngresar = new ArrayList<Agrupacion>();
		List<Tipocatalogo> listaTipoCatalogoIngresar = new ArrayList<Tipocatalogo>();
		List<Camposproyecto> listaCamposProyecto = new ArrayList<Camposproyecto>();
		
		System.out.println(agrupaciones);

		proyecto.ingresarProyecto(nombreProyecto.toUpperCase(), tipoProyecto.toUpperCase());

//		Tipoproyecto tipo = this.tipoProyecto.findByDescripcion(tipoProyecto);
//		System.out.println("Tipo proyecto: "+tipo.getDescripcion());
		Proyecto proyecto = this.proyecto.obtenerProyectoPorNombre(nombreProyecto);
		List<Tipocatalogo> listaTipoCatalogo = tipoCatalogo.findAll();

		List<Agrupacion> agrupacionesAlmacenadas = this.agrupaciones.findAll();
		System.out.println("Tamaño Lista: : " + agrupacionesAlmacenadas.size());
		for (Tipocatalogo item : listaTipoCatalogo) {
			listaTipo.add(item.getTipo());
		}

		for (Agrupaciones elemento : agrupaciones) {
			for (int i = 0; i < elemento.getCampos().size(); i++) {
				if (elemento.getCampos().get(i).getTipoCampo().equalsIgnoreCase("CATALOGO")) {
					catalogosAIngresar.add(elemento.getCampos().get(i).getNombreCampo().toUpperCase());
				}
			}
			agrupacionesAux.add(elemento.getAgrupacion().toUpperCase());
		}
		catalogosAIngresar.removeAll(listaTipo);
		List<String> listaAEliminar = new ArrayList<String>();
		for (Agrupacion agrupacion : agrupacionesAlmacenadas) {
			listaAEliminar.add(agrupacion.getAgrupacion().toUpperCase());
		}
		if(!listaAEliminar.isEmpty()) {
			agrupacionesAux.removeAll(listaAEliminar);
		}

		int ultimoId = 0;
		if (agrupacionesAlmacenadas.isEmpty()) {
			ultimoId = 1;
		} else {
			ultimoId = agrupacionesAlmacenadas.get(agrupacionesAlmacenadas.size() - 1).getIdagrupacion();
			ultimoId++;
		}

		for (String item : agrupacionesAux) {
			System.out.println("Agrupacion a ingresar: " + item);
			agrupacionesAIngresar.add(new Agrupacion(ultimoId, item));
			agrupacionesAlmacenadas.add(new Agrupacion(ultimoId, item));
			ultimoId++;
		}

		if (!catalogosAIngresar.isEmpty()) {
			for (String item : catalogosAIngresar) {
				listaTipoCatalogoIngresar.add(new Tipocatalogo(0, item.toUpperCase()));
			}
			tipoCatalogo.saveAll(listaTipoCatalogoIngresar);
		}

		if (!agrupacionesAIngresar.isEmpty()) {
			//Comprobar que las agrupaciones no se repitan
			eliminarAgrupacionesRepetidas(agrupacionesAIngresar);
			this.agrupaciones.saveAll(agrupacionesAIngresar);
		}

		for (int i = 0; i < agrupaciones.size(); i++) {
			for (int j = 0; j < agrupaciones.get(i).getCampos().size(); j++) {
				System.out.println("Campo a ingresar: " + agrupaciones.get(i).getCampos().get(j).getNombreCampo());
				Agrupacion aux = this.agrupaciones.obtenerAgrupacionPorAgrupacion(agrupaciones.get(i).getAgrupacion());
				listaCamposProyecto.add(new Camposproyecto(0, "INSERTA LOS DATOS SOLICITADOS EN EL CAMPO",
						agrupaciones.get(i).getCampos().get(j).getNombreCampo().toUpperCase(), "FALSE", "TRUE",
						agrupaciones.get(i).getCampos().get(j).getLongitud(),
						"[" + agrupaciones.get(i).getCampos().get(j).getRestriccion() + "]",
						agrupaciones.get(i).getCampos().get(j).getTipoCampo(), aux, proyecto, ""));
			}
		}

		System.out.println(listaCamposProyecto);
		camposProyecto.saveAll(listaCamposProyecto);
		respuesta = "Se guardo el proyecto exitosamente";

		return respuesta;
	}
	
	public static void eliminarAgrupacionesRepetidas(List<Agrupacion> lista) {
        Set<String> nombresVistos = new HashSet<>();
        List<Agrupacion> agrupacionesEliminar = new ArrayList<>();

        for (Agrupacion agrupacion : lista) {
            String nombreAgrupacion = agrupacion.getAgrupacion();
            if (!nombresVistos.add(nombreAgrupacion)) {
                agrupacionesEliminar.add(agrupacion);
            }
        }

        lista.removeAll(agrupacionesEliminar);
    }

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/proyectos")
	public List<Proyecto> getProyectos() {
		return this.proyecto.findAll();
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/eliminar/proyectos")
	public String eliminarProyecto(@RequestBody Proyecto proyecto) {
		String respuesta = "Error";
		List<Inventario> listaInventarios = this.inventario.obtenerPorIdProyecto(proyecto.getIdproyecto());

		for (Inventario inventario : listaInventarios) {

			this.valores.eliminarPoridInventario(inventario.getIdinventario());
			this.evidencia.eliminarPoridInventario(inventario.getIdinventario());
			this.firma.eliminarPoridInventario(inventario.getIdinventario());
			this.pendiente.eliminarInventario(inventario.getIdinventario());
			this.enProceso.eliminarInventario(inventario.getIdinventario());
			this.edicionAsignada.eliminarPorInventario(inventario.getIdinventario());
			this.documentoGenerado.eliminarPoridInventario(inventario.getIdinventario());
			this.asignarRegistros.eliminarPoridInventario(inventario.getIdinventario());

		}

		List<Catalogo> listaCatalogos = this.catalogo.obtenerCatalogosPorProyecto(proyecto.getIdproyecto());
		for (Catalogo catalogo : listaCatalogos) {
			this.catalogoRelacionado.eliminarPorPadreOHijo(catalogo.getIdcatalogo(), catalogo.getIdcatalogo());

		}

		this.catalogo.eliminarPorProyecto(proyecto.getIdproyecto());
		this.camposProyecto.eliminarPoridProyecto(proyecto.getIdproyecto());

		this.inventario.deleteAll(listaInventarios);
		this.asignacionProyecto.eliminarPoridProyecto(proyecto.getIdproyecto());

		this.proyecto.eliminarPoridProyecto(proyecto.getIdproyecto());

		respuesta = "Correcto";

		System.out.println("Se elimino el proyecto correctamente");

		return respuesta;

	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/eliminar/proyecto/{password}")
	public Map<String,String> eliminarProyectoPassword(@RequestBody Proyecto proyecto,@PathVariable(value = "password") String password) {
		Map<String,String> respuesta = new HashMap<String,String>();
		
		if(password.equals("eliminarproyecto170313")) {
			List<Inventario> listaInventarios = this.inventario.obtenerPorIdProyecto(proyecto.getIdproyecto());

			for (Inventario inventario : listaInventarios) {

				this.valores.eliminarPoridInventario(inventario.getIdinventario());
				this.evidencia.eliminarPoridInventario(inventario.getIdinventario());
				this.firma.eliminarPoridInventario(inventario.getIdinventario());
				this.pendiente.eliminarInventario(inventario.getIdinventario());
				this.enProceso.eliminarInventario(inventario.getIdinventario());
				this.edicionAsignada.eliminarPorInventario(inventario.getIdinventario());
				this.documentoGenerado.eliminarPoridInventario(inventario.getIdinventario());
				this.asignarRegistros.eliminarPoridInventario(inventario.getIdinventario());

			}

			List<Catalogo> listaCatalogos = this.catalogo.obtenerCatalogosPorProyecto(proyecto.getIdproyecto());
			for (Catalogo catalogo : listaCatalogos) {
				this.catalogoRelacionado.eliminarPorPadreOHijo(catalogo.getIdcatalogo(), catalogo.getIdcatalogo());

			}

			this.catalogo.eliminarPorProyecto(proyecto.getIdproyecto());
			this.camposProyecto.eliminarPoridProyecto(proyecto.getIdproyecto());

			this.inventario.deleteAll(listaInventarios);
			this.asignacionProyecto.eliminarPoridProyecto(proyecto.getIdproyecto());

			this.proyecto.eliminarPoridProyecto(proyecto.getIdproyecto());

			respuesta.put("Respuesta", "Proyecto eliminado");
			System.out.println("Proyecto eliminado: " + proyecto);

			System.out.println("Se elimino el proyecto correctamente");
		}else {
			System.out.println("La contraseña es incorrecta" + proyecto);
			respuesta.put("Respuesta", "La contraseña no coinside");
		}

		return respuesta;

	}

	private String respuesta = "";

	@CrossOrigin(origins = "*")
	@GetMapping("/actualizar/dashboard/{proyecto}/{anterior}/{nuevo}")
	public String actualizarDashboard(@PathVariable(value = "proyecto") String proyecto,
			@PathVariable(value = "anterior") String anterior, @PathVariable(value = "nuevo") String nuevo) {
		System.out.println(nuevo);
		System.out.println(anterior);

		try {

			DatabaseReference totalRef = obtenerReferencia("TotalDatos", proyecto);

			ValueEventListener listener = new ValueEventListener() {

				@Override
				public void onDataChange(DataSnapshot snapshot) {
					Map<String, Object> datosAnteriores = (Map<String, Object>) snapshot.getValue();
					System.out.println(datosAnteriores);
					String numeroAnterior = "";
					String numeroNuevo = "";
					String etiquetaAnterior = "";
					String etiquetaNuevo = "";
					switch (anterior) {
					case "NUEVO":
						numeroAnterior = datosAnteriores.get("totalNuevos").toString();
						etiquetaAnterior = "totalNuevos";
						System.out.println(numeroAnterior);
						break;
					case "ASIGNADO":
						numeroAnterior = datosAnteriores.get("totalAsignados").toString();
						etiquetaAnterior = "totalAsignados";
						System.out.println(numeroAnterior);
						break;
					case "PENDIENTE":
						numeroAnterior = datosAnteriores.get("totalPendientes").toString();
						etiquetaAnterior = "totalPendientes";
						System.out.println(numeroAnterior);
						break;
					case "EN PROCESO":
						numeroAnterior = datosAnteriores.get("totalEnProceso").toString();
						etiquetaAnterior = "totalEnProceso";
						System.out.println(numeroAnterior);
						break;
					case "CERRADO":
						numeroAnterior = datosAnteriores.get("totalCerrados").toString();
						etiquetaAnterior = "totalCerrados";
						System.out.println(numeroAnterior);
						break;
					default:
						etiquetaAnterior = "ninguno";
						numeroAnterior = "0";
						break;
					}

					int aux = Integer.parseInt(numeroAnterior) - 1;
					numeroAnterior = String.valueOf(aux);

					switch (nuevo) {
					case "NUEVO":
						numeroNuevo = datosAnteriores.get("totalNuevos").toString();
						etiquetaNuevo = "totalNuevos";
						break;
					case "ASIGNADO":
						numeroNuevo = datosAnteriores.get("totalAsignados").toString();
						etiquetaNuevo = "totalAsignados";
						break;
					case "PENDIENTE":
						numeroNuevo = datosAnteriores.get("totalPendientes").toString();
						etiquetaNuevo = "totalPendientes";
						break;
					case "EN PROCESO":
						numeroNuevo = datosAnteriores.get("totalEnProceso").toString();
						etiquetaNuevo = "totalEnProceso";
						break;
					case "CERRADO":
						numeroNuevo = datosAnteriores.get("totalCerrados").toString();
						etiquetaNuevo = "totalCerrados";
						break;
					default:
						etiquetaNuevo = "ninguno";
						numeroNuevo = "0";
						break;
					}

					aux = Integer.parseInt(numeroNuevo) + 1;
					numeroNuevo = String.valueOf(aux);

					Map<String, Object> datos = new HashMap<>();
					datos.put(etiquetaAnterior, Integer.parseInt(numeroAnterior));
					datos.put(etiquetaNuevo, Integer.parseInt(numeroNuevo));
					DatabaseReference actualizar = obtenerReferencia("TotalDatos", proyecto);
					actualizar.updateChildrenAsync(datos);

				}

				@Override
				public void onCancelled(DatabaseError error) {
					// TODO Auto-generated method stub
					System.out.println("Se cancela la operacion");
				}

			};

			totalRef.addListenerForSingleValueEvent(listener);

//			totalRef.removeEventListener(listener);

		} catch (Exception e) {
			System.out.println(e);
		}
		return respuesta;
	}

	private DatabaseReference obtenerReferencia(String padre, String hijo) {
		URL url;
		DatabaseReference respuesta = null;
		try {
			url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");

			FileInputStream serviceAccount = new FileInputStream(descargarArchivo(url, "google-service-descarga.json"));

			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://isae-de6da.firebaseio.com").build();

			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}

			DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
			respuesta = ref.child(padre).child(hijo);

			return respuesta;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	private String descargarArchivo(URL url, String localFilename) throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;

		String tempDir = System.getProperty("java.io.tmpdir");
		String outputPath = tempDir + "/" + localFilename;

		try {
			// connect
			URLConnection urlConn = url.openConnection();

			// get inputstream from connection
			is = urlConn.getInputStream();
			fos = new FileOutputStream(outputPath);

			// 4KB buffer
			byte[] buffer = new byte[4096];
			int length;

			// read from source and write into local file
			while ((length = is.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			return outputPath;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} finally {
				if (fos != null) {
					fos.close();
				}
			}
		}
	}

//---------------------------------------------Aplicacion movil-----------------------------------------------

	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/proyectos/asignados")
	public List<Proyecto> getProyectosAsignadosUsuario(@RequestBody Usuario usuario) {
		List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
		List<Asignacionproyecto> proyectosAsignados = this.asignacionProyecto
				.obtenerProyectosAsignados(usuario.getIdusuario());

		for (Asignacionproyecto item : proyectosAsignados) {
			listaProyectos.add(item.getProyecto());
		}

		return listaProyectos;
	}

}
