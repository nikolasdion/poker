package com.example.lib;
import java.util.*;

/**
 * Created by NDS on 26/10/2017.
 */

public class Hand {

    ArrayList<Card> cards = new ArrayList<>(); // the cards contained in the hand.
    private int rank;       // rank within the type of hand
    private int type;       // type, e.g. straight flush (9), four of a kind (8), .... pair(2), highest card(1)

    Hand() {
        this.rank = 0;
        this.type = 0;
    }

    Hand(ArrayList<Card> cards) {
        this.cards = cards;
        this.rank = 0;
        this.type = 0;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /* Return a string displaying the cards in the hand. */
    String show() {
        String temp = "";
        for (int i = 0; i < this.cards.size(); i++) {
            temp = temp + this.cards.get(i).strCard() + " ";
        }
        return temp;
    }

    /* Add a card to the hand. */
    void add(Card card) {
        this.cards.add(card);
    }

    Card get(int i){
        return cards.get(i);
    }

    /* Show the number of cards in the hand */
    int size(){
        return this.cards.size();
    }

    /* Return the highest card in the hand. */
    Card highestCard(){
        Card highest = this.cards.get(0);
        for(Card card:this.cards){
            if(card.absValue()>highest.absValue()){
                highest = card;
            }
        }
        return highest;
    }

    /* Create an inventory of the hand, i.e. how many cards have certain value. returns an
    * array from 0 to 12, number of cards with value n is stored in index n-2. */
    int[] inventory(){
        int[] inv = new int[13];
        for(Card card:this.cards){
            inv[card.getValue() - 2] += 1;
        }
        return inv;
    }


    /* Out of the cards in the hand, return the 5-card hand with the highest rank*/
    Hand getBestHand(){
        int highest = 0;
        Hand highestHand = new Hand();
        for(Hand combination:combinations(5)){
            if(combination.absoluteRank()> highest){
                highest = combination.absoluteRank();
                highestHand = combination;
            }
        }
        return highestHand;
    }

    /* Out of the cards in the hand, return an ArrayList of combinations of k different hands
     defined recursively*/
    ArrayList<Hand> combinations(int k){
        ArrayList<Hand> combinations = new ArrayList<>();
        if(k == 1){
            for(Card card:cards){
                Hand tempHand = new Hand();
                tempHand.add(card);
                combinations.add(tempHand);
            }
            return combinations;
        }

        else if(k == cards.size()){
            combinations.add(this);
            return combinations;
        }

        for(int i = 0; i < size() - k + 1; i++ ){
            Card head = get(i);
            Hand tail = new Hand();
            for(int j = i+1; j < size(); j++){
                tail.add(get(j));
            }
            ArrayList<Hand> tailComb = tail.combinations(k - 1);
            for(Hand hand:tailComb){
                hand.add(head);
                combinations.add(hand);
            }
        }

        return combinations;
    }

    /* absolute rank of the hand, which if compared with other hand will give its standing
    * wrt to that hand*/
    int absoluteRank(){
        checkTypeRank();
        return (100 * this.type) + this.rank;
    }

    /* initialise process that checks what type of hand it is and what's the rank within the hand
    * the value is saved within thw  */
    void checkTypeRank(){
        if(this.isStraightFlush()) {

        } else if(this.isFourOfAKind()){

        } else if(this.isFullHouse()){

        } else if(this.isFlush()){

        } else if(this.isStraight()){

        } else if(this.isThreeOfAKind()){

        } else if(this.isDoublePair()){

        } else if(this.isPair()){

        } else{
            this.type = 1;
            this.rank = this.highestCard().absValue();
        }
    }

    /* Below are methods which determine the type of hand and its ranking.
    * this.type and this.rank is set while the methods are run, therefore it not only returns
    * a boolean value but also modifies the hand's properties (i.e. rank and type)
    * NOTE: most of these only work properly with a 5-hand card*/

    boolean isStraightFlush(){
        if(this.isStraight() && this.isFlush()){
            this.type = 9;
            this.rank = this.highestCard().absValue();
            return true;
        }
        else {
            return false;
        }
    }

    boolean isFourOfAKind(){
        int [] inv = this.inventory();
        for(int i=0 ; i<13; i++){
            if(inv[i] == 4){
                this.type = 8;
                this.rank = i+2;
                return true;
            }
        }
        return false;
//        int count = 0;
//        int value = 0;
//        for(int i = 0; i <= this.cards.size()-4; i++){
//            for (int j = i+1; j <= this.cards.size(); j++){
//                if(this.cards.get(i).value == this.cards.get(j).value){
//                    value = this.cards.get(i).value;
//                    count = count +1;
//                }
//            }
//        }
//        if(count >=4){
//            this.type = 8;
//            this.rank = value;
//            return true;
//        } else{
//            return false;
//        }
    }

    boolean isFullHouse(){
        if(this.isPair() && this.isThreeOfAKind()){
            this.type = 7;
            return true;
        }
        else {
            return false;
        }
    }

    boolean isFlush() {
        int spades = 0;
        int hearts = 0;
        int diamonds = 0;
        int clubs = 0;
        for (Card card : cards) {
            switch (card.getSuit()) {
                case 1:
                    clubs = clubs + 1;
                    break;
                case 2:
                    diamonds = diamonds + 1;
                    break;
                case 3:
                    hearts = hearts + 1;
                    break;
                case 4:
                    spades = spades + 1;
                    break;

            }
        }
        if (spades == 5 || hearts == 5 || diamonds == 5 || clubs == 5) {
            this.type = 6;
            this.rank = this.highestCard().absValue();
            return true;
        } else {
            return false;
        }
    }

    boolean isStraight() {
       int[] inv = this.inventory();
       int count;
       for(int i = 0; i < inv.length-5; i++){
           count = 0;
           for(int j = i ; j < inv.length; j++){
               if(inv[j] ==0) break;
               else count++;

               if(count ==5){
                   this.type = 5;
                   this.rank = j + 2;
                   return true;
               }
           }
       }
       return false;
    }

    boolean isThreeOfAKind() {
        int[] inv = this.inventory();
        for (int i = 0; i < 13; i++) {
            if (inv[i] == 3) {
                this.type = 4;
                this.rank = i + 2;
                return true;
            }
        }
        return false;
    }

    boolean isDoublePair() {
        int numberOfPairs = 0;
        int highestPair = 0;
        int[] inv = this.inventory();
        for (int i = 0; i < 13; i++) {
            if (inv[i] == 2) {
                numberOfPairs += 1;
                highestPair = i + 2;
            }
        }
        if (numberOfPairs == 2) {
            this.type = 3;
            this.rank = highestPair;
            return true;
        } else {
            return false;
        }
    }

    boolean isPair() {
        int[] inv = this.inventory();
        for (int i = 0; i < 13; i++) {
            if (inv[i] == 2) {
                this.type = 2;
                this.rank = i + 2;
                return true;
            }
        }
        return false;
    }

//    /*Selects best hand out of possible hands*/
//    void finalHand() {
//
//
//    }
//



}
