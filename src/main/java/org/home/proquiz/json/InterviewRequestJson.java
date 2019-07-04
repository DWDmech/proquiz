package org.home.proquiz.json;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.home.proquiz.entities.Interview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewRequestJson extends RequestJson {
	@NotNull(message="Error - forgot interview")
	@Valid
	private Interview interview;
	
	@NotNull(message="Error - forgot categoryId")
	@Min(0)
	private Long categoryId;
}
