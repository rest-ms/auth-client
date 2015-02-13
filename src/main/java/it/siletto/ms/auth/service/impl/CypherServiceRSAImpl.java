package it.siletto.ms.auth.service.impl;

import it.siletto.ms.auth.dto.EncryptedTokenDTO;
import it.siletto.ms.auth.service.CypherService;
import it.siletto.ms.auth.utils.RSAUtils;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
public class CypherServiceRSAImpl implements CypherService {

	private Map<String, Key> keyCache = new HashMap<String, Key>();

	@Inject
	private ObjectMapper mapper;
	
	protected PrivateKey getPrivateKeyFromCache(String filename) throws Exception {
		Key fromCache = keyCache.get(filename);
		if (fromCache != null)
			return (PrivateKey) fromCache;

		PrivateKey fromDisk = RSAUtils.getPrivateKey(filename);
		keyCache.put(filename, fromDisk);
		return fromDisk;

	}

	protected PublicKey getPublicKeyFromCache(String filename) throws Exception {
		Key fromCache = keyCache.get(filename);
		if (fromCache != null)
			return (PublicKey) fromCache;

		PublicKey fromDisk = RSAUtils.getPublicKey(filename);
		keyCache.put(filename, fromDisk);
		return fromDisk;

	}

	public String toJSON(EncryptedTokenDTO token) throws Exception{
		String data = mapper.writeValueAsString(token);
		if(data.length()>245)
			throw new RuntimeException("data too long");//TODO handle
		return data;
	}
	
	@Override
	public String generateToken(String publicKeyFile, String username, Set<String> roles, long expires) throws Exception {
		EncryptedTokenDTO token = new EncryptedTokenDTO();
		token.setUsername(username);
		token.setRoles(roles);
		token.setExpires(expires);

		return RSAUtils.encryptString(toJSON(token), getPublicKeyFromCache(publicKeyFile));
	}

	@Override
	public String decrypt(String privateKeyFile, String token)throws Exception {
		return RSAUtils.decryptString(token, getPrivateKeyFromCache(privateKeyFile));
	}

}
