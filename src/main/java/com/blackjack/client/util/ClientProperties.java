package com.blackjack.client.util;

import java.util.Properties;

/**
 * @author Timur Berezhnoi
 *
 */
public enum ClientProperties {
	
	PORT("server.port"),
	IP("server.IP"),
	NAME("server.name");
	
	private String key;

	private ClientProperties(String key) {
		this.key = key;
	}

	private static Properties properties;

	static {
		properties = new Properties();
		try {
			properties.load(ClientProperties.class.getClassLoader().getResourceAsStream("client.properties"));
		} catch (Exception e) {
			throw new RuntimeException("Error when loading configuration file", e);
		}
	}

	/**
	 * Call the <tt>Properties</tt> method {@code get}
	 * to get data by key provided in the current enum value. 
	 * @return value by key
	 */
	public String getValue() {
		return properties.getProperty(key);
	}
}