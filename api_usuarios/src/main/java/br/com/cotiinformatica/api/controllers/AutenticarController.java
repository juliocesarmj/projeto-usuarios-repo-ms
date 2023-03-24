package br.com.cotiinformatica.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.application.dtos.AutenticarDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/usuarios")
public class AutenticarController {
	
	@PostMapping("/autenticar")
	public String post(@RequestBody @Valid AutenticarDTO dto) {
		return null;
	}

}
