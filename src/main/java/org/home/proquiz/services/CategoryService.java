package org.home.proquiz.services;

import java.util.List;

import org.home.proquiz.entities.Category;

public interface CategoryService {
	List<Category> getCategories();
	boolean exits(long id);
}
