package com.parking.buddy.entity;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private  String username;
	private  Long id;

private Role role;

	public JwtResponse(String jwttoken, String username, Long id, Role role) {
		this.jwttoken = jwttoken;
		this.username = username;
		this.id = id;
		this.role = role;

	}

	public String getToken() {
		return this.jwttoken;
	}

	public String getUsername() {
		return this.username;
	}

	public Long getId() {
		return this.id;
	}


	public Role getRole() {
		return this.role;
	}
}