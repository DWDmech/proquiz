package org.home.proquiz.model;

import org.home.proquiz.entities.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	@Query(nativeQuery=true,
			value="SELECT c.* FROM comment c, comments cc where cc.interview_id = :interviewID and c.id = cc.comment_id;")
	Iterable<Comment> findByInterviewId(@Param("interviewID") Long interviewID);
	
	@Query(nativeQuery=true,
			value="DELETE FROM comments cc where cc.interview_id = ?1; DELETE FROM comment c WHERE c.id = ?1;")
	void deleteAllFromInterview(Long interviewId);
	
//	@Query(nativeQuery=true,
//			value="INSERT INTO comments VALUES(:interviewID, :commentID)")
//	void commentInInterview(@Param("interviewID") Long interviewID,
//			                @Param("commentID") Long commentID);
}