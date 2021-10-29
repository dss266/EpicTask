package br.com.fiap.epictask.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictask.model.User;
import br.com.fiap.epictask.repository.UserRepository;
import br.com.fiap.epictask.service.AuthenticationService;
import br.com.fiap.epictask.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("users");
		List<User> users = repository.findAll();
		modelAndView.addObject("users", users);
		System.out.println(users);
		return modelAndView;
	}
	
	@RequestMapping("new")
	public String create(User user) {
		return "user-form";
	}
	
	@PostMapping
	public String save(@Valid User user, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) return "user-form";
		user.setPassword(
					AuthenticationService
					.getPasswordEncoder()
					.encode(user.getPassword())
		);
		repository.save(user);
		redirect.addFlashAttribute("message", messages.getMessage("newuser.success", null, LocaleContextHolder.getLocale()));
		return "redirect:user";
	}
	
	@RequestMapping("delete/{id}")
	public String delete (User user, BindingResult result, RedirectAttributes redirect) {
		repository.delete(user);
		return "redirect:/user";
	}
	
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable ("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("user-edit");
		modelAndView.addObject("user", repository.findById(id));
		return modelAndView;
	}
	
	@PostMapping("/update/{id}")
	public String update(@PathVariable Long id, @Valid User user, BindingResult result, RedirectAttributes redirect) {
			
		  Optional <User> optional = repository.findById(id);
		  
		  User newUser = optional.get(); 
		  newUser.setName(user.getName());
		  newUser.setGithubuser(user.getGithubuser());
		  newUser.setEmail(user.getEmail()); 
		  newUser.setPassword(user.getPassword());
		  
		  repository.save(user);

		return "redirect:/user";
	}
	@RequestMapping("ranking")
	public ModelAndView ranking(@PageableDefault(size = 2) Pageable pageable) {
		ModelAndView modelAndView = new ModelAndView("ranking");
		List <User> users = userService.rankUsers();
		modelAndView.addObject("users", users);
		return modelAndView;
	}
}
