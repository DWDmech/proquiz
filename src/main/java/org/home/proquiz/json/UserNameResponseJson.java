package org.home.proquiz.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNameResponseJson extends ResponseJson<String> {
	@JsonProperty("user_name")
	private String userName;
	
	public UserNameResponseJson(String data) {
		this.userName = data;
	}
}
