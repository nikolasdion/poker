package com.example.lib;
import java.util.*;

/**
 * Created by NDS on 27/10/2017.
 */

public class Test {
    public static void main(String[] args){

//        Card card = new Card(14,2);
//        System.out.println(card.strCard());
//        Hand hand1 = new Hand();
//        hand1.add(card);
//        Player player1 = new Player( 100, hand1,"test" ) ;
//        System.out.println(player1.hand.show());
//
//

        /* Test hand rank generation. */
//        Hand tempHand = new Hand();
//        Card card1 = new Card(2,2);
//        Card card2 = new Card(3,2);
//        Card card3 = new Card(4,3);
//        Card card4 = new Card(7,2);
//        Card card5 = new Card(6,2);
//        Card card6 = new Card(12,1);
//        Card card7 = new Card(5,2);
//
//        tempHand.add(card1);
//        tempHand.add(card2);
//        tempHand.add(card3);
//        tempHand.add(card4);
//        tempHand.add(card5);
//        tempHand.add(card6);
//        tempHand.add(card7);
//        System.out.println(tempHand.show());

        Deck deck = new Deck();

        Hand tempHand = new Hand(deck.getCards());

        ArrayList<Hand> combi = tempHand.combinations(5);

//        for(Hand cards:combi){
//            System.out.println("Cards collection " + combi.indexOf(cards) + " " + cards.show());
//        }

        System.out.println(" Best Hand: " +  tempHand.getBestHand().show() + ";  Score:  " + tempHand.getBestHand().absoluteRank());






//        System.out.println("Hand                : " + tempHand.show());
//        System.out.println("Straight flush      : " + tempHand.isStraightFlush());
//        System.out.println("Four of a Kind      : " + tempHand.isFourOfAKind());
//        System.out.println("Full House          : " + tempHand.isFullHouse());
//        System.out.println("Flush               : " + tempHand.isFlush());
//        System.out.println("Straight            : " + tempHand.isStraight());
//        System.out.println("Three of a Kin      : " + tempHand.isThreeOfAKind());
//        System.out.println("Double Pair         : " + tempHand.isDoublePair());
//        System.out.println("Pair                : " + tempHand.isPair());
//        System.out.println("Highest Card        : " + tempHand.highestCard().strCard());
//        tempHand.checkTypeRank();
//        System.out.println("ABSOLUTE RANK       : " + tempHand.absoluteRank());


//        Scanner scanner = new Scanner( System.in );
//
//        Game game = new Game(2,100);
//

//        while(game.getIsPlaying()){
//            game.reset();
//
//            game.dealPlayers();
//            game.dealCommunity();
//            game.dealCommunity();
//            game.dealCommunity();
//            game.displayStatus();
//
//            /*BETTING STAGE*/
//
//            game.bettingStage();
//
//            /*SHOWDOWN (if needed)*/
//
//            if(game.getShowdown()) game.showdown();
//
//            game.reward();
//
//            game.cont();
//
//
//        }
















    }


}
