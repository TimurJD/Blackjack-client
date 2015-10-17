package com.blackjack.model;

import java.util.List;

/**
 * @author Timur Berezhnoi
 */
public interface Deck {
	void init();
	void shuffle();
	Card getNextCard();
	int getCardsLeft();
	List<Card> getAll();
}