package com.generation.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
	
	//usar a interface PostagemRepository para verificar se os endpoints estão funcionando
	//e para isso criamos uma injeção de dependência
	@Autowired
	private PostagemRepository postagemRepository;
	
	@GetMapping("/listagem")
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(postagemRepository.findAll());
		/* select * from tb_postagem */
	}
	
	
	//configuração de variável de caminho
	//PathVariable pegará a variável {id} e armazenará em id do tipo da classe Long
	//pega um id, não precisa de List
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id){
		/* Optional dá a possibilidade de checar se o objeto é null ou não sem dar erro.
		Optional<Postagem> buscarPostagem = postagemRepository.findById(id);
		
		if(buscarPostagem.isPresent())
			return ResponseEntity.ok(buscarPostagem.get());
		else
			return ResponseEntity.notFound().build();
		*/
		
		/* select * from tb_postagens where id = 1 */
		
		return postagemRepository.findById(id)
				//lambidas
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//QueryMetod
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
		/* select * from tb_postagem where titulo like "%titulo%"; */	
	}
	
	//cadastrar postagem
	@PostMapping
	public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
	}
	
	@DeleteMapping("/{id}")
	public void deletePostagem (@PathVariable Long id) {
		postagemRepository.deleteById(id);
	}
	
	//verificar se a postagem existe antes de deleter e devolver 404.
	//verificar se a postagem existe antes de atualizar.
}
