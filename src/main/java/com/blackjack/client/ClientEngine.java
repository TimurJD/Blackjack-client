package com.blackjack.client;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import com.blackjack.model.GameStatus;
import com.blackjack.model.Hand;

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
			while (true) {
				
				System.out.println("Your choise: [1 - DEAL]");

				if (scanner.nextInt() == 1) {
					client.sendDataToSever(GameStatus.DEAL);
				}

				Map<String, Object> data = client.getDataFromServer();

				Hand dealerHand = (Hand) data.get("dealerHand");
				Hand playerHand = (Hand) data.get("playerHand");
				
				if (data.get("status") != GameStatus.PLAYER_WON) {
					System.out.println("Dealer hand is:" + dealerHand + " -> " + dealerHand.getHandScore());
					System.out.println("Your hand is: " + playerHand + " -> " + playerHand.getHandScore());

					System.out.println("Your choise: [1 - HIT | 2 - STAND]");
					int option = scanner.nextInt();
					if (option == 1) {
						client.sendDataToSever(GameStatus.HIT);
					} else if (option == 2) {
						client.sendDataToSever(GameStatus.STAND);
						System.out.println("Dealer hand is: " + dealerHand + " -> " + dealerHand.getHandScore());
						
						if(dealerHand.getHandScore() > playerHand.getHandScore()) {
							System.out.println("Your hand is: " + playerHand + " -> " + playerHand.getHandScore());
							System.out.println("You BUST");
						} else if(dealerHand.getHandScore() < playerHand.getHandScore()) {
							System.out.println("Your hand is: " + playerHand + " -> " + playerHand.getHandScore());
							System.out.println("You WON");
						} else if(dealerHand.getHandScore() == playerHand.getHandScore()) {
							
						}
						continue;
					}

					while (true) {
						data = client.getDataFromServer();
						
						playerHand = (Hand) data.get("playerHand");
						System.out.println("Your hand is: " + playerHand + " -> " + playerHand.getHandScore());

						if (data.get("status") == GameStatus.BUST) {
							System.out.println("You BUST");
							break;
						} else if (data.get("status") == GameStatus.PLAYER_WON) {
							System.out.println("You WON");
							break;
						} else {
							System.out.println("Your choise: [1 - HIT | 2 - STAND]");
							if (option == 1) {
								client.sendDataToSever(GameStatus.HIT);
							} else if (option == 2) {
								client.sendDataToSever(GameStatus.STAND);
							}
						}
					}
				} else {
					System.out.println(playerHand);
					System.out.println("YOU WON");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}