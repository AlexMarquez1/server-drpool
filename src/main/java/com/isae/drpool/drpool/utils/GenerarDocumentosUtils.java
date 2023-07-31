package com.isae.drpool.drpool.utils;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.isae.drpool.drpool.dao.IValoresDAO;
import com.isae.drpool.drpool.entity.AsistenciaUsuario;
import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.ReporteAsistencia;
import com.isae.drpool.drpool.entity.Valore;
import com.isae.drpool.drpool.entity.VistaDatosISSSTE;

public class GenerarDocumentosUtils {
	
	
	
	
	//Inicio Prueba
	
	public ByteArrayInputStream generarExcelPrueba(List<String> dias, List<String> horas,
			List<AsistenciaUsuario> listaAsistencia, List<ReporteAsistencia> listaRegistro)
			throws IOException, ParseException {
		int columns = dias.size();// para generar total de columnas por ID encontrados
		int totalDias = dias.size();
		int totalRetardos = 0;
		int faltasAcumuladas = 0;
		int faltas = dias.size();
		int asistencias = 0;
		int totalAsistencias = 0;
		int totalFaltas = 0;
		try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Asistencias");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.WHITE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
			headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// Aplicar borde a las celdas
			headerCellStyle.setBorderBottom(BorderStyle.HAIR);
			headerCellStyle.setBorderTop(BorderStyle.HAIR);
			headerCellStyle.setBorderLeft(BorderStyle.HAIR);
			headerCellStyle.setBorderRight(BorderStyle.HAIR);

			// Header Row
			Row headerRow = sheet.createRow(0);
			Cell encabezado = headerRow.createCell(0);
			encabezado.setCellValue("Usuario");
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
			encabezado.setCellStyle(headerCellStyle);

			int col = 1;
			for (String dia : dias) {
				encabezado = headerRow.createCell(col);
				encabezado.setCellValue(dia);
				encabezado.setCellStyle(headerCellStyle);
				col += 2;
			}

			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Total dias");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Total retardos");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Faltas acumuladas por retardo");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Faltas");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Total Faltas");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Asistencia");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Total asistencia");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));

			Row headerRow2 = sheet.createRow(1);

			Cell campoHora;
			for (int i = 0; i < horas.size(); i++) {
				campoHora = headerRow2.createCell(i + 1);
				campoHora.setCellValue(horas.get(i));
				sheet.autoSizeColumn(i + 1);
				campoHora.setCellStyle(headerCellStyle);
			}
			Cell campoDatos;
			int celda = 2;
			int fila = 0;
			boolean mismoUsuario = false;
			Row datos = sheet.createRow(celda++);
			boolean faltaEncontrada = true;
			int indDia = 0;
			CellStyle cellStyle = workbook.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			cellStyle.setBorderBottom(BorderStyle.HAIR);
			cellStyle.setBorderTop(BorderStyle.HAIR);
			cellStyle.setBorderLeft(BorderStyle.HAIR);
			cellStyle.setBorderRight(BorderStyle.HAIR);

			Font faltaFont = workbook.createFont();
			faltaFont.setBold(true);
			faltaFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle faltaCellStyle = workbook.createCellStyle();
			faltaCellStyle.setFont(faltaFont);
			faltaCellStyle.setAlignment(HorizontalAlignment.CENTER);
			faltaCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
			byte r = BigInteger.valueOf(Integer.valueOf(246)).byteValue();
			byte g = BigInteger.valueOf(Integer.valueOf(157)).byteValue();
			byte b = BigInteger.valueOf(Integer.valueOf(157)).byteValue();

			HSSFColor ColorFaltas = setColor((HSSFWorkbook) workbook, r, g, b);

			faltaCellStyle.setFillForegroundColor(ColorFaltas.getIndex());
			faltaCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			faltaCellStyle.setBorderBottom(BorderStyle.HAIR);
			faltaCellStyle.setBorderTop(BorderStyle.HAIR);
			faltaCellStyle.setBorderLeft(BorderStyle.HAIR);
			faltaCellStyle.setBorderRight(BorderStyle.HAIR);

			Font RetardoFont = workbook.createFont();
			RetardoFont.setBold(true);
			RetardoFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle RetardoCellStyle = workbook.createCellStyle();
			RetardoCellStyle.setFont(faltaFont);
			RetardoCellStyle.setAlignment(HorizontalAlignment.CENTER);
			RetardoCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());

			r = BigInteger.valueOf(Integer.valueOf(255)).byteValue();
			g = BigInteger.valueOf(Integer.valueOf(204)).byteValue();
			b = BigInteger.valueOf(Integer.valueOf(153)).byteValue();

			HSSFColor colorRetardos = setColor((HSSFWorkbook) workbook, r, g, b);

			RetardoCellStyle.setFillForegroundColor(colorRetardos.getIndex());
			RetardoCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			RetardoCellStyle.setBorderBottom(BorderStyle.HAIR);
			RetardoCellStyle.setBorderTop(BorderStyle.HAIR);
			RetardoCellStyle.setBorderLeft(BorderStyle.HAIR);
			RetardoCellStyle.setBorderRight(BorderStyle.HAIR);

			int posicionRetardos = 0;
			int posicionAsistencias = 0;
			int posicionTotalAsistencia = 0;
			int posicionFaltas = 0;
			int posicionTotalFaltas = 0;
			int posicionFaltasAcumuladas = 0;
			int conteoRetardos = 0;
			for (int i = 0; i < listaAsistencia.size(); i++) {
				if (mismoUsuario) {
					faltaEncontrada = true;
					while (faltaEncontrada) {
						if (indDia < dias.size() - 1) {
							if (dias.get(indDia).equalsIgnoreCase(listaAsistencia.get(i).getDia())) {
								faltaEncontrada = false;
								indDia++;
							} else {
								fila += 2;
								indDia++;

							}
						} else {
							faltaEncontrada = false;
						}
					}

					campoDatos = datos.createCell(fila);
					if (listaAsistencia.get(i).getHoraEntrada() != null) {
						campoDatos.setCellValue(listaAsistencia.get(i).getHoraEntrada());
						if (Integer.parseInt(listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 9
								&& Integer.parseInt(listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 15) {
							campoDatos.setCellStyle(RetardoCellStyle);
							totalRetardos++;
							asistencias++;
							faltas--;
							conteoRetardos++;
							if (conteoRetardos == 3) {
								conteoRetardos = 0;
								faltasAcumuladas++;
							}
						} else {
							campoDatos.setCellStyle(cellStyle);
							asistencias++;
							faltas--;
						}
					} else {
						campoDatos.setCellValue("-");
					}
					sheet.autoSizeColumn(fila++);
					campoDatos = datos.createCell(fila);
					campoDatos.setCellValue(listaAsistencia.get(i).getHoraSalida() == null ? "-"
							: listaAsistencia.get(i).getHoraSalida());
					campoDatos
							.setCellStyle(listaAsistencia.get(i).getHoraSalida() == null ? faltaCellStyle : cellStyle);
					sheet.autoSizeColumn(fila++);

				} else {
					int fechaAux = 1;
					for (int j = 0; j < dias.size(); j++) {
						campoDatos = datos.createCell(fechaAux);
						campoDatos.setCellStyle(faltaCellStyle);
						campoDatos.setCellValue("-");
						sheet.autoSizeColumn(fechaAux++);
						campoDatos = datos.createCell(fechaAux);
						campoDatos.setCellValue("-");
						campoDatos.setCellStyle(faltaCellStyle);
						sheet.autoSizeColumn(fechaAux++);
					}
					campoDatos = datos.createCell(fechaAux);
					campoDatos.setCellValue(totalDias);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress((celda - 1), (celda - 1), fechaAux, fechaAux + 1));

					fechaAux += 2;
					posicionRetardos = fechaAux;
					fechaAux += 2;
					posicionFaltasAcumuladas = fechaAux;
					fechaAux += 2;
					posicionFaltas = fechaAux;
					fechaAux += 2;
					posicionTotalFaltas = fechaAux;
					fechaAux += 2;
					posicionAsistencias = fechaAux;
					fechaAux += 2;
					posicionTotalAsistencia = fechaAux;

					campoDatos = datos.createCell(fila);
					campoDatos.setCellValue(listaAsistencia.get(i).getUsuario());
					sheet.autoSizeColumn(fila++);
					for (int j = 0; j < dias.size(); j++) {
						if (listaAsistencia.get(i).getDia() != null) {
							if (listaAsistencia.get(i).getDia().equalsIgnoreCase(dias.get(j))) {
								if (j == 0) {
									fila = 1;
									campoDatos = datos.createCell(fila);
									if (listaAsistencia.get(i).getHoraEntrada() != null) {
										campoDatos.setCellValue(listaAsistencia.get(i).getHoraEntrada());
										if (Integer.parseInt(listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 9
												&& Integer.parseInt(
														listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 10) {
											campoDatos.setCellStyle(RetardoCellStyle);
											totalRetardos++;
											asistencias++;
											faltas--;
											conteoRetardos++;
											if (conteoRetardos == 3) {
												conteoRetardos = 0;
												faltasAcumuladas++;
											}
										} else {
											campoDatos.setCellStyle(cellStyle);
											asistencias++;
											faltas--;
										}
									} else {
										campoDatos.setCellValue("-");
									}
									sheet.autoSizeColumn(fila++);
									campoDatos = datos.createCell(fila);
									campoDatos.setCellValue(listaAsistencia.get(i).getHoraSalida() == null ? "-"
											: listaAsistencia.get(i).getHoraSalida());
									campoDatos.setCellStyle(
											listaAsistencia.get(i).getHoraSalida() == null ? faltaCellStyle
													: cellStyle);
									sheet.autoSizeColumn(fila++);
									indDia++;
									break;
								} else {
									campoDatos = datos.createCell(fila);
									if (listaAsistencia.get(i).getHoraEntrada() != null) {
										campoDatos.setCellValue(listaAsistencia.get(i).getHoraEntrada());
										if (Integer.parseInt(listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 9
												&& Integer.parseInt(
														listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 10) {
											campoDatos.setCellStyle(RetardoCellStyle);
											totalRetardos++;
											asistencias++;
											faltas--;
											conteoRetardos++;
											if (conteoRetardos == 3) {
												conteoRetardos = 0;
												faltasAcumuladas++;
											}
										} else {
											campoDatos.setCellStyle(cellStyle);
											asistencias++;
											faltas--;
										}
									} else {
										campoDatos.setCellValue("-");
									}
									sheet.autoSizeColumn(fila++);
									campoDatos = datos.createCell(fila);
									campoDatos.setCellValue(listaAsistencia.get(i).getHoraSalida() == null ? "-"
											: listaAsistencia.get(i).getHoraSalida());
									campoDatos.setCellStyle(
											listaAsistencia.get(i).getHoraSalida() == null ? faltaCellStyle
													: cellStyle);
									sheet.autoSizeColumn(fila++);
									indDia++;
									break;
								}
							} else {
								fila += 2;
								indDia++;
							}
						} else {
							if (j == 0) {
								fila = 1;
							}
							campoDatos = datos.createCell(fila);
							campoDatos.setCellValue("-");
							campoDatos.setCellStyle(faltaCellStyle);
							sheet.autoSizeColumn(fila++);
							campoDatos = datos.createCell(fila);
							campoDatos.setCellValue("-");
							campoDatos.setCellStyle(faltaCellStyle);
							sheet.autoSizeColumn(fila++);
						}
					}

				}
				if (listaAsistencia.get(i).getUsuario().equalsIgnoreCase(
						listaAsistencia.get(i != (listaAsistencia.size() - 1) ? i + 1 : 0).getUsuario())) {
					mismoUsuario = true;
				} else {

					totalFaltas = faltasAcumuladas + faltas;
					if (totalFaltas > asistencias) {
						totalAsistencias = 0;
					} else {
						totalAsistencias = asistencias - totalFaltas;
					}

					campoDatos = datos.createCell(posicionRetardos);
					campoDatos.setCellValue(totalRetardos);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(
							new CellRangeAddress((celda - 1), (celda - 1), posicionRetardos, posicionRetardos + 1));

					campoDatos = datos.createCell(posicionFaltasAcumuladas);
					campoDatos.setCellValue(faltasAcumuladas);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress((celda - 1), (celda - 1), posicionFaltasAcumuladas,
							posicionFaltasAcumuladas + 1));

					campoDatos = datos.createCell(posicionFaltas);
					campoDatos.setCellValue(faltas);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(
							new CellRangeAddress((celda - 1), (celda - 1), posicionFaltas, posicionFaltas + 1));

					campoDatos = datos.createCell(posicionTotalFaltas);
					campoDatos.setCellValue(totalFaltas);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress((celda - 1), (celda - 1), posicionTotalFaltas,
							posicionTotalFaltas + 1));

					campoDatos = datos.createCell(posicionAsistencias);
					campoDatos.setCellValue(asistencias);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress((celda - 1), (celda - 1), posicionAsistencias,
							posicionAsistencias + 1));

					campoDatos = datos.createCell(posicionTotalAsistencia);
					campoDatos.setCellValue(totalAsistencias);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress((celda - 1), (celda - 1), posicionTotalAsistencia,
							posicionTotalAsistencia + 1));

					faltas = dias.size();
					asistencias = 0;
					totalAsistencias = 0;
					totalRetardos = 0;
					faltasAcumuladas = 0;
					totalFaltas = 0;
					mismoUsuario = false;
					conteoRetardos = 0;
					datos = sheet.createRow(celda++);
					fila = 0;
					indDia = 0;
				}
			}

			int firstCol = 1;
			int lastCol = 2;
			for (int colx = 1; colx < columns + 1; colx++) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, firstCol, lastCol));
				firstCol += 2;
				lastCol += 2;
				// asi voy fucionando las columnas de 2 en 2 según numero de registro
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	// Fin de la prueba
	
	
	
	
	

	public ByteArrayInputStream generarExcel(List<String> dias, List<String> horas,
			List<AsistenciaUsuario> listaAsistencia, List<ReporteAsistencia> listaRegistro)
			throws IOException, ParseException {
		int columns = dias.size();// para generar total de columnas por ID encontrados
		int totalDias = dias.size();
		int totalRetardos = 0;
		int faltasAcumuladas = 0;
		int faltas = dias.size();
		int asistencias = 0;
		int totalAsistencias = 0;
		int totalFaltas = 0;
		try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Asistencias");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.WHITE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
			headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// Aplicar borde a las celdas
			headerCellStyle.setBorderBottom(BorderStyle.HAIR);
			headerCellStyle.setBorderTop(BorderStyle.HAIR);
			headerCellStyle.setBorderLeft(BorderStyle.HAIR);
			headerCellStyle.setBorderRight(BorderStyle.HAIR);

			// Header Row
			Row headerRow = sheet.createRow(0);
			Cell encabezado = headerRow.createCell(0);
			encabezado.setCellValue("Usuario");
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
			encabezado.setCellStyle(headerCellStyle);

			int col = 1;
			for (String dia : dias) {
				encabezado = headerRow.createCell(col);
				encabezado.setCellValue(dia);
				encabezado.setCellStyle(headerCellStyle);
				col += 2;
			}

			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Total dias");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Total retardos");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Faltas acumuladas por retardo");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Faltas");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Total Faltas");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Asistencia");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));
			col += 2;
			encabezado = headerRow.createCell(col);
			encabezado.setCellValue("Total asistencia");
			encabezado.setCellStyle(headerCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col + 1));

			Row headerRow2 = sheet.createRow(1);

			Cell campoHora;
			for (int i = 0; i < horas.size(); i++) {
				campoHora = headerRow2.createCell(i + 1);
				campoHora.setCellValue(horas.get(i));
				sheet.autoSizeColumn(i + 1);
				campoHora.setCellStyle(headerCellStyle);
			}
			Cell campoDatos;
			int celda = 2;
			int fila = 0;
			boolean mismoUsuario = false;
			Row datos = sheet.createRow(celda++);
			boolean faltaEncontrada = true;
			int indDia = 0;
			CellStyle cellStyle = workbook.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			cellStyle.setBorderBottom(BorderStyle.HAIR);
			cellStyle.setBorderTop(BorderStyle.HAIR);
			cellStyle.setBorderLeft(BorderStyle.HAIR);
			cellStyle.setBorderRight(BorderStyle.HAIR);

			Font faltaFont = workbook.createFont();
			faltaFont.setBold(true);
			faltaFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle faltaCellStyle = workbook.createCellStyle();
			faltaCellStyle.setFont(faltaFont);
			faltaCellStyle.setAlignment(HorizontalAlignment.CENTER);
			faltaCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
			byte r = BigInteger.valueOf(Integer.valueOf(246)).byteValue();
			byte g = BigInteger.valueOf(Integer.valueOf(157)).byteValue();
			byte b = BigInteger.valueOf(Integer.valueOf(157)).byteValue();

			HSSFColor ColorFaltas = setColor((HSSFWorkbook) workbook, r, g, b);

			faltaCellStyle.setFillForegroundColor(ColorFaltas.getIndex());
			faltaCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			faltaCellStyle.setBorderBottom(BorderStyle.HAIR);
			faltaCellStyle.setBorderTop(BorderStyle.HAIR);
			faltaCellStyle.setBorderLeft(BorderStyle.HAIR);
			faltaCellStyle.setBorderRight(BorderStyle.HAIR);

			Font RetardoFont = workbook.createFont();
			RetardoFont.setBold(true);
			RetardoFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle RetardoCellStyle = workbook.createCellStyle();
			RetardoCellStyle.setFont(faltaFont);
			RetardoCellStyle.setAlignment(HorizontalAlignment.CENTER);
			RetardoCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());

			r = BigInteger.valueOf(Integer.valueOf(255)).byteValue();
			g = BigInteger.valueOf(Integer.valueOf(204)).byteValue();
			b = BigInteger.valueOf(Integer.valueOf(153)).byteValue();

			HSSFColor colorRetardos = setColor((HSSFWorkbook) workbook, r, g, b);

			RetardoCellStyle.setFillForegroundColor(colorRetardos.getIndex());
			RetardoCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			RetardoCellStyle.setBorderBottom(BorderStyle.HAIR);
			RetardoCellStyle.setBorderTop(BorderStyle.HAIR);
			RetardoCellStyle.setBorderLeft(BorderStyle.HAIR);
			RetardoCellStyle.setBorderRight(BorderStyle.HAIR);

			int posicionRetardos = 0;
			int posicionAsistencias = 0;
			int posicionTotalAsistencia = 0;
			int posicionFaltas = 0;
			int posicionTotalFaltas = 0;
			int posicionFaltasAcumuladas = 0;
			int conteoRetardos = 0;
			for (int i = 0; i < listaAsistencia.size(); i++) {
				if (mismoUsuario) {
					faltaEncontrada = true;
					while (faltaEncontrada) {
						if (indDia < dias.size() - 1) {
							if (dias.get(indDia).equalsIgnoreCase(listaAsistencia.get(i).getDia())) {
								faltaEncontrada = false;
								indDia++;
							} else {
								fila += 2;
								indDia++;

							}
						} else {
							faltaEncontrada = false;
						}
					}

					campoDatos = datos.createCell(fila);
					if (listaAsistencia.get(i).getHoraEntrada() != null) {
						campoDatos.setCellValue(listaAsistencia.get(i).getHoraEntrada());
						if (Integer.parseInt(listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 9
								&& Integer.parseInt(listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 10) {
							campoDatos.setCellStyle(RetardoCellStyle);
							totalRetardos++;
							asistencias++;
							faltas--;
							conteoRetardos++;
							if (conteoRetardos == 3) {
								conteoRetardos = 0;
								faltasAcumuladas++;
							}
						} else {
							campoDatos.setCellStyle(cellStyle);
							asistencias++;
							faltas--;
						}
					} else {
						campoDatos.setCellValue("-");
					}
					sheet.autoSizeColumn(fila++);
					campoDatos = datos.createCell(fila);
					campoDatos.setCellValue(listaAsistencia.get(i).getHoraSalida() == null ? "-"
							: listaAsistencia.get(i).getHoraSalida());
					campoDatos
							.setCellStyle(listaAsistencia.get(i).getHoraSalida() == null ? faltaCellStyle : cellStyle);
					sheet.autoSizeColumn(fila++);

				} else {
					int fechaAux = 1;
					for (int j = 0; j < dias.size(); j++) {
						campoDatos = datos.createCell(fechaAux);
						campoDatos.setCellStyle(faltaCellStyle);
						campoDatos.setCellValue("-");
						sheet.autoSizeColumn(fechaAux++);
						campoDatos = datos.createCell(fechaAux);
						campoDatos.setCellValue("-");
						campoDatos.setCellStyle(faltaCellStyle);
						sheet.autoSizeColumn(fechaAux++);
					}
					campoDatos = datos.createCell(fechaAux);
					campoDatos.setCellValue(totalDias);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress((celda - 1), (celda - 1), fechaAux, fechaAux + 1));

					fechaAux += 2;
					posicionRetardos = fechaAux;
					fechaAux += 2;
					posicionFaltasAcumuladas = fechaAux;
					fechaAux += 2;
					posicionFaltas = fechaAux;
					fechaAux += 2;
					posicionTotalFaltas = fechaAux;
					fechaAux += 2;
					posicionAsistencias = fechaAux;
					fechaAux += 2;
					posicionTotalAsistencia = fechaAux;

					campoDatos = datos.createCell(fila);
					campoDatos.setCellValue(listaAsistencia.get(i).getUsuario());
					sheet.autoSizeColumn(fila++);
					for (int j = 0; j < dias.size(); j++) {
						if (listaAsistencia.get(i).getDia() != null) {
							if (listaAsistencia.get(i).getDia().equalsIgnoreCase(dias.get(j))) {
								if (j == 0) {
									fila = 1;
									campoDatos = datos.createCell(fila);
									if (listaAsistencia.get(i).getHoraEntrada() != null) {
										campoDatos.setCellValue(listaAsistencia.get(i).getHoraEntrada());
										if (Integer.parseInt(listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 9
												&& Integer.parseInt(
														listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 10) {
											campoDatos.setCellStyle(RetardoCellStyle);
											totalRetardos++;
											asistencias++;
											faltas--;
											conteoRetardos++;
											if (conteoRetardos == 3) {
												conteoRetardos = 0;
												faltasAcumuladas++;
											}
										} else {
											campoDatos.setCellStyle(cellStyle);
											asistencias++;
											faltas--;
										}
									} else {
										campoDatos.setCellValue("-");
									}
									sheet.autoSizeColumn(fila++);
									campoDatos = datos.createCell(fila);
									campoDatos.setCellValue(listaAsistencia.get(i).getHoraSalida() == null ? "-"
											: listaAsistencia.get(i).getHoraSalida());
									campoDatos.setCellStyle(
											listaAsistencia.get(i).getHoraSalida() == null ? faltaCellStyle
													: cellStyle);
									sheet.autoSizeColumn(fila++);
									indDia++;
									break;
								} else {
									campoDatos = datos.createCell(fila);
									if (listaAsistencia.get(i).getHoraEntrada() != null) {
										campoDatos.setCellValue(listaAsistencia.get(i).getHoraEntrada());
										if (Integer.parseInt(listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 9
												&& Integer.parseInt(
														listaAsistencia.get(i).getHoraEntrada().split(":")[0]) >= 10) {
											campoDatos.setCellStyle(RetardoCellStyle);
											totalRetardos++;
											asistencias++;
											faltas--;
											conteoRetardos++;
											if (conteoRetardos == 3) {
												conteoRetardos = 0;
												faltasAcumuladas++;
											}
										} else {
											campoDatos.setCellStyle(cellStyle);
											asistencias++;
											faltas--;
										}
									} else {
										campoDatos.setCellValue("-");
									}
									sheet.autoSizeColumn(fila++);
									campoDatos = datos.createCell(fila);
									campoDatos.setCellValue(listaAsistencia.get(i).getHoraSalida() == null ? "-"
											: listaAsistencia.get(i).getHoraSalida());
									campoDatos.setCellStyle(
											listaAsistencia.get(i).getHoraSalida() == null ? faltaCellStyle
													: cellStyle);
									sheet.autoSizeColumn(fila++);
									indDia++;
									break;
								}
							} else {
								fila += 2;
								indDia++;
							}
						} else {
							if (j == 0) {
								fila = 1;
							}
							campoDatos = datos.createCell(fila);
							campoDatos.setCellValue("-");
							campoDatos.setCellStyle(faltaCellStyle);
							sheet.autoSizeColumn(fila++);
							campoDatos = datos.createCell(fila);
							campoDatos.setCellValue("-");
							campoDatos.setCellStyle(faltaCellStyle);
							sheet.autoSizeColumn(fila++);
						}
					}

				}
				if (listaAsistencia.get(i).getUsuario().equalsIgnoreCase(
						listaAsistencia.get(i != (listaAsistencia.size() - 1) ? i + 1 : 0).getUsuario())) {
					mismoUsuario = true;
				} else {

					totalFaltas = faltasAcumuladas + faltas;
					if (totalFaltas > asistencias) {
						totalAsistencias = 0;
					} else {
						totalAsistencias = asistencias - totalFaltas;
					}

					campoDatos = datos.createCell(posicionRetardos);
					campoDatos.setCellValue(totalRetardos);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(
							new CellRangeAddress((celda - 1), (celda - 1), posicionRetardos, posicionRetardos + 1));

					campoDatos = datos.createCell(posicionFaltasAcumuladas);
					campoDatos.setCellValue(faltasAcumuladas);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress((celda - 1), (celda - 1), posicionFaltasAcumuladas,
							posicionFaltasAcumuladas + 1));

					campoDatos = datos.createCell(posicionFaltas);
					campoDatos.setCellValue(faltas);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(
							new CellRangeAddress((celda - 1), (celda - 1), posicionFaltas, posicionFaltas + 1));

					campoDatos = datos.createCell(posicionTotalFaltas);
					campoDatos.setCellValue(totalFaltas);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress((celda - 1), (celda - 1), posicionTotalFaltas,
							posicionTotalFaltas + 1));

					campoDatos = datos.createCell(posicionAsistencias);
					campoDatos.setCellValue(asistencias);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress((celda - 1), (celda - 1), posicionAsistencias,
							posicionAsistencias + 1));

					campoDatos = datos.createCell(posicionTotalAsistencia);
					campoDatos.setCellValue(totalAsistencias);
					campoDatos.setCellStyle(cellStyle);
					sheet.addMergedRegion(new CellRangeAddress((celda - 1), (celda - 1), posicionTotalAsistencia,
							posicionTotalAsistencia + 1));

					faltas = dias.size();
					asistencias = 0;
					totalAsistencias = 0;
					totalRetardos = 0;
					faltasAcumuladas = 0;
					totalFaltas = 0;
					mismoUsuario = false;
					conteoRetardos = 0;
					datos = sheet.createRow(celda++);
					fila = 0;
					indDia = 0;
				}
			}

			int firstCol = 1;
			int lastCol = 2;
			for (int colx = 1; colx < columns + 1; colx++) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, firstCol, lastCol));
				firstCol += 2;
				lastCol += 2;
				// asi voy fucionando las columnas de 2 en 2 según numero de registro
			}

			// ------------------Inicia segunda hora-----------------
			Sheet sheet2 = workbook.createSheet("Reporte");
			Font headerFont2 = workbook.createFont();
			headerFont2.setBold(true);
			headerFont2.setColor(IndexedColors.WHITE.getIndex());

			celda = 0;
			// Header Row
			Row rowTitulo = sheet2.createRow(celda++);
			Cell encabezado2 = rowTitulo.createCell(0);

			encabezado2 = rowTitulo.createCell(0);
			encabezado2.setCellValue("RESPONSABLE");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(0);

			encabezado2 = rowTitulo.createCell(1);
			encabezado2.setCellValue("PROYECTO");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(1);

			encabezado2 = rowTitulo.createCell(2);
			encabezado2.setCellValue("NOMBRE DEL COLABORADOR");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(2);

			encabezado2 = rowTitulo.createCell(3);
			encabezado2.setCellValue("PUESTO");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(3);

			encabezado2 = rowTitulo.createCell(4);
			encabezado2.setCellValue("ZONA");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(4);

			encabezado2 = rowTitulo.createCell(5);
			encabezado2.setCellValue("SUELDO MENSUAL");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(5);

			encabezado2 = rowTitulo.createCell(6);
			encabezado2.setCellValue("SUELDO QUINCENAL");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(6);

			encabezado2 = rowTitulo.createCell(7);
			encabezado2.setCellValue("SALARIO DIARIO");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(7);

//			encabezado2 = rowTitulo.createCell(8);
//			encabezado2.setCellValue("PROYECTO");
//			encabezado2.setCellStyle(headerCellStyle);
//			sheet2.autoSizeColumn(8);

			Date fecha = new Date();

			int col2 = 8;
			for (String dia : dias) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("ES", "MX"));
				fecha = sdf.parse("" + dia.split("-")[2] + "/" + dia.split("-")[1] + "/" + dia.split("-")[0] + "");
				sdf.applyPattern("EEEE, dd MMM");

				encabezado2 = rowTitulo.createCell(col2);
				encabezado2.setCellValue(sdf.format(fecha).toUpperCase());
				encabezado2.setCellStyle(headerCellStyle);
				sheet2.autoSizeColumn(col2);
				col2++;
			}

			encabezado2 = rowTitulo.createCell(col2);
			encabezado2.setCellValue("DIAS LABORADOS");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(col2++);

			encabezado2 = rowTitulo.createCell(col2);
			encabezado2.setCellValue("DIAS DESCANSADOS");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(col2++);

			encabezado2 = rowTitulo.createCell(col2);
			encabezado2.setCellValue("TOTAL DE DIAS");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(col2++);

			encabezado2 = rowTitulo.createCell(col2);
			encabezado2.setCellValue("FALTAS");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(col2++);

			encabezado2 = rowTitulo.createCell(col2);
			encabezado2.setCellValue("TOTAL DE PAGO");
			encabezado2.setCellStyle(headerCellStyle);
			sheet2.autoSizeColumn(col2++);

			int columnaDatos = 1;
			int filaDatos = 0;
			Row rowDatos = sheet2.createRow(columnaDatos);
			Cell registros = rowDatos.createCell(filaDatos);
			DataFormat formato = workbook.createDataFormat();
			CellStyle cellStyleDatos = workbook.createCellStyle();
			cellStyleDatos.setDataFormat(
					formato.getFormat("_-\"$\"* #.##0,00_-;-\"$\"* #.##0,00_-;_-\"$\"* \"-\"??_-;_-@_-"));
			// _-"$"* #.##0,00_-;-"$"* #.##0,00_-;_-"$"* "-"??_-;_-@_-

			mismoUsuario = false;
			indDia = 0;
			int indDiaAux = 8;
			boolean asistencia = true;
			for (int i = 0; i < listaRegistro.size(); i++) {
				if (mismoUsuario) {
					asistencia = true;
					while (asistencia) {

						if (listaRegistro.get(i).getDiaAsistencia().equalsIgnoreCase(dias.get(indDia))) {
							registros = rowDatos.createCell(filaDatos);
							registros.setCellValue("A");
							registros.setCellStyle(cellStyle);
							sheet2.autoSizeColumn(filaDatos++);
							indDia++;
							asistencia = false;
						} else {
							filaDatos++;
							indDia++;
						}
					}

				} else {

					indDiaAux = 8;
					for (int j = 0; j < dias.size(); j++) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("ES", "MX"));
						fecha = sdf.parse("" + dias.get(j).split("-")[2] + "/" + dias.get(j).split("-")[1] + "/"
								+ dias.get(j).split("-")[0] + "");
						sdf.applyPattern("EEEE, dd MMM");
						if (sdf.format(fecha).contains("sábado") || sdf.format(fecha).contains("domingo")) {
							registros = rowDatos.createCell(indDiaAux);
							registros.setCellValue("D");
							registros.setCellStyle(cellStyle);
							sheet2.autoSizeColumn(indDiaAux++);
						} else {
							registros = rowDatos.createCell(indDiaAux);
							registros.setCellValue("F");
							registros.setCellStyle(cellStyle);
							sheet2.autoSizeColumn(indDiaAux++);
						}
					}

					registros = rowDatos.createCell(filaDatos);
					registros.setCellValue(listaRegistro.get(i).getResponsable());
					sheet2.autoSizeColumn(filaDatos++);

					registros = rowDatos.createCell(filaDatos);
					registros.setCellValue(listaRegistro.get(i).getProyecto());
					sheet2.autoSizeColumn(filaDatos++);

					registros = rowDatos.createCell(filaDatos);
					registros.setCellValue(listaRegistro.get(i).getUsuario());
					sheet2.autoSizeColumn(filaDatos++);

					registros = rowDatos.createCell(filaDatos);
					registros.setCellValue(listaRegistro.get(i).getPuesto());
					sheet2.autoSizeColumn(filaDatos++);

					registros = rowDatos.createCell(filaDatos);
					registros.setCellValue(listaRegistro.get(i).getZona());
					sheet2.autoSizeColumn(filaDatos++);

					registros = rowDatos.createCell(filaDatos);
					registros.setCellValue(0); // Sueldo Mensual
					registros.setCellStyle(cellStyleDatos);
					sheet2.autoSizeColumn(filaDatos++);

					registros = rowDatos.createCell(filaDatos);
					// registros.setCellValue(0); // Sueldo quincenal
					registros.setCellFormula("F" + (columnaDatos + 1) + "/2");
					registros.setCellStyle(cellStyleDatos);
					sheet2.autoSizeColumn(filaDatos++);
					registros = rowDatos.createCell(filaDatos);
					// registros.setCellValue(0); // Salario Diario
					registros.setCellFormula("G" + (columnaDatos + 1) + "/15");
					registros.setCellStyle(cellStyleDatos);
					sheet2.autoSizeColumn(filaDatos++);

//					registros = rowDatos.createCell(filaDatos);
//					registros.setCellValue(listaRegistro.get(i).getProyecto());
//					sheet2.autoSizeColumn(filaDatos++);

					for (int j = 0; j < dias.size(); j++) {
						if (listaRegistro.get(i).getDiaAsistencia().equalsIgnoreCase(dias.get(j))) {
							if (j == 0) {
								filaDatos = 8;
								registros = rowDatos.createCell(filaDatos);
								registros.setCellValue("A");
								registros.setCellStyle(cellStyle);
								sheet2.autoSizeColumn(filaDatos++);
								indDia++;
								break;
							} else {
								registros = rowDatos.createCell(filaDatos);
								registros.setCellValue("A");
								registros.setCellStyle(cellStyle);
								sheet2.autoSizeColumn(filaDatos++);
								indDia++;
								break;
							}

						} else {
							filaDatos++;
							indDia++;
						}
					}

				}

				if (listaRegistro.get(i).getUsuario().equalsIgnoreCase(
						listaRegistro.get(i != (listaRegistro.size() - 1) ? i + 1 : 0).getUsuario())) {
					mismoUsuario = true;
				} else {
					mismoUsuario = false;
					int ultimoDiaPosicion = 8 + dias.size();

					registros = rowDatos.createCell(ultimoDiaPosicion);
					registros.setCellFormula("COUNTIF(A" + (columnaDatos + 1) + ":Y" + (columnaDatos + 1) + ",\"A\")");
					registros.setCellStyle(cellStyle);
					sheet2.autoSizeColumn(ultimoDiaPosicion++);

					registros = rowDatos.createCell(ultimoDiaPosicion);
					registros.setCellFormula("COUNTIF(A" + (columnaDatos + 1) + ":Y" + (columnaDatos + 1) + ",\"D\")");
					registros.setCellStyle(cellStyle);
					sheet2.autoSizeColumn(ultimoDiaPosicion++);

					registros = rowDatos.createCell(ultimoDiaPosicion);
					registros.setCellFormula("SUM(" + CellReference.convertNumToColString(ultimoDiaPosicion - 1)
							+ (columnaDatos + 1) + ":" + CellReference.convertNumToColString(ultimoDiaPosicion - 2)
							+ (columnaDatos + 1) + ")");
					// registros.setCellFormula("P2+Q2");
					registros.setCellStyle(cellStyle);
					sheet2.autoSizeColumn(ultimoDiaPosicion++);

					registros = rowDatos.createCell(ultimoDiaPosicion);
					registros.setCellFormula("COUNTIF(A" + (columnaDatos + 1) + ":Y" + (columnaDatos + 1) + ",\"F\")");
					registros.setCellStyle(cellStyle);
					sheet2.autoSizeColumn(ultimoDiaPosicion++);

					registros = rowDatos.createCell(ultimoDiaPosicion);
					registros.setCellFormula("H" + (columnaDatos + 1) + " * "
							+ CellReference.convertNumToColString(ultimoDiaPosicion - 1) + (columnaDatos + 1));
					registros.setCellStyle(cellStyleDatos);
					sheet2.autoSizeColumn(ultimoDiaPosicion++);

					rowDatos = sheet2.createRow(++columnaDatos);
					filaDatos = 0;
					indDia = 0;
				}

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

//	@Async("threadPoolTaskExecutor")
//	public ByteArrayInputStream generarExcelRegistros(List<Proyecto> listaProyectos,
//			Map<String, List<Camposproyecto>> camposProyecto, Map<String, List<Registro>> registros,
//			List<List<Valore>> valores) throws IOException {
//		try (SXSSFWorkbook workbook = new SXSSFWorkbook(1); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
//			Sheet sheet;
//			Font headerFont = workbook.createFont();
//			headerFont.setBold(true);
//			headerFont.setColor(IndexedColors.WHITE.getIndex());
//
//			CellStyle headerCellStyle = workbook.createCellStyle();
//			headerCellStyle.setFont(headerFont);
//			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
//			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//			// headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
//			headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//			// Aplicar borde a las celdas
//			headerCellStyle.setBorderBottom(BorderStyle.HAIR);
//			headerCellStyle.setBorderTop(BorderStyle.HAIR);
//			headerCellStyle.setBorderLeft(BorderStyle.HAIR);
//			headerCellStyle.setBorderRight(BorderStyle.HAIR);
//
//			CellStyle cellStyle = workbook.createCellStyle();
//
//			cellStyle.setAlignment(HorizontalAlignment.CENTER);
//			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//			cellStyle.setBorderBottom(BorderStyle.HAIR);
//			cellStyle.setBorderTop(BorderStyle.HAIR);
//			cellStyle.setBorderLeft(BorderStyle.HAIR);
//			cellStyle.setBorderRight(BorderStyle.HAIR);
//
//			Font headerFont2 = workbook.createFont();
//			headerFont2.setBold(true);
//			headerFont2.setColor(IndexedColors.WHITE.getIndex());
//
//			for (Proyecto proyecto : listaProyectos) {
//				sheet = workbook.createSheet(proyecto.getProyecto());
//
//				int celda = 0;
//				// Header Row
//				Row rowTitulo = sheet.createRow(celda++);
//				Cell encabezado2 = rowTitulo.createCell(0);
//
//				for (int i = 0; i < camposProyecto.get(proyecto.getProyecto()).size(); i++) {
//					encabezado2 = rowTitulo.createCell(i);
//					encabezado2.setCellValue(camposProyecto.get(proyecto.getProyecto()).get(i).getCampo());
//					encabezado2.setCellStyle(headerCellStyle);
////					sheet.autoSizeColumn(i);
//
//				}
//
//				Row rowDatos = sheet.createRow(celda++);
//				Cell dato = rowDatos.createCell(0);
//				for (List<Valore> listaValores : valores) {
//
//					for (int i = 0; i < listaValores.size(); i++) {
//
//						dato = rowDatos.createCell(i);
//						dato.setCellValue(listaValores.get(i).getValor());
//						dato.setCellStyle(cellStyle);
////						sheet.autoSizeColumn(i);
//
//					}
//					System.out.println("Celda: " + celda);
//					rowDatos = sheet.createRow(celda++);
//
//				}
//
//			}
//
//			workbook.write(out);
//			workbook.dispose();
//			return new ByteArrayInputStream(out.toByteArray());
//		}
//	}

//	@Async("threadPoolTaskExecutor")
//	public ByteArrayInputStream generarExcelRegistros(List<Proyecto> listaProyectos,
//			Map<String, List<Camposproyecto>> camposProyecto, Map<String, List<Registro>> registros,
//			List<Valore> listaValores) throws IOException, ParseException {
//
//		try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
//			Sheet sheet;
//			Font headerFont = workbook.createFont();
//			headerFont.setBold(true);
//			headerFont.setColor(IndexedColors.WHITE.getIndex());
//
//			CellStyle headerCellStyle = workbook.createCellStyle();
//			headerCellStyle.setFont(headerFont);
//			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
//			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//			// headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
//			headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//			// Aplicar borde a las celdas
//			headerCellStyle.setBorderBottom(BorderStyle.HAIR);
//			headerCellStyle.setBorderTop(BorderStyle.HAIR);
//			headerCellStyle.setBorderLeft(BorderStyle.HAIR);
//			headerCellStyle.setBorderRight(BorderStyle.HAIR);
//
//			CellStyle cellStyle = workbook.createCellStyle();
//
//			cellStyle.setAlignment(HorizontalAlignment.CENTER);
//			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//			cellStyle.setBorderBottom(BorderStyle.HAIR);
//			cellStyle.setBorderTop(BorderStyle.HAIR);
//			cellStyle.setBorderLeft(BorderStyle.HAIR);
//			cellStyle.setBorderRight(BorderStyle.HAIR);
//
//			Font headerFont2 = workbook.createFont();
//			headerFont2.setBold(true);
//			headerFont2.setColor(IndexedColors.WHITE.getIndex());
//
//			for (Proyecto proyecto : listaProyectos) {
//				sheet = workbook.createSheet(proyecto.getProyecto());
//
//				int celda = 0;
//				// Header Row
//				Row rowTitulo = sheet.createRow(celda++);
//				Cell encabezado2 = rowTitulo.createCell(0);
//
//				for (int i = 0; i < camposProyecto.get(proyecto.getProyecto()).size(); i++) {
//					encabezado2 = rowTitulo.createCell(i);
//					encabezado2.setCellValue(camposProyecto.get(proyecto.getProyecto()).get(i).getCampo());
//					encabezado2.setCellStyle(headerCellStyle);
//					sheet.autoSizeColumn(i);
//
//				}
//
//				Row rowDatos = sheet.createRow(celda++);
//				Cell dato = rowDatos.createCell(0);
//				for (int i = 0; i < listaValores.size(); i++) {
//					for (int j = 0; j < camposProyecto.get(proyecto.getProyecto()).size(); j++) {
//						dato = rowDatos.createCell(j);
//						dato.setCellValue(listaValores.get(j).getValor());
//						dato.setCellStyle(cellStyle);
//						sheet.autoSizeColumn(j);
//
//					}
//					rowDatos = sheet.createRow(celda++);
//				}
//
//			}
//
//			workbook.write(out);
//			return new ByteArrayInputStream(out.toByteArray());
//		}
//	}

	@Async("threadPoolTaskExecutor")
	public ByteArrayInputStream generarExcelRegistros(List<Proyecto> listaProyectos,
			Map<String, List<Camposproyecto>> camposProyecto, Map<String, List<Inventario>> registros,
			IValoresDAO valoresDAO) throws IOException {
		
		

		try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet;
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.WHITE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
			headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// Aplicar borde a las celdas
			headerCellStyle.setBorderBottom(BorderStyle.HAIR);
			headerCellStyle.setBorderTop(BorderStyle.HAIR);
			headerCellStyle.setBorderLeft(BorderStyle.HAIR);
			headerCellStyle.setBorderRight(BorderStyle.HAIR);

			CellStyle cellStyle = workbook.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			cellStyle.setBorderBottom(BorderStyle.HAIR);
			cellStyle.setBorderTop(BorderStyle.HAIR);
			cellStyle.setBorderLeft(BorderStyle.HAIR);
			cellStyle.setBorderRight(BorderStyle.HAIR);

			Font headerFont2 = workbook.createFont();
			headerFont2.setBold(true);
			headerFont2.setColor(IndexedColors.WHITE.getIndex());

			for (Proyecto proyecto : listaProyectos) {
				sheet = workbook.createSheet(proyecto.getProyecto());

				int celda = 0;
				// Header Row
				Row rowTitulo = sheet.createRow(celda++);
				Cell encabezado2 = rowTitulo.createCell(0);

				for (int i = 0; i < camposProyecto.get(proyecto.getProyecto()).size(); i++) {
					encabezado2 = rowTitulo.createCell(i);
					encabezado2.setCellValue(camposProyecto.get(proyecto.getProyecto()).get(i).getCampo());
					encabezado2.setCellStyle(headerCellStyle);
					sheet.autoSizeColumn(i);

				}

				Row rowDatos = sheet.createRow(celda++);
				Cell dato = rowDatos.createCell(0);
				for (Inventario registro : registros.get(proyecto.getProyecto())) {

					List<Valore> valores = valoresDAO.obtenerDatosCampoPorProyecto(registro.getIdinventario(),
							proyecto.getIdproyecto());

					for (int i = 0; i < valores.size(); i++) {

						dato = rowDatos.createCell(i);
						dato.setCellValue(valores.get(i).getValor());
						dato.setCellStyle(cellStyle);
						sheet.autoSizeColumn(i);

					}
					rowDatos = sheet.createRow(celda++);

				}

			}

			workbook.write(out);
	        
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	
	@Async("threadPoolTaskExecutor")
	public ByteArrayInputStream generarExcelRegistrosISSSTE(List<VistaDatosISSSTE> listaDatos) throws IOException {
		
		    //start time

		try (SXSSFWorkbook workbook = new SXSSFWorkbook(listaDatos.size()); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			System.out.println("Inicia la generacion del Excel");
			long  startTime = System.currentTimeMillis();
			
			Sheet sheet;
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.WHITE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
			headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// Aplicar borde a las celdas
			headerCellStyle.setBorderBottom(BorderStyle.HAIR);
			headerCellStyle.setBorderTop(BorderStyle.HAIR);
			headerCellStyle.setBorderLeft(BorderStyle.HAIR);
			headerCellStyle.setBorderRight(BorderStyle.HAIR);

			CellStyle cellStyle = workbook.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			cellStyle.setBorderBottom(BorderStyle.HAIR);
			cellStyle.setBorderTop(BorderStyle.HAIR);
			cellStyle.setBorderLeft(BorderStyle.HAIR);
			cellStyle.setBorderRight(BorderStyle.HAIR);

			Font headerFont2 = workbook.createFont();
			headerFont2.setBold(true);
			headerFont2.setColor(IndexedColors.WHITE.getIndex());

			
				sheet = workbook.createSheet("Concentrado ISSSTE");

				int celda = 0;
				// Header Row
				Row rowTitulo = sheet.createRow(celda++);
				Cell encabezado2 = rowTitulo.createCell(0);
				
				int filaDatos =0;
				// ------------------- Comienza encabezado -------------------
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("CODIGO DE BARRAS");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("LARGO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("ESTADO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("CLUE");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("LARGO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("CLAVE PRESUPUESTAL");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("LARGO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("UNIDAD MEDICA O ADMINISTRATIVA");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("ID UNICO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("FOLIO UNIDAD MEDICA");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("FOLIO UNIDAD ADMINISTRATIVA");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("FECHA");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("HORA");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("NOMBRE DEL EMPLEADO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("NUMERO DEL EMPLEADO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("AREA DE ADSCRIPCION");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("CENTRO DE TRABAJO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("DOMICILIO LABORAL");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("CORREO INSTITUCIONAL");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("NUMERO DE RED");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("MARCA CANDADO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("MODELO CANDADO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("SERIE CANDADO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("ETIQUETA CANDADO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("LARGO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("MARCA NO BREAK");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("MODELO NO BREAK");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("SERIE NO BREAK");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("LARGO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("ETIQUETA NO BREAK");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;

					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("LARGO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("NOMBRE DEL ENLACE");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("NUMERO DEL EMPLEADO ENLACE");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("UNIDAD MEDICA O ADMINISTRATIVA ENLACE");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("ADSCRIPCION ENLACE");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("UBICACION ENLACE");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("CORREO ENLACE");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("NUMERO DE RED ENLACE");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("APOYO DEL ENLACE");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;

					encabezado2 = rowTitulo.createCell(filaDatos);
					encabezado2.setCellValue("RED DEL APOYO");
					encabezado2.setCellStyle(headerCellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;

				

				Row rowDatos = sheet.createRow(celda++);
				Cell dato = rowDatos.createCell(0);
				filaDatos = 0;
				for (VistaDatosISSSTE datos : listaDatos) {
					
					filaDatos = 0;
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getCodigoBarras());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getLargoCB());
					dato.setCellStyle(cellStyle);
					////sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getEstado());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getClue());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getLargoCLUE());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getClavePresupuestal());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getLargoClavePresupuestal());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getUnidadMedicaAdministrativa());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getIdUnico());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getFolioUnidadMedica());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getFolioUnidadAdministrativa());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getFecha());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getHora());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getNombreEmpleado());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getNumeroEmpleado());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getAreaAdscripcion());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getCentroTrabajo());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getDomicilioLaboral());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getCorreoEmpleado());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getNumeroRedEmpleado());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;

					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getMarcaCandado());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getModeloCandado());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getSerieCandado());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getEtiquetaCandado());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getLargoCandado());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getMarcaNoBreak());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getModeloNoBreak());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getSerieNoBreak());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getLargoSerie());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getEtiquetaNobreak());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getLargoEtiqueta());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getNombreEnlace());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getNumeroEmpleadoEnlace());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getUnidadMedicaAdministrativaEnlace());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getAdscripcionEnlace());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getUbicacionEnlace());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getCorreoEnlace());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getNumeroRedEnlace());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getApoyoEnlace());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					dato = rowDatos.createCell(filaDatos);
					dato.setCellValue(datos.getRedDelApoyo());
					dato.setCellStyle(cellStyle);
					//sheet.autoSizeColumn(filaDatos++);
					filaDatos++;
					
					
					
					rowDatos = sheet.createRow(celda++);

				}

			

			workbook.write(out);
			
			long finishedTime = System.currentTimeMillis();
	        System.out.println("Tiempo transcurrido: " + (finishedTime - startTime)/1000 + " Segundos");
	        System.out.println("Termina la generacion del Excel");
			return new ByteArrayInputStream(out.toByteArray());
		}catch (Exception e) {
			
			System.out.println("Error en la generacion del excel: " + e);
			return new ByteArrayInputStream(new byte[0]);
		}
	}

	private HSSFColor setColor(HSSFWorkbook workbook, byte r, byte g, byte b) {
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor hssfColor = null;
		try {
			hssfColor = palette.findColor(r, g, b);
			if (hssfColor == null) {
				palette.setColorAtIndex(HSSFColorPredefined.RED.getIndex(), r, g, b);
				hssfColor = palette.getColor(HSSFColorPredefined.RED.getIndex());
			}
		} catch (Exception e) {
			System.err.println(e);
		}

		return hssfColor;
	}
}
