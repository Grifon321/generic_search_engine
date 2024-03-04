package com.project.Search.Engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.Search.Engine.processing.ArrayListKeywords;

@SpringBootApplication	
public class SearchEngineApplication{
	// Preprocesses all the files
	@SuppressWarnings("unused")
	private ArrayListKeywords ListKeywords = new ArrayListKeywords();

	public static void main(String[] args) {
		SpringApplication.run(SearchEngineApplication.class, args);
	}

}
