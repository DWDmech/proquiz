package org.home.proquiz.event;

import org.home.proquiz.entities.Comment;
import org.home.proquiz.util.Const;
import org.springframework.context.ApplicationEvent;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class CommentAddEvent extends ApplicationEvent {
	private static final long serialVersionUID = -6646778451444562961L;
	@Getter
	@Setter
	private Object comment;
	@Getter
	@JsonProperty("interview_id")
	private Long interviewId;
	@Getter
	private String html = ""; 
			

	
	private CommentAddEvent(Object source, Comment comment, Long interviewId) {
		super(source);
		this.comment = comment;
		this.interviewId = interviewId;
		
		if(comment.getAuthor() != null) {
			this.html = String.format(
				"  <div class=\"comment border-bottom mb-4\">\r\n" + 
				"		<div class=\"comment-user-info\">\r\n" + 
				"			<img src=\"img/fail.jpg\" alt=\"img\" class=\"comment-user-img\">\r\n" + 
				"			<span class=\"comment-user-name ml-2\">%s</span><span class=\"comment-timestamp ml-1\"> - %s</span>\r\n" + 
				"		</div>\r\n" + 
				"		<div class=\"comment-text pt-xl-3 pl-4\">%s</div>\r\n" + 
				"	</div>", 
				comment.getAuthor().getName(), comment.getDate().format(Const.getFormatter()).toString(), comment.getText());
		} else {
			this.html = String.format(
				"  <div class=\"comment border-bottom mb-4\">\r\n" +
				"		<div class=\"comment-user-info\">\r\n" +
				"			<img src=\"img/fail.jpg\" alt=\"img\" class=\"comment-user-img\">\r\n" +
				"			<span class=\"comment-user-name ml-2\">Ім`я анонімне</span><span class=\"comment-timestamp ml-1\"> - %s</span>\r\n" + 
				"		</div>\r\n" +
				"		<div class=\"comment-text pt-xl-3 pl-4\">%s</div>\r\n" +
				"	</div>",
				comment.getDate().format(Const.getFormatter()).toString(), comment.getText());
		}
	}
	
	public static CommentAddEvent of(Object source, Comment comment, Long interviewId) {
		return new CommentAddEvent(source, comment, interviewId);
	}
}
