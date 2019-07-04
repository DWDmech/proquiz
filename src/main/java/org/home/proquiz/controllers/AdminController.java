package org.home.proquiz.controllers;

import org.home.proquiz.entities.User;
import org.home.proquiz.services.CategoryService;
import org.home.proquiz.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/panel")
public class AdminController {
	@Autowired
	private CategoryService caSer;

	@PostAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/statistics")
	public ModelAndView statisticsAdmin(@SessionAttribute("user") User user) {
		ModelAndView out = new ModelAndView("panel");
		out.addObject("title", "Панель для адміна");
		out.addObject("style", PageInfo.PAGE.getStyle("statistic_admin"));
		out.addObject("content", PageInfo.PAGE.getContent("statistic_admin"));
		out.addObject("userName", user.getName());
		out.addObject("leftPanel", "template/panel/blank/leftPanel.jsp");
		out.addObject("topPanel", "template/panel/blank/topPanel.jsp");
		out.addObject("categories", caSer.getCategories());
		out.addObject("toActive", 6);
		return out;
	}
}
