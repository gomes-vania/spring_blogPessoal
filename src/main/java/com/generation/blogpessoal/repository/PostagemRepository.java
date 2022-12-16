package com.generation.blogpessoal.repository;

import org.springframework.stereotype.Repository;
import com.generation.blogpessoal.model.Postagem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

//classe de ferramentas = que herda por meio da herança os métodos básicos
@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {
	
	//QueryMetod
	//@Param usa se for do tipo like
	public List <Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
	
	
}
