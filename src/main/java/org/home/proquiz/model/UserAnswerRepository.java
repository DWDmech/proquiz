package org.home.proquiz.model;

import java.util.List;

import org.home.proquiz.entities.UserAnswer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserAnswerRepository extends CrudRepository<UserAnswer, Long> {
//	@Query(nativeQuery=true, 
//			  value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END "
//					+ "FROM user_answers "
//					+ "WHERE interview_id = :interviewID "
//					+ "and question_id = :questionID "
//					+ "and answer_id = :answerID "
//					+ "and user_id = :userID")
	@Query("SELECT new java.lang.Boolean(count(*) > 0) "
			+ "FROM UserAnswer "
			+ "WHERE interviewId = :interviewID "
			+ "and questionId = :questionID "
			+ "and answerId = :answerID "
			+ "and userId = :userID")
	Boolean existUserAnswer(@Param("answerID") Long answerId, 
			                @Param("questionID") Long questionId, 
			                @Param("interviewID") Long interviewId, 
			                @Param("userID") Long userId);
	
	@Query(nativeQuery=true, 
			  value = "SELECT count(*) "
					+ "FROM user_answers "
					+ "WHERE interview_id = ?3 "
					+ "and question_id = ?2 "
					+ "and answer_id = ?1")
	int userAnswerCount(Long answerId, Long questionId, Long interviewId);
	
	@Query(nativeQuery=true,
			value="SELECT count(*) FROM user_answers WHERE interview_id = ?1;")
	int userAnswerCount(Long interviewId);

	@Query(nativeQuery=true, 
	value = "INSERT INTO "
			+ "user_answers('answer_id', 'question_id', 'interview_id', 'user_id') "
			+ "VALUES (?1, ?2, ?3, ?4)")
	void saveUserAnswer(Long answerId, Long questionId, Long interviewId, Long userId);
	
	@Query(nativeQuery=true, 
			  value = "DELETE FROM user_answers "
					+ "WHERE interview_id = ?3 "
					+ "and question_id = ?2 "
					+ "and answer_id = ?1 "
					+ "and user_id = ?4")
	void delete(Long answerId, Long questionId, Long interviewId, Long userId);

	@Query(nativeQuery=true, 
			  value = "SELECT count(*)"
					+ "FROM user_answers "
					+ "WHERE interview_id = ?3 "
					+ "and question_id = ?2 "
					+ "and answer_id = ?1 "
					+ "and user_id = ?4")
	int userAnswerCount(Long answerId, Long questionId, Long interviewId, Long userId);

	@Query(nativeQuery=true, 
			  value = "SELECT count(*)"
					+ "FROM user_answers "
					+ "WHERE interview_id = ?2 "
					+ "and question_id = ?1 "					
					+ "and user_id = ?3")
	int countUserAnswer(Long questionId, Long interviewId, Long userId);

	@Query(nativeQuery=true, 
			  value = "SELECT *"
					+ "FROM user_answers "
					+ "WHERE interview_id = ?1 "
					+ "and user_id = ?2")
	List<UserAnswer> findUserAnswerByUserIdAndInterviewId(long interviewId, long userId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM UserAnswer "
					+ "WHERE interviewId = ?1")
	void resetUserAnswer(Long interviewId);
}
