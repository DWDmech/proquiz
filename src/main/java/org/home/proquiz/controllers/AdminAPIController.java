package org.home.proquiz.controllers;

import java.util.List;

import org.home.proquiz.entities.Interview;
import org.home.proquiz.entities.User;
import org.home.proquiz.services.InterviewService;
import org.home.proquiz.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/api/admin")
public class AdminAPIController {
	@Autowired
	private InterviewService iSer;
 
	@GetMapping(value="/{page}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Interview>> getAdminPage(@PathVariable int page, 
                                                        @SessionAttribute("user")User user){ 
		if(page < 0) 
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(iSer.getAdminPage(page, PageInfo.PAGE.getStatisticsPageSize())); 
	}
	
	@PutMapping("/block/{interview_id}")
	public ResponseEntity<?> blockInterview(@PathVariable("interview_id")Long id, 
			                                @SessionAttribute("user")User user) {
		
		Interview i = iSer.getById(id);
		i.setActive(false);
		iSer.edit(i);
		
		return ResponseEntity.ok().build();
	}
}
