package com.login.module.controller.dto.requestdto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterRequestDTO {

	private String name;
	private String surname;
	private String email;
	private String password;
	private String rePassword;
	private String postalAddress;
}
