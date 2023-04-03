package br.com.cotiinformatica.domain.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import br.com.cotiinformatica.domain.interfaces.UsuarioDomainService;
import br.com.cotiinformatica.domain.models.Usuario;
import br.com.cotiinformatica.infrastructure.component.MD5Component;
import br.com.cotiinformatica.infrastructure.repositories.UsuarioRepository;
import br.com.cotiinformatica.infrastructure.security.TokenCreator;

@Service
public class UsuarioDomainServiceImpl implements UsuarioDomainService {

	private final UsuarioRepository usuarioRepository;
	private final MD5Component md5Component;
	private final TokenCreator tokenCreator;


	public UsuarioDomainServiceImpl(UsuarioRepository usuarioRepository, MD5Component md5Component, TokenCreator tokenCreator) {
		this.usuarioRepository = usuarioRepository;
		this.md5Component = md5Component;
		this.tokenCreator = tokenCreator;
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
		if (usuarioRepository.findByEmail(email).isPresent())
			throw new IllegalArgumentException("O email informado já existe.");
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> optional = usuarioRepository.findByEmailAndSenha(email, md5Component.encrypt(senha));
		if (optional.isEmpty()) {
			throw new IllegalArgumentException("Acesso negado. Usuário não encontrado.");
		}
		
		Usuario usuario = optional.get();
		usuario.setAccessToken(tokenCreator.generateToken(usuario.getEmail()));
		return usuario;
	}

	@Override
	public Usuario recuperarSenha(String email) {
		 Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Usuário inválido. Verifique o email informado."));
		Faker fake = new Faker();
		usuario.setNovaSenha(fake.internet().password(8, 10, true, true, true));
		
		usuario.setSenha(md5Component.encrypt(usuario.getNovaSenha()));
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario atualizarDados(Usuario usuario) {
		Usuario usuarioAtualizado = findById(usuario.getId());
		
		if(usuario.getNome() != null)
			usuarioAtualizado.setNome(usuario.getNome());
		
		if(usuario.getSenha() != null)
			usuarioAtualizado.setSenha(md5Component.encrypt(usuario.getSenha()));
		
		usuarioAtualizado.setDtaHoraUltimaAtualizacao(Instant.now());
		
		usuarioRepository.save(usuarioAtualizado);
		
		return usuarioAtualizado;
	}
	
	private Usuario findById(String id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado. Verifique o id informado."));
	}
}
