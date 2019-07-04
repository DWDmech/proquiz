package org.home.proquiz.controllers;


import org.home.proquiz.util.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView out = new ModelAndView("index");
		out.addObject("content", PageInfo.PAGE.getContent("home_index"));
		out.addObject("style", PageInfo.PAGE.getStyle("home_index"));
		out.addObject("title", "Вітаю");
		
		return out;
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView out = new ModelAndView("index");
		out.addObject("content", PageInfo.PAGE.getContent("login"));
		out.addObject("style", PageInfo.PAGE.getStyle("login"));
		out.addObject("title", "Авторизація");
		
		return out;
	}
	
	@GetMapping("/registry")
	public ModelAndView registry() {
		ModelAndView out = new ModelAndView("index");
		out.addObject("content", PageInfo.PAGE.getContent("registry"));
		out.addObject("style", PageInfo.PAGE.getStyle("registry"));
		out.addObject("registryText", "Для реєстрації потрібно <br>заповнити усі поля");
		out.addObject("title", "Реєстрація");

		return out;
	}
	
	@GetMapping("/about")
	public ModelAndView about() {
		ModelAndView out = new ModelAndView("index");
		out.addObject("content", PageInfo.PAGE.getContent("about"));
		out.addObject("style", PageInfo.PAGE.getStyle("about"));
		out.addObject("title", "Про нас");
		
		return out;
	}
	
	@GetMapping("/contact")
	public ModelAndView contact() {
		ModelAndView out = new ModelAndView("index");
		out.addObject("content", PageInfo.PAGE.getContent("contact"));
		out.addObject("style", PageInfo.PAGE.getStyle("contact"));
		out.addObject("title", "Контакти");
		
		return out;
	}
}
