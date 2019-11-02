package org.algorithm.example.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class Util {
	private static Properties PROPERTY = null;
	private static final String PROPERTY_PATH = "config.properties";
	private static final Logger LOG = Logger.getLogger(Util.class);

	static {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(PROPERTY_PATH);
			PROPERTY = new Properties();
			PROPERTY.load(input);
		} catch (Exception e) {
			LOG.error("Error while load property file config.property", e);
		}
	}

	public static String property(String name) {
		return PROPERTY.getProperty(name);
	}

	public static String encode(String url) {
		try {
			String encodeURL = URLEncoder.encode(url, "UTF-8");
			return encodeURL;
		} catch (UnsupportedEncodingException e) {
			return "Issue while encoding" + e.getMessage();
		}
	}

	public static String decode(String url) {
		try {
			String prevURL = "";
			String decodeURL = url;
			while (!prevURL.equals(decodeURL)) {
				prevURL = decodeURL;
				decodeURL = URLDecoder.decode(decodeURL, "UTF-8");
			}
			return decodeURL;
		} catch (UnsupportedEncodingException e) {
			return "Issue while decoding" + e.getMessage();
		}
	}
}
