package it.siletto.ms.auth.dto;

public class TokenResponseDTO {

	public static String STATUS_OK="OK";
	public static String STATUS_KO="KO";
	
	private String status;
	private String token;
	private Long expires;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getExpires() {
		return expires;
	}
	public void setExpires(Long expires) {
		this.expires = expires;
	}
	
	
}
