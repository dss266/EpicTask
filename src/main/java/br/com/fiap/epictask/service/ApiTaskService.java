package br.com.fiap.epictask.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.repository.TaskRepository;

@Service
public class ApiTaskService {
	
	@Autowired
	private TaskRepository repository;

	public ResponseEntity<Task> deleteTask(Long id) {
		Optional<Task> task = repository.findById(id);

		if (task.isEmpty())
			return ResponseEntity.notFound().build();
		
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	

}
