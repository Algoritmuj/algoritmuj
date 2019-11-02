package org.algorithm.example.api;

import java.util.Date;

import org.algorithm.example.json.Token;
import org.apache.log4j.Logger;

public class AccessToken {
	private static final Logger LOG = Logger.getLogger(AccessToken.class);
	
	private static AccessToken INSTANCE = null;
	private String token;
	private String refreshToken;
	private long expiresAt;
	
	private AccessToken() {
		
	}
	
	public static AccessToken getInstance() {
		if(INSTANCE == null) {
			synchronized(AccessToken.class) {
				if(INSTANCE == null) {
					INSTANCE = new AccessToken();
				}
			}
		}
		return INSTANCE;
	}
	
	public void initialize(Token model) {
		this.token = model.getAccess_token();
		this.refreshToken = model.getRefresh_token();
		this.expiresAt = model.getExpires_at();
		LOG.info("Initialize access_token=" + token + ", refresh_token=" + refreshToken + ", expires_at=" + expiresAt);
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public boolean isSet() {
		return (token != null && token.isEmpty() == false);
	}
	
	public boolean isExpired() {
		LOG.info("expires_at=" + expiresAt + " < now=" + (new Date().getTime() / 1000));
		return expiresAt < (new Date().getTime() / 1000);
	}
	

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}