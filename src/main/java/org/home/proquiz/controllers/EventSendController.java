package org.home.proquiz.controllers;

import javax.servlet.http.HttpServletResponse;

import org.home.proquiz.event.EventsHandler;
import org.home.proquiz.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("/api/sse")
public class EventSendController {
	@Autowired
	private EventsHandler subscriber;
	//7_200_000l -> 2 hours
//	private Long keepingTime = 7200000l;
	private Long keepingTime = CommonUtils.ToHour(5);

	@GetMapping("/subscribe/{interview_id}")
	public SseEmitter addSseInterview(@PathVariable("interview_id") Long id, HttpServletResponse res) {
		res.setHeader("Cache-Control", "no-store");
		SseEmitter sse = new SseEmitter(keepingTime);
		subscriber.addInterview(id, sse);
		return sse;
   }
	
	@GetMapping("/subscribe/statistics/{interview_id}")
	public SseEmitter addSseStatistics(@PathVariable("interview_id") Long id, HttpServletResponse res) {
		res.setHeader("Cache-Control", "no-store");

		SseEmitter sse = new SseEmitter(keepingTime);
		subscriber.addStatistics(id, sse);
		return sse;
   }
}
