package com.example.lib;

import java.util.Scanner;

/**
 * A player in the poker game. During her turn, a player has 3 choices, represented by an integer:
 * 1: raise, 2: check, 3: fold.
 */

public class Player {
    private int mMoney;
    private Hand mHand = new Hand();
    private String mName;
    private Choice mChoice; // Player's action (1: raise, 2: call, 3: fold)
    private int mBet; // The amount of money the player is betting in the current game.
    private boolean mHasFolded;
    Scanner mScanner = new Scanner( System.in );

    enum Choice {
        RAISE,
        CALL,
        FOLD,
        UNDECLARED;

        public static Choice fromInt(int x) {
            switch(x) {
                case 1:
                    return RAISE;
                case 2:
                    return CALL;
                case 3:
                    return FOLD;
                default:
                    return UNDECLARED;
            }
        }
    }

    /**
     * Initialise a player with some money and a name.
     * @param money amount of money a player has
     * @param name name of player
     */
    Player(int money, String name) {
        mMoney = money;
        mName = name;
        mChoice = Choice.UNDECLARED;
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

    public void setChoice(Choice choice) {
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

    public Choice getChoice() {
        return mChoice;
    }

    public int getBet() {
        return mBet;
    }

    public boolean hasFolded() {
        return mHasFolded;
    }

    /* A player's turn in the game. Return the raise she makes (positive for raise, 0 for fold,
    -1 for fold. */
    public int turn(int currentBet, int noOfRaises ) {

        /* Set player's choice to 0. */
        mChoice = Choice.UNDECLARED;

        /* Display status of the game for player's information. */
        System.out.println("!!!PLAYER " + mName + "'s TURN!!!");
        System.out.println("Player " + mName + "'s hand  : " + mHand.show());
        System.out.println("Player " + mName + "'s money : " + mMoney);
        System.out.println("Player " + mName + "'s bet   : " + mBet);

        /* Player makes a choice. */
        while (mChoice == Choice.UNDECLARED) {
            System.out.println("(1: raise, 2: call, 3: fold)");
            System.out.print("Player " + mName + "'s move  : ");
            mChoice = Choice.fromInt(mScanner.nextInt());

            switch (mChoice) {

                /* Player raises.*/
                case RAISE:

                    /* Give error message and force player to choose again if there has been 3
                     * raises in a round. */
                    if (noOfRaises > 2) {
                        System.out.println("Cannot raise further in this round.");
                        mChoice = Choice.UNDECLARED;
                        break;
                    }

                    /* Get the amount by which the player wishes to raise. */
                    System.out.print("Raise by         : ");
                    int raise = mScanner.nextInt();

                    /* Give error message and force player to choose again if she has insufficient
                     * money. */
                    if (mMoney + mBet < currentBet + raise) {
                        System.out.println("Insufficient money to raise.");
                        mChoice = Choice.UNDECLARED;
                        break;
                    }

                    /* If raise was successful, update game and player variables. */
                    mMoney = (mMoney + mBet) - (currentBet + raise);
                    mBet = currentBet + raise;
                    return raise;

                /* Player calls. */
                case CALL:

                    /* Give error message and force player to choose again if she has insufficient money. */
                    if (mMoney + mBet < currentBet) {
                        System.out.println("Insufficient money to call.");
                        mChoice = Choice.UNDECLARED;
                        break;
                    }

                    /* If call was successful, update game and player variables. */
                    mMoney = mMoney + (mBet- currentBet);
                    mBet = currentBet;
                    System.out.println("Called.");
                    return 0;

                /* Player folds. */
                case FOLD:
                    mHasFolded = true;
                    System.out.println("Folded.");
                    return -1;

                default:
                    System.out.println("Invalid input.");
                    mChoice = Choice.UNDECLARED;

            }
        }

        /* If all else fails (which shouldn't happen). */
        return 10000000;
    }
}
