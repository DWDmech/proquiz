package org.home.proquiz.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.home.proquiz.entities.Interview;
import org.home.proquiz.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class DefaultInterviewSearchService implements SearchService {
//	@Autowired
//	private InterviewRepository iRep;
//	
//	@Transactional(readOnly=true)
//	public List<Interview> searchByUserName(String name) {
//		AssertUtil.isNull(name, 
//				this.getClass().getName() + ".searchByUserName has error - name is null");
//		List<Interview> res = new ArrayList<>();
//		res = iRep.findByUserName(name);
//
//		return res;
//	}
//
//	@Transactional(readOnly=true)
//	public List<Interview> searchByTitle(String title) {
//		AssertUtil.isNull(title, 
//				this.getClass().getName() + ".searchByTitle has error - title is null");
//		return iRep.findByTitle(title);
//	}
	
	private JdbcTemplate db;
	@Autowired
	public DefaultInterviewSearchService(DataSource sour) {
		this.db = new JdbcTemplate(sour);
	}

	@Override
	public List<Interview> searchByUserName(String name) {
		if(name.toLowerCase().contains("insert") || 
		   name.toLowerCase().contains("delete") || 
		   name.toLowerCase().contains("update") ||
		   name.toLowerCase().contains("select")) {
			return Collections.emptyList();
		}
		
		String sql0 = "SELECT * "
				+ "FROM interview i inner join users u on "
				+ "i.user_id = u.id "
				+ "and "
				+ "u.name like '%" + name + "%'";
		List<Interview> res = db.query(sql0, new RowMapper<Interview>() {
			@Override
			public Interview mapRow(ResultSet rs, int rowNum) throws SQLException {
				Interview i = new Interview();
				i.setId(rs.getLong("id"));
				i.setTitle(rs.getString("title"));
				i.setDate(Timestamp.valueOf(rs.getString("date")).toLocalDateTime());
				i.setAuthor(new User());
				i.getAuthor().setId(rs.getLong("user_id"));
				i.getAuthor().setName(rs.getString("name"));
				i.setActive(rs.getBoolean("active"));
				i.setIsComment(rs.getBoolean("isComment"));
				i.setIsAnonymous(rs.getBoolean("isAnonymous"));
				return i;
			}
		});
		return res;
	}

	@Override
	public List<Interview> searchByTitle(String title) {
		String sql0 = "SELECT * FROM interview i, users u WHERE i.title like '%" + title + "%' and u.id = i.user_id";
		List<Interview> res = db.query(sql0, new RowMapper<Interview>() {
			@Override
			public Interview mapRow(ResultSet rs, int rowNum) throws SQLException {
				Interview i = new Interview();
				i.setId(rs.getLong("id"));
				i.setTitle(rs.getString("title"));
				i.setDate(Timestamp.valueOf(rs.getString("date")).toLocalDateTime());
				i.setAuthor(new User());
				i.getAuthor().setId(rs.getLong("user_id"));
				i.getAuthor().setName(rs.getString("name"));
				i.setActive(rs.getBoolean("active"));
				i.setIsComment(rs.getBoolean("isComment"));
				i.setIsAnonymous(rs.getBoolean("isAnonymous"));
				return i;
			}
		});
		return res;
	}

}
