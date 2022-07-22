package com.alexandrelopes.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexandrelopes.workshopmongo.domain.User;
import com.alexandrelopes.workshopmongo.dto.UserDTO;
import com.alexandrelopes.workshopmongo.repository.UserRepository;
import com.alexandrelopes.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));	
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	//Método para instanciar o User a partir do UserDTO aqui e não dentro do proprio User porque aqui temos uma referencia para o banco de dados
	//e podemos vir a querer utilizar essa referência para poder pegar dados do Usuário que formos instanciar que já estejam no banco
	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
