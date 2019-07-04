package org.home.proquiz.util;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.home.proquiz.entities.Interview;
import org.home.proquiz.entities.User;

public class CommonUtils {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static DateTimeFormatter getFormatter() { return formatter; }
	
	public static List<Interview> processList(List<Interview> list) {
		for(Interview i: list) {
			User user = new User();
			User tmpUser = i.getAuthor();
			user.setId(tmpUser.getId());
			user.setName(tmpUser.getName());
			user.setRoles(null);
			user.setActive(null);
			i.setAuthor(user);
			
			i.setQuestions(null);
			i.setComments(null);
			i.setIsAnonymous(null);
			i.setIsComment(null);
		}
		return list;
	}
	
	public static long ToSec(int milliSec) {
		return milliSec * 1000l;
	}
	
	public static long ToMin(int milliSec) {
		return milliSec * 60 * 1000l;
	}
	
	public static long ToHour(int milliSec) {
		return milliSec * 60 * 60 * 1000l;
	}

	public static List<Interview> processUser(List<Interview> list) {
		for(Interview i: list) {
			User user = new User();
			User tmpUser = i.getAuthor();
			user.setId(tmpUser.getId());
			user.setName(tmpUser.getName());
			user.setRoles(null);
			user.setActive(null);
			i.setAuthor(user);
			
		}
		return list;
	}
}
