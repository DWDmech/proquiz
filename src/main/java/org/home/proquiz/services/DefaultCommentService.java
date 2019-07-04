package org.home.proquiz.services;

import java.util.List;

import org.home.proquiz.entities.Comment;
import org.home.proquiz.entities.Interview;
import org.home.proquiz.event.CommentAddEvent;
import org.home.proquiz.model.CommentRepository;
import org.home.proquiz.model.InterviewRepository;
import org.home.proquiz.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultCommentService implements CommentService {
	@Autowired
	private CommentRepository cRep;
	@Autowired
	private InterviewRepository iRep;
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Transactional
	public void save(Comment comment, Long interviewId) {
		AssertUtil.isNull(comment, 
				this.getClass().getName() + ".save has error - Comment is null");		
		AssertUtil.isNull(interviewId, 
				this.getClass().getName() + ".save has error - interviewId is null");
		AssertUtil.isPositiv(interviewId, 
				this.getClass().getName() + ".save has error - interviewId is less then zero");
		
		Comment c = cRep.save(comment);
		Interview i = iRep.findById(interviewId).get();
		i.getComments().add(c);
		iRep.save(i);
		eventPublisher.publishEvent(CommentAddEvent.of(this, c, interviewId));
	}

	@Transactional
	public void delete(Long id) {
		AssertUtil.isNull(id, 
				this.getClass().getName() + ".delete has error - id is null");
		AssertUtil.isPositiv(id, 
				this.getClass().getName() + ".delete has error - id is less then zero");
		
		if(cRep.existsById(id))
			cRep.deleteById(id);
	}

	@Transactional
	public void edit(Comment comment) {
		AssertUtil.isNull(comment, 
				this.getClass().getName() + ".edit has error - comment is null");
		AssertUtil.isPositiv(comment.getId(), 
				this.getClass().getName() + ".edit has error - Comment.id is less then zero");
		
		if(cRep.existsById(comment.getId()))
			cRep.save(comment);
	}

	@Transactional(readOnly=true)
	public List<Comment> getByInterviewId(Long interviewId) {
		AssertUtil.isNull(interviewId, 
				this.getClass().getName() + ".getByInterviewId has error - interviewId is null");
		AssertUtil.isPositiv(interviewId, 
				this.getClass().getName() + ".getByInterviewId has error - interviewId is less then zero");
		
		return (List<Comment>) cRep.findByInterviewId(interviewId);
	}

	@Override
	public void deleteAll(Long interviewId) {
		AssertUtil.isNull(interviewId, 
				this.getClass().getName() + ".deleteAll has error - interviewId is null");
		AssertUtil.isPositiv(interviewId, 
				this.getClass().getName() + ".deleteAll has error - interviewId is less then zero");
		
		cRep.deleteAllFromInterview(interviewId);
	}
}