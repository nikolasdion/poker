package com.example.lib;

/**
 * Created by NDS on 25/10/2017.
 */

public class Card {
    /* Suit is represented as a number 1-4 from clubs to spades.
    Value is represented as a number 2-14 from 2 to ace.
    */
    private int value;
    private int suit;

    Card(int value, int suit){
        setValue(value);
        setSuit(suit);
    }

    public void setValue(int value){
        this.value = value;
    }

    public void setSuit(int suit){
        this.suit = suit;
    }

    public int getValue(){
        return value;
    }

    public int getSuit(){
        return suit;
    }

    /* Display card data as a string, e.g. 2H for Card(2,3) (two of hearts) */
    public String strCard(){
        String showValue = "";
        String showSuit = "";

        if((value > 1) && (value < 11)){
            showValue = String.valueOf(this.value);
        } else if (this.value == 11) {
            showValue = "J";
        } else if (this.value == 12) {
            showValue = "Q";
        } else if (this.value == 13) {
            showValue = "K";
        } else if (this.value == 14) {
            showValue = "A";
        }

        if(this.suit == 1 ){
            showSuit = "C";
        } else if (this.suit == 2) {
            showSuit = "D";
        } else if (this.suit == 3) {
            showSuit = "H";
        } else if (this.suit == 4){
            showSuit = "S";
        }

        return showValue + showSuit;
    }

    /* Generate absolute value (i.e. rank) of the card. The formula is 4*value + suit. */
    int absValue(){
         return this.value*4 + this.suit;
    }







}
