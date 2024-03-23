package com.javachinna.dto;

import java.util.Date;
import java.util.List;

import lombok.Value;

@Value
public class UserInfo {
	private String id,num,address,sex,displayName, email;
	private Long cin;
	private byte[] image;
	private Date birth_date;
	private List<String> roles;
}