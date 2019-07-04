package org.home.proquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class ErrorController {
	@GetMapping("/404")
	public ModelAndView error404() {
		return new ModelAndView("404");
	}
	
	@GetMapping("/403")
	public ModelAndView error403() {
		return new ModelAndView("403");
	}
	
}
