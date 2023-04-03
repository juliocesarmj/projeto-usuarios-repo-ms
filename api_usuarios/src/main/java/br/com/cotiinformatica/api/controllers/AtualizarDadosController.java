package br.com.cotiinformatica.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.application.dtos.AtualizarDadosDTO;
import br.com.cotiinformatica.application.dtos.AtualizarDadosResponseDTO;
import br.com.cotiinformatica.application.interfaces.UsuarioAppService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/usuarios")
public class AtualizarDadosController {
	
	@Autowired
	UsuarioAppService usuarioAppService;
	
	@PutMapping("/atualizar-dados")
	public ResponseEntity<AtualizarDadosResponseDTO> atualizarDados(@RequestBody @Valid AtualizarDadosDTO dto) {
		return ResponseEntity.ok().body(usuarioAppService.atualizarDados(dto));
	}
}
