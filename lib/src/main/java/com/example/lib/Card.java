package com.example.lib;

import java.rmi.AccessException;

/**
 * Created by NDS on 25/10/2017.
 */

public class Card {

    private int value;
    private int suit;

    Card(int value, int suit){
        setValue(value);
        setSuit(suit);
    }
    void setValue(int value){
        this.value = value;
    }

    void setSuit(int suit){
        this.suit = suit;
    }
    //suit is represented as a number 1-4 from clubs to spades
    // value is represented as a number 1-13 from ace to king

    public String strCard(){
        String showValue = "";
        String showSuit = "";

        if((this.value > 1) && (this.value < 11)){
            showValue = String.valueOf(this.value);
        } else if (this.value == 1) {
            showValue = "A";
        } else if (this.value == 11) {
            showValue = "J";
        } else if (this.value == 12) {
            showValue = "Q";
        } else if (this.value == 13) {
            showValue = "K";
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

        String showCardStr = showValue + showSuit;

        return showCardStr;
    }






}
