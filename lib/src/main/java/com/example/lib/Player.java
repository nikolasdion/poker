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
    private Choice mChoice; // Player's choice (defined in enum below)
    private int mBet; // The amount of money the player is betting in the current game.
    Scanner mScanner = new Scanner( System.in );

    /**
     * Player's choice. There are only 4 possible options. Default is UNDECLARED, but player may
     * choose to either RAISE, CALL, or FOLD during her turn in the game.
     *
     * RAISE and CALL are reset at the start of every round using game.resetChoices(). FOLD only
     * resets to UNDECLARED when a new game is started.
     * */
    enum Choice {
        RAISE,
        CALL,
        FOLD,
        UNDECLARED;

        /**
         *  Given an integer (from user's input), get a Choice corresponding to that integer.
         *  @param x integer (from user's input)
         *  @return Choice corresponding to the integer (1: RAISE, 2: CALL, 3: FOLD)
         */
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

    /* Whether player has folded (valid for an entire game). */
    public boolean hasFolded() {
        return mChoice == Choice.FOLD;
    }

    /* Whether player has called in the current round (Choice is reset at the end of a round). */
    public boolean hasCalled() {
        return mChoice == Choice.CALL;
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
//                    mHasFolded = true;
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
