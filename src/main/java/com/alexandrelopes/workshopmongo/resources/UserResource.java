package com.alexandrelopes.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alexandrelopes.workshopmongo.domain.User;
import com.alexandrelopes.workshopmongo.dto.UserDTO;
import com.alexandrelopes.workshopmongo.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(method=RequestMethod.GET) /* Também seria possível trocar esta annotation por @GetMapping que daria no mesmo */
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		//Agora que estamos usando o UserDTO teremos que passar a lista de Users para uma stream e usar o metodo map para instanciar um novo userDTO a cada use na lista
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) /* Também seria possível trocar esta annotation por @GetMapping que daria no mesmo */
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj));
	}
	
	@RequestMapping(method=RequestMethod.POST) /* Também seria possível trocar esta annotation por @PostMapping que daria no mesmo */
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {
		User obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		//Vamos retornar uma resposta vazia <Void>, porém com uma url do novo recurso criado no cabeçalho
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE) /* Também seria possível trocar esta annotation por @GetMapping que daria no mesmo */
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
