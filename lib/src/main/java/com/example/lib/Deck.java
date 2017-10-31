package com.example.lib;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A deck of cards, from which cards are dealt to the players and the community hand.
 */

public class Deck{
    private ArrayList<Card> cards = new ArrayList<Card>();

    /**
     * Create a deck with 52 cards from 2 of clubs to ace of spades
     * unshuffled, arranged by suits.
     */
    Deck() {
        ArrayList<Card> unshuffledDeck = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            for (int j = 2; j < 15; j++) {
                Card temp = new Card(j, i);
                unshuffledDeck.add(temp);
            }
        }
        setCards(unshuffledDeck);
    }

    /**
     * Create a deck with specified cards (for playing with modified deck).
     * @param cards cards which will be in the deck
     */
    Deck(ArrayList cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /** Shuffle deck. */
    public void shuffle() {
        Collections.shuffle(cards);
        System.out.println("Deck shuffled.");
    }

    /**
     * Deals the first card in the deck to specified hand.
     * Dealt card is removed from the deck.
     * @param hand the hand to which the card is dealt
     */
    public void dealTo(Hand hand) {
        hand.add(cards.remove(0));
    }

    /**
     * Returns a string with the cards in the deck.
     * @return string displaying the cards in the deck
     */
    public String show() {
        String temp = "";
        for (int i = 0; i<cards.size(); i++ ) {
            temp = temp + cards.get(i).strCard() + " ";
        }
        return temp;
    }

    /**
     *  Returns the number of cards in the deck.
     *  @return number of cards in the deck
     */
    public int size() {
        return cards.size();
    }

}
