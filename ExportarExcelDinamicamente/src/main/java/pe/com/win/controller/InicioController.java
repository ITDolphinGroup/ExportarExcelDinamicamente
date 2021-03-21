package pe.com.win.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

	@GetMapping(value = {"/", "/inicio"})
	public String inicio() {
		return "inicio";
	}
}
