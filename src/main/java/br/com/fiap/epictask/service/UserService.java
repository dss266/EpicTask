package br.com.fiap.epictask.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;

	public List<User> rankUsers() {
		List<User> users = repository.findAllByOrderByPointsDesc();
		return users;
	}

}
