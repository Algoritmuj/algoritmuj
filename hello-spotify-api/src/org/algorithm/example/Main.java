package org.algorithm.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

public class Main {

	private static Properties prop;
	
	static {
		try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
			prop = new Properties();
			if (input == null) {
			    System.out.println("Sorry, unable to find config.properties");
			}
			prop.load(input);
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String key = prop.getProperty("api_key");
		String secret = prop.getProperty("secret");
		
		// For all requests an access token is needed
		SpotifyApi spotifyApi = new SpotifyApi.Builder()
				.setClientId(key)
				.setClientSecret(secret)
		        .build();
		
		ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
		
		try {
			final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
		    spotifyApi.setAccessToken(clientCredentials.getAccessToken());
			Paging<AlbumSimplified> tracks = spotifyApi.getListOfNewReleases().build().execute();
			for(AlbumSimplified t : tracks.getItems()) {
				System.out.println(t.getName());
			}
		} catch (SpotifyWebApiException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
