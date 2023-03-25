package br.com.cotiinformatica.domain.services;

import java.time.Instant;

import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.interfaces.UsuarioService;
import br.com.cotiinformatica.domain.models.Usuario;
import br.com.cotiinformatica.infrastructure.component.MD5Component;
import br.com.cotiinformatica.infrastructure.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	private final MD5Component md5Component;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, MD5Component md5Component) {
		this.usuarioRepository = usuarioRepository;
		this.md5Component = md5Component;
	}
	
	@Override
	public void criarConta(Usuario usuario) {
		validaUsuarioExistente(usuario.getEmail());
		
		usuario.setSenha(md5Component.encrypt(usuario.getSenha()));
		usuario.setDtaHoraCriacao(Instant.now());
		usuario.setDtaHoraUltimaAtualizacao(Instant.now());
		
		usuarioRepository.save(usuario);
	}
	
	private void validaUsuarioExistente(String email) {
		if(usuarioRepository.findByEmail(email).isPresent())
			throw new IllegalArgumentException("O email informado já existe.");
	}
}
