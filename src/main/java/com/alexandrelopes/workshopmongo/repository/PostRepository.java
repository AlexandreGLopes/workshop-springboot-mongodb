package com.alexandrelopes.workshopmongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alexandrelopes.workshopmongo.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{
	
	//Doesn't need methods to work
	
	//The only methods assigned here will be those to work with the "query methods" format
	//The reference on how to work with "query methods" is in the next links:
	//https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
	//https://docs.spring.io/spring-data/data-document/docs/current/reference/html/
	//The methods used in tgis project will be listed below:
	List<Post> findByTitleContainingIgnoreCase(String text);
	
	
}
