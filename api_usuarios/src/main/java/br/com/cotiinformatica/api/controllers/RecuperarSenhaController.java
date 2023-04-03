package br.com.cotiinformatica.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.application.dtos.RecuperarSenhaDTO;
import br.com.cotiinformatica.application.dtos.RecuperarSenhaResponseDTO;
import br.com.cotiinformatica.application.interfaces.UsuarioAppService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/usuarios")
public class RecuperarSenhaController {
	
	@Autowired
	UsuarioAppService service;
	
	@PostMapping("/recuperar-senha")
	public ResponseEntity<RecuperarSenhaResponseDTO> post(@RequestBody @Valid RecuperarSenhaDTO dto) {
		return ResponseEntity.ok(service.recuperarSenha(dto));
	}
}
