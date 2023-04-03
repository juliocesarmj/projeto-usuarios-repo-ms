package br.com.cotiinformatica.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.application.dtos.AutenticarDTO;
import br.com.cotiinformatica.application.dtos.AutenticarResponseDTO;
import br.com.cotiinformatica.application.interfaces.UsuarioAppService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/usuarios")
public class AutenticarController {

	@Autowired
	UsuarioAppService usuarioAppService;

	@PostMapping("/autenticar")
	public ResponseEntity<AutenticarResponseDTO> post(@Valid @RequestBody AutenticarDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioAppService.autenticar(dto));
	}
}
