package com.example.lib;

/**
 * A player in the poker game. During her turn, a player has 3 choices, represented by an integer:
 * 1: raise, 2: check, 3: fold.
 */

public class Player {
    private int mMoney;
    private Hand mHand = new Hand();
    private String mName;
    private int mChoice; // Player's action (1: raise, 2: check, 3: fold)
    private int mBet; // The amount of money the player is betting in the current game.
    private boolean mHasFolded;

    /**
     * Initialise a player with some money and a name.
     * @param money amount of money a player has
     * @param name name of player
     */
    Player(int money, String name) {
        mMoney = money;
        mName = name;
        mChoice = 0;
        mBet = 0;
        mHasFolded = false;
    }

    public void setMoney(int money) {
        mMoney = money;
    }

    public void setHand(Hand hand) {
        mHand = hand;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setChoice(int choice) {
        mChoice = choice;
    }

    public void setBet(int bet) {
        mBet = bet;
    }

    public void setFolded(boolean hasFolded) {
        mHasFolded = hasFolded;
    }

    public int getMoney() {
        return mMoney;
    }

    public Hand getHand() {
        return mHand;
    }

    public String getName() {
        return mName;
    }

    public int getChoice() {
        return mChoice;
    }

    public int getBet() {
        return mBet;
    }

    public boolean hasFolded() {
        return mHasFolded;
    }




}
