package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.user.UserUpdateDTO;
import com.example.demo.domain.user.Users;
import com.example.demo.repositories.UserRepository;
import com.github.matheus321699.cursomc.services.exceptions.DataIntegrityException;

@Service
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	public Optional<Users> findUserById(Integer id) {
		Optional<Users> user = repository.findById(id);
		return user;
	}

	public Optional<Users> updateUser(UserUpdateDTO userDTO) {
		Optional<Users> user = findUserById(userDTO.id());
		user.get().setLogin(userDTO.name());
		user.get().setPassword(userDTO.password());
		repository.save(user.get());
		return findUserById(userDTO.id());
	}

	public void deleteUserById(Integer id) {
		findUserById(id);
		/*
		 * Capturando exceção que é lançada quando um objeto que está associado
		 * a outro objeto sofre uma tentativa de deleção, não permitindo que o
		 * o objeto seja deletado.
		 */
		try {
			repository.deleteById(id);
		} 
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos"
					+ " relacionados!");
		}		
	}
}
