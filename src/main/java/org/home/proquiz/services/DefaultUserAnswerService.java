package org.home.proquiz.services;

import java.util.List;

import org.home.proquiz.entities.UserAnswer;
import org.home.proquiz.event.ResetUserAnswersEvent;
import org.home.proquiz.event.UserVoteEvent;
import org.home.proquiz.model.InterviewRepository;
import org.home.proquiz.model.UserAnswerRepository;
import org.home.proquiz.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultUserAnswerService implements UserAnswerService {
	@Autowired
	private UserAnswerRepository uaRep;
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	@Autowired
	private InterviewRepository iRep;
	
	@Transactional
	public void saveUserAnswer(UserAnswer ua) {
		AssertUtil.isNull(ua, 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer is null");
		AssertUtil.isNull(ua.getAnswerId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.answerId is null");
		AssertUtil.isNull(ua.getQuestionId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.questionId is null");
		AssertUtil.isNull(ua.getInterviewId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.interviewId is null");
		AssertUtil.isNull(ua.getUserId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.userId is null");
		AssertUtil.isPositiv(ua.getAnswerId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.answerId is less then zero");
		AssertUtil.isPositiv(ua.getQuestionId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.questionId is less then zero");
		AssertUtil.isPositiv(ua.getInterviewId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.interviewId is less then zero");
		AssertUtil.isPositiv(ua.getUserId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.userId is less then zero");
		
		UserAnswer u = null;
		if(!uaRep.existUserAnswer(ua.getAnswerId(), 
								  ua.getQuestionId(), 
				                  ua.getInterviewId(), 
				                  ua.getUserId())) {
			
			u = uaRep.save(ua);
			eventPublisher.publishEvent(new UserVoteEvent(this, u));
		}
	}

	@Transactional(readOnly=true)
	public boolean exist(UserAnswer ua) {
		AssertUtil.isNull(ua, 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer is null");
		AssertUtil.isNull(ua.getAnswerId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.answerId is null");
		AssertUtil.isNull(ua.getQuestionId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.questionId is null");
		AssertUtil.isNull(ua.getInterviewId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.interviewId is null");
		AssertUtil.isNull(ua.getUserId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.userId is null");
		AssertUtil.isPositiv(ua.getAnswerId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.answerId is less then zero");
		AssertUtil.isPositiv(ua.getQuestionId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.questionId is less then zero");
		AssertUtil.isPositiv(ua.getInterviewId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.interviewId is less then zero");
		AssertUtil.isPositiv(ua.getUserId(), 
				this.getClass().getName()+".saveUserAnswer has error - userAnswer.userId is less then zero");
		
		return uaRep.existUserAnswer(ua.getAnswerId(), ua.getQuestionId(), ua.getInterviewId(), ua.getUserId());
	}

	public int countAnswersOfInterview(Long interviewId) {
		AssertUtil.isNull(interviewId, 
				this.getClass().getName()+".count has error - interviewId is null");
		AssertUtil.isPositiv(interviewId, 
				this.getClass().getName()+".count has error - interviewId is less then zero");
		
		return uaRep.userAnswerCount(interviewId);
	}

	public int countAnswers(Long interviewId, Long questionId, Long answerId) {
		AssertUtil.isNull(interviewId, 
				this.getClass().getName()+".count has error - interviewId is null");
		AssertUtil.isPositiv(questionId, 
				this.getClass().getName()+".count has error - interviewId is less then zero");
		AssertUtil.isNull(questionId, 
				this.getClass().getName()+".count has error - questionId is null");
		AssertUtil.isPositiv(interviewId, 
				this.getClass().getName()+".count has error - questionId is less then zero");
		AssertUtil.isNull(answerId, 
				this.getClass().getName()+".count has error - answerId is null");
		AssertUtil.isPositiv(answerId, 
				this.getClass().getName()+".count has error - answerId is less then zero");
		
		return uaRep.userAnswerCount(answerId, questionId, interviewId);
	}

	@Override
	public int countAnswersOfUser(UserAnswer var0) {
		return uaRep.countUserAnswer(var0.getQuestionId(),
									 var0.getInterviewId(), 
									 var0.getUserId());
	}


	@Override
	public List<UserAnswer> getUserAnser(long interviewId, long userId) {
		return uaRep.findUserAnswerByUserIdAndInterviewId(interviewId, userId);
	}

	@Override
	public boolean resetUserAnswers(Long interviewId, Long userId) {
		AssertUtil.isNull(interviewId, 
				this.getClass().getName()+".deleteUserAnswers has error - interviewId is null");
		AssertUtil.isPositiv(interviewId, 
				this.getClass().getName()+".deleteUserAnswers has error - interviewId is less then zero");
		AssertUtil.isNull(userId, 
				this.getClass().getName()+".deleteUserAnswers has error - userId is null");
		AssertUtil.isPositiv(userId, 
				this.getClass().getName()+".deleteUserAnswers has error - userId is less then zero");
		
		Long uid = iRep.userIdOfInterview(interviewId);
		if(userId.equals(uid) && iRep.existsById(interviewId)) {
			uaRep.resetUserAnswer(interviewId);
			eventPublisher.publishEvent(ResetUserAnswersEvent.of(this, interviewId));
		}
		
		return true;
	}
}
