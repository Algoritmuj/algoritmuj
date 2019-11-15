package org.algorithm.example;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Chart;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Period;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;

public class Main {

	private static Properties prop;
	private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("d.M.yyyy HH:mm");
	private static final DateFormat DATE_FORMAT = SimpleDateFormat.getDateInstance();
	
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
		Caller.getInstance().setUserAgent("test");
		
		String key = prop.getProperty("api_key");
		String user = prop.getProperty("user");
		
		// read weekly artist chart
		Chart<Artist> chart = User.getWeeklyArtistChart(user, 10, key);
		
		String from = DATE_FORMAT.format(chart.getFrom());
		String to = DATE_FORMAT.format(chart.getTo());
		System.out.printf("Charts for %s for the week from %s to %s:%n", user, from, to);
		Collection<Artist> artists = chart.getEntries();
		for (Artist artist : artists) {
		    System.out.println(artist.getName());
		}
		
		// read 10 recent tracks
		PaginatedResult<Track> pages = User.getRecentTracks(user, 1, 10, key);
		Iterator<Track> iter = pages.iterator();
		boolean isFirst = true;
		while(iter.hasNext()) {
			Track track = iter.next();
			System.out.println(
					(isFirst?"Scrobbling now":("Scrobbled " + DATETIME_FORMAT.format(track.getPlayedWhen()))) + 
					": " + track.getName() + " (" + track.getArtist() + ")");
			isFirst = false;
		}
		
		// user top tracks
		Collection<Track> tracks = User.getTopTracks(user, Period.ONE_MONTH, key);
		for(Track track : tracks) {
			System.out.println(track.getName() + " (" + track.getArtist() + ")");
		}
		
		// album info
		Album album = Album.getInfo("U2", "Achtung Baby", key);
		System.out.println(
				album.getName() + " (" + 
				album.getArtist() + "), listeners: " + 
				album.getListeners() + ", percentage change: " + 
				album.getPercentageChange() + ", play count: " + 
				album.getPlaycount()
		);
		System.out.println();
		
		// top artists from chart
		for(Artist artist : Chart.getTopArtists(key)) {
			System.out.println(artist.getName());
		}
		System.out.println();
		
		// top tracks from chart
		for(Track track : Chart.getTopTracks(key)) {
			System.out.println(track.getName() + " (" + track.getArtist() + ")");
		}
		System.out.println();
		
		for(int i=1; i<2; i++) {
			for(Track track : User.getLovedTracks(user, i, key)) {
				System.out.println(track.getName() + " (" + track.getArtist() + ")");
			}
		}
	}
}
