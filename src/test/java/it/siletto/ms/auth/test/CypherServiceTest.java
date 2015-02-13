package it.siletto.ms.auth.test;

import it.siletto.ms.auth.dto.EncryptedTokenDTO;
import it.siletto.ms.auth.service.impl.CypherServiceRSAImpl;

import java.security.SecureRandom;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import static org.fest.assertions.api.Assertions.assertThat;

public class CypherServiceTest {

	@Test
	public void testCypher() throws Exception {

		String username="E2500022";
		long expire = new SecureRandom().nextLong();
		String privateKeyFile = "C:/Users/Alessandro/workspace/auth-service/src/test/java/private.key";
		String publicKeyFile="C:/Users/Alessandro/workspace/auth-service/src/test/java/public.key";
		
		CypherServiceRSAImpl cryptService = new CypherServiceRSAImpl();
		Set<String> roles = Sets.newHashSet();
		roles.add("user");
		roles.add("client");
		String token = cryptService.generateToken(publicKeyFile, username, roles, expire);
		System.out.println("token: "+token);
	
		String decrypted = cryptService.decrypt(privateKeyFile, token);
				
		System.out.println("decripted: "+decrypted);
		
		ObjectMapper mapper = new ObjectMapper();
		EncryptedTokenDTO dto = mapper.readValue(decrypted, EncryptedTokenDTO.class);
		
		assertThat(username).isEqualTo(dto.getUsername());
		
		assertThat(expire).isEqualTo(dto.getExpires());
		
		assertThat(roles).isEqualTo(dto.getRoles());
		
	}
}
