package com.example.lib;
import java.util.*;

/**
 * Created by NDS on 26/10/2017.
 */

public class Deck{
    private ArrayList<Card> cards = new ArrayList<Card>();

    /* When initialised without argument, a deck has 52 cards from 2 of clubs to ace of spades
    * unshuffled, arranged by suits. */
    Deck(){
        ArrayList<Card> unshuffledDeck = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            for (int j = 2; j < 15; j++) {
                Card temp = new Card(j, i);
                unshuffledDeck.add(temp);
            }
        }
        setCards(unshuffledDeck);
    }

    /* We can also initialise a deck with specified cards (for playing with modified deck */
    Deck(ArrayList _cards){
        setCards(_cards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /*Shuffle deck*/
    void shuffle() {
        Collections.shuffle(this.cards);
        System.out.println("Deck shuffled.");
    }

    /*Deals one card to specified hand*/
    void dealTo(Hand hand){
        boolean add = hand.cards.add(this.cards.remove(0));
    }

    /* Returns a string with the cards in the deck. */
    String show(){
        String temp = "";
        for(int i = 0; i<this.cards.size(); i++ ){
            temp = temp + this.cards.get(i).strCard() + " ";
        }
        return temp;
    }

    /* Returns the number of cards in deck. */
    int size(){
        return this.cards.size();
    }

}
