package com.isae.drpool.drpool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.isae.drpool.drpool.dao.IFotoAsistenciaDAO;
import com.isae.drpool.drpool.dao.IUsuarioDAO;
import com.isae.drpool.drpool.entity.Evidencia;
import com.isae.drpool.drpool.entity.Fotosasistencia;
import com.isae.drpool.drpool.entity.Usuario;


@RestController
//@RequestMapping("/foto/asistencia")
public class FotoAsistenciaRestController {

	@Autowired
	private IFotoAsistenciaDAO fotoAsistencia;
	
	@Autowired
	private IUsuarioDAO usuario;
	
	@CrossOrigin(origins = "*")
	@PostMapping("/guardar/foto/asistencia")
	public List<String> registrarFoto(
			@RequestBody Fotosasistencia foto) {
		List<String> respuesta = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		foto.setFecharegistro(new Date());
		System.out.println(foto);
		this.fotoAsistencia.insertarFoto(foto.getIdfoto(),foto.getNombrefoto(),foto.getUrl(),foto.getCoordenadas(),foto.getUsuario().getIdusuario());
		
		respuesta.add("Correcto");

		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/id/ultimo/folio")
	public List<String> getIdUltimoFolio() {
		List<String> respuesta = new ArrayList<>();

		respuesta.add(String.valueOf(this.fotoAsistencia.obtenerUltimoId()));
		System.out.println(respuesta);
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/id/ultima/foto")
	public List<String> getIdUltimaFoto() {
		List<String> respuesta = new ArrayList<>();

		System.out.println(String.valueOf(this.fotoAsistencia.obtenerUltimoIdFoto()));
		respuesta.add(String.valueOf(this.fotoAsistencia.obtenerUltimoIdFoto()));
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/actualizar/foto/asistencia/{idusuario}")
	public List<String> actualizarEvidencia(
			@PathVariable(value = "idusuario") String idusuario,
			@RequestBody Evidencia evidencia
			) {
		List<String> respuesta = new ArrayList<String>();
		
		byte[] evidenciaByte = new byte[evidencia.getEvidencia().size()];

		int ind =0;
		for(int item : evidencia.getEvidencia()) {
			evidenciaByte [ind] = (byte) item;
			ind++;
		}
		
		try {
			File evidenciaFile = File.createTempFile(evidencia.getNombreEvidencia(), ".png");
			
			FileUtils.writeByteArrayToFile(evidenciaFile, evidenciaByte);
			
			Usuario usuario = this.usuario.obtenerUsuarioPorId(Integer.parseInt(idusuario));
			
			String urlEvidencia = guardarEvidencia(usuario.getUsuario(), evidenciaFile, evidencia.getNombreEvidencia());
			
			this.fotoAsistencia.insertarFoto(evidencia.getIdEvidencia(),evidencia.getNombreEvidencia(),urlEvidencia,evidencia.getCoordenadas(),usuario.getIdusuario());

			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		respuesta.add("Correcto");
		
		return respuesta;
		
	}
	
	public String guardarEvidencia(String usuario, File file, String nombreEvidencia) {
		try {
			
	
			//URL url = new URL("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
			URL url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/service%2Fdr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json?alt=media&token=e0caf9de-daa9-479d-904c-c1f323cd5012&_gl=1*1veermv*_ga*NTM3NzEyMjI5LjE2OTU5MzIzODU.*_ga_CW55HF8NVT*MTY5NTkzMjM4NS4xLjEuMTY5NTkzNDY0NS40NS4wLjA.");
			FileInputStream serviceAccount = new FileInputStream(descargarRecurso(url, "google-service-descarga.json"));
			//String bucketName = "isae-de6da.appspot.com";
			String bucketName = "dr-pool-eca1c.appspot.com";
			boolean bandera = true;
			Storage storage = (Storage) getStrogaeOptions(serviceAccount).getService();

			if (file != null) {
				String objectName = "Sesion/Usuarios/"+usuario+"/"+nombreEvidencia+"";

				Map<String, String> map = new HashMap<>();
		        map.put("firebaseStorageDownloadTokens", nombreEvidencia.replace(" ", "")+".png");
		        
				BlobId blobId = BlobId.of(bucketName, objectName);
				BlobInfo blobInfo = BlobInfo
						.newBuilder(blobId)
						.setContentType("image/png")
						.setMetadata(map)
						.build();
				

				Blob b = storage.create(blobInfo, new FileInputStream(file.getAbsolutePath()), new Storage. BlobWriteOption[0]);
				
				
				System.out.println("File format uploaded to bucket " + bucketName + " as " + objectName);
				
// Sirve para crear un link temporal para la descarga del archivo almacenado en Firebase
//				URL urlFirma = storage.signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());
				
				String [] direccionTemporal = b.getSelfLink().split("/");
				
				String urlFirma = generarUrl(direccionTemporal,b.getMetadata().get("firebaseStorageDownloadTokens"));
				
				return urlFirma;
			}

		} catch (IOException e) {

			System.out.println("Exception " + e.getMessage());
			return "Error al guardar la firma";
		}
		return "";
	}
	
	private String descargarRecurso(URL url, String localFilename) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;

        String tempDir = System.getProperty("java.io.tmpdir");
        String outputPath = tempDir + "/" + localFilename;

        try {
        	//connect
            URLConnection urlConn = url.openConnection();

            //get inputstream from connection
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
	
	private StorageOptions getStrogaeOptions(FileInputStream serviceAccount) throws IOException {
		StorageOptions storageOptions = ((StorageOptions.Builder) ((StorageOptions.Builder) StorageOptions.newBuilder()
				.setProjectId("isae-de6da")).setCredentials((Credentials) GoogleCredentials.fromStream(serviceAccount)))
						.build();
		return storageOptions;
	}
	
	private String generarUrl(String [] direccionTemporal, String token) {
		//String url= "https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/";
		String url = "https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/";
		
		return url+direccionTemporal[8]+"?alt=media&token="+token;
	}
}
