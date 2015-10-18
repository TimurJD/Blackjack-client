package com.blackjack.client;

import java.io.IOException;

/**
 * @author Timur Berezhnoi
 */
public interface Client<T1, T2> {
	void connect() throws IOException;
	void sendDataToSever(T2 t) throws IOException;
	T1 getDataFromServer() throws ClassNotFoundException, IOException;
	void disconnect() throws IOException;
}