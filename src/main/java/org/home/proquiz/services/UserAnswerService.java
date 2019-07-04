package org.home.proquiz.services;

import java.util.List;

import org.home.proquiz.entities.UserAnswer;

public interface UserAnswerService {
	void saveUserAnswer(UserAnswer userAnswer);
	// boolean exist(UserAnswer userAnswer);

	int countAnswersOfInterview(Long interviewId);
	int countAnswers(Long interviewId, Long questionId, Long answerId);
	int countAnswersOfUser(UserAnswer var0);
	List<UserAnswer> getUserAnser(long interviewId, long userId);
	boolean resetUserAnswers(Long interviewId, Long userId);
}
