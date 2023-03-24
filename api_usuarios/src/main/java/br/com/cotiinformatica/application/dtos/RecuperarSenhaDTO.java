package br.com.cotiinformatica.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecuperarSenhaDTO {
	
	@NotBlank(message = "Email do usuário é obrigatório.")
	@Email(message = "Informe um endereço de email válido.")
	private String email;
}
