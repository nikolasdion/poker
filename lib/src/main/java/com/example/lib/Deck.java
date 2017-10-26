package com.example.lib;

import java.util.*;

/**
 * Created by NDS on 26/10/2017.
 */

public class Deck{
    static ArrayList<Card> cards = new ArrayList<Card>();

// automatically add 52 cards to deck when initialised, unshuffled
    static{
        for (int i = 1; i < 5; i++){
            for (int j = 1; j<14; j++) {
                Card temp = new Card(j, i);
                cards.add(temp);
            }
        }   
    }

    Deck(){
        this.cards = cards;
    }

//  shuffle deck

    void shuffle() {
        Collections.shuffle(this.cards);
        System.out.println("Deck shuffled.");
    }
        
    void dealTo(Hand hand){
        boolean add = hand.cards.add(this.cards.remove(0));
    }

    //    show cards on the deck
    String show(){
        String temp = "";
        for(int i = 0; i<this.cards.size(); i++ ){
            temp = temp + this.cards.get(i).strCard() + " ";
        }
        return temp;
    }

    //    show size of deck
    int size(){
        return this.cards.size();
    }

}
