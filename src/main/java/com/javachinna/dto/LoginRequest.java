package com.javachinna.dto;



import lombok.AllArgsConstructor;

import lombok.Data;


@Data
@AllArgsConstructor
public class LoginRequest {

	private String num;
	private String password;
}