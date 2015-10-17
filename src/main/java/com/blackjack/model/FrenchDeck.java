package com.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Timur Berezhnoi
 */
public class FrenchDeck implements Deck {

	private List<Card> cards;

	public FrenchDeck() {
		this.cards = new ArrayList<Card>(52);
		init();
		shuffle();
	}

	@Override
	public void init() {
		int index = 0;
		for (int i = 0; i < Suit.NUMBERS_OF_SUITS; i++) {
			for (int b = 0; b < Rank.NUMBERS_OF_RANKS; b++) {
				cards.add(index, new Card(Rank.values()[b], Suit.values()[i]));
				index++;
			}
		}
	}

	@Override
	public void shuffle() {
		cards.clear();
		init();
		Collections.shuffle(cards);
	}

	@Override
	public Card getNextCard() {
		Card result = cards.remove(cards.size() - 1);
		return result;
	}

	@Override
	public int getCardsLeft() {
		return cards.size();
	}
	
	@Override
	public List<Card> getAll() {
		return cards;
	}
}