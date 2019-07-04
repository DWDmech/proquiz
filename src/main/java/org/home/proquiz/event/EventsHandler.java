package org.home.proquiz.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.home.proquiz.services.CommentService;
import org.home.proquiz.services.UserAnswerService;
import org.home.proquiz.util.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@Component("userVoteHandler")
public class EventsHandler {
	private ExecutorService sender;
	private Map<Long, List<SseEmitter>> interviews;
	private Map<Long, SseEmitter> statistics;
	
	
	@Autowired
	private UserAnswerService uaSer;
	@Autowired
	private CommentService cSer;
	
	{
		interviews = new HashMap<>();
		statistics = new HashMap<>();
		sender = Executors.newSingleThreadExecutor();
	}
	
	public void addInterview(Long interviewId, SseEmitter sse) {
		sse.onCompletion(() -> this.interviews.get(interviewId).remove(sse));
		sse.onTimeout(()    -> this.interviews.get(interviewId).remove(sse));
		sse.onError((ex)    -> this.interviews.get(interviewId).remove(sse));
		
		if(this.interviews.get(interviewId) != null) {
			this.interviews.get(interviewId).add(sse);
		} else {
			this.interviews.put(interviewId, new ArrayList<SseEmitter>());
			this.interviews.get(interviewId).add(sse);
		}
	}
	
	public void addStatistics(Long interviewId, SseEmitter sse) {
		sse.onCompletion(() -> this.statistics.remove(interviewId));
		sse.onTimeout(()    -> this.statistics.remove(interviewId));
		sse.onError((ex)    -> this.statistics.remove(interviewId));

		this.statistics.put(interviewId, sse);
	}
	
	@EventListener
	public void userVoteEventHandler(UserVoteEvent event) {
		sender.execute(() -> {
			SseEmitter sse = statistics.get(event.getVote().getInterviewId());
			if(sse != null) {
				int count = uaSer
						.countAnswers(event.getVote().getInterviewId(), 
								      event.getVote().getQuestionId(), 
								      event.getVote().getAnswerId());
				event.getVote().setCountAnswers(count);
				try {
				  sse.send(SseEmitter.event()
			                         .data(event)
			                         .name(EventType.VOTE.toString()));
				} catch (Exception e) {
					e.printStackTrace();
					statistics.remove(event.getVote().getInterviewId(), sse);
				}
			}
		});
	}
	
	@EventListener
	public void resetEventHandler(ResetUserAnswersEvent event) {
		sender.execute(() -> {
			List<SseEmitter> deadEmitters = new ArrayList<>();
			SseEventBuilder sseEvent = SseEmitter.event()
		                                         .data(event)
		                                         .name(EventType.RESET.toString());

			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			List<SseEmitter> sses = interviews.get(event.getInterviewId());
			if(sses != null) {
				for(SseEmitter sse: sses) {
					try {
						sse.send(sseEvent);
					} catch (Exception e) {
						e.printStackTrace();
						deadEmitters.add(sse);
					}
				} 
			    interviews.get(event.getInterviewId()).removeAll(deadEmitters);
			}
					
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			SseEmitter sse = statistics.get(event.getInterviewId());
			if(sse != null) {
				try {
				  sse.send(sseEvent);
				} catch (Exception e) {
				  e.printStackTrace();
				  statistics.remove(event.getInterviewId(), sse);
				}
			}
		});
	}
	
	@EventListener
	public void commentAddEventHandler(CommentAddEvent event) {
		sender.execute(() -> {
			List<SseEmitter> deadEmitters = new ArrayList<>();
			event.setComment(cSer.getByInterviewId(event.getInterviewId()));
			SseEventBuilder sseEvent = SseEmitter.event()
					                             .data(event)
					                             .name(EventType.COMMENT.toString());
				
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			List<SseEmitter> sses = interviews.get(event.getInterviewId());
			if(sses != null) {
				for(SseEmitter sse: sses) {
					try {
						sse.send(sseEvent);
					} catch (Exception e) {
						e.printStackTrace();
						deadEmitters.add(sse);
					}
				} 
				interviews.get(event.getInterviewId()).removeAll(deadEmitters);
			}
				
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			SseEmitter sse = statistics.get(event.getInterviewId());
			if(sse != null) {
				try {
				  sse.send(sseEvent);
				} catch (Exception e) {
				  e.printStackTrace();
				  statistics.remove(event.getInterviewId(), sse);
				}
			}
		});
	}
}
