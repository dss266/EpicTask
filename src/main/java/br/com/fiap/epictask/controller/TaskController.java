package br.com.fiap.epictask.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictask.exception.NotAllowedException;
import br.com.fiap.epictask.exception.TaskNotFoundException;
import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.TaskRepository;
import br.com.fiap.epictask.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private MessageSource message;
	
	@Autowired
	private TaskRepository repository;
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("tasks");
		List<Task> tasks = repository.findAll();
		modelAndView.addObject("tasks", tasks);
		return modelAndView;
	}
	
	@RequestMapping("new")
	public String create(Task task) {
		return "task-form";
	}
	
	@PostMapping
	public String save(@Valid Task task, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			return "task-form";
		}
		repository.save(task);
		redirect.addFlashAttribute("message", message.getMessage("newtask.success", null, LocaleContextHolder.getLocale()));
		return "redirect:/task";
	}
	
	@GetMapping("/hold/{id}")
	public String hold(@PathVariable Long id, Authentication auth) {
		Optional<Task> optional = taskService.holdTask(id,auth);
				
		return "redirect:/task";
	}
	
	@GetMapping("/release/{id}")
	public String release(@PathVariable Long id, Authentication auth) {
		Optional<Task> optional = taskService.releaseTask(id,auth);
		return "redirect:/task";
	
	}
	
	@RequestMapping("done")
	public ModelAndView done() {
		ModelAndView modelAndView = new ModelAndView("completed-tasks");
		List<Task> tasks = taskService.completedTasks();
		modelAndView.addObject("tasks", tasks);
		return modelAndView;
	}
	
	
	
	

	

}
