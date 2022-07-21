package com.alexandrelopes.workshopmongo.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alexandrelopes.workshopmongo.domain.User;
import com.alexandrelopes.workshopmongo.dto.UserDTO;
import com.alexandrelopes.workshopmongo.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResources {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(method=RequestMethod.GET) /* Também seria possível trocar esta annotation por @GetMapping que daria no mesmo */
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		//Agora que estamos usando o UserDTO teremos que passar a lista de Users para uma stream e usar o metodo map para instanciar um novo userDTO a cada use na lista
		
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

}
