package com.example.lib;

/**
 * A player in the poker game. During her turn, a player has 3 choices, represented by an integer:
 * 1: raise, 2: check, 3: fold.
 */

public class Player {
    private int money;
    private Hand hand = new Hand();
    private String name;
    private int choice; // Player's action (1: raise, 2: check, 3: fold)
    private int bet; // The amount of money the player is betting in the current game.
    private boolean hasFolded;

    /**
     * Initialise a player with some money and a name.
     * @param money amount of money a player has
     * @param name name of player
     */
    Player(int money, String name) {
        setMoney(money);
        setName(name);
        setChoice(0);
        setBet(0);
        setFolded(false);
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setFolded(boolean hasFolded) {
        this.hasFolded = hasFolded;
    }

    public int getMoney() {
        return money;
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public int getChoice() {
        return choice;
    }

    public int getBet() {
        return bet;
    }

    public boolean hasFolded() {
        return hasFolded;
    }




}
