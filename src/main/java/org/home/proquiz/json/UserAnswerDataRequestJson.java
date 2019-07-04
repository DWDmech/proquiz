package org.home.proquiz.json;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAnswerDataRequestJson {
	@Min(1)
	private long interviewId;
	@Min(1)
	private long userId;
}
