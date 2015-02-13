package it.siletto.ms.auth.service;

import java.util.Set;

public interface CypherService {

	public abstract String decrypt(String privateKeyFile, String token) throws Exception;

	public abstract String generateToken(String publicKeyFile, String username, Set<String> roles, long expires) throws Exception;

}
