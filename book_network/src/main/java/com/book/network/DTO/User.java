package com.book.network.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class User {

	private String id;

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String userName;
	
	private String password;
}
