package com.blackjack.client.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.blackjack.client.util.ClientProperties;


/**
 * @author Timur Berezhnoi
 *
 */
public class ClientPropertiesTest {
	
	@Test
	public void shouldCorrectPort() {
		String expected = "7765";
		assertEquals(expected, ClientProperties.PORT.getValue());
	}
	
	@Test
	public void shouldCorrectIP() {
		String expected = "127.0.0.1";
		assertEquals(expected, ClientProperties.IP.getValue());
	}
}