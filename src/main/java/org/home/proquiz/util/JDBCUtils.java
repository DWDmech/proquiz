package org.home.proquiz.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JDBCUtils {
	@Autowired
	private DataSource source;

	public void delete(Long interviewId) {
		JdbcTemplate db = new JdbcTemplate(source);
		String sql0 = String.format("DELETE FROM interviews WHERE interview_id = %d", interviewId);
		db.execute(sql0);
	}
}
