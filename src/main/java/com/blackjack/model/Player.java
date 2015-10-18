package com.blackjack.model;

import java.io.IOException;
import java.util.List;

import com.blackjack.client.BlackjackClient;

/**
 * @author Timur Berezhnoi
 */
public class Player {
	
	private String name;
	private int check = 5000; // by default
	private List<Card> hand; 
	
	private BlackjackClient client;
	
	public Player(String name, BlackjackClient client) {
		this.name = name;
		this.client = client;
	}
	
	public void doBet() throws IOException, ClassNotFoundException {
		client.sendDataToSever(GameStatus.BET);
	}
	
	public void initHand(List<Card> hand) {
		this.hand =  hand;
	}
	
	/**
	 * @return the hand
	 */
	public List<Card> getHand() {
//		StringBuilder result = new StringBuilder();
//		for(Card card: hand) {
//			result
//				.append(card.getRank().getScore())
//				.append(" of ")
//				.append(card.getSuit())
//				.append(" ");
//		}
//		return result.toString();
		return hand;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}