package com.example.lib;

/**
 * Created by NDS on 25/10/2017.
 */

public class Player {
//   a player has two attributes: cards in her hand and the money she has
    private int money;
    private Card[] hand;

    Player(int money, Card[] hand){
//        this.money = money;
//        this.hand = hand;
        setMoney(money);
        setHand(hand);
    }

    void setMoney(int money){
        this.money = money;
    }

    void setHand(Card[] hand){
        this.hand = hand;
    }

    //prints out current hand of the player
    void displayHand(){
        System.out.println("Your current hand:");
        for(int i =0; i<this.hand.length; i++){
            System.out.println(this.hand[i].strCard());
        }
    }

}
