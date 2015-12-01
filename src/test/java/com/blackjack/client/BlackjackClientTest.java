package com.blackjack.client;

import static com.blackjack.client.util.ClientProperties.PORT;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.blackjack.model.GameStatus;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author Timur Berezhnoi
 */
public class BlackjackClientTest {

	private BlackjackClient client;
	
	private Socket serverSocket;
	private ServerSocket server;
	private ObjectInputStream serverIn;
	private ObjectOutputStream serverOut;
	
	@Before
	public void setUp() {
		new Thread() {
			@Override
			public void run() {
				try {
					TimeUnit.MILLISECONDS.sleep(100);
					client = new BlackjackClient();
					client.connect();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		try {
			server = new ServerSocket((Integer.valueOf(PORT.getValue())));
			serverSocket = server.accept();
			serverIn = new ObjectInputStream(serverSocket.getInputStream());
			serverOut = new ObjectOutputStream(serverSocket.getOutputStream());
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldBeConnected() {
		assertTrue(client.isConnected());
	}
	
	@Test
	public void shouldSendMessageToServer() {
		GameStatus expected = GameStatus.DEAL;
		try {
			client.sendDataToSever(expected);
			assertEquals(expected, serverIn.readObject());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		try {
			client.disconnect();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}