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

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {
	
	//usar a interface TemaRepository e para isso criamos uma injeção de dependência
		@Autowired
		private TemaRepository temaRepository;
		
		@GetMapping("/listagem")
		public ResponseEntity<List<Tema>> getAll(){
			return ResponseEntity.ok(temaRepository.findAll());
			/* select * from tb_tema */
		}
		
		//configuração de variável de caminho
		//PathVariable pegará a variável {id} e armazenará em id do tipo da classe Long
		//pega um id, não precisa de List
		@GetMapping("/{id}")
		public ResponseEntity<Tema> getById(@PathVariable Long id){
			/* Optional dá a possibilidade de checar se o objeto é null ou não sem dar erro.
			Optional<Tema> buscarTema = TemaRepository.findById(id);
			
			if(buscarTema.isPresent())
				return ResponseEntity.ok(buscarTema.get());
			else
				return ResponseEntity.notFound().build();
			*/
			
			/* select * from tb_tema where id = 1 */
			
			return temaRepository.findById(id)
					//lambidas
					.map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.notFound().build());
		}
		
		//QueryMetod
		@GetMapping("/descricao/{descricao}")
		public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String descricao){
			return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
			/* select * from tb_tema where descricao like "%descricao%"; */	
		}

		@PostMapping //cadastrar tema
		public ResponseEntity<Tema> postTema(@Valid @RequestBody Tema tema){
			return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
		}
		
		@PutMapping
		public ResponseEntity<Tema> putTema(@Valid @RequestBody Tema tema){
			return ResponseEntity.status(HttpStatus.OK).body(temaRepository.save(tema));
		}
		
		@DeleteMapping("/{id}")
		public void deleteTema(@PathVariable Long id) {
			temaRepository.deleteById(id);
		}
		
		//verificar se a postagem existe antes de deleter e devolver 404.
		//verificar se a postagem existe antes de atualizar.
}
