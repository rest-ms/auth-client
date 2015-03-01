package it.siletto.ms.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import it.siletto.ms.auth.dto.EncryptedTokenDTO;
import it.siletto.ms.auth.service.CypherService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.inject.Inject;

public class CredentialsAuthenticator implements Authenticator<Credentials, User> {

	private String privateKeyFile;

	public void setPrivateKeyFile(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}

	@Inject
	protected CypherService cypherService;

	@Inject
	private ObjectMapper mapper;

	@Override
	public Optional<User> authenticate(Credentials credentials) throws AuthenticationException {

		User userData = null;

		String token = credentials.getSessionToken();
		try {
			String json = cypherService.decrypt(privateKeyFile, token);
			if (json != null) {
				EncryptedTokenDTO dto = mapper.readValue(json, EncryptedTokenDTO.class);
				userData = new User();
				userData.setUserName(dto.getUsername());
				userData.setAuthorities(dto.getRoles());
			}
			Optional<User> user = Optional.fromNullable(userData);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException(e);
		}

	}

}
