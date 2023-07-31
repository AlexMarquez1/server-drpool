package com.isae.drpool.drpool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.isae.drpool.drpool.dao.IFotoEvidenciaDAO;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.entity.Evidencia;
import com.isae.drpool.drpool.entity.Fotoevidencia;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Usuario;

@RestController
//@RequestMapping("/foto/evidencia")
public class FotoEvidenciaRestController {

	@Autowired
	private IFotoEvidenciaDAO fotoEvidencia;

	@Autowired
	private IInventarioDAO inventario;

	@CrossOrigin(origins = "*")
	@PostMapping("/guardar/evidencia")
	public List<String> guardarEvidencia(@RequestBody Fotoevidencia evidencia) {
		List<String> respuesta = new ArrayList<String>();


		List<Fotoevidencia> lista = this.fotoEvidencia.obtenerConcidencia(evidencia.getNombrefoto(),
				evidencia.getCampoProyecto().getIdcamposproyecto(), evidencia.getInventario().getIdinventario(),
				evidencia.getUsuario().getIdusuario());


		if (!lista.isEmpty()) {
			evidencia.setIdfoto(lista.get(0).getIdfoto());
			this.fotoEvidencia.save(evidencia);
		} else {
			this.fotoEvidencia.save(evidencia);
		}

		respuesta.add("Correcto");

		return respuesta;

	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/actualizar/lista/evidencia/registro/{idUsuario}")
	public String actualizarEvidencia(@PathVariable(value = "idUsuario") String idUsuario,
			@RequestBody List<Evidencia> evidencias) {
		String respuesta = "Error";
		
		for(Evidencia evidencia : evidencias) {
			List<Fotoevidencia> lista = this.fotoEvidencia.obtenerConcidencia(evidencia.getNombreEvidencia(),
					evidencia.getCamposProyecto().getIdcamposproyecto(), evidencia.getInventario().getIdinventario());

			Fotoevidencia nuevaEvidencia = new Fotoevidencia(0, evidencia.getNombreEvidencia(), "", "",
					new Usuario(Integer.parseInt(idUsuario)), evidencia.getInventario(), evidencia.getCamposProyecto());

			nuevaEvidencia.setInventario(this.inventario.findById(nuevaEvidencia.getInventario().getIdinventario()).get());

			byte[] evidenciaByte = new byte[evidencia.getEvidencia().size()];

			int ind = 0;
			for (int item : evidencia.getEvidencia()) {
				evidenciaByte[ind] = (byte) item;
				ind++;
			}

			try {
				File evidenciaFile = File.createTempFile(nuevaEvidencia.getNombrefoto().length() <= 3 ? "000"+nuevaEvidencia.getNombrefoto()  :nuevaEvidencia.getNombrefoto(), ".png");

				FileUtils.writeByteArrayToFile(evidenciaFile, evidenciaByte);

				String urlFirma = guardarEvidencia(nuevaEvidencia.getInventario(), evidenciaFile,
						nuevaEvidencia.getNombrefoto());

				if (!lista.isEmpty()) {
					nuevaEvidencia.setIdfoto(lista.get(0).getIdfoto());
					nuevaEvidencia.setUrl(urlFirma);
					if(idUsuario == "0") {
						nuevaEvidencia.setUsuario(lista.get(0).getUsuario());
					}
					this.fotoEvidencia.save(nuevaEvidencia);
				} else {
					nuevaEvidencia.setUrl(urlFirma);
					this.fotoEvidencia.save(nuevaEvidencia);
				}

			} catch (IOException e) {
				e.printStackTrace();
				respuesta = "Error";
			}
		}
		respuesta= "Correcto";

		return respuesta;

	}

	@CrossOrigin(origins = "*")
	@PostMapping("/actualizar/evidencia/registro/{idUsuario}")
	public List<String> actualizarEvidencia(@PathVariable(value = "idUsuario") String idUsuario,
			@RequestBody Evidencia evidencia) {
		List<String> respuesta = new ArrayList<String>();
		
		List<Fotoevidencia> lista = this.fotoEvidencia.obtenerConcidencia(evidencia.getNombreEvidencia(),
				evidencia.getCamposProyecto().getIdcamposproyecto(), evidencia.getInventario().getIdinventario());

		Fotoevidencia nuevaEvidencia = new Fotoevidencia(0, evidencia.getNombreEvidencia(), "", "",
				new Usuario(Integer.parseInt(idUsuario)), evidencia.getInventario(), evidencia.getCamposProyecto());

		nuevaEvidencia.setInventario(this.inventario.findById(nuevaEvidencia.getInventario().getIdinventario()).get());

		byte[] evidenciaByte = new byte[evidencia.getEvidencia().size()];

		int ind = 0;
		for (int item : evidencia.getEvidencia()) {
			evidenciaByte[ind] = (byte) item;
			ind++;
		}

		try {
			File evidenciaFile = File.createTempFile(nuevaEvidencia.getNombrefoto().length() <= 3 ? "000"+nuevaEvidencia.getNombrefoto()  :nuevaEvidencia.getNombrefoto(), ".png");

			FileUtils.writeByteArrayToFile(evidenciaFile, evidenciaByte);

			String urlFirma = guardarEvidencia(nuevaEvidencia.getInventario(), evidenciaFile,
					nuevaEvidencia.getNombrefoto());

			if (!lista.isEmpty()) {
				nuevaEvidencia.setIdfoto(lista.get(0).getIdfoto());
				nuevaEvidencia.setUrl(urlFirma);
				if(idUsuario == "0") {
					nuevaEvidencia.setUsuario(lista.get(0).getUsuario());
				}
				this.fotoEvidencia.save(nuevaEvidencia);
			} else {
				nuevaEvidencia.setUrl(urlFirma);
				this.fotoEvidencia.save(nuevaEvidencia);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		respuesta.add("Correcto");

		return respuesta;

	}
	
	
	
	@CrossOrigin(origins = "*")
	@GetMapping("/eliminar/evidencia/registro/{idinventario}")
	public String eliminarEvidenciasRegistro(@PathVariable(value = "idinventario") String idinventario) {
		this.fotoEvidencia.eliminarPoridInventario(Integer.parseInt(idinventario));
		return "borrado";
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/eliminar/evidencia/registro/{idinventario}/{idcampo}")
	public String eliminarEvidenciasRegistroIdCampo(@PathVariable(value = "idinventario") String idinventario, @PathVariable(value = "idcampo") String idcampo) {
		
		this.fotoEvidencia.eliminarPoridInventarioIdCampo(Integer.parseInt(idinventario), Integer.parseInt(idcampo));
		return "borrado";
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/evidencia/documento/byte/{idInventario}/{idCampo}/{idUsuario}")
	public List<Integer> getFirmasDocumentoBytes(@PathVariable(value = "idInventario") String idInventario,
			@PathVariable(value = "idCampo") String idCampo, @PathVariable(value = "idUsuario") String idUsuario) {

		List<Integer> respuesta = new ArrayList<Integer>();
		List<Fotoevidencia> evidencia = this.fotoEvidencia.obtenerEvidencia(Integer.parseInt(idCampo),
				Integer.parseInt(idUsuario), Integer.parseInt(idInventario));
		try {
			if (!evidencia.isEmpty()) {
				File firmaArchivo = new File(descargarRecurso(new URL(evidencia.get(0).getUrl()),
						evidencia.get(0).getNombrefoto() + ".png"));
				byte[] bytes = FileUtils.readFileToByteArray(firmaArchivo);
				respuesta = new ArrayList<Integer>();

				for (int i = 0; i < bytes.length; i++) {
					respuesta.add((int) bytes[i]);
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/busqueda/evidencia/byte/{idinventario}/{idproyecto}/{idusuario}")
	public List<Fotoevidencia> obtenerBusquedaEvidenciaByte(@PathVariable(value = "idinventario") String idinventario,
			@PathVariable(value = "idproyecto") String idproyecto,
			@PathVariable(value = "idusuario") String idusuario) {

		List<Integer> bytesImagen = new ArrayList<Integer>();
		List<Evidencia> respuesta = new ArrayList<Evidencia>();
		List<Fotoevidencia> evidencia = new ArrayList<Fotoevidencia>();

		boolean buscarProProyecto = !idproyecto.equalsIgnoreCase("TODO");
		boolean buscarProInventario = !idinventario.equalsIgnoreCase("TODO");
		boolean buscarProUsuario = !idusuario.equalsIgnoreCase("TODO");

		if (!buscarProProyecto && !buscarProInventario && !buscarProUsuario) {
			// TODO: Comprueba si se estan consultando todos los registros
			System.out.println("Comprueba si se estan consultando todos los registros");
			evidencia = this.fotoEvidencia.findAll();
		} else if (buscarProProyecto && buscarProInventario && buscarProUsuario) {
			// TODO: Comprueba si se estan filtrando por todos los campos
			System.out.println("Comprueba si se estan filtrando por todos los campos");
			evidencia = this.fotoEvidencia.obtenerFitroProyectoInventarioUsuario(Integer.parseInt(idproyecto),
					Integer.parseInt(idinventario), Integer.parseInt(idusuario));

		} else if (buscarProProyecto && !buscarProInventario && !buscarProUsuario) {
			// TODO: Comprueba si se esta filtrando por proyecto
			System.out.println("Comprueba si se esta filtrando por proyecto");
			evidencia = this.fotoEvidencia.obtenerFitroProyecto(Integer.parseInt(idproyecto));
		} else if (!buscarProProyecto && buscarProInventario && !buscarProUsuario) {
			// TODO: Comprueba si se esta filtrando por inventario
			System.out.println("Comprueba si se esta filtrando por inventario");
			evidencia = this.fotoEvidencia.obtenerFitroInventario(Integer.parseInt(idinventario));

		} else if (!buscarProProyecto && !buscarProInventario && buscarProUsuario) {
			// TODO: Comprueba si se esta filtrando por usuario
			System.out.println("Comprueba si se esta filtrando por usuario");
			evidencia = this.fotoEvidencia.obtenerFitroUsuario(Integer.parseInt(idusuario));
		} else if (buscarProProyecto && buscarProInventario && !buscarProUsuario) {
			// TODO: Comprueba si se esta filtrando por proyecto e inventario
			System.out.println("Comprueba si se esta filtrando por proyecto e inventario");
			evidencia = this.fotoEvidencia.obtenerFitroProyectoInventario(Integer.parseInt(idproyecto),
					Integer.parseInt(idinventario));

		} else if (buscarProProyecto && !buscarProInventario && buscarProUsuario) {
			// TODO: Comprueba si se esta filtrando por proyecto y usuario
			System.out.println("Comprueba si se esta filtrando por proyecto y usuario");
			evidencia = this.fotoEvidencia.obtenerFitroProyectoUsuario(Integer.parseInt(idproyecto),
					Integer.parseInt(idusuario));

		} else if (!buscarProProyecto && buscarProInventario && buscarProUsuario) {
			// TODO: Comprueba si se esta filtrando por inventario y usuario
			System.out.println("Comprueba si se esta filtrando por inventario y usuario");
			evidencia = this.fotoEvidencia.obtenerFitroInventarioUsuario(Integer.parseInt(idinventario),
					Integer.parseInt(idusuario));

		}

//		try {
//			if (!evidencia.isEmpty()) {
//				for (Fotoevidencia item : evidencia) {
//
//					File firmaArchivo = new File(
//							descargarRecurso(new URL(item.getUrl()), item.getNombrefoto() + ".png"));
//					byte[] bytes = FileUtils.readFileToByteArray(firmaArchivo);
//					bytesImagen = new ArrayList<Integer>();
//
//					for (int i = 0; i < bytes.length; i++) {
//						bytesImagen.add((int) bytes[i]);
//					}
//
//					respuesta.add(new Evidencia(item.getIdfoto(), item.getNombrefoto(), bytesImagen,
//							item.getCampoProyecto(), item.getInventario()));
//				}
//			}
//
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		return evidencia;
	}

	public String guardarEvidencia(Inventario inventario, File file, String nombreFirma) {
		try {

			URL url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
			FileInputStream serviceAccount = new FileInputStream(descargarRecurso(url, "google-service-descarga.json"));
			String bucketName = "isae-de6da.appspot.com";
			boolean bandera = true;
			Storage storage = (Storage) getStrogaeOptions(serviceAccount).getService();

			if (file != null) {
				String objectName = "Proyectos/" + inventario.getProyecto().getIdproyecto() + "-"
						+ inventario.getProyecto().getProyecto().toUpperCase() + "/" + inventario.getIdinventario()
						+ "-" + inventario.getFolio() + "/Evidencias/" + nombreFirma;

				Map<String, String> map = new HashMap<>();
				map.put("firebaseStorageDownloadTokens", nombreFirma.replace(" ", "") + ".png");

				BlobId blobId = BlobId.of(bucketName, objectName);
				BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/png").setMetadata(map).build();

				Blob b = storage.create(blobInfo, new FileInputStream(file.getAbsolutePath()),
						new Storage.BlobWriteOption[0]);

				System.out.println("File format uploaded to bucket " + bucketName + " as " + objectName);

// Sirve para crear un link temporal para la descarga del archivo almacenado en Firebase
//				URL urlFirma = storage.signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());

				String[] direccionTemporal = b.getSelfLink().split("/");

				String urlFirma = generarUrl(direccionTemporal, b.getMetadata().get("firebaseStorageDownloadTokens"));

				return urlFirma;
			}

		} catch (IOException e) {

			System.out.println("Exception " + e.getMessage());
			return "Error al guardar la firma";
		}
		return "";
	}

	private String generarUrl(String[] direccionTemporal, String token) {
		String url = "https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/";

		return url + direccionTemporal[8] + "?alt=media&token=" + token;
	}

	private StorageOptions getStrogaeOptions(FileInputStream serviceAccount) throws IOException {
		StorageOptions storageOptions = ((StorageOptions.Builder) ((StorageOptions.Builder) StorageOptions.newBuilder()
				.setProjectId("isae-de6da")).setCredentials((Credentials) GoogleCredentials.fromStream(serviceAccount)))
						.build();
		return storageOptions;
	}

	private String descargarRecurso(URL url, String localFilename) throws IOException {
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

}
