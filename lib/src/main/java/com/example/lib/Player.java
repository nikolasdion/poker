package com.example.lib;

/**
 * Created by NDS on 25/10/2017.
 */

public class Player {
    /* a player has two attributes: cards in her hand and the money she has */
    private int money;
    private Hand hand = new Hand();
    private String name;
    private int choice;


    Player(int money, String name){
        setMoney(money);
        setName(name);
        setChoice(0);
    }

    void setMoney(int money){
        this.money = money;
    }

    void setHand(Hand hand){
        this.hand = hand;
    }

    void setName(String name){
        this.name = name;
    }

    void setChoice(int choice){
        this.choice = choice;
    }

    int getMoney(){return money;}

    Hand getHand(){return hand;}

    String getName(){return name;}

    int getChoice(){return choice;}


}
