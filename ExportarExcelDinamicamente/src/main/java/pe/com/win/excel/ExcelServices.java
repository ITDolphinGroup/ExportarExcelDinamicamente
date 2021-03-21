package pe.com.win.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.win.mapper.SentenciaMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*************************************************
 * @uthor: Eric Pineda.
 * @since: 19/03/2021
 ************************************************* */
@Service
public class ExcelServices {
	private static Logger log = LogManager.getLogger(ExcelServices.class);
	private SXSSFSheet sheet;
	private Cell cell;
	
	@Autowired
	private SentenciaMapper sentenciaMapper;
	
		
	public ByteArrayInputStream obtieneArchivoXLSX(String estado) {
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			sheet = workbook.createSheet("PRIMERA_HOJA");// ========================= Primera Pestaña (nombre)
			nuevaHojaXLSXEntrada(workbook, estado);	
			out = new ByteArrayOutputStream();
			workbook.write(out);
		}catch(Exception ex) {
			String stacktrace = ExceptionUtils.getStackTrace(ex);
			log.error(stacktrace);
		}
	    return new ByteArrayInputStream(out.toByteArray());
	}
	
	
	private void nuevaHojaXLSXEntrada(SXSSFWorkbook workbook, String estado) {
		try {
			List<String> nombreColumnas = new ArrayList<String>();
			List<HashMap<?, ?>> datos = new ArrayList<HashMap<?, ?>>();

			List<LinkedHashMap<?, ?>> listaConsulta = sentenciaMapper.lista(estado);
			
			int inicio = 1;
			for (HashMap<?, ?> map : listaConsulta) {
				for (Map.Entry me : map.entrySet()) {
					// System.out.println("Key: " + me.getKey() + " & Value: " + me.getValue());
					if (inicio == 1) {
						nombreColumnas.add(me.getKey().toString());
					}
				}
				datos.add(map);
				inicio++;
			}
			int rownum = 0;
			Row row = sheet.createRow(rownum);

			CellStyle style = workbook.createCellStyle();// Create style
			Font font = workbook.createFont();// Create font
			font.setBold(true);
			style.setFont(font);// set it to bold

			int numeroColumna = 0;
			for (String columna : nombreColumnas) {
				cell = row.createCell(numeroColumna);
				cell.setCellStyle(style);
				cell.setCellValue(columna);
				numeroColumna++;
			}

			rownum = 1;
			row = sheet.createRow(rownum);
			for (HashMap<?, ?> map : datos) {
				numeroColumna = 0;
				for (String columna : nombreColumnas) {
					cell = row.createCell(numeroColumna);
					cell.setCellValue( (map.get(columna)!= null) ? map.get(columna).toString() : "");
					numeroColumna++;
				}
				rownum++;
				row = sheet.createRow(rownum);
			}	
	/*
			for (int i = 1; i < nombreColumnas.size(); i++) {
				sheet.autoSizeColumn(i);
			}
			 */
		} catch (Exception ex) {
			String stacktrace = ExceptionUtils.getStackTrace(ex);
			log.error(stacktrace);
		}
	}
}
