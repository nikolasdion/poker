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
    private int bet;
    private boolean hasFolded;


    Player(int money, String name){
        setMoney(money);
        setName(name);
        setChoice(0);
        setBet(0);
        setFolded(false);
    }

    public void setMoney(int money){
        this.money = money;
    }

    public void setHand(Hand hand){
        this.hand = hand;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setChoice(int choice){
        this.choice = choice;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setFolded(boolean hasFolded) {
        this.hasFolded = hasFolded;
    }

    public int getMoney(){return money;}

    public Hand getHand(){return hand;}

    public String getName(){return name;}

    public int getChoice(){return choice;}

    public int getBet() {
        return bet;
    }

    public boolean hasFolded() {
        return hasFolded;
    }




}
