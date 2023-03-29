package br.com.cotiinformatica.application.services;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.application.dtos.CriarContaDTO;
import br.com.cotiinformatica.application.dtos.CriarContaResponseDTO;
import br.com.cotiinformatica.application.dtos.EmailMessageDTO;
import br.com.cotiinformatica.application.interfaces.UsuarioAppService;
import br.com.cotiinformatica.domain.interfaces.UsuarioDomainService;
import br.com.cotiinformatica.domain.models.Usuario;
import br.com.cotiinformatica.infrastructure.producers.MessageProducer;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioAppServiceImpl implements UsuarioAppService {

	@Autowired
	UsuarioDomainService usuarioDomainService;
	
	@Autowired
	MessageProducer messageProducer;
	
	@Autowired
	ObjectMapper objectMapper;

	@Override
	@Transactional
	public CriarContaResponseDTO criarConta(CriarContaDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		
		usuarioDomainService.criarConta(usuario);
		CriarContaResponseDTO response = modelMapper.map(usuario, CriarContaResponseDTO.class);
		response.setMensagem("Usuario criado com sucesso.");
		CompletableFuture.runAsync(() -> messageProducer.send(criarMessageProducer(novoDto(usuario))));
		return response;
	}	
	
	private String criarMessageProducer(EmailMessageDTO dto) {
		try {
			return objectMapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			log.error("Erro ao serializar o objeto : " + e.getMessage());
			return null;
		}
	}
	
	private EmailMessageDTO novoDto(Usuario usuario) {
		EmailMessageDTO emailMessageDTO = new EmailMessageDTO();
		emailMessageDTO.setTo(usuario.getEmail());
		emailMessageDTO.setSubject("Parabéns " + usuario.getNome() + ", sua conta foi criada com sucesso!");
		emailMessageDTO.setBody("Olá, sua conta de usuário foi criada com sucesso em nosso sistema!<br/>Att,<br/>API Usuários");
		
		return emailMessageDTO;
	}
}
