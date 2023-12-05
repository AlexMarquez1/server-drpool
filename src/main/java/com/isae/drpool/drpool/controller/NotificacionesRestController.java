package com.isae.drpool.drpool.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.isae.drpool.drpool.dao.IImagenNotificacionDAO;
import com.isae.drpool.drpool.dao.INotificacionDAO;
import com.isae.drpool.drpool.entity.Fotoevidencia;
import com.isae.drpool.drpool.entity.ImagenesNotificacion;
import com.isae.drpool.drpool.entity.Notificaciones;
import com.isae.drpool.drpool.entity.NotificacionesMovil;
import com.isae.drpool.drpool.entity.Usuario;
import com.isae.drpool.drpool.utils.FirebaseMessagingService;
import com.isae.drpool.drpool.utils.Notificacion;

@RestController
//@RequestMapping("/notificaciones")
public class NotificacionesRestController {
	
	@Autowired
	private INotificacionDAO notificacion;
	
	@Autowired
	private IImagenNotificacionDAO imagenNotificacion;

	
	@CrossOrigin(origins = "*")
	@PostMapping("/nueva/notificacion")
	public String eliminarAsignacionProyecto(
			@RequestBody Notificacion notificacion
			) throws IOException, FirebaseMessagingException {
		String respuesta = "";
		System.out.println("Se recibe la consulta: "+ notificacion.getData());
		
		FirebaseMessagingService firebase = new FirebaseMessagingService(firebaseMessaging());
		
			for(Usuario usuario : notificacion.getToken()) {
				firebase.sendNotification(notificacion, usuario.getToken());
				guardarNotificacion(notificacion, usuario);
			}
		
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/notificacion/usuario/{idusuario}")
	public List<NotificacionesMovil> obtenerNotificacionPorUsuario(
			@PathVariable(value = "idusuario") String idusuario
			) throws IOException, FirebaseMessagingException {
		List<NotificacionesMovil> respuesta = new ArrayList<NotificacionesMovil>();
		
		List<Notificaciones> notificaciones = this.notificacion.obtenerNotificacionPorUsuario(Integer.parseInt(idusuario));
		
		
		for(Notificaciones not : notificaciones) {
			Map<String, String> imagenesRespuesta = new HashMap<String, String>();
			List<ImagenesNotificacion> imagenes = this.imagenNotificacion.obtenerPorIdNotificacion(not.getIdnotificacion());
			for(ImagenesNotificacion img : imagenes) {
				imagenesRespuesta.put(img.getIdimagen()+"-"+img.getNombre(),img.getUrl());
			}
			respuesta.add(new NotificacionesMovil(
					not.getIdnotificacion(),
					not.getTitulo(),
					not.getContenido(),
					not.getPrioridad(),
					not.getRemitente(),
					not.getFecha(),
					not.getHora(),
					imagenesRespuesta,
					not.getAtendido()
					));
		}
		
		
		return respuesta;
	}

	
private void guardarNotificacion(Notificacion notificacion, Usuario usuario) {
	Map<String, String> map =obtenerMapa(notificacion.getData());
	String h = String.valueOf(ZonedDateTime.now().getHour()).length() == 1 ? "0"+String.valueOf(ZonedDateTime.now().getHour()) : String.valueOf(ZonedDateTime.now().getHour());
	String m = String.valueOf(ZonedDateTime.now().getMinute()).length() == 1 ? "0"+String.valueOf(ZonedDateTime.now().getMinute()) : String.valueOf(ZonedDateTime.now().getMinute());
	String hora = h+":"+m;
	Notificaciones not = new Notificaciones(
			0,
			notificacion.getTitulo(),
			notificacion.getContenido(),
			map.get("PRIORIDAD"),
			map.get("REMITENTE"),
			map.get("FECHA"),
			hora,
			usuario,
			"FALSE"
			);
	this.notificacion.save(not);
	Notificaciones notificacionGuardada = this.notificacion.obtenerPorId(this.notificacion.obtenerUltimoId());
	
	for(Fotoevidencia foto : notificacion.getImagenes()) {
		ImagenesNotificacion imagenesNotificacion = new ImagenesNotificacion(0,foto.getNombrefoto(),foto.getUrl(),notificacionGuardada);
		this.imagenNotificacion.save(imagenesNotificacion);
	}
}

private Map<String, String> obtenerMapa(String contenido) {

	String valor = contenido.substring(1, contenido.length() - 1);
	valor = valor.replace("\"", "");
	String[] llaveDato = valor.split(",");
	Map<String, String> map = new HashMap<>();

	for (String par : llaveDato) {
		String[] entry = par.split(":");
		if(entry[1].trim().equalsIgnoreCase("https") || entry[1].trim().equalsIgnoreCase("http")) {
			map.put(entry[0].trim(), entry[1].trim()+":"+entry[2].trim());
		}else {
			map.put(entry[0].trim(), entry[1].trim());
		}
	}

	return map;
}
	
private  FirebaseMessaging firebaseMessaging() throws IOException {
		
		//URL url = new URL("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
		URL url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/service%2Fdr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json?alt=media&token=e0caf9de-daa9-479d-904c-c1f323cd5012&_gl=1*1veermv*_ga*NTM3NzEyMjI5LjE2OTU5MzIzODU.*_ga_CW55HF8NVT*MTY5NTkzMjM4NS4xLjEuMTY5NTkzNDY0NS40NS4wLjA.");
		FileInputStream serviceAccount = new FileInputStream(descargarArchivo(url, "google-service-descarga.json"));
		
	    GoogleCredentials googleCredentials = GoogleCredentials
	            .fromStream(serviceAccount);
	    FirebaseOptions firebaseOptions = FirebaseOptions
	            .builder()
	            .setCredentials(googleCredentials)
	            .build();
	    FirebaseApp app = null;
	    if (FirebaseApp.getApps().isEmpty()) {
	    	 app = FirebaseApp.initializeApp(firebaseOptions);
		}else {
			app = FirebaseApp.getApps().get(0);
		}
	    System.out.println(FirebaseApp.getApps());
	    
	   
	    return FirebaseMessaging.getInstance(app);
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
}
