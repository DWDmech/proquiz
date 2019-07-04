package org.home.proquiz.event;

import org.springframework.context.ApplicationEvent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@JsonIgnoreProperties(value = { "source" })
public class ResetUserAnswersEvent extends ApplicationEvent {
	private static final long serialVersionUID = -7353731562072113557L;
	@Getter
	@JsonProperty("interview_id")
	private Long interviewId;

	private ResetUserAnswersEvent (Object source, Long interviewId) {
		super(source);
		this.interviewId = interviewId;
	}
	
	public static ResetUserAnswersEvent of(Object source, Long interviewId) {
		return new ResetUserAnswersEvent(source, interviewId);
	}
}
