package com.blackjack.client;

import static com.blackjack.client.util.ClientProperties.IP;
import static com.blackjack.client.util.ClientProperties.NAME;
import static com.blackjack.client.util.ClientProperties.PORT;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.blackjack.client.exception.EmptyPropertyException;
import com.blackjack.model.GameStatus;

/**
 * Simple Blackjack socket client
 * @author Timur Berezhnoi
 */
public class BlackjackClient implements Client<Map<String, Object>, GameStatus> {

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
	@Override
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
	@Override
	public void sendDataToSever(GameStatus status) throws IOException {
		out.writeObject(status);
		out.flush();
		out.reset();
	}

	/**
	 * The method receive data from server
	 * 
	 * @return object
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Override
	public Map<String, Object> getDataFromServer() throws ClassNotFoundException, IOException {
		return (Map<String, Object>) in.readObject();
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
	@Override
	public void disconnect() throws IOException {
		socket.close();
	}
	
	@Override
	public String toString() {
		return NAME.getValue();
	}
}