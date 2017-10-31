package com.example.lib;

/**
 * Created by NDS on 25/10/2017.
 */

public class Card {
    /* Suit is represented as a number 1-4 from clubs to spades.
     * Value is represented as a number 2-14 from 2 to ace.  */
    private int value;
    private int suit;

    Card(int value, int suit) {
        setValue(value);
        setSuit(suit);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }

    /* Display card data as a string, e.g. "2H" for Card(2,3) (two of hearts) */
    public String strCard() {
        String showValue = "";
        String showSuit = "";

        if ((value > 1) && (value < 11)) {
            showValue = String.valueOf(this.value);
        } else if (value == 11) {
            showValue = "J";
        } else if (value == 12) {
            showValue = "Q";
        } else if (value == 13) {
            showValue = "K";
        } else if (value == 14) {
            showValue = "A";
        }

        if (suit == 1 ) {
            showSuit = "C";
        } else if (suit == 2) {
            showSuit = "D";
        } else if (suit == 3) {
            showSuit = "H";
        } else if (suit == 4) {
            showSuit = "S";
        }

        return showValue + showSuit;
    }

    /* Generate absolute value (i.e. rank) of the card. The formula is 4*value + suit.
     * This is used to compare the value of the card (to find which is highest). */
    public int absValue() {
         return value*4 + suit;
    }







}
