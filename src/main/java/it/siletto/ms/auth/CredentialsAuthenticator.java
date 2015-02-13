package it.siletto.ms.auth;


import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import it.siletto.ms.auth.dto.EncryptedTokenDTO;
import it.siletto.ms.auth.service.impl.CypherServiceRSAImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

/**
 * <p>Authenticator to provide the following to application:</p>
 * <ul>
 * <li>Verifies the provided credentials are valid</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class CredentialsAuthenticator implements Authenticator<Credentials, User> {

	private String privateKeyFile;

	private ObjectMapper mapper = new ObjectMapper();
	
	public CredentialsAuthenticator(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}
	
  @Override
  public Optional<User> authenticate(Credentials credentials) throws AuthenticationException {

	User userData = null;
	
	String token = credentials.getSessionToken();	
	try{
		String json = new CypherServiceRSAImpl().decrypt(privateKeyFile, token);
		if(json!=null){
			EncryptedTokenDTO dto = mapper.readValue(json, EncryptedTokenDTO.class);
			userData = new User();
			userData.setUserName(dto.getUsername());
			userData.setAuthorities(dto.getRoles());
		}
		Optional<User> user = Optional.fromNullable(userData);
	    return user;
	}catch (Exception e) {
		e.printStackTrace();
		throw new AuthenticationException(e);
	}

  }

}
