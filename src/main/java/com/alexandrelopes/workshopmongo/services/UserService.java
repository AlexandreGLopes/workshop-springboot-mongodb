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
		//Optional is a container object used to contain not-null objects. Optional object is used to represent null with absent value. This class has various utility methods to facilitate code to handle values as ‘available’ or ‘not available’ instead of checking null values.
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));	
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		//Antes de deletar vamos usar o mesmo método findById desta classa para caso não haver dado com esse id na base lançar a exceção
		findById(id);
		repo.deleteById(id);
	}
	
	public User update(User obj) {
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

	//Método para instanciar o User a partir do UserDTO aqui e não dentro do proprio User porque aqui temos uma referencia para o banco de dados
	//e podemos vir a querer utilizar essa referência para poder pegar dados do Usuário que formos instanciar que já estejam no banco
	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
