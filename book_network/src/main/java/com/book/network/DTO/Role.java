package com.book.network.DTO;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Role {

	private String id;
	private String name;
	private String desc;

	private List<AttributeDTO> attributes;
}
