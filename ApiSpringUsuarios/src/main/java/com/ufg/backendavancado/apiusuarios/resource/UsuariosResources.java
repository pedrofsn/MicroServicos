package com.ufg.backendavancado.apiusuarios.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ufg.backendavancado.apiusuarios.domain.Usuario;
import com.ufg.backendavancado.apiusuarios.repository.UsuarioRepository;
import com.ufg.backendavancado.apiusuarios.services.UsuarioService;
import com.ufg.backendavancado.apiusuarios.services.exceptions.UsuarioNaoEncontradoException;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuariosResources {
	@Autowired
	private UsuarioService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Usuario>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(service.listar());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@RequestBody Usuario usuario) {
		usuario = service.salvar(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Usuario usuario = usuario = service.busca(id);
		return ResponseEntity.status(HttpStatus.OK).body(usuario);
	}

	@RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {		
		service.deletar(id);					
		return ResponseEntity.noContent().build();		 
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Usuario usuario, @PathVariable("id") Long id) {
		usuario.setId(id);		
		service.atualiza(usuario);		
		return ResponseEntity.noContent().build();
	}
	
	
}
