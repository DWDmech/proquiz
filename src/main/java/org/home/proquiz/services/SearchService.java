package org.home.proquiz.services;

import java.util.List;

import org.home.proquiz.entities.Interview;

public interface SearchService {
	List<Interview> searchByUserName(String name);
	List<Interview> searchByTitle(String title);
}