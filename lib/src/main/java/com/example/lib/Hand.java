package com.example.lib;
import java.util.ArrayList;

/**
 * A hand which consists of a collection of cards.
 * A hand can belong to a player or the game (community hand).
 */

public class Hand {

    ArrayList<Card> cards = new ArrayList<>(); // Cards contained in the hand.
    private int type;  //  Type, e.g. straight flush (9), four of a kind (8), .... pair(2), highest card(1)
    private int rank;  //  Rank within the type of hand

    /**
     *  By default initialise an empty hand.
     */
    Hand() {
        type = 0;
        rank = 0;
    }

    /**
     *  Construct a hand with specified cards.
     *  @param cards an ArrayList of cards to be included in the hand
     */
    Hand(ArrayList<Card> cards) {
        this.cards = cards;
        type = 0;
        rank = 0;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     *  Return a string displaying the cards in the hand.
     *  @return string displaying the cards in the hand
     */
    String show() {
        String temp = "";
        for (int i = 0; i < cards.size(); i++) {
            temp = temp + cards.get(i).strCard() + " ";
        }
        return temp;
    }

    /**
     * Add a card to the hand.
     * @param card card to be added to the hand
     */
    void add(Card card) {
        cards.add(card);
    }

    /**
     * Return the ith card in the hand.
     * @return ith card in the hand
     * @param i index of the card
     * */
    Card get(int i) {
        return cards.get(i);
    }

    /**
     *  Return the number of cards in the hand.
     *  @return number of cards in the hand
     */
    int size() {
        return cards.size();
    }

    /**
     * Out of the cards in the hand, return the 5-card hand with the highest rank.
     * @return 5-card hand with the highest value
     */
    public Hand getBestHand() {
        int highest = 0;
        Hand highestHand = new Hand();
        for (Hand combination:combinations(5)) {
            if (combination.absoluteRank()> highest) {
                highest = combination.absoluteRank();
                highestHand = combination;
            }
        }
        return highestHand;
    }

    /**
     * Out of the cards in the hand, return combinations of k cards.
     * @return ArrayList of hands which are k cards combinations of current hand
     * @param k number of cards
     */
    public ArrayList<Hand> combinations(int k) {
        ArrayList<Hand> combinations = new ArrayList<>();
        if (k == 1) {
            for (Card card:cards) {
                Hand tempHand = new Hand();
                tempHand.add(card);
                combinations.add(tempHand);
            }
            return combinations;
        }

        else if (k == cards.size()) {
            combinations.add(this);
            return combinations;
        }

        for (int i = 0; i < size() - k + 1; i++ ) {
            Card head = get(i);
            Hand tail = new Hand();
            for (int j = i+1; j < size(); j++) {
                tail.add(get(j));
            }
            ArrayList<Hand> tailComb = tail.combinations(k - 1);
            for (Hand hand:tailComb) {
                hand.add(head);
                combinations.add(hand);
            }
        }
        return combinations;
    }

    /**
     * Returns the absolute rank of the hand, which can be compared with that of another hand
     * to determine which has a higher rank. Used in showdown.
     * @return absolute rank of the hand
     */
    public int absoluteRank() {
        checkTypeRank();
        return (100 * this.type) + this.rank;
    }

    /** Check and save the type and rank of the hand. */
    public void checkTypeRank() {
        if (this.isStraightFlush()) {

        } else if (this.isFourOfAKind()) {

        } else if (this.isFullHouse()) {

        } else if (this.isFlush()) {

        } else if (this.isStraight()) {

        } else if (this.isThreeOfAKind()) {

        } else if (this.isDoublePair()) {

        } else if (this.isPair()) {

        } else {
            this.type = 1;
            this.rank = this.highestCard().absValue();
        }
    }

    /**
     * METHODS TO DETERMINE TYPE AND RANK
     * type and rank are set while the methods are run, therefore it not only returns
     * a boolean value but also modifies the hand's properties (i.e. rank and type).
     * Note that most of these only work properly with a 5-card hand.
     * Use combinations(5) to get a 5-card hand.
     */

    private boolean isStraightFlush() {
        if (isStraight() && isFlush()) {
            type = 9;
            rank = highestCard().absValue();
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isFourOfAKind() {
        int [] inv = inventory();
        for (int i=0 ; i<13; i++) {
            if (inv[i] == 4) {
                type = 8;
                rank = i+2;
                return true;
            }
        }
        return false;
    }

    private boolean isFullHouse() {
        if (this.isPair() && this.isThreeOfAKind()) {
            type = 7; // Rank is already determined by isThreeOfAKind
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isFlush() {
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
            type = 6;
            rank = highestCard().absValue();
            return true;
        } else {
            return false;
        }
    }

    private boolean isStraight() {
       int[] inv = inventory();
       int count;
       for (int i = 0; i < inv.length-5; i++) {
           count = 0;
           for (int j = i ; j < inv.length; j++) {
               if (inv[j] == 0) {
                   break;
               } else {
                   count++;
               }

               if (count ==5) {
                   type = 5;
                   rank = j + 2;
                   return true;
               }
           }
       }
       return false;
    }

    private boolean isThreeOfAKind() {
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

    private boolean isDoublePair() {
        int numberOfPairs = 0;
        int highestPair = 0;
        int[] inv = inventory();
        for (int i = 0; i < 13; i++) {
            if (inv[i] == 2) {
                numberOfPairs += 1;
                highestPair = i + 2;
            }
        }
        if (numberOfPairs == 2) {
            type = 3;
            rank = highestPair;
            return true;
        } else {
            return false;
        }
    }

    private boolean isPair() {
        int[] inv = inventory();
        for (int i = 0; i < 13; i++) {
            if (inv[i] == 2) {
                type = 2;
                rank = i + 2;
                return true;
            }
        }
        return false;
    }


    /**
     * Create an inventory of the hand, i.e. how many cards have certain value. returns an
     * array from 0 to 12, number of cards with value n is stored in index n-2.
     * @return an array (indices 0-12), number of cards with value n is stored in index n-2
     */
    int[] inventory() {
        int[] inv = new int[13];
        for (Card card:cards) {
            inv[card.getValue() - 2] += 1;
        }
        return inv;
    }

    /**
     *  Return the highest card in the hand.
     *  @return highest card in the hand
     */
    Card highestCard() {
        Card highest = this.cards.get(0);
        for (Card card:this.cards) {
            if (card.absValue()>highest.absValue()) {
                highest = card;
            }
        }
        return highest;
    }

}
