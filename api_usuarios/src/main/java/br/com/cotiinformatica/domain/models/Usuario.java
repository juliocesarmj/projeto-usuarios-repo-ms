package br.com.cotiinformatica.domain.models;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {
	
	@Id
	private String id;
	private String nome;
	
	@Indexed(unique = true)
	private String email;
	private String senha;
	
	@Field(name = "dta_hora_criacao")
	private Instant dtaHoraCriacao;
	private Instant dtaHoraUltimaAtualizacao;
	
	@Transient
	private String accessToken;

}
