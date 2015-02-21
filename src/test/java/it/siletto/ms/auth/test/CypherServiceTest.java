package it.siletto.ms.auth.test;

import static org.fest.assertions.api.Assertions.assertThat;
import it.siletto.ms.auth.dto.EncryptedTokenDTO;
import it.siletto.ms.auth.service.CypherService;
import it.siletto.ms.auth.service.impl.CypherServiceRSAImpl;

import java.security.SecureRandom;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class CypherServiceTest {

	@Test
	public void testCypher() throws Exception {

		String username="E2500022";
		long expire = new SecureRandom().nextLong();
		String privateKeyFile = "D:/dev/workspaces/personale/rest-ms/auth-service/src/test/java/private.key";
		String publicKeyFile="D:/dev/workspaces/personale/rest-ms/auth-service/src/test/java/public.key";
		
		Injector injector = Guice.createInjector();
		CypherService cypherService = injector.getInstance(CypherServiceRSAImpl.class);
		Set<String> roles = Sets.newHashSet();
		roles.add("user");
		roles.add("client");
		String token = cypherService.generateToken(publicKeyFile, username, roles, expire);
		System.out.println("token: "+token);
	
		String decrypted = cypherService.decrypt(privateKeyFile, token);
				
		System.out.println("decripted: "+decrypted);
		
		ObjectMapper mapper = new ObjectMapper();
		EncryptedTokenDTO dto = mapper.readValue(decrypted, EncryptedTokenDTO.class);
		
		assertThat(username).isEqualTo(dto.getUsername());
		
		assertThat(expire).isEqualTo(dto.getExpires());
		
		assertThat(roles).isEqualTo(dto.getRoles());
		
	}
}
