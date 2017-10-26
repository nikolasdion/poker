package com.example.lib;

import java.util.ArrayList;

/**
 * Created by NDS on 26/10/2017.
 */

public class Hand {
    ArrayList<Card> cards = new ArrayList<Card>();
    int rank = 0;

    Hand(){
        this.cards = cards;
        this.rank = rank;
    }

//    show cards on the hand
    String show(){
        String temp = "";
        for(int i = 0; i<this.cards.size(); i++ ){
            temp = temp + this.cards.get(i).strCard() + " ";
        }
        return temp;
    }

//    add card to the hand
    void add(Card card){
        this.cards.add(card);
    }


//    show size of hand
    int size(){
        return this.cards.size();
    }

//    check if hand is flush
    boolean isFlush(){

    }


}
