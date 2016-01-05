package com.blackjack.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.blackjack.model.GameStatus;
import com.blackjack.model.Hand;

/**
 * @author Timur Berezhnoi
 */
public class ClientEngine {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		// Stage - 1
		BlackjackClient client = new BlackjackClient();
		client.connect();

		Scanner scanner = new Scanner(System.in);

		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			System.out.println("********** Welcome to the BlackJak tournament **********\n");
			while (true) {
				
				// Stage - 2
				System.out.println("\n> Your choise: [1 - DEAL]");

				if (scanner.nextInt() == 1) {
					client.sendDataToSever(GameStatus.DEAL);
				}

				data = client.getDataFromServer();

				Hand dealerHand = (Hand) data.get("dealerHand");
				Hand playerHand = (Hand) data.get("playerHand");
				
				if (data.get("status") == GameStatus.PLAYER_WON) { // If player hand at start is equals to 21
					System.out.println("///// DEALER HAND ///// - " + dealerHand + " -> " + dealerHand.getHandScore());
					System.out.println("///// YOUR HAND ///// - " + playerHand + " -> " + playerHand.getHandScore());
					System.out.println("***** YOU WON *****");
					continue;
				} else {
					// Stage - 3
					System.out.println("///// DEALER HAND ///// - " + dealerHand + " -> " + dealerHand.getHandScore());
					System.out.println("///// YOUR HAND ///// - " + playerHand + " -> " + playerHand.getHandScore());
					
					while(true) {
						System.out.println("\n> Your choise: [1 - HIT | 2 - STAND]");
						int option = scanner.nextInt();
						if (option == 1) {
							client.sendDataToSever(GameStatus.HIT);
							
							data = client.getDataFromServer();
							
							dealerHand = (Hand) data.get("dealerHand");
							playerHand = (Hand) data.get("playerHand");
							
							System.out.println("///// DEALER HAND ///// - " + dealerHand + " -> " + dealerHand.getHandScore());
							System.out.println("///// YOUR HAND ///// - " + playerHand + " -> " + playerHand.getHandScore());
	
							if (data.get("status") == GameStatus.PLAYER_WON) { // If player hand at start is equals to 21
								System.out.println("***** YOU WON *****");
								break;
							} else if(data.get("status") == GameStatus.BUST) {
								System.out.println("***** YOU BUSTED *****");
								break;
							} else {
								continue;
							}
						} else if (option == 2) {
							client.sendDataToSever(GameStatus.STAND);
							dealerHand = (Hand) client.getDataFromServer().get("dealerHand");
							
							System.out.println("///// DEALER HAND ///// - " + dealerHand + " -> " + dealerHand.getHandScore());
							System.out.println("///// YOUR HAND ///// - " + playerHand + " -> " + playerHand.getHandScore());
							
							if(dealerHand.getHandScore() > playerHand.getHandScore()) {
								System.out.println("You BUST");
								break;
							} else if(dealerHand.getHandScore() < playerHand.getHandScore()) {
								System.out.println("***** YOU WON *****");
								break;
							} else if(dealerHand.getHandScore() == playerHand.getHandScore()) {
								System.out.println("DRAW");
								break;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}