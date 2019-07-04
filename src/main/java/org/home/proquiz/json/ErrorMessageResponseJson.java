package org.home.proquiz.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessageResponseJson extends ResponseJson<String> {
	private String errMsg;
	
	private ErrorMessageResponseJson(String errMsg) {
		this.errMsg = errMsg;
	}

	public static ErrorMessageResponseJson of(String errMsg) {
		return new ErrorMessageResponseJson(errMsg);
	}
}
