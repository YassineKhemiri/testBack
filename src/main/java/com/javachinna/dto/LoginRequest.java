package com.javachinna.dto;



import lombok.AllArgsConstructor;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
public class LoginRequest {

	private String num;
	private String password;

	@NotEmpty
	private String recaptchaResponse;
}