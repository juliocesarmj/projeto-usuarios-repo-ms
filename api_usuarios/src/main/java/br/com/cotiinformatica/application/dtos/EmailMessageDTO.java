package br.com.cotiinformatica.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessageDTO {
	
	private String to;
	private String subject;
	private String body;
	
}
