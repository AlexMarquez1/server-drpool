package com.isae.drpool.drpool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.isae.drpool.drpool.dao.IAsignacionClienteDAO;
import com.isae.drpool.drpool.dao.IClienteDAO;
import com.isae.drpool.drpool.entity.Cliente;
import com.isae.drpool.drpool.entity.Proyecto;

@RestController
public class ClienteRestController {

	@Autowired
	private IClienteDAO cliente;
	
	@Autowired
	private IAsignacionClienteDAO asignacionCliente;
	
	@CrossOrigin(origins="*")
	@GetMapping("/obtener/clientes")
	public List<Cliente> obtenerClientes() {
		return this.cliente.findAll();
	}
	
	@CrossOrigin(origins="*")
	@GetMapping("/obtener/clientes/usuario/{idclienteaplicacion}")
	public List<Cliente> obtenerClientesPoUsuario(@PathVariable("idclienteaplicacion") String idClienteAplicacion) {
		
		return this.cliente.obtenerClientesPorUsuario(Integer.parseInt(idClienteAplicacion));
	}
	
	@CrossOrigin(origins="*")
	@PostMapping("/obtener/proyectos/cliente")
	public List<Proyecto> obtenerProyectosPorCliente(@RequestBody Cliente cliente) {
		
		return this.asignacionCliente.obtenerProyectosPorCliente(cliente);
	}
	
	@CrossOrigin(origins="*")
	@PostMapping("/nuevo/cliente")
	public String nuevoCliente(@RequestBody Map<String, Object> cliente) {
		String respuesta = "errorGuardar";
		Gson gson = new Gson();
		String json = gson.toJson(cliente.get("cliente"));
		
		Cliente nuevoCliente = gson.fromJson(json, new TypeToken<Cliente>() {}.getType());
		json = gson.toJson(cliente.get("imagen"));
		
		List<Integer> listaBites = gson.fromJson(json, new TypeToken<List<Integer>>() {}.getType());
		
		nuevoCliente.setEstatus("ACTIVO");
		try {
			if ( !listaBites.isEmpty() ) {
				byte[] imagenClienteByte = new byte[listaBites.size()];
				int i = 0;
				for(int element : listaBites ) {
					imagenClienteByte[i] = (byte) element;
					i++;
				}
				File archivoImagen = File.createTempFile(nuevoCliente.getCliente(), ".png");
				FileUtils.writeByteArrayToFile(archivoImagen, imagenClienteByte);
				String urlImagen = guardarEvidencia(nuevoCliente, archivoImagen);
				nuevoCliente.setUrllogo(urlImagen);
				System.out.println(nuevoCliente);
				this.cliente.save(nuevoCliente);
				respuesta = "Cliente Guardado";
			}else {
				nuevoCliente.setUrllogo("");
				System.out.println(nuevoCliente);
				this.cliente.save(nuevoCliente);
				respuesta = "Cliente Guardado";
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return respuesta;
	}
	


public String guardarEvidencia(Cliente cliente, File file) {
		try {

			URL url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/drpool-45023.appspot.com/o/service%2Fgoogle-services.json?alt=media&token=c11fbea5-2b37-4807-a113-32603256c4fd");
			FileInputStream serviceAccount = new FileInputStream(descargarArchivo(url, "google-service-descarga.json"));
			String bucketName = "drpool-45023.appspot.com";
			boolean bandera = true;
			Storage storage = (Storage) getStrogaeOptions(serviceAccount).getService();

			if (file != null) {
				String objectName = "Clientes/" + cliente.getIdcliente() + "-"
						+ cliente.getCliente().toUpperCase() + "/" + cliente.getCliente() + ".png";

				Map<String, String> map = new HashMap<>();
				map.put("firebaseStorageDownloadTokens", cliente.getCliente().replace(" ", "") + ".png");

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
	
	private StorageOptions getStrogaeOptions(FileInputStream serviceAccount) throws IOException{
		StorageOptions storageOptions = ((StorageOptions.Builder) ((StorageOptions.Builder) StorageOptions.newBuilder()
				.setProjectId("drpool-45023")).setCredentials((Credentials) GoogleCredentials.fromStream(serviceAccount)))
						.build();
		return storageOptions;
	}
	
	private String descargarArchivo(URL url, String localFilename) throws IOException {
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

	private String generarUrl(String[] direccionTemporal, String token) {
		String url = "https://firebasestorage.googleapis.com/v0/b/drpool-45023.appspot.com/o/";

		return url + direccionTemporal[8] + "?alt=media&token=" + token;
	}
	
	
}
