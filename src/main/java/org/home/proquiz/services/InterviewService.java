package org.home.proquiz.services;

import java.util.List;

import org.home.proquiz.entities.Interview;
import org.springframework.data.domain.Page;

public interface InterviewService {
	Interview getById(Long id);
	List<Interview> getByUserId(Long id, int page, int size);
	List<Interview> getAdminPage(int page, int size);
	Page<Interview> getPageByCategory(Long categoryId, Integer page, Integer size);
	
	boolean save(Interview interview, Long categoryId);
	boolean delete(Long id);
	boolean edit(Interview interview);
	boolean exist(Long interviewId);
	
	Long count();
	int countCorrectAnswers(Long questionId);
	boolean isCorrect(Long answerId);
	boolean isAnonymous(Long id);
	boolean isComment(Long interviewId);
	boolean isAuthor(Long userId, Long interviewId);
	Short countVotes(Long interviewId);
}
