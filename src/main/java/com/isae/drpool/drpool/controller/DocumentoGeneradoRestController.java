package com.isae.drpool.drpool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IDocumentoGenerado;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.entity.Documentogenerado;
import com.isae.drpool.drpool.entity.Inventario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
//@RequestMapping("/documento")
public class DocumentoGeneradoRestController {
	
	@Autowired
	private IDocumentoGenerado documentoGenerado;
	
	@Autowired
	private IInventarioDAO inventario;
	
	private static final String SECRET_KEY = "clave_secreta";
	
	@CrossOrigin(origins = "*")
	@PostMapping("/guardar/documento")
	public Documentogenerado guardarDocumento(
			@RequestBody Documentogenerado documento
			) {
		
		List<Documentogenerado> lista = this.documentoGenerado.obtenerDocumento(documento.getInventario().getIdinventario());
		if(lista.isEmpty()) {
			this.documentoGenerado.save(documento);
		}else {
			documento.setIddocumento(lista.get(0).getIddocumento());
			this.documentoGenerado.save(documento);
		}
		
		return documento;
		
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/documento/inventario/{idinventario}")
	public String obtenerDocumento(
			@PathVariable(value = "idinventario") String idinventario
			) {
		String respuesta = this.documentoGenerado.obtenerUrlPorIdInventario(Integer.parseInt(idinventario));
		return respuesta;
		
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/abrir/documento/inventario/{idinventario}")
	public ResponseEntity<InputStreamResource> abrirDocumento(
			@PathVariable(value = "idinventario") String idinventario
			) {
		String respuesta = this.documentoGenerado.obtenerUrlPorIdInventario(Integer.parseInt(idinventario));
		Inventario inventario = this.inventario.findById(Integer.parseInt(idinventario)).get();
		String nombre = inventario.getFolio() + ".pdf";
		try {
			 
			 FileInputStream archivo = new FileInputStream(
						descargarConector(new URL(respuesta), nombre));
			
	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + nombre);
//	        headers.add("Content-Disposition", "attachment; filename=" + nombre);

	        InputStreamResource resource = new InputStreamResource(archivo);
	        return ResponseEntity.ok()
	                .headers(headers)
	                .body(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
		
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
	
	private String generateToken(String filename) {
        LocalDateTime expirationDateTime = LocalDateTime.now().plusHours(2); // Fecha de expiración del token

        return Jwts.builder()
                .setSubject(filename)
                .setExpiration(Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

}
