package com.coderscampus.com.tim_smith_spring_assignment13.web;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.com.tim_smith_spring_assignment13.domain.Account;
import com.coderscampus.com.tim_smith_spring_assignment13.domain.Address;
import com.coderscampus.com.tim_smith_spring_assignment13.domain.User;
import com.coderscampus.com.tim_smith_spring_assignment13.service.UserService;

@Controller
public class UserController {       
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String getCreateUser (ModelMap model) { 
		model.put("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String postCreateUser (User user, Address address) {
		userService.saveUser(user, address);
		return "redirect:/register";
	}
	
	@GetMapping("/users")
	public String getAllUsers (ModelMap model, Address address) {
		Set<User> users = userService.findAll();
		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}
		return "users";
	}
	
	@GetMapping("/users/{userId}")
	public String getOneUser (ModelMap model, @PathVariable Long userId, Address address) {
		User user = userService.findOne(userId);
		Optional<Address> addresses = userService.findAddress(address, userId);
		model.put("users", Arrays.asList(user));
		model.put("address", addresses);
		model.put("user", user);
		return "users";
	}
	
	@PostMapping("/users/{userId}")
	public String postOneUser (User user, Address address) { 
		userService.saveUser(user, address);
		return "redirect:/users/"+user.getUserId();
	}
	
	@GetMapping("/users/{userId}/account/{accountId}")
	public String getOneAccount (ModelMap model,@PathVariable Long userId, @PathVariable Long accountId) {
		Account account = userService.findOneAccount(accountId);
		User user = userService.findByUserId(userId);
		model.put("account", account);
		model.put("user", user);
		return "account";
	}
	
	@GetMapping("/users/{userId}/account/")
	public String createAccount (ModelMap model, Account account) {
		model.put("account", account);
		
		return "account";
	}

	@PostMapping("/users/{userId}/account/{accountId}")
	public String updateAccount (Account account, User user) {
		userService.updateAccount(account);
		return "redirect:/users/{userId}/account/" + account.getAccountId();
	}
	@PostMapping("/users/{userId}/account/createAccount")
	public String createAccount (@PathVariable Long userId) {
		User user = userService.findByUserId(userId);
		Account account = userService.createAccount(user);
		return "redirect:/users/"+user.getUserId()+"/account/" + account.getAccountId();
	}

	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser (@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}
}   


