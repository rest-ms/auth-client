package it.siletto.ms.auth;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;

public class User {

    private String userName;
    @JsonProperty
    private Set<String> authorities = Sets.newHashSet();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    public boolean hasAllAuthorities(Set<String> requiredAuthorities) {
        return authorities.containsAll(requiredAuthorities);
    }

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
}
