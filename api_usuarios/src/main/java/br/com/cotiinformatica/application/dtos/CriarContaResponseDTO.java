package br.com.cotiinformatica.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarContaResponseDTO {
	
	private String mensagem;
	private String id;
	private String nome;
	private String email;
}
