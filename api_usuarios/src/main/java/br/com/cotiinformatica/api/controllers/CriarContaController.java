package br.com.cotiinformatica.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.application.dtos.CriarContaDTO;
import br.com.cotiinformatica.application.dtos.CriarContaResponseDTO;
import br.com.cotiinformatica.application.interfaces.UsuarioAppService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/usuarios")
public class CriarContaController {
	
	@Autowired
	UsuarioAppService service;
	
	@PostMapping("/criar-conta")
	public ResponseEntity<CriarContaResponseDTO> post(@RequestBody @Valid CriarContaDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarConta(dto));
	}
}
