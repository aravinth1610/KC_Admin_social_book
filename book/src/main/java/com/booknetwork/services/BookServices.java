package com.booknetwork.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookServices {

	
	public void bookData(Map<String,String> bookData) {
		log.info("bookData Args Data {}", bookData);
		System.out.println(bookData);
	}
}
