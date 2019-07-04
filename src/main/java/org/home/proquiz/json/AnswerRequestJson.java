package org.home.proquiz.json;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequestJson extends RequestJson {

	@JsonProperty("answer_id")
	@Min(1)
	private Long answerId;
}
