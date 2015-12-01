package com.blackjack.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.blackjack.model.Card;
import com.blackjack.model.GameStatus;

/**
 * @author Timur Berezhnoi
 */
public class ClientEngine {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		BlackjackClient client = new BlackjackClient(); 
		client.connect();
		
		Scanner scanner = new Scanner(System.in);
		
		try {						
			System.out.println("********** Welcome to the BlackJak tournament **********");
			while(true) {
				System.out.println("Your choise: [1 - DEAL]");
				
				if(scanner.nextInt() == 1) {					
					client.sendDataToSever(GameStatus.DEAL);
				}
				
				Map<String, Object> data = client.getDataFromServer();
				
				ArrayList<Card> dealerHand = (ArrayList<Card>) data.get("dealerHand");
				ArrayList<Card> playerHand = (ArrayList<Card>) data.get("playerHand");
				
				if(data.get("status") != GameStatus.PLAYER_WON) {
					System.out.println("Your hand: " + playerHand);
					
					System.out.println("Your choise: [1 - HIT | 2 - STAND]");
					int option = scanner.nextInt();
					if(option == 1) {
						client.sendDataToSever(GameStatus.HIT);
					} else if(option == 2) {
						client.sendDataToSever(GameStatus.STAND);
					}
					
					data = client.getDataFromServer();
					playerHand = (ArrayList<Card>) data.get("playerHand");
					System.out.println("Your hand: " + playerHand);
					
					if(data.get("status") == GameStatus.BUST) {
						System.out.println("You BUST");
						continue;
					} else if(data.get("status") == GameStatus.PLAYER_WON) {
						System.out.println("You WON");
						continue;
					} else {
						System.out.println("CONTINUE");
					}
				} else {
					System.out.println(playerHand);
					System.out.println("YOU WON");
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}