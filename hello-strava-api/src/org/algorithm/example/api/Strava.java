package org.algorithm.example.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.algorithm.example.json.Athlete;
import org.algorithm.example.json.Activity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Strava {
	private static final Logger LOG = Logger.getLogger(Strava.class);
	
	public static Athlete athleteInfo(Integer page, Integer perPage) throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet req = new HttpGet("https://www.strava.com/api/v3/athlete");
		req.addHeader("accept", "application/json");
		req.addHeader("Authorization","Bearer " + AccessToken.getInstance().getToken());
		
		HttpResponse resp = client.execute(req);
		int responseCode = resp.getStatusLine().getStatusCode();
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		LOG.info(result.toString());
		
		try {
			Gson gson = new Gson();
			if(responseCode == 200) {
				return gson.fromJson(result.toString(), Athlete.class);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<Activity> activities(Integer page, Integer perPage) throws ClientProtocolException, IOException {
		if(page == null) {
			page = 1;
		}
		
		if(perPage == null) {
			perPage = 30;
		}
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet req = new HttpGet(String.format("https://www.strava.com/api/v3/athlete/activities?page=%s&per_page=%s", page, perPage));
		req.addHeader("accept", "application/json");
		req.addHeader("Authorization","Bearer " + AccessToken.getInstance().getToken());
		
		HttpResponse resp = client.execute(req);
		int responseCode = resp.getStatusLine().getStatusCode();
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		LOG.info(result.toString());
		
		try {
			Gson gson = new Gson();
			if(responseCode == 200) {
				Type listType = new TypeToken<ArrayList<Activity>>(){}.getType();
				return gson.fromJson(result.toString(), listType);
			} else {
				LOG.warn("Cannot get activities");
			}
		} catch(Exception e) {
			LOG.error("Response error", e);
		}
		
		return null;
	}
	
	public static List<Activity> activities() throws ClientProtocolException, IOException {
		List<Activity> result = new ArrayList<Activity>();
		List<Activity> temp = null;
		int page = 1;
		do {
			temp = activities(page, 30);
			if(temp == null || temp.isEmpty()) {
				break;
			}
			result.addAll(temp);
			page += 1;
		} while (page < 10);
		return result;
	}
}
