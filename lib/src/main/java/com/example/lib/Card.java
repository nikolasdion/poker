package com.example.lib;

/**
 * Represents a card.
 * Suit is represented as a number 1-4 from clubs to spades.
 * Value is represented as a number 2-14 from 2 to ace.
 */

public class Card {
    private int mValue; // Suit is represented as a number 1-4 from clubs to spades.
    private int mSuit; // Value is represented as a number 2-14 from 2 to ace.

    Card(int value, int suit) {
        setValue(value);
        setSuit(suit);
    }

    /**
     * Display card data as a string, e.g. "2H" for Card(2,3) (two of hearts).
     * @return a string displaying the card
     */
    public String strCard() {
        String showValue = "";
        String showSuit = "";

        if ((mValue > 1) && (mValue < 11)) {
            showValue = String.valueOf(mValue);
        } else if (mValue == 11) {
            showValue = "J";
        } else if (mValue == 12) {
            showValue = "Q";
        } else if (mValue == 13) {
            showValue = "K";
        } else if (mValue == 14) {
            showValue = "A";
        }

        if (mSuit == 1 ) {
            showSuit = "C";
        } else if (mSuit == 2) {
            showSuit = "D";
        } else if (mSuit == 3) {
            showSuit = "H";
        } else if (mSuit == 4) {
            showSuit = "S";
        }

        return showValue + showSuit;
    }

    /**
     *  Generate absolute value (i.e. rank) of the card. The formula is 4*value + mSuit.
     *  This is used to compare the value of the card (to find which is highest).
     *  @return absolute value of the card
     */
    public int absValue() {
         return mValue*4 + mSuit;
    }

    public void setValue(int value) {
        mValue = value;
    }

    public void setSuit(int suit) {
        mSuit = suit;
    }

    public int getValue() {
        return mValue;
    }

    public int getSuit() {
        return mSuit;
    }








}
