package com.isae.drpool.drpool.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.isae.drpool.drpool.dao.IAsignacionProyectoDAO;
import com.isae.drpool.drpool.dao.IAsignarRegistroDAO;
import com.isae.drpool.drpool.dao.IAsistenciaDAO;
import com.isae.drpool.drpool.dao.ICatalogoDAO;
import com.isae.drpool.drpool.dao.IDiaAsistenciaDAO;
import com.isae.drpool.drpool.dao.IEdicionAsignadaDAO;
import com.isae.drpool.drpool.dao.IFotoAsistenciaDAO;
import com.isae.drpool.drpool.dao.IFotoEvidenciaDAO;
import com.isae.drpool.drpool.dao.INotificacionDAO;
import com.isae.drpool.drpool.dao.IUsuarioDAO;
import com.isae.drpool.drpool.entity.Diasasistencia;
import com.isae.drpool.drpool.entity.Usuario;

@RestController
//@RequestMapping("/usuario")
public class UsuarioRestController {

	@Autowired
	private IUsuarioDAO usuario;

	@Autowired
	private IFotoAsistenciaDAO fotoAsistencia;

	@Autowired
	private IAsignarRegistroDAO asignacionRegistro;

	@Autowired
	private IEdicionAsignadaDAO edicionAsignada;
	
	@Autowired
	private IAsistenciaDAO asistencia;
	
	@Autowired
	private ICatalogoDAO catalogo;
	
	@Autowired
	private IFotoEvidenciaDAO fotoEvidencia;
	
	@Autowired
	private IAsignacionProyectoDAO asignacionProyecto;
	
	@Autowired
	private INotificacionDAO notificacion;
	
	@Autowired
	private IDiaAsistenciaDAO diaAsistencia;

	@Autowired
	private JavaMailSender mailSender;

	@CrossOrigin(origins = "*")
	@PostMapping("/crear/usuario")
	public List<String> ingresarUsuario(@RequestBody Usuario usuario) {
		List<String> respuesta = new ArrayList<>();

		usuario.setToken("");
		System.out.println(usuario);
		if(usuario.getVistaCliente().getIdcliente() == 0) {
			usuario.setVistaCliente(null);
		}

		this.usuario.save(usuario);

		respuesta.add("correcto");
		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/usuario/eliminar/{idusuario}/{password}")
	public String eliminarUsuario(@PathVariable(value = "idusuario") String idusuario,
			@PathVariable(value = "password") String password) {
		String respuesta = "Error al eliminar el registro";
		if (password.equals("eliminarusuario170313")) {
			Usuario usuario = this.usuario.findById(Integer.parseInt(idusuario)).get();

			List<Diasasistencia> diasObtenidos =  this.diaAsistencia.obtenerIdDiasPorIdAsistencia(this.asistencia.obtenerIdPorIdUsuario(Integer.parseInt(idusuario)));
			this.diaAsistencia.deleteAll(diasObtenidos);
			System.out.println("Se eliminaros los dias asistencia");
			this.asistencia.eliminarPoridUsuario(usuario.getIdusuario());
			System.out.println("Se elimino la asistencia");
			this.fotoAsistencia.eliminarPorIdUsuario(usuario.getIdusuario());
			System.out.println("Se eliminaros las todos de asistencia");
			this.fotoEvidencia.eliminarPoridUsuario(usuario.getIdusuario());
			System.out.println("Se eliminaron las fotos asistencia");
			this.asignacionRegistro.eliminarPoridUsuario(usuario.getIdusuario());
			this.edicionAsignada.eliminarPoridUsuario(usuario.getIdusuario());
			this.catalogo.eliminarPoridUsuario(usuario.getIdusuario());
			this.asignacionProyecto.eliminarPoridUsuario(usuario.getIdusuario());
			this.notificacion.eliminarPoridUsuario(usuario.getIdusuario());

			this.usuario.deleteById(usuario.getIdusuario());

			// Eliminando archivos de Firebase
			try {
				URL url = new URL(
						"https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
				FileInputStream serviceAccount = new FileInputStream(
						descargarConector(url, "google-service-descarga.json"));
				String direccionCarpeta = "Sesion/Usuarios/" + usuario.getUsuario();
				System.out.println("Direccion a eliminar: " + direccionCarpeta);
				respuesta = eliminarObjetoFirebase(serviceAccount, "isae-de6da.appspot.com", direccionCarpeta);
			} catch (IOException e) {
				System.out.println("Error al eliminar los archivos");
			}
		} else {
			respuesta = "Acceso denegado, Contraseña no valida";
		}

		return respuesta;
	}

	private String eliminarObjetoFirebase(FileInputStream serviceAccount, String bucketName, String objectName) {
		String respuesta = "";
		try {

			Storage storage = (Storage) getStrogaeOptions(serviceAccount).getService();

			Page<Blob> blobPage = storage.list(bucketName, Storage.BlobListOption.prefix(objectName));

			List<BlobId> blobIdList = new LinkedList<>();
			for (Blob blob : blobPage.iterateAll()) {
				blobIdList.add(blob.getBlobId());
			}

			if (!blobIdList.isEmpty()) {
				List<Boolean> deleted = storage.delete(blobIdList);

				if (deleted.contains(false)) {
					System.out.println("No se pudo eliminar la carpeta o algun archivo");
					respuesta = "No se pudo eliminar la carpeta o algun archivo";
				} else {
					System.out.println("Se elimino la carpeta correctamente");
					respuesta = "Se elimino la carpeta correctamente";
				}
			} else {
				System.out.println("No existen archivos");
				respuesta = "Se elimino la carpeta correctamente";
			}

			// Con esta linea solo se elimina un solo archivo
//			boolean deleted = storage.delete(bucketName, objectName);
//			
//			if(deleted) {
//				System.out.println("Se elimino la carpeta correctamente");
//			}else {
//				System.out.println("No se pudo eliminar la carpeta");
//			}

			System.out.println("Object " + objectName + " was deleted from " + bucketName);
			return respuesta;
		} catch (IOException e) {
			System.out.println("Error al acceder a Firebase");
			respuesta = "Error al eliminar el registro";
			return respuesta;
		}
	}

	private StorageOptions getStrogaeOptions(FileInputStream serviceAccount) throws IOException {
		StorageOptions storageOptions = ((StorageOptions.Builder) ((StorageOptions.Builder) StorageOptions.newBuilder()
				.setProjectId("isae-de6da")).setCredentials((Credentials) GoogleCredentials.fromStream(serviceAccount)))
				.build();
		return storageOptions;
	}

	private String descargarConector(URL url, String localFilename) throws IOException {
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

	@CrossOrigin(origins = "*")
	@PostMapping("/editar/usuario")
	public List<String> editarUsuario(@RequestBody Usuario usuario) {
		List<String> respuesta = new ArrayList<>();
		
		this.usuario.save(usuario);

		respuesta.add("correcto");
		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/eliminar/usuario")
	public List<String> eliminarUsuario(@RequestBody Usuario usuario) {
		List<String> respuesta = new ArrayList<>();
		respuesta.add("correcto");

		this.usuario.eliminarPorUsuario(usuario.getUsuario());

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/editar/password")
	public List<String> asignarPassword(@RequestBody Usuario usuario) {
		List<String> respuesta = new ArrayList<>();
		respuesta.add("correcto");

		this.usuario.asignarPassword(usuario.getPass(), usuario.getUsuario());

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/comprobar/Usuario")
	public String comprobarUsuario(@RequestBody Usuario usuario) {
		String respuesta = "vacio";
		List<Usuario> lista = this.usuario.obtenerUsuarioPorCorreo(usuario.getUsuario(), usuario.getCorreo());
		if (!lista.isEmpty()) {
			respuesta = "correcto";
		}

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/enviar/codigo/{codigo}")
	public String enviarCodigo(@RequestBody Usuario usuario, @PathVariable(value = "codigo") String codigo) {
		String respuesta = "correcto";

		SimpleMailMessage email = new SimpleMailMessage();

		email.setTo(usuario.getCorreo());
		email.setSubject("Solicitud de cambio de contraseña");
		email.setText(
				"Ingrese el siguiente codigo en la aplicacion para poder asignar una nueva contraseña: " + codigo);
		this.mailSender.send(email);

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/usuarios")
	public List<Usuario> getUsuarios() {
		return this.usuario.listarUsuariosOrdenados();
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/usuarios/token")
	public List<Usuario> obtenerUsuariosConToken() {

		return this.usuario.obtenerUsuariosConToken();
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/usuario")
	public List<Usuario> getUsuario(@RequestBody Usuario usuario) {
		System.out.println(usuario);
		return this.usuario.login(usuario.getUsuario(), usuario.getPass());
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/estados")
	public List<String> getEstados() {
		List<String> estados = this.usuario.obtenerEstados();
		return estados;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/usuario/id/{id}")
	public Usuario obtenerUsuarioPorId(@PathVariable(value = "id") String id) {
		Usuario usuario = null;
		usuario = this.usuario.obtenerUsuarioPorId(Integer.parseInt(id));
		return usuario;
	}

}
