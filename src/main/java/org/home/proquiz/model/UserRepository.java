package org.home.proquiz.model;

import java.util.List;
import java.util.Optional;

import org.home.proquiz.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
	@Query("FROM User u WHERE u.email = :email")
	Optional<User> findByEmail(@Param("email") String email);
	
	@Query("FROM User u WHERE u.email = ':email' and u.password = ':password'")
	Optional<User> findByEmailAndPassword(@Param("email") String email, 
			                              @Param("password") String password);
	
	@Query("SELECT u.name FROM User u WHERE u.id = :id")
	String findUserNameById(@Param("id") Long id);

	@Query(nativeQuery = true, 
			value = "SELECT u.* "
					+ "FROM users u INNER JOIN user_answers ua "
					+ "ON ua.answer_id = ?1 and u.id = ua.user_id")
	Optional<List<User>> findByAnswer(Long answerId);	
}