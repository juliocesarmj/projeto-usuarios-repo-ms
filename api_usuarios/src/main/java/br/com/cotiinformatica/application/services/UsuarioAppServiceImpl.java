package br.com.cotiinformatica.application.services;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.application.dtos.AtualizarDadosDTO;
import br.com.cotiinformatica.application.dtos.AtualizarDadosResponseDTO;
import br.com.cotiinformatica.application.dtos.AutenticarDTO;
import br.com.cotiinformatica.application.dtos.AutenticarResponseDTO;
import br.com.cotiinformatica.application.dtos.CriarContaDTO;
import br.com.cotiinformatica.application.dtos.CriarContaResponseDTO;
import br.com.cotiinformatica.application.dtos.EmailMessageDTO;
import br.com.cotiinformatica.application.dtos.RecuperarSenhaDTO;
import br.com.cotiinformatica.application.dtos.RecuperarSenhaResponseDTO;
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
		emailMessageDTO.setBody(
				"Olá, sua conta de usuário foi criada com sucesso em nosso sistema!<br/>Att,<br/>API Usuários");

		return emailMessageDTO;
	}

	@Override
	public AutenticarResponseDTO autenticar(AutenticarDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		Usuario usuario = usuarioDomainService.autenticar(dto.getEmail(), dto.getSenha());
		AutenticarResponseDTO response = modelMapper.map(usuario, AutenticarResponseDTO.class);
		response.setMensagem("Usuário autenticado com sucesso.");
		return response;
	}

	@Override
	public RecuperarSenhaResponseDTO recuperarSenha(RecuperarSenhaDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		
		Usuario usuario = usuarioDomainService.recuperarSenha(dto.getEmail());
		RecuperarSenhaResponseDTO recuperarSenhaResponse = modelMapper.map(usuario, RecuperarSenhaResponseDTO.class);
		recuperarSenhaResponse.setMensagem("Foi enviada uma nova senha para o seu email.");
		
		EmailMessageDTO emailMessageDTO = new EmailMessageDTO();
		emailMessageDTO.setTo(usuario.getEmail());
		emailMessageDTO.setSubject("Recuperação de senha realizada com sucesso!");
		emailMessageDTO.setBody("Olá, " + usuario.getNome() + ". Acesse o sistema com a senha: " + usuario.getNovaSenha() + "<br/>Att,<br/>API Usuários");
		
		CompletableFuture.runAsync(() -> messageProducer.send(criarMessageProducer(emailMessageDTO)));
		
		return recuperarSenhaResponse;
	}

	@Override
	public AtualizarDadosResponseDTO atualizarDados(AtualizarDadosDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		
		Usuario usuarioAtualizado = usuarioDomainService.atualizarDados(modelMapper.map(dto, Usuario.class));
		
		AtualizarDadosResponseDTO atualizarDadosResponse = modelMapper.map(usuarioAtualizado, AtualizarDadosResponseDTO.class);
		atualizarDadosResponse.setMensagem("Usuário atualizado com sucesso.");
		
		return atualizarDadosResponse;
	}
}
