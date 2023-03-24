package br.com.cotiinformatica.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.application.dtos.CriarContaDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/usuarios")
public class CriarContaController {
	
	@PostMapping("/criar-conta")
	public String post(@RequestBody @Valid CriarContaDTO dto) {
		return null;
	}

}
