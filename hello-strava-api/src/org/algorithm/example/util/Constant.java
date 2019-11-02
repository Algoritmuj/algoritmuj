package org.algorithm.example.util;

public final class Constant {
	public static final String USER_AGENT = "Mozilla/5.0";
	public static final String PROP_CLIENT_ID = "strava.client_id";
	public static final String PROP_CLIENT_SECRET = "strava.client_secret";
	public static final String PROP_REFRESH_TOKEN = "strava.refresh_token";
	public static final String PROP_CALLBACK = "strava.callback";
	
	public static final String GRAND_TYPE_REFRESH = "refresh_token";
	
	public static final String AUTHORIZATION_URL = "https://www.strava.com/oauth/authorize?client_id=%s&response_type=code&redirect_uri=%s&approval_prompt=auto&scope=activity:read";
}
