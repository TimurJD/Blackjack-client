package com.blackjack.client;

import static com.blackjack.client.util.ClientProperties.IP;
import static com.blackjack.client.util.ClientProperties.NAME;
import static com.blackjack.client.util.ClientProperties.PORT;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.blackjack.client.exception.EmptyPropertyException;
import com.blackjack.model.Card;

/**
 * Simple Blackjack socket client
 * @author Timur Berezhnoi
 */
public class BlackjackClient {

	private final Logger logger = Logger.getLogger(this.getClass());

	private Socket socket;

	private ObjectInputStream in;
	private ObjectOutputStream out;

	/**
	 * The method incapsulates all logic during connection. 
	 * 
	 * @throws IOException
	 * @throws EmptyPropertyException
	 */
	public void connect() throws IOException {
		setUpSocket();
		setUpStreams();
	}

	private void setUpSocket() throws UnknownHostException, IOException {
		socket = new Socket(IP.getValue(), Integer.valueOf(PORT.getValue()));
		logger.info("Client is connected");
	}
	
	/**
	 * The method set ups the streams.
	 * 
	 * @throws IOException
	 */
	private void setUpStreams() throws IOException {
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		logger.info("OUT_STREAM & INPUT_STREAM initialized!");
	}
	
	/**
	 * The method which send data to a server and flush (see 
	 * {@link java.io.ObjectOutputStream#flush()}) the stream
	 * 
	 * @param object the object to be sended to a server.
	 * @throws IOException - if I/O errors occur while writing to the underlying stream.
	 */
	public void sendDataToSever(Object object) throws IOException {
		out.writeObject(object);
		out.flush();
	}

	/**
	 * The method receive data from server
	 * 
	 * @return object
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Object getDataFromServer() throws ClassNotFoundException, IOException {
		return in.readObject();
	}

	/**
	 * Returns the connection state of the socket.
	 * <p>
	 * @return true if the socket was successfuly connected to a server
	 */
	public boolean isConnected() {
		return socket.isConnected();
	}
	
	/**
	 * Disconnect from a server.
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		socket.close();
	}
	
	@Override
	public String toString() {
		return NAME.getValue();
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		BlackjackClient client = new BlackjackClient();
		client.connect();
		
		Scanner scanner = new Scanner(System.in);
		
		// Here is sending bet to server to begin the game
		System.out.print("> Enter your bet: " );
		client.sendDataToSever(scanner.nextInt());
		
		// Now client (player) should get first two cards from server.
		List<Card> hand = (List<Card>) client.getDataFromServer();
		System.out.println(hand);
		System.out.println(client.getDataFromServer());
	}
}