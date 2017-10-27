package com.example.lib;
import java.util.*;

/**
 * Created by NDS on 26/10/2017.
 */

public class Deck{
    static ArrayList<Card> cards = new ArrayList<Card>();

    /* When initialised, a deck has 52 cards from 2 of clubs to ace of spades (unshuffled, arranged
     * by suits. */
    static{
        for (int i = 1; i < 5; i++){
            for (int j = 2; j < 15; j++) {
                Card temp = new Card(j, i);
                cards.add(temp);
            }
        }   
    }

    Deck(){
        this.cards = cards;
    }

    void shuffle() {
        Collections.shuffle(this.cards);
        System.out.println("Deck shuffled.");
    }
        
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
