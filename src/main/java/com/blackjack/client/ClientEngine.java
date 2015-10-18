package com.blackjack.client;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.blackjack.model.Card;
import com.blackjack.model.Player;

/**
 * @author Timur Berezhnoi
 */
public class ClientEngine {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		BlackjackClient client = new BlackjackClient();
		Scanner scanner = new Scanner(System.in); 
		client.connect();
		
		
		Player player = new Player("Timur", client);
		System.out.println("\n\n*** Welcom to the Blackjack client ***");
		System.out.print("> " + player.getName() + ", enter your bet: ");
		scanner.nextInt();
		
		player.doBet();
		
		Map<String, Object> data = client.getDataFromServer();
		
		
		player.initHand((List<Card>) data.get("playerHand"));
		
		System.out.println("> DEALER HAND " + ((List<Card>) data.get("dealerHand")).get(0) + " *");
		System.out.println("> DEALER HAND " + player.getHand());
		
//		System.out.println("*******************************************\n"
//						 + "*         / Welcome to Balckjack /        *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*     " + player.getHand() + "      *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*                                         *\n"
//						 + "*******************************************");
//		
	}
}