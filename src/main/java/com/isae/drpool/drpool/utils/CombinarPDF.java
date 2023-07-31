package com.isae.drpool.drpool.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class CombinarPDF {

	private static String combinarPdf(List<InputStream> inputPdfList, ByteArrayOutputStream baos, String paginas){
		
		String respuesta = "correcto";
		List<Integer> pages = new ArrayList<Integer>();
		if(!paginas.equals("0")) {
			for(String item : paginas.split("-")) {
				pages.add(Integer.parseInt(item));
			}
		}
		
		// Create document and pdfReader objects.
		Document document = new Document();
		List<PdfReader> readers = new ArrayList<PdfReader>();
		int totalPages = 0; // Create pdf Iterator object using inputPdfList.
		Iterator<InputStream> pdfIterator = inputPdfList.iterator();
		// Create reader list for the input pdf files.
		int documentoActual = 0;
		
		try {
			while (pdfIterator.hasNext()) {
				InputStream pdf = pdfIterator.next();
				PdfReader pdfReader = new PdfReader(pdf);
				if(!paginas.equals("0")) {
					pdfReader.selectPages(pages);
				}
				
				readers.add(pdfReader);
				totalPages = totalPages + pdfReader.getNumberOfPages();
				documentoActual++;
			}
			System.out.println("Fin del while");
			// Create writer for the outputStream
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			// Open document.
			document.open();
			// Contain the pdf data.
			PdfContentByte pageContentByte = writer.getDirectContent();
			PdfImportedPage pdfImportedPage;
			int currentPdfReaderPage = 1;
			Iterator<PdfReader> iteratorPDFReader = readers.iterator();
			// Iterate and process the reader list.
			while (iteratorPDFReader.hasNext()) {
				PdfReader pdfReader = iteratorPDFReader.next();
				// Create page and add content.
				while (currentPdfReaderPage <= pdfReader.getNumberOfPages()) {
					document.newPage();
					pdfImportedPage = writer.getImportedPage(pdfReader, currentPdfReaderPage);
					pageContentByte.addTemplate(pdfImportedPage, 0, 0);
					currentPdfReaderPage++;
				}
				currentPdfReaderPage = 1;
			}
			System.out.println("Pdf files merged successfully.");
			
			return "correcto";
			
		} catch (IOException e) {
			// TODO: handle exception
			respuesta = "Error leyendo el documento:" + documentoActual;
			System.out.println(respuesta);
//			e.printStackTrace();
			
		}catch(DocumentException ex) {
			respuesta = "Error escribiendo en el documento:" + documentoActual;
			System.out.println(respuesta);
			ex.printStackTrace();
		}finally {
			try {
				// Close document and outputStream.
				baos.flush();
				document.close();
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				respuesta = "Error cerrando el documento:" + documentoActual;
				System.out.println(respuesta);
				e.printStackTrace();
			}
		}
		return respuesta;
		
	}

	@Async("threadPoolTaskExecutor")
	public Map<String, Object> generarPdf(List<String> documentos, String nombreProyecto, String paginas) {
		Map<String, Object> respuesta  = new HashMap<String,Object>();
		try {
			// Prepare input pdf file list as list of input stream.
			List<InputStream> inputPdfList = new ArrayList<InputStream>();
			int ind = 0;
			for (String url : documentos) {
				String direccion = descargarDocumento(new URL(url), "documento" + ind + ".pdf");
				System.out.println("Direccion: " + direccion);
				inputPdfList.add(new FileInputStream(direccion));
				ind++;
			}
			// Prepare output stream for merged pdf file.
			String tempDir = System.getProperty("java.io.tmpdir");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			OutputStream outputStream = new FileOutputStream(tempDir + "/Evidencias-"+ nombreProyecto+".pdf");
//			OutputStream outputStream = new FileOutputStream("./Evidencias-" + nombreProyecto + ".pdf");
			// call method to merge pdf files.
			String res = combinarPdf(inputPdfList, baos, paginas);
			respuesta.put("respuesta", res);
			if(res.equals("correcto")) {
				
				List<Integer> pdfInteger = new ArrayList<Integer>();
				try {
//					baos.write(new byte[1024]);
//					System.out.println("Antes de leer");
//					baos.writeTo(outputStream);
//					System.out.println("Despues de leer");
					
					byte[] pdfByte = baos.toByteArray();
					System.out.println(pdfByte.length);
					
					pdfInteger = new ArrayList<Integer>();

					for (int i = 0; i < pdfByte.length; i++) {
						pdfInteger.add((int) pdfByte[i]);
					}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Error en el metodo principal");
						e.printStackTrace();
					}
				respuesta.put("pdf", pdfInteger);
			}

			return respuesta;
		} catch (Exception e) {
			System.out.println("Error al generar el PDF");
			respuesta.put("respuesta", "Error al generar el PDF");
			e.printStackTrace();
		}
		return respuesta;
	}

	private static String descargarDocumento(URL url, String localFilename) throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;

		String tempDir = System.getProperty("java.io.tmpdir");
		String outputPath = tempDir + localFilename;

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
