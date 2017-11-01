package com.example.lib;
import java.util.ArrayList;

/**
 * A hand which holds a number cards.
 * Can belong to a player or the game (community hand).
 * Has a type (e.g. flush) and a rank (their standing within the type), but these are only evaluated
 * accurately for a 5-card hand.
 * Can hold any number of cards, typically a player's hand has two cards and a community hand has
 * 3-5 cards.
 */

public class Hand {

    ArrayList<Card> mCards = new ArrayList<>(); // Cards contained in the hand.
    private int mType;  //  Type, e.g. straight flush (9), four of a kind (8), .... pair(2), highest card(1)
    private int mRank;  //  Rank within the type of hand

    /**
     *  By default initialise an empty hand.
     */
    Hand() {
        mType = 0;
        mRank = 0;
    }

    /**
     *  Construct a hand with specified cards.
     *  @param cards an ArrayList of cards to be included in the hand
     */
    Hand(ArrayList<Card> cards) {
        mCards = cards;
        mType = 0;
        mRank = 0;
    }

    public ArrayList<Card> getCards() {
        return mCards;
    }

    public void setCards(ArrayList<Card> cards) {
        mCards = cards;
    }

    /**
     *  Return a string displaying the cards in the hand.
     *  @return string displaying the cards in the hand
     */
    String show() {
        String temp = "";

        /* Loop through all the cards in the deck. */
        for (Card card : mCards) {
            temp = temp + card.strCard() + " ";
        }

        return temp;
    }

    /**
     * Add a card to the hand.
     * @param card card to be added to the hand
     */
    void add(Card card) {
        mCards.add(card);
    }

    /**
     * Return the ith card in the hand.
     * @return ith card in the hand
     * @param i index of the card
     * */
    Card get(int i) {
        return mCards.get(i);
    }

    /**
     *  Return the number of cards in the hand.
     *  @return number of cards in the hand
     */
    int size() {
        return mCards.size();
    }

    /**
     * Out of the cards in the hand, return the 5-card hand with the highest rank.
     * @return 5-card hand with the highest value
     */
    public Hand getBestHand() {
        int highest = 0;
        Hand highestHand = new Hand();

        /* Loop through all possible 5-card hand combination. */
        for (Hand combination : combinations(5)) {
            if (combination.absoluteRank()> highest) {
                highest = combination.absoluteRank();
                highestHand = combination;
            }
        }

        return highestHand;
    }

    /**
     * Out of the cards in the hand, return combinations of k cards. This function is defined
     * recursively.
     * @return ArrayList of hands which are k cards combinations of current hand
     * @param k number of cards
     */
    public ArrayList<Hand> combinations(int k) {
        ArrayList<Hand> combinations = new ArrayList<>();

        /* 1-card combinations, which are just hands each with one of the cards of the original hand. */
        if (k == 1) {
            for (Card card : mCards) {
                Hand tempHand = new Hand();
                tempHand.add(card);
                combinations.add(tempHand);
            }
            return combinations;
        }

        /* k-card combinations, where k is the same as the number of card of the original hand.
         * This returns the an arraylist containing a hand identical to the original hand */
        else if (k == mCards.size()) {
            combinations.add(this);
            return combinations;
        }

        /* If k is neither of the first two scenarios, follow this recursive method. */
        else {
            for (int i = 0; i < size() - k + 1; i++ ) {
                Card head = get(i);
                Hand tail = new Hand();
                for (int j = i + 1; j < size(); j++) {
                    tail.add(get(j));
                }
                ArrayList<Hand> tailComb = tail.combinations(k - 1);
                for (Hand hand : tailComb) {
                    hand.add(head);
                    combinations.add(hand);
                }
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
        return (100 * mType) + mRank;
    }

    /** Check and save the type and rank of the hand. */
    public void checkTypeRank() {
        if (isStraightFlush()) {

        } else if (isFourOfAKind()) {

        } else if (isFullHouse()) {

        } else if (isFlush()) {

        } else if (isStraight()) {

        } else if (isThreeOfAKind()) {

        } else if (isDoublePair()) {

        } else if (isPair()) {

        } else {
            mType = 1;
            mRank = highestCard().absValue();
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

        /* Check if the hand is both straight and flush. */
        if (isStraight() && isFlush()) {
            mType = 9;
            mRank = highestCard().absValue();
            return true;
        } else {
            return false;
        }
    }

    private boolean isFourOfAKind() {
        /* Create an inventory of the hand, which stores how many cards have each value.
        * The number of cards with value n is stored in index n-2. */
        int [] inv = inventory();

        /* Loop through the inventory. */
        for (int index=0 ; index<13; index++) {

            /* Check if there are 4 cards with this value in the hand. */
            if (inv[index] == 4) {
                mType = 8;
                mRank = index+2;
                return true;
            }
        }
        return false;
    }

    private boolean isFullHouse() {
        /* Check if the hand contain both a pair and a three of a kind. */
        if (isPair() && isThreeOfAKind()) {
            mType = 7; // Rank is already determined by isThreeOfAKind.
            return true;
        } else {
            return false;
        }
    }

    private boolean isFlush() {

        /* Counters for the number of cards with the respective suits. */
        int spades = 0;
        int hearts = 0;
        int diamonds = 0;
        int clubs = 0;

        /* Loop through the cards in the hand. */
        for (Card card : mCards) {

            /* Increase the counter of the suit of the following card. */
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

        /* If there are 5 cards with the same suit, the hand is a flush. */
        if (spades == 5 || hearts == 5 || diamonds == 5 || clubs == 5) {
            mType = 6;
            mRank = highestCard().absValue();
            return true;
        } else {
            return false;
        }
    }

    private boolean isStraight() {
        /* Create an inventory of the hand, which stores how many cards have each value.
        * The number of cards with value n is stored in index n-2. */
        int[] inv = inventory();

        /* Counter for the number of consecutive cards present. */
        int count;

        /* Loop through the possible starting value of a straight. */
        for (int start = 0; start < inv.length-5; start++) {
            count = 0;

            /* Loop through the subsequent values.*/
            for (int currentIndex = start ; currentIndex < inv.length; currentIndex++) {

                /* Check if there are cards with the value.*/
                if (inv[currentIndex] == 0) {
                    break; // Break the loop if there are no cards with the value.
                } else {
                    count++; // Increase the counter if there are cards with the value
                }

                /* Once there has been 5 consecutive cards, the hand is a straight. */
                if (count ==5) {
                    mType = 5;
                    mRank = currentIndex + 2;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isThreeOfAKind() {
        /* Create an inventory of the hand, which stores how many cards have each value.
        * The number of cards with value n is stored in index n-2. */
        int[] inv = inventory();

        /* Loop through the inventory. */
        for (int index = 0; index < 13; index++) {

            /* If there are 3 cards of the same value, the hand is a three of a kind. */
            if (inv[index] == 3) {
                mType = 4;
                mRank = index + 2;
                return true;
            }
        }
        return false;
    }

    private boolean isDoublePair() {
        int numberOfPairs = 0;
        int highestPair = 0;

        /* Create an inventory of the hand, which stores how many cards have each value.
        * The number of cards with value n is stored in index n-2. */
        int[] inv = inventory();

        /* Loop through the inventory. */
        for (int index = 0; index < 13; index++) {

            /* If there are 2 cards of the same value, the hand has a pair. */
            if (inv[index] == 2) {
                numberOfPairs++;
                highestPair = index + 2;
            }
        }

        /* If there are two pairs, the hand is a double pair.*/
        if (numberOfPairs == 2) {
            mType = 3;
            mRank = highestPair;
            return true;
        } else {
            return false;
        }
    }

    private boolean isPair() {

        /* Create an inventory of the hand, which stores how many cards have each value.
        * The number of cards with value n is stored in index n-2. */
        int[] inv = inventory();

        /* Loop through the inventory. */
        for (int index = 0; index < 13; index++) {
            /* If there are 2 cards of the same value, the hand is a pair. */
            if (inv[index] == 2) {
                mType = 2;
                mRank = index + 2;
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

        /* Loop through the cards in the hand. Increase the inventory based on the values of the card. */
        for (Card card:mCards) {
            inv[card.getValue() - 2]++;
        }
        return inv;
    }

    /**
     *  Return the highest card in the hand.
     *  @return highest card in the hand
     */
    Card highestCard() {
        /* Set highest card to the first card. */
        Card highest = mCards.get(0);

        /* Loop through the cards in the hand. Update highest value if the card is higher than the
         * current card. */
        for (Card card : mCards) {
            if (card.absValue()>highest.absValue()) {
                highest = card;
            }
        }
        return highest;
    }

}
