package org.home.proquiz.model;

import org.home.proquiz.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	@Query(nativeQuery=true, value="SELECT * FROM category")
	Iterable<Category> findAll();
}