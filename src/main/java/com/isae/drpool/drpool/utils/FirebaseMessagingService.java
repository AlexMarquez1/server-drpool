package com.isae.drpool.drpool.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

//@Service
public class FirebaseMessagingService {

	private final FirebaseMessaging firebaseMessaging;

	public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
		this.firebaseMessaging = firebaseMessaging;
	}

	public String sendNotification(Notificacion note, String token) throws FirebaseMessagingException {
		Notification notification;
		Map<String, String> data = obtenerMapa(note.getData());

		if (note.getImage().isEmpty()) {
			notification = Notification.builder().setTitle(note.getTitulo()).setBody(note.getContenido()).build();

		} else {
			notification = Notification.builder().setTitle(note.getTitulo()).setBody(note.getContenido())
					.setImage(note.getImage()).build();
		}

		Message message = Message.builder().setToken(token).setNotification(notification).putAllData(data).build();

		return firebaseMessaging.send(message);
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

	public BatchResponse sendNotification(Notificacion note, List<String> token) throws FirebaseMessagingException {

		Notification notification;
		Map<String, String> data = new HashMap<>();

		if (note.getImage().isEmpty()) {
			notification = Notification.builder().setTitle(note.getTitulo()).setBody(note.getContenido()).build();

		} else {
			notification = Notification.builder().setTitle(note.getTitulo()).setBody(note.getContenido())
					.setImage(note.getImage()).build();
		}

		MulticastMessage message = MulticastMessage.builder().setNotification(notification).putAllData(data)
				.addAllTokens(token).build();

		return firebaseMessaging.sendMulticast(message);
	}

}
