package org.algorithm.example.json;

public class Token {
	private String token_type;
	private Long expires_at;
	private Integer expires_in;
	private String refresh_token;
	private String access_token;
	private Athlete athlete;
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public Long getExpires_at() {
		return expires_at;
	}
	public void setExpires_at(Long expires_at) {
		this.expires_at = expires_at;
	}
	public Integer getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Athlete getAthlete() {
		return athlete;
	}
	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}
}
