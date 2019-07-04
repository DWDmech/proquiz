package org.home.proquiz.event;

import org.home.proquiz.entities.UserAnswer;
import org.springframework.context.ApplicationEvent;

import lombok.Getter;

public class UserVoteEvent extends ApplicationEvent {
	private static final long serialVersionUID = 7414874162511541936L;
	@Getter
	private UserAnswer vote;

	public UserVoteEvent(Object source, UserAnswer vote) {
		super(source);
		this.vote = vote;
	}
}
