package pe.com.win.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.win.excel.ExcelServices;

@RestController
@RequestMapping("/reportes")
public class ReportesController {
	private static Logger log = LogManager.getLogger(ReportesController.class);

	@Autowired
	private ExcelServices excelServices;
	
	
	/*********************************************************
	 * Este reporte primero crea en memoria y cuando lo tiene armado procede a descargar.
	 * 1. Los datos del excel se obtiene de acuerdo a la cabecera y resultado que devuelva el Stored procedure
	 * 2. El nombre de las columnas es definido por el SP
	 * 3. La cantidad de Columnas es definido por el SP
	 * 4. Se recomienda que el resultado del SP sea texto y diferente de null
	 *********************************************************/
	  @GetMapping("/descarga/reporte1")
	  public ResponseEntity<Resource> getFile(
				@RequestParam(name = "estado", required = false) String estado,
				HttpServletRequest httpServletRequest, HttpServletResponse response) {

		log.info("Iniciar" + estado);
	    String filename = "NOMBRE_ACRCHIVO.xlsx";	    
	    InputStreamResource file = new InputStreamResource(excelServices.obtieneArchivoXLSX(estado));

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .header("Set-Cookie", "fileDownload=true; path=/")
	        .header("Cache-Control", "no-cache, no-store, must-revalidate")
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }
}
