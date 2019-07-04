package org.home.proquiz.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.home.proquiz.entities.User;
import org.home.proquiz.util.Const;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class UserSuccessAuthnticationHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication a)
			throws IOException, ServletException {

		User user = ((UserAuth) a.getPrincipal()).getUser();
		user.setPassword("[ Secred password ]");
		req.getSession().setAttribute(Const.getUserName(), user);
		
		String url = req.getContextPath() + "/panel/category/1";
		res.setStatus(HttpServletResponse.SC_OK);
		res.sendRedirect(url);
	}
}