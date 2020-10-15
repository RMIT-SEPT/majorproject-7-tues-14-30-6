package com.rmit.sept.tues06.appointmentservicebackend.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class JwtResponse {
	private String token;
	@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	@Schema(example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjdXN0b21lciIsImlhdCI6MTYwMjUwNzAzNywiZXhwIjoxNjAyNTkzNDM3fQ.xLgGRH1k4WV3aRJNn6KLUte0Deo2UrHiqI4tJlCvgaKf5g5ttB0eG-m8FX0Da4iThbysHhqh8zMjS8y9UgAomg")
	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	@Schema(example = "Bearer")
	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Schema(example = "customer@email.com")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Schema(example = "customer")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
