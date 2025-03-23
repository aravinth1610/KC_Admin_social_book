package com.booknetwork.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.customeResponse.ResponseEntityWrapper;

@RestController
@RequestMapping("/details")
public class BookController {


	@GetMapping
	public final ResponseEntityWrapper<?> getData() { 
		return new ResponseEntityWrapper<>(Arrays.asList(Map.of("Name", "Ram", "Age", 12, "Books", Arrays.asList("Jemmy", "Makers"))));
	}
}
