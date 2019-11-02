package org.algorithm.example.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.algorithm.example.api.AccessToken;
import org.algorithm.example.json.Token;
import org.algorithm.example.util.Constant;
import org.algorithm.example.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class StravaAuthorizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(StravaAuthorizationServlet.class);
       
    public StravaAuthorizationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		if(code != null && !code.isEmpty()) {
			generateToken(code);
		}
		
		String redirectTo = req.getParameter("redirect");
		if(redirectTo != null && !redirectTo.isEmpty()) {
			LOG.info("Redirect to " + redirectTo);
			try {
				resp.sendRedirect(redirectTo);
				return;
			} catch (IOException e) {
				LOG.error("Cannot redirect to " + redirectTo, e);
			}
		}
	}
	
	private void generateToken(String code) throws ClientProtocolException, IOException {
		CloseableHttpClient  client = HttpClientBuilder.create().build();
		HttpPost req = new HttpPost("https://www.strava.com/oauth/token");
		
		req.addHeader("User-Agent", Constant.USER_AGENT);
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("client_id", Util.property(Constant.PROP_CLIENT_ID)));
		urlParameters.add(new BasicNameValuePair("client_secret",Util.property(Constant.PROP_CLIENT_SECRET)));
		urlParameters.add(new BasicNameValuePair("code", code));
		urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
		req.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		HttpResponse resp = client.execute(req);
		BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		LOG.info(result.toString());
		
		try {
			Gson gson = new Gson();
			Token accessToken = gson.fromJson(result.toString(), Token.class);
			AccessToken.getInstance().initialize(accessToken);
		} catch(Exception e) {
			LOG.error("Error while retrieve access token", e);
		}
	}

}
