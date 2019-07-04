package org.home.proquiz.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
	ROLE_ADMIN, ROLE_USER, ROLE_SURVEY_CREATOR;
	
	@Override
	public String getAuthority() {
		return this.toString();
	}
}
