package com.isae.drpool.drpool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
import com.google.firebase.auth.FirebaseAuth;
import com.isae.drpool.drpool.dao.IFirmaDocumentosDAO;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.dao.IValoresDAO;
import com.isae.drpool.drpool.entity.Firma;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.firmasdocumento;

@RestController
//@RequestMapping("/firma")
public class FirmaDocumentosRestController {
	
	@Autowired
	private IFirmaDocumentosDAO firma;
	
	@Autowired
	private IInventarioDAO inventario;
	
	@Autowired
	private IValoresDAO valores;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/firmas")
	public List<firmasdocumento> getFirmas() {
		return this.firma.findAll();
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/firmas/documento/{idInventario}/{idCampo}")
	public List<firmasdocumento> getFirmasDocumento(
			@PathVariable(value = "idInventario") String idInventario,
			@PathVariable(value = "idCampo") String idCampo
			) {
		
		return this.firma.obtenerFirmasDocumento(Integer.parseInt(idCampo), Integer.parseInt(idInventario));
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/firmas/documento/byte/{idInventario}/{idCampo}")
	public List<Integer> getFirmasDocumentoBytes(
			@PathVariable(value = "idInventario") String idInventario,
			@PathVariable(value = "idCampo") String idCampo
			) {
		
		List<Integer> respuesta = new ArrayList<Integer>();
		List<firmasdocumento> firma = this.firma.obtenerFirmasDocumento(Integer.parseInt(idCampo), Integer.parseInt(idInventario));
		try {
			if(!firma.isEmpty()) {
				File firmaArchivo = new File(descargarFrima(new URL(firma.get(0).getUrl()),firma.get(0).getNombrefirma()+".png"));
				byte[] bytes = FileUtils.readFileToByteArray(firmaArchivo);
				 respuesta = new ArrayList<Integer>();
				
				for(int i = 0; i < bytes.length; i++) {
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
	
	private String descargarFrima(URL url, String localFilename) throws IOException {
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
	
	@CrossOrigin(origins = "*")
	@PostMapping("/guardar/firma/documento")
	public List<String> guardarFirma(
			@RequestBody firmasdocumento firma
			) {
		List<String> respuesta = new ArrayList<String>();
		
		List<firmasdocumento> lista = this.firma.obtenerConcidencia(firma.getNombrefirma(), firma.getCamposProyecto().getIdcamposproyecto(), firma.getInventario().getIdinventario());
		
		if(!lista.isEmpty()) {
			firma.setIdfirma(lista.get(0).getIdfirma());
			this.firma.save(firma);
		}else {
			this.firma.save(firma);
		}
		
		respuesta.add("Correcto");
		
		return respuesta;
		
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/actualizar/firma/registro")
	public List<String> actualizarFirma(
			@RequestBody Firma firma
			) {
		List<String> respuesta = new ArrayList<String>();
		
		List<firmasdocumento> lista = this.firma.obtenerConcidencia(firma.getNombreFirma(), firma.getCamposProyecto().getIdcamposproyecto(), firma.getInventario().getIdinventario());
		
		firmasdocumento nuevaFirma = new firmasdocumento(0,firma.getNombreFirma(),"",firma.getCamposProyecto(),firma.getInventario());
	
		nuevaFirma.setInventario(this.inventario.findById(nuevaFirma.getInventario().getIdinventario()).get());
		
		byte[] firmaByte = new byte[firma.getFirma().size()];

		int ind =0;
		for(int item : firma.getFirma()) {
			firmaByte [ind] = (byte) item;
			ind++;
		}
		
		try {
			File firmaFile = File.createTempFile(nuevaFirma.getNombrefirma(), ".png");
			
			FileUtils.writeByteArrayToFile(firmaFile, firmaByte);
			
			
			String urlFirma = guardarFirma(nuevaFirma.getInventario(), firmaFile, nuevaFirma.getNombrefirma());

			System.out.println("Tamaño de la firma: " + firma.getFirma().size());
			if(!lista.isEmpty()) {
				if(firma.getFirma().size() == 0) {
					this.firma.deleteById(lista.get(0).getIdfirma());
					this.valores.actualizaridValorValores("FALSE", lista.get(0).getCamposProyecto().getIdcamposproyecto(), lista.get(0).getInventario().getIdinventario());
				}else {
					nuevaFirma.setIdfirma(lista.get(0).getIdfirma());
					nuevaFirma.setUrl(urlFirma);
					this.firma.save(nuevaFirma);
					this.valores.actualizaridValorValores("TRUE", lista.get(0).getCamposProyecto().getIdcamposproyecto(), lista.get(0).getInventario().getIdinventario());
				}
			}else {
				if(firma.getFirma().size() != 0) {
					nuevaFirma.setUrl(urlFirma);
					this.firma.save(nuevaFirma);
					this.valores.actualizaridValorValores("TRUE", lista.get(0).getCamposProyecto().getIdcamposproyecto(), lista.get(0).getInventario().getIdinventario());
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		respuesta.add("Correcto");
		
		return respuesta;
		
	}
	
	public String guardarFirma(Inventario inventario, File file, String nombreFirma) {
		try {
			
	
			//URL url = new URL("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
			URL url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/service%2Fdr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json?alt=media&token=e0caf9de-daa9-479d-904c-c1f323cd5012&_gl=1*1veermv*_ga*NTM3NzEyMjI5LjE2OTU5MzIzODU.*_ga_CW55HF8NVT*MTY5NTkzMjM4NS4xLjEuMTY5NTkzNDY0NS40NS4wLjA.");
			FileInputStream serviceAccount = new FileInputStream(descargarFrima(url, "dr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json"));
			//String bucketName = "isae-de6da.appspot.com";
			String bucketName = "dr-pool-eca1c.appspot.com";
			boolean bandera = true;
			Storage storage = (Storage) getStrogaeOptions(serviceAccount).getService();

			if (file != null) {
				String objectName = "Proyectos/" + inventario.getProyecto().getIdproyecto() + "-"
						+ inventario.getProyecto().getProyecto().toUpperCase() + "/" + inventario.getIdinventario()
						+ "-"+inventario.getFolio()+"/Firmas/"+nombreFirma;

				Map<String, String> map = new HashMap<>();
		        map.put("firebaseStorageDownloadTokens", nombreFirma.replace(" ", "")+".png");
		        
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
	
	private String generarUrl(String [] direccionTemporal, String token) {
		String url = "https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/";
		//String url= "https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/";
		
		return url+direccionTemporal[8]+"?alt=media&token="+token;
	}
	
	
	
	
	private StorageOptions getStrogaeOptions(FileInputStream serviceAccount) throws IOException {
		StorageOptions storageOptions = ((StorageOptions.Builder) ((StorageOptions.Builder) StorageOptions.newBuilder()
				.setProjectId("dr-pool-eca1c")).setCredentials((Credentials) GoogleCredentials.fromStream(serviceAccount)))
						.build();
		return storageOptions;
	}
	
}
