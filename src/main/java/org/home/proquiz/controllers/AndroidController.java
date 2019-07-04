package org.home.proquiz.controllers;

import java.util.List;

import org.home.proquiz.entities.Category;
import org.home.proquiz.entities.Interview;
import org.home.proquiz.services.CategoryService;
import org.home.proquiz.services.InterviewService;
import org.home.proquiz.util.CommonUtils;
import org.home.proquiz.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/android")
public class AndroidController {
	@Autowired
	private CategoryService cSer;
	@Autowired
	private InterviewService iSer;
	
	@GetMapping(value="/categories", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Category>> getCategories() {
		List<Category> list = cSer.getCategories();
		//todo: check this
		list.forEach(c -> {
			c.setInterviews(null);
		});
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value="/page/{categoryId}/{pageNumber}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Interview>> getNextPage(@PathVariable("pageNumber") Integer page, 
			                                           @PathVariable("categoryId") Long categoryId) {
		
		if(page < 0) return ResponseEntity.badRequest().build();
		if(categoryId < 0) return ResponseEntity.badRequest().build();
		
		
		List<Interview> list = 
				iSer.getPageByCategory(categoryId, page, 
							PageInfo.PAGE.getAndroidInterviewPageSize()).getContent();
		return ResponseEntity.ok(CommonUtils.processList(list));
	}
}