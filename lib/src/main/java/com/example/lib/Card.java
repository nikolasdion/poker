package com.example.lib;

/**
 * Represents a card.
 * Suit is represented as a number 1-4 from clubs to spades.
 * Value is represented as a number 2-14 from 2 to ace.
 */

public class Card {
    private Value mValue; // Suit is represented as a number 1-4 from clubs to spades.
    private Suit mSuit; // Value is represented as a number 2-14 from 2 to ace.

    public enum Suit {
        ClUBS(0), DIAMONDS(1), HEARTS(2), SPADES(3);

        private int suitInt;

        Suit (int n) {
            suitInt = n;
        }

        public int getInt() {
            return suitInt;
        }

    }

    public enum Value {
        TWO(0),
        THREE(1),
        FOUR(2),
        FIVE(3),
        SIX(4),
        SEVEN(5),
        EIGHT(6),
        NINE(7),
        TEN(8),
        JACK(9),
        QUEEN(10),
        KING(11),
        ACE(12);

        private int valueInt;

        Value (int n){
            valueInt = n;
        }

        public int getInt() {
            return valueInt;
        }

    }

    Card(Value value, Suit suit) {
        mValue = value;
        mSuit = suit;
    }

    /**
     * Display card data as a string, e.g. "2H" for Card(2,3) (two of hearts).
     * @return a string displaying the card
     */
    public String strCard() {
        String showValue = "";
        String showSuit = "";

        /* Get a string containing showing the value of the card. */
        if (mValue.getInt() < 9 ) {
            showValue = String.valueOf(mValue.getInt() + 2);
        } else if (mValue == Value.JACK) {
            showValue = "J";
        } else if (mValue == Value.QUEEN) {
            showValue = "Q";
        } else if (mValue == Value.KING) {
            showValue = "K";
        } else if (mValue == Value.ACE) {
            showValue = "A";
        }

        /* Get a string containing showing the suit of the card. */
        if (mSuit == Suit.ClUBS ) {
            showSuit = "C";
        } else if (mSuit == Suit.DIAMONDS) {
            showSuit = "D";
        } else if (mSuit == Suit.HEARTS) {
            showSuit = "H";
        } else if (mSuit == Suit.SPADES) {
            showSuit = "S";
        }

        return showValue + showSuit;
    }

    /**
     * Display card data as a long string directly from the enum constants, e.g. "TWO of HEARTS"
     * @return a string displaying the card in long form*/
    public String strCardLong() {
        return mValue + " of " + mSuit;
    }

    /**
     *  Generate absolute value (i.e. rank) of the card. The formula is 4*value + mSuit.
     *  This is used to compare the value of the card (to find which is highest).
     *  @return absolute value of the card
     */
    public int absValue() {
         return mValue.getInt()*4 + mSuit.getInt();
    }

    public Suit getSuit() {
        return mSuit;
    }

    public Value getValue() {

        return mValue;
    }

    /* Get the value of the card as an integer. */
    public int getValueInt() {
        return mValue.getInt();
    }

    /* Get the suit of the card as an integer. */
    public int getSuitInt() {
        return mSuit.getInt();
    }










}
