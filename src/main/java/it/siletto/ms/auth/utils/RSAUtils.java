package it.siletto.ms.auth.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

import com.sun.jersey.core.util.Base64;

public class RSAUtils {

	protected static String sign(PrivateKey privateKey, byte[] data) throws Exception {
		Signature signature = Signature.getInstance("SHA256WithRSA");
		signature.initSign(privateKey);

		signature.update(data);
		byte[] sign = signature.sign();
		return new String(Base64.encode(sign));
	}
	
	protected static Boolean verifySignature(PublicKey publicKey , String token, byte[] data) throws Exception {
		byte[] tokenByte = Base64.decode(token);

		Signature signature = Signature.getInstance("SHA256WithRSA");
		signature.initVerify(publicKey);
		signature.update(data);
		boolean check = signature.verify(tokenByte);

		return check;
	}
	
	public static String encryptString(String text, PublicKey publicKey) throws Exception {
		byte[] cipherText = null;
		final Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		cipherText = cipher.doFinal(text.getBytes());
		return new String(Base64.encode(cipherText));
	  }
	
	public static String decryptString(String text, PrivateKey privateKey) throws Exception {
		byte[] textByte = Base64.decode(text);
	    byte[] dectyptedText = null;
	    final Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.DECRYPT_MODE, privateKey);
	    dectyptedText = cipher.doFinal(textByte);
	    return new String(dectyptedText);
	  }
	private static byte[] toByteArray(File file) throws IOException {
		try (FileInputStream in = new FileInputStream(file);
				FileChannel channel = in.getChannel()) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			channel.transferTo(0, channel.size(), Channels.newChannel(out));
			return out.toByteArray();
		}
	}
	
	private static byte[] toDecodedBase64ByteArray(byte[] base64EncodedByteArray) {
		  return DatatypeConverter.parseBase64Binary(
		      new String(base64EncodedByteArray, Charset.forName("UTF-8")));
		}
	
	public static PublicKey getPublicKey(String filename) throws Exception {
		byte[] publicKeyBytes = toByteArray(new File(filename));
		publicKeyBytes = toDecodedBase64ByteArray(publicKeyBytes);
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
		Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(publicKeyBytes));
		PublicKey publicKey = certificate.getPublicKey();
		return publicKey;
	}

	public static PrivateKey getPrivateKey(String filename) throws Exception {

		byte[] privateKeyBytes = toByteArray(new File(filename));
		privateKeyBytes = toDecodedBase64ByteArray(privateKeyBytes);
		KeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		return privateKey;
	}
}