package com.isae.drpool.drpool.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Valore;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;


public class LeerDocumentoExcel {

	public Map<String, Object> leerDocumento(File documento, List<Camposproyecto> listaDeCampos, Proyecto proyecto) {

		FileInputStream fileInputStream;
		XSSFWorkbook workBook;

		Map<String, Object> respuesta = new HashMap<String, Object>();

		try {

			fileInputStream = new FileInputStream(documento);
			workBook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workBook.getSheetAt(0);
			FormulaEvaluator formulaEvaluator = workBook.getCreationHelper().createFormulaEvaluator();
			List<Inventario> listaInventarios = new ArrayList<Inventario>();
			List<Valore> listaValores = new ArrayList<Valore>();
			List<String> folios = new ArrayList<String>();
			boolean datosCompletos = true;
//			System.out.println(sheet.getRow(2));


			if (sheet.getRow(0).getLastCellNum() == listaDeCampos.size()) {
				System.out.println("Mismo tamaño de campos: " + listaDeCampos.size());
				System.out.println("Ultima Fila: " + sheet.getLastRowNum());
				int i = 0;
				int j = 0;
				for (Row row : sheet) {
					j = 0;
					if(i != 0) {
						for (Cell cell : row) {
							if (cell.getCellType() == CellType.FORMULA && cell.getCellFormula().contains("_xlfn.CONCAT")) {
								cell.setCellFormula(cell.getCellFormula().replace("_xlfn.CONCAT", "CONCATENATE"));
							}
							String valor = "";
							switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
							case NUMERIC:
								if (DateUtil.isCellDateFormatted(cell)) {
									DataFormatter dataFormatter = new DataFormatter();
									valor = dataFormatter.formatCellValue(cell);
									if(valor.contains("/")) {
										valor = String.join("/", concatenarCero(valor.split("/")[0]), concatenarCero(valor.split("/")[1]), valor.split("/")[2]);
										System.out.println("Valor fecha: " + valor);
									}
								} else {

									Object dato = cell.getNumericCellValue();
									if (dato.toString().contains("E")) {

										valor = new BigDecimal(dato.toString()).toPlainString();
									} else {
										int datoEntero = Integer.parseInt(dato.toString().replace(".0", ""));
										valor = String.valueOf(datoEntero);
									}
								}

								break;
							case STRING:
								valor = cell.getStringCellValue();
								if (j == 0) {
									listaInventarios.add(new Inventario(0, new Date(), valor, "NUEVO", proyecto));
									folios.add(valor);
								}
								break;

							case FORMULA:
								valor = cell.getCellFormula();
								break;

							case BLANK:
								System.out.println("Case: Blank");
								System.out.println(valor);
								break;

							case BOOLEAN:
								if (cell.getBooleanCellValue()) {
									valor = "TRUE";
								} else {
									valor = "FALSE";
								}
								break;

							case ERROR:
								System.out.println(cell.getErrorCellValue());
								System.out.println(valor);
								break;

							case _NONE:
								System.out.println("Case: NONE " + cell.getStringCellValue());
								System.out.println(valor);
								break;

							default:
								System.out.println("Case: default " + cell.getStringCellValue());
								break;
							}
							
							
							listaValores.add(new Valore(0, valor.toUpperCase(),
									new Camposproyecto(listaDeCampos.get(j).getIdcamposproyecto()),
									new Inventario()));
							j++;
						}
						if(j != sheet.getRow(i).getLastCellNum()) {
							datosCompletos = false;
							break;
						}
					}else {
						i++;
					}
				}

				respuesta.put("listaInventarios", listaInventarios);
				respuesta.put("listaValores", listaValores);
				respuesta.put("listaFolios", folios);
				if(datosCompletos) {
					respuesta.put("Estatus", "Correcto");
				}else {
					respuesta.put("Estatus", "Error, Existe uno o mas campos vacios, ingrese informacion o de los contrario un '-' para evitar errores en la carga de la informacion");
				}
			} else {
				System.out.println("El documento no tiene el mismo formato :(");
				respuesta.put("Estatus", "El documento no tiene el mismo formato");
			}

			workBook.close();

			return respuesta;

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al leer el documento");
			respuesta.put("Estatus", "Error");
			return respuesta;
		}

	}
	
	private String concatenarCero(String dato) {
		String respuesta = dato;
		if(dato.length() != 2) {
			respuesta = "0"+dato;
		}
		return respuesta;
	}

}
