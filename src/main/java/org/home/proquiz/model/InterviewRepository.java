package org.home.proquiz.model;

import java.util.List;

import org.home.proquiz.entities.Interview;
import org.home.proquiz.entities.UserAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface InterviewRepository extends PagingAndSortingRepository<Interview, Long> {
	@Query(nativeQuery=true, 
	value = "SELECT * FROM interview WHERE user_id = ?1 ORDER BY ?#{#pageable}")
	Page<Interview> findByUserId(Long userId, Pageable page);

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@Query(nativeQuery=true, 
			value = "SELECT i.* "
					+ "FROM interview i inner join users u on "
					+ "i.user_id = u.id "
					+ "and "
					+ "u.name like '%?1%' "
					+ "ORDER BY ?#{#pageable}")
	Page<Interview> findByUserName(String name, Pageable page);
	
	@Query(nativeQuery=true, 
		   value="SELECT * FROM interview i WHERE i.title like '%?1%' ORDER BY ?#{#pageable}")
	Page<Interview> findByTitle(String title, Pageable page);
	
	@Query(nativeQuery=true, 
			value = "SELECT i.* "
					+ "FROM interview i inner join users u on "
					+ "i.user_id = u.id "
					+ "and "
					+ "u.name like '%?1%'")
	List<Interview> findByUserName(String name);
	
	@Query(nativeQuery=true, 
		   value="SELECT * FROM interview i WHERE i.title like '%?1%'")
	List<Interview> findByTitle(String title);
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Modifying
	@Query(nativeQuery=true,
			value = "DELETE FROM interviews WHERE interview_id = ?1")
	void deleteInterviewInCategory(Long interviewID);
	
	@Query(value = "SELECT correct FROM Answer WHERE id = ?1")
	boolean isCorrect(Long answerId);
	
	@Query("SELECT isAnonymous FROM Interview WHERE id = ?1")
	Boolean isAnonymous(Long id);

	@Query("SELECT i.isComment FROM Interview i WHERE i.id = :id")
	boolean isComment(@Param("id") Long id);
	
	@Query(nativeQuery=true, 
			value = "SELECT i.* "
			+ "FROM interview i "
			+ "INNER JOIN interviews ii "
			+ "ON i.id = ii.interview_id and ii.category_id = ?1 and i.active = true "
			+ "ORDER BY ?#{#pageable}",
			countQuery = "SELECT count(i.id) "
					+ "FROM interview i "
					+ "INNER JOIN interviews ii ON i.id = ii.interview_id and ii.category_id = ?1")
	Page<Interview> findPageByCategory(Long id, Pageable page);

	@Query(value = "SELECT new java.lang.Boolean(count(id) > 0) "
	             + "FROM Interview i "
	             + "WHERE i.author.id = :id")
	boolean existsByUserId(@Param("id") Long id);
	
	@Query(nativeQuery = true, 
			value = "SELECT count(a.id) "
					+ "FROM answer a "
					+ "INNER JOIN answers aa "
					+ "ON a.id = aa.answer_id and aa.question_id = ?1 and a.correct = true;")
	int countOfCorrectAnswers(Long questionId);
	
//	@Query(nativeQuery = true, 
//			value = "SELECT count(interview_id) "
//					+ "FROM user_answers "
//					+ "WHERE interview_id = ?1")
	@Query(nativeQuery = true, value = "SELECT count(user_id) FROM user_answers WHERE interview_id = ?1 group by user_id")
	Short countVotes(Long interviewId);

	@Query(nativeQuery = true, 
			value = "SELECT * "
					+ "FROM user_answer "
					+ "INNER JOIN answers aa "
					+ "ON a.id = aa.answer_id and aa.question_id = ?1 and a.correct = true;")
	List<UserAnswer> findUserAnswerByUserIdAndInterviewId(long interviewId, long userId);

	@Query(nativeQuery=true, value="SELECT user_id FROM interview WHERE id = ?1")
	Long userIdOfInterview(Long interviewId);

	@Query(value = "SELECT new java.lang.Boolean(count(id) = 1) "
            + "FROM Interview i "
            + "WHERE i.author.id = ?1 and i.id = ?2")
	boolean isAuthor(Long userId, Long interviewId);
}