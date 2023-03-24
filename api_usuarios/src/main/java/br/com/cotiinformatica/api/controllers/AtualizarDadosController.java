package br.com.cotiinformatica.api.controllers;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.application.dtos.AtualizarDadosDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/usuarios")
public class AtualizarDadosController {
	
	@PutMapping("/atualizar-dados")
	public String put(@RequestBody @Valid AtualizarDadosDTO dto) {
		return null;
	}


}
