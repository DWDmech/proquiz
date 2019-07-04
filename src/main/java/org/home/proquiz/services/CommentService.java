package org.home.proquiz.services;

import java.util.List;

import org.home.proquiz.entities.Comment;

public interface CommentService {
	void save(Comment comment, Long interviewId);
	void delete(Long id);
	void deleteAll(Long interviewId);
	void edit(Comment comment);
	List<Comment> getByInterviewId(Long interviewId);
}