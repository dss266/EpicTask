package br.com.fiap.epictask.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.fiap.epictask.exception.NotAllowedException;
import br.com.fiap.epictask.exception.TaskNotFoundException;
import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository repository;

	public List<Task> completedTasks() {
		List<Task> tasks = repository.findAll();
		System.out.println(tasks);
		return tasks;
	}

	public Optional<Task> holdTask(Long id, Authentication auth) {
		
		Optional<Task> optional = repository.findById(id);
		
		if(optional.isEmpty())
			throw new TaskNotFoundException("Tarefa não encontrada");

		Task task = optional.get();
		
		if(task.getUser() != null)
			throw new NotAllowedException("Tarefa já atribuída");
		
		User user = (User) auth.getPrincipal();
		task.setUser(user);
		repository.save(task);
		return optional;
	}

	public Optional<Task> releaseTask(Long id, Authentication auth) {
		Optional<Task> optional = repository.findById(id);
		
		if(optional.isEmpty())
			throw new TaskNotFoundException("Tarefa não encontrada");

		Task task = optional.get();
		User user = (User) auth.getPrincipal();
		
		if(!task.getUser().equals(user))
			throw new NotAllowedException("Essa tarefa não está atribuída para você");
		
		
		task.setUser(null);
		repository.save(task);
		return optional;
	}
	
	
	

}
