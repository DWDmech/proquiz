package org.home.proquiz.services;

import java.util.List;

import org.home.proquiz.entities.Category;
import org.home.proquiz.model.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UniversityCategoryService implements CategoryService {
	@Autowired
	private CategoryRepository cRep;
	
	@Override
	@Transactional(readOnly=true)
	public List<Category> getCategories() {
		return (List<Category>) cRep.findAll();
	}

	@Override
	public boolean exits(long id) {
		return cRep.existsById(id);
	}
}
