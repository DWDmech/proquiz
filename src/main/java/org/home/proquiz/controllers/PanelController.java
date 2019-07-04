package org.home.proquiz.controllers;

import java.util.List;

import org.home.proquiz.entities.Interview;
import org.home.proquiz.entities.User;
import org.home.proquiz.services.CategoryService;
import org.home.proquiz.services.InterviewService;
import org.home.proquiz.services.SearchService;
import org.home.proquiz.services.UserService;
import org.home.proquiz.util.PageInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/panel")
public class PanelController implements InitializingBean {
	@Autowired
	private CategoryService caSer;
	@Autowired
	private UserService uSer;
	@Autowired
	private InterviewService iSer;
	@Autowired
	private SearchService sSer; 

	@GetMapping 
	public String index() { return "redirect:/panel/category/1"; }
	@GetMapping("/category/**") 
	public String category() { return "redirect:/panel/category/1"; }

	@GetMapping("/category/{id}")
	public ModelAndView categoryById(@PathVariable("id")Long categoryId, 
									 @SessionAttribute("user")User user) {
		
		if(!caSer.exits(categoryId)) return new ModelAndView("redirect:/404");
		
		ModelAndView out = new ModelAndView("panel");
		out.addObject("style", PageInfo.PAGE.getStyle("panel_index"));
		out.addObject("title", "Категорії");
		out.addObject("content", PageInfo.PAGE.getContent("panel_index"));
		out.addObject("leftPanel", "template/panel/blank/leftPanel.jsp");
		out.addObject("categories", caSer.getCategories());
		out.addObject("topPanel", "template/panel/blank/topPanel.jsp");
		out.addObject("userName", user.getName());
		out.addObject("toActive", categoryId);
		out.addObject("categoryId", categoryId);
		return out;
	}
	
	@GetMapping("/statistics")
	public ModelAndView statistics(@SessionAttribute("user")User user) {
		ModelAndView out = new ModelAndView("panel");
		out.addObject("title", "Статистика опитувань");
		out.addObject("style", PageInfo.PAGE.getStyle("statistic"));
		out.addObject("content", PageInfo.PAGE.getContent("statistic"));
		out.addObject("leftPanel", "template/panel/blank/leftPanel.jsp");
		out.addObject("topPanel", "template/panel/blank/topPanel.jsp");
		out.addObject("categories", caSer.getCategories());
		out.addObject("toActive", 5);
		out.addObject("userName", user.getName());
		return out;
	}
	
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ PAGE OF INTERVIEW ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@GetMapping("/interview/{id}")
	public ModelAndView getByIdPage(@PathVariable("id")Long interviewId, @SessionAttribute("user")User user) {
		if(!iSer.exist(interviewId)) return new ModelAndView("redirect:/404");
		
		ModelAndView out = new ModelAndView("panel");
		out.addObject("style", PageInfo.PAGE.getStyle("interview"));
		out.addObject("title", "Опитування");
		out.addObject("content", PageInfo.PAGE.getContent("interview"));
		out.addObject("leftPanel", "template/panel/blank/leftPanel.jsp");
		out.addObject("topPanel", "template/panel/blank/topPanel.jsp");
		out.addObject("categories", caSer.getCategories());
		out.addObject("interview", iSer.getById(interviewId));
		out.addObject("userName", user.getName());
		out.addObject("userId", user.getId());
		out.addObject("interviewId", interviewId);
		return out;
	}
	
	@GetMapping(value="/interview/{interview_id}/users/{answer_id}")
	public ModelAndView listUsers(@PathVariable("answer_id") Long answerId,
								  @PathVariable("interview_id") Long interviewId) {
		
//		if(!iSer.exist(interviewId)) return new ModelAndView("redirect:/404");

//		Interview i = iSer.getById(interviewId);
		ModelAndView out = new ModelAndView("template/panel/content/user_vote_list");
		
		if(!iSer.isAnonymous(interviewId)) {
			List<User> list = uSer.usersByAnswer(answerId);
			
			if(list == null)
				out.addObject("text", "Немає голосів");

			out.addObject("users", list);
		} else {
			out.addObject("text", "Анонімне голосування");
		}
		
		
		return out;
	}
	
	@GetMapping("/interview/edit/{id}")
	public ModelAndView getEditPage(@PathVariable("id")Long interviewId, 
			                        @SessionAttribute("user")User user) {
		if(!iSer.exist(interviewId)) return new ModelAndView("redirect:/error");
		
		ModelAndView out = new ModelAndView("panel");
		out.addObject("title", "Редагування");
		out.addObject("style", PageInfo.PAGE.getStyle("editInterview"));
		out.addObject("content", PageInfo.PAGE.getContent("editInterview"));
		out.addObject("leftPanel", "template/panel/blank/leftPanel.jsp");
		out.addObject("topPanel", "template/panel/blank/topPanel.jsp");
		out.addObject("categories", caSer.getCategories());
		out.addObject("userName", user.getName());
		out.addObject("userId", user.getId());
		out.addObject("i", iSer.getById(interviewId));
		return out;
	}
	
	@GetMapping("/interview/add")
	public ModelAndView addPage(@SessionAttribute("user")User user) {
		ModelAndView out = new ModelAndView("panel");
		out.addObject("title", "Створення опитування");
		out.addObject("style", PageInfo.PAGE.getStyle("create"));
		out.addObject("content", PageInfo.PAGE.getContent("create"));
		out.addObject("leftPanel", "template/panel/blank/leftPanel.jsp");
		out.addObject("topPanel", "template/panel/blank/topPanel.jsp");
		out.addObject("categories", caSer.getCategories());
		out.addObject("userName", user.getName());
		out.addObject("user", user);
		return out;
	}
	
	@GetMapping("/interview/add/question")
	public ModelAndView getQuestion(@RequestParam("q") int i) {
		ModelAndView out = new ModelAndView("template/panel/blank/question");
		out.addObject("qPosition", i);
		return out;
	}
	
	@GetMapping("/interview/add/answer")
	public ModelAndView getAnswer(@RequestParam("a") int a, 
								  @RequestParam("q") int q) {
		
		ModelAndView out = new ModelAndView("template/panel/blank/answer");
		out.addObject("aPosition", a);
		out.addObject("qPosition", q);
		return out;
	}
	
	@GetMapping("/search")
	public ModelAndView getPage(@RequestParam("input-search")String toSearch, 
								@RequestParam("mode")String mode, 
								@SessionAttribute("user")User user) {
		
		List<Interview> list = null;
		
		ModelAndView out = new ModelAndView("panel");
		out.addObject("title", "Результат пошуку");
		out.addObject("text", "Результат пошуку");
		out.addObject("style", PageInfo.PAGE.getStyle("search"));
		out.addObject("content", PageInfo.PAGE.getContent("search"));
		out.addObject("leftPanel", "template/panel/blank/leftPanel.jsp");
		out.addObject("topPanel", "template/panel/blank/topPanel.jsp");
		out.addObject("categories", caSer.getCategories());
		out.addObject("userName", user.getName());
		
		
		switch(mode) {
			case "name": 
				list = sSer.searchByUserName(toSearch);
				break;
			case "title": 
				list = sSer.searchByTitle(toSearch);
				break;
			default:
				out.addObject("text", "че умный сильно?");
				break;
		}
		
		if(list != null) {
			if(list.size() == 0) 
				out.addObject("text", "Порожній результат");
			else 
				out.addObject("interviews", list);
		}
		
		return out;
	}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ PAGE OF INTERVIEW ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(caSer == null) throw new IllegalArgumentException("Category Serview dose not set");	
	}
}
