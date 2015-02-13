package it.siletto.ms.auth.service.impl;

import java.util.Set;

import it.siletto.ms.auth.User;
import it.siletto.ms.auth.service.AuthDAO;

import com.google.common.collect.Sets;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
public class AuthDAOStaticImpl implements AuthDAO {

	public User getUser(String username, String password){
		if("pippo".equals(username) && "pluto".equals(password)){
			User ret = new User();
			ret.setUserName(username);
			Set<String> authorities = Sets.newHashSet();
			authorities.add("user");
			ret.setAuthorities(authorities);
			return ret;
		}
		return null;
	}
}
