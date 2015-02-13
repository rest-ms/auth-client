package it.siletto.ms.auth.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptedTokenDTO {

	@JsonProperty("u")
	private String username;
	
	@JsonProperty("r")
	private Set<String> roles = new HashSet<String>();
	
	@JsonProperty("e")
	private Long expires;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public Long getExpires() {
		return expires;
	}
	public void setExpires(Long expires) {
		this.expires = expires;
	}
	
	
}
