package org.algorithm.example.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.algorithm.example.api.AccessToken;
import org.algorithm.example.json.Token;
import org.algorithm.example.util.Constant;
import org.algorithm.example.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class StravaAuthorizationFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(StravaAuthorizationFilter.class);

    public StravaAuthorizationFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String url = req.getRequestURL().toString();
		String queryString = req.getQueryString();
		String originUrl = url;
		if(queryString != null && !queryString.isEmpty()) {
			originUrl = originUrl.concat(queryString);
		}
		 
		// first initialize access token and redirect to origin URL
		if(!AccessToken.getInstance().isSet()) {
			LOG.info("Generate first access token");
			try {
				String redirectUrl = String.format(Util.property(Constant.PROP_CALLBACK), Util.encode(originUrl));
				resp.sendRedirect(String.format(Constant.AUTHORIZATION_URL, Util.property(Constant.PROP_CLIENT_ID), redirectUrl));
				return;
			} catch (IOException e) {
				LOG.error("Cannot redirect for authenticate", e);
			}
		} 
		
		// refresh exired access token and redirect to origin URL
		else if(AccessToken.getInstance().isExpired()) {
			LOG.info("Refreshing access token");
			refreshToken();
		}
		
		chain.doFilter(request, response);
	}
	
	private void refreshToken() {
		try {
			CloseableHttpClient  client = HttpClientBuilder.create().build();
			HttpPost req = new HttpPost("https://www.strava.com/oauth/token");
			
			req.addHeader("User-Agent", Constant.USER_AGENT);
			
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("client_id", Util.property(Constant.PROP_CLIENT_ID)));
			urlParameters.add(new BasicNameValuePair("client_secret",Util.property(Constant.PROP_CLIENT_SECRET)));
			urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
			urlParameters.add(new BasicNameValuePair("refresh_token", Util.property(Constant.PROP_REFRESH_TOKEN)));
			req.setEntity(new UrlEncodedFormEntity(urlParameters));
			
			HttpResponse resp = client.execute(req);
			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = null;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			LOG.info(result.toString());
			
			try {
				Gson gson = new Gson();
				Token token = gson.fromJson(result.toString(), Token.class);
				AccessToken.getInstance().initialize(token);
			} catch(Exception e) {
				LOG.error("Error while read token response", e);
			}
		} catch(Exception e ) {
			LOG.error("Error while refresh access token", e);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
