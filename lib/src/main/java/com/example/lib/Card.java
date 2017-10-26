package com.example.lib;
import java.util.*;

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

// convert card into a string understandable to player
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


    // compare two cards
    boolean isHigherThan(Card otherCard){
        int card1;
        int card2;
        //aces are the highrst card, change ace value from 1 to 14
        if(this.value == 1){
            card1 = 13*4 + this.suit;
        } else{
            card1 = this.value*4 + this.suit;
        }
        if(otherCard.value == 1){
            card2 = 13*4 + this.suit;
        } else{
            card2 = this.value*4 + this.suit;
        }
        return card1 > card2;
    }







}
