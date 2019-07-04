package org.home.proquiz.services;

import java.util.List;

import org.home.proquiz.entities.Category;
import org.home.proquiz.entities.Interview;
import org.home.proquiz.model.CategoryRepository;
import org.home.proquiz.model.InterviewRepository;
import org.home.proquiz.model.UserRepository;
import org.home.proquiz.util.AssertUtil;
import org.home.proquiz.util.JDBCUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultInterviewService implements InterviewService {
	@Autowired
	private InterviewRepository iRep;
	@Autowired
	private UserRepository uRep;
	@Autowired
	private CategoryRepository cRep;
	@Autowired
	private JDBCUtils jdbcUtils;
	

	@Transactional(readOnly=true)
	public Interview getById(Long id) {
		AssertUtil.isNull(id, 
				this.getClass().getName()+".getById has error - id is null");
		AssertUtil.isPositiv(id, 
				this.getClass().getName()+".getById has error - id is less then zero");
		
		return iRep.findById(id).get();
	}

	@Transactional
	public boolean save(Interview interview, Long categoryId) {
		AssertUtil.isNull(interview, 
				this.getClass().getName()+".save has error - interview is null");
		AssertUtil.isNull(categoryId, 
				this.getClass().getName()+".save has error - categoryId is null");
		AssertUtil.isPositiv(categoryId,
				this.getClass().getName()+".save has error - categoryId less then zero");
		
		Interview i = iRep.save(interview);
        Category c = cRep.findById(categoryId).get();
        c.getInterviews().add(i);
        cRep.save(c);
        return true;
	}
	
	@Transactional
	public boolean delete(Long id) {
		AssertUtil.isNull(id, 
				this.getClass().getName()+".delete has error - id is null");
		AssertUtil.isPositiv(id, 
				this.getClass().getName()+".delete has error - id is less than zero");
		
		if(iRep.existsById(id)) {
//			iRep.deleteInterviewInCategory(id);
			jdbcUtils.delete(id);
			iRep.deleteById(id);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		} else return false;
		//ОЧЕНЬВАЖНОСДЕЛАТЬЭТОЕБАННОЕДЕРЬМО!
		
		return true;
	}

	@Transactional
	public boolean edit(Interview interview) {
		AssertUtil.isNull(interview, 
				this.getClass().getName()+".edit has error - interview is null");
		AssertUtil.isNull(interview.getId(), 
				this.getClass().getName()+".edit has error - interview.id is null");
		AssertUtil.isPositiv(interview.getId(), 
				this.getClass().getName()+".edit has error - interview.id is negative number");
		
		if(iRep.existsById(interview.getId()))
			iRep.save(interview);
		else return false;
		
		return true;
	}

	@Transactional(readOnly=true)
	public Long count() {
		return iRep.count();
	}

	@Transactional(readOnly=true)
	public boolean exist(Long interviewId) {
		AssertUtil.isNull(interviewId, 
				this.getClass().getName()+".exist has error - interview is null");
		AssertUtil.isPositiv(interviewId, 
				this.getClass().getName()+".exist has error - interview is less then zero");
		
		return iRep.existsById(interviewId);
	}

	@Transactional(readOnly=true)
	public List<Interview> getByUserId(Long id, int page, int size) {
		AssertUtil.isNull(id, 
				this.getClass().getName()+".getByUserId has error - id is null");
		AssertUtil.isPositiv(id, 
				this.getClass().getName()+".getByUserId has error - id is less then zero");
		
//		if(uRep.existsById(id) && iRep.existsByUserId(id))
		if(uRep.existsById(id))
			return iRep.findByUserId(id, PageRequest.of(page, size)).getContent();
		else 
			return null;
	}

	
	@Override
	public Page<Interview> getPageByCategory(Long categoryId, Integer page, Integer size) {
		AssertUtil.isNull(size, 
				this.getClass().getName()+".getNextPageByCategory has error - size is null");
		AssertUtil.isPositiv(size, 
				this.getClass().getName()+".getNextPageByCategory has error - size is less then zero");
		AssertUtil.isNull(categoryId, 
				this.getClass().getName()+".getNextPageByCategory has error - size is null");
		AssertUtil.isPositiv(categoryId, 
				this.getClass().getName()+".getNextPageByCategory has error - size is less then zero");
		AssertUtil.isNull(page, 
				this.getClass().getName()+".getNextPageByCategory has error - size is null");
		AssertUtil.isPositiv(page, 
				this.getClass().getName()+".getNextPageByCategory has error - size is less then zero");
		
		return iRep.findPageByCategory(categoryId, PageRequest.of(page, size));
	}
	
	@Override
	public List<Interview> getAdminPage(int page, int size) {
		AssertUtil.isPositiv(page, this.getClass().getName()+".isAuthor has error - userId is null");
		AssertUtil.isPositiv(size, this.getClass().getName()+".isAuthor has error - userId is null");
		
		return iRep.findAll(PageRequest.of(page, size)).getContent();
	}
	
	@Override
	public int countCorrectAnswers(Long questionId) {
		AssertUtil.isNull(questionId, 
				this.getClass().getName()+".countCorrectAnswers has error - questionId is null");
		AssertUtil.isPositiv(questionId, 
				this.getClass().getName()+".countCorrectAnswers has error - questionId is less then zero");
		
		return iRep.countOfCorrectAnswers(questionId);
	}

	@Override
	public boolean isCorrect(Long answerId) {
		AssertUtil.isNull(answerId, 
				this.getClass().getName()+".isCorrect has error - answerId is null");
		AssertUtil.isPositiv(answerId, 
				this.getClass().getName()+".isCorrect has error - answerId is less then zero");
		
		return iRep.isCorrect(answerId);
	}

	@Override
	public boolean isAnonymous(Long id) {
		AssertUtil.isNull(id, 
				this.getClass().getName()+".isAnonymous has error - id is null");
		AssertUtil.isPositiv(id, 
				this.getClass().getName()+".isAnonymous has error - id is less then zero");
		
		return iRep.isAnonymous(id);
	}

	@Override
	public boolean isComment(Long interviewId) {
		AssertUtil.isNull(interviewId, 
				this.getClass().getName()+".isComment has error - interviewId is null");
		AssertUtil.isPositiv(interviewId, 
				this.getClass().getName()+".isComment has error - interviewId is less then zero");
		
		return iRep.isComment(interviewId);
	}



	@Override
	public boolean isAuthor(Long userId, Long interviewId) {
		AssertUtil.isNull(userId, 
				this.getClass().getName()+".isAuthor has error - userId is null");
		AssertUtil.isPositiv(userId, 
				this.getClass().getName()+".isAuthor has error - userId is less then zero");
		AssertUtil.isNull(interviewId, 
				this.getClass().getName()+".isAuthor has error - interviewId is null");
		AssertUtil.isPositiv(interviewId, 
				this.getClass().getName()+".isAuthor has error - interviewId is less then zero");
		return iRep.isAuthor(userId, interviewId);
	}

	@Override
	public Short countVotes(Long interviewId) {
		AssertUtil.isNull(interviewId, 
				this.getClass().getName()+".countVotes has error - interviewId is null");
		AssertUtil.isPositiv(interviewId, 
				this.getClass().getName()+".countVotes has error - interviewId is less then zero");
		
		return iRep.countVotes(interviewId);
	}
}