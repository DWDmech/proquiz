package org.home.proquiz.json;

import org.home.proquiz.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseJson extends ResponseJson<User> {
	private User user;

	public UserResponseJson(User user) {
		this.user = user;
	}

	public static UserResponseJson of(User user) {
		return new UserResponseJson(user);
	}
}
