package com.generation.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.generation.blogpessoal.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	/*
	 Método que busca um usúario pelo seu usuário (email)
	 
	 select * from tb_usuarios where usuario = "usuario procurado"
	  
	  */
	
	// Usamos a classe Optional para verigicar se o usuário exite.
	
	public Optional<Usuario> findByUsuario(String usuario);

	public List <Usuario> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);	

}
	