package org.home.proquiz.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PageInfo {
	PAGE;
	
	private Map<String, String> content;
	private Map<String, List<String>> style;
	private final int COUNT_INTERVIEW_AT_WEB_PAGE = 9;
	private final int COUNT_INTERVIEW_AT_STATISTICS = 6;
	private final int COUNT_INTERVIEW_AT_ANDROID = 11;
	
	PageInfo(){
			content = new HashMap<>();
			content.put(new String("home_index"), new String("template/home/content/index.jsp"));
			content.put(new String("login"), new String("template/home/content/login.jsp"));
			content.put(new String("about"), new String("template/home/content/about.jsp"));
			content.put(new String("registry"), new String("template/home/content/registry.jsp"));
			content.put(new String("contact"), new String("template/home/content/contact.jsp"));
			
			content.put(new String("panel_index"), new String("template/panel/content/index.jsp"));
			content.put(new String("create"), new String("template/panel/content/create.jsp"));
			content.put(new String("interview"), new String("template/panel/content/interview.jsp"));
			content.put(new String("interview_user_list"), new String("template/panel/content/user_vote_list.jsp"));
			content.put(new String("statistic"), new String("template/panel/content/statistic.jsp"));
			content.put(new String("edit"), new String("template/panel/content/edit.jsp"));
			content.put(new String("delete"), new String("template/panel/content/delete.jsp"));
			content.put(new String("search"), new String("template/panel/content/search.jsp"));
			content.put(new String("editInterview"), new String("template/panel/content/edit_interview.jsp"));
			content.put(new String("statistic_admin"), new String("template/panel/content/statistic_admin.jsp"));

			style = new HashMap<>();
			style.put(new String("home_index"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/common.css")));
			style.put(new String("login"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/common.css"), new String("css/login.css")));
			style.put(new String("registry"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/common.css"), new String("css/login.css")));
			style.put(new String("about"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/common.css"), new String("css/login.css")));
			style.put(new String("contact"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/common.css"), new String("css/login.css")));
			
			style.put(new String("panel_index"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/panel.css")));
			style.put(new String("create"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/panel.css"), new String("css/create.css")));
			style.put(new String("interview"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/panel.css"), new String("css/create.css")));
			style.put(new String("interview_user_list"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/panel.css")));
			style.put(new String("edit"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/panel.css"), new String("css/create.css")));
			style.put(new String("delete"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/panel.css"), new String("css/create.css")));
			style.put(new String("statistic"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/panel.css"), new String("css/stat.css")));
			style.put(new String("search"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/panel.css")));
			style.put(new String("editInterview"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/panel.css"), new String("css/create.css")));
			style.put(new String("statistic_admin"), Arrays.asList(new String("css/bootstrap/bootstrap.css"), new String("css/panel.css"), new String("css/stat.css")));
	}
	
	public String getContent(String page) { 
		if(content != null)
			return new String(content.get(page));
		return null;
	}
	
	public List<String> getStyle(String page) {
		if(style != null)
			return new ArrayList<String>(style.get(page));
		return null;
	}
	
	public int getStatisticsPageSize()       { return this.COUNT_INTERVIEW_AT_STATISTICS; }
	public int getWebInterviewPageSize()     { return this.COUNT_INTERVIEW_AT_WEB_PAGE; }
	public int getAndroidInterviewPageSize() { return this.COUNT_INTERVIEW_AT_ANDROID; }
	public String getTopPanel() { return ""; }
	public String getLeftPanel() { return "";  }
}
