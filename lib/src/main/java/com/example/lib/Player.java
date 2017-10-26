package com.example.lib;

/**
 * Created by NDS on 25/10/2017.
 */

public class Player {
//   a player has two attributes: cards in her hand and the money she has
    public int money;
    public Hand hand;
    public String name;

    Player(int money, Hand hand, String name){
//        this.money = money;
//        this.hand = hand;
        setMoney(money);
        setHand(hand);
        setName(name);
    }

    void setMoney(int money){
        this.money = money;
    }

    void setHand( Hand hand){
        this.hand = hand;
    }

    void setName( String name){
        this.name = name;
    }

}
