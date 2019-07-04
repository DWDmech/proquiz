package org.home.proquiz.controllers;

import javax.validation.Valid;

import org.home.proquiz.entities.User;
import org.home.proquiz.json.ErrorMessageResponseJson;
import org.home.proquiz.json.ResponseJson;
import org.home.proquiz.json.UserNameResponseJson;
import org.home.proquiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService uSer;
	
//	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public ResponseEntity<?> getUserById(@PathVariable Long id) {
//		if(id <= 0) return ResponseEntity.badRequest().build();
//		if(!uSer.exist(id)) return ResponseEntity.badRequest().build();
//
//		User user = uSer.getById(id);
//		user.setPassword(null);
//		return ResponseEntity.ok(user);
//	}
	
	@GetMapping(value = "/get/name/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseJson<String>> getUserName(@PathVariable  Long id) {
		if(id <= 0) return ResponseEntity.badRequest().build();
		if(!uSer.exist(id)) return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(new UserNameResponseJson(uSer.getUserNameById(id)));
	}
	
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> addUser (@RequestBody @Valid User user, BindingResult errRes) {
		if(errRes.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for(ObjectError o: errRes.getAllErrors()) {
				sb.append(o.getDefaultMessage());
				sb.append('\n');
			}
			return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of(sb.toString()));
		}
		
		if(uSer.getByEmail(user.getEmail()) != null) {
			return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of("Користувач з такою поштою вже зареєстрований"));
		}
		
		uSer.save(user);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> editUser (@RequestBody @Valid User user,
									   @SessionAttribute("user")User logged,
									   BindingResult errRes) {
		
		if(errRes.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for(ObjectError o: errRes.getAllErrors()) {
				sb.append(o.getDefaultMessage());
				sb.append('\n');
			}
			return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of(sb.toString()));
		}
		
		if(!user.getId().equals(logged.getId()))
			return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of("Error: you are not logged user"));
		
		if(user.getId() != null) uSer.save(user);
		return ResponseEntity.ok().build();
	}
}