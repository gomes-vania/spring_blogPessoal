package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogar;
import com.generation.blogpessoal.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired //injeção de independência para ter acesso na controller
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
		
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();
		
		usuario.setSenha(cripitograrSenha(usuario.getSenha()));
		 
		return Optional.of(usuarioRepository.save(usuario));
	}
	
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
			 
			usuario.setSenha(cripitograrSenha(usuario.getSenha()));
			
			return Optional.ofNullable(usuarioRepository.save(usuario));
	}
		
			return Optional.empty();
	
	}
	
	public Optional<UsuarioLogar> logarUsuario(Optional<UsuarioLogar> usuarioLogin) {
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {
			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
				
				usuarioLogin.get().setId(usuario.get().getId());				
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(usuario.get().getSenha());

				return usuarioLogin;

			}
		}
			
			return Optional.empty();
		}		
		

	private String gerarToken(String usuario, String senha) {
		String token = usuario + ":" + senha;
		//converter token em Base64
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		//converter o vetor em String
		return "Basic " + new String(tokenBase64) ;
	}


	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		
		//método security
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(senhaDigitada, senhaBanco);
	}


	private String cripitograrSenha(String senha) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);
	}
	
}

