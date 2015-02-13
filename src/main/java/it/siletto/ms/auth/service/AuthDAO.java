package it.siletto.ms.auth.service;

import it.siletto.ms.auth.User;

public interface AuthDAO {

	public User getUser(String username, String password);
	
}
