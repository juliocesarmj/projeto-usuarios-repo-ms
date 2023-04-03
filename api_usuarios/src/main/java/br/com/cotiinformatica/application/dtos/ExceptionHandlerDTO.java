package br.com.cotiinformatica.application.dtos;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionHandlerDTO {
	
	private HttpStatus status;
	private List<String> errors;
}
