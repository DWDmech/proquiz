package org.home.proquiz.auth;

import org.home.proquiz.entities.User;
import org.home.proquiz.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDitalsService implements UserDetailsService {
	@Autowired
	private UserRepository uRep;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = uRep.findByEmail(username).get();
		if(user == null) throw new UsernameNotFoundException("Can't find User by username [Email - " + username + "]");

		return new UserAuth(user);
	}

}
