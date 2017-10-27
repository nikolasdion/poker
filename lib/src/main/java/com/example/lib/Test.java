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

//        /* Test hand rank generation. */
//        Hand tempHand = new Hand();
//        Card card1 = new Card(2,2);
//        Card card2 = new Card(3,2);
//        Card card3 = new Card(4,2);
//        Card card4 = new Card(7,2);
//        Card card5 = new Card(6,2);
//        tempHand.add(card1);
//        tempHand.add(card2);
//        tempHand.add(card3);
//        tempHand.add(card4);
//        tempHand.add(card5);
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

        Game game = new Game(2,100);

        game.dealPlayers();

        game.dealCommunity();
        game.dealCommunity();
        game.dealCommunity();



        game.displayStatus();

        mainLoop: //Label, broken when everyone has folded
        for(int round = 1; round < 4; round++){
            for(int turn = 1; turn < 4; turn++){

                if(game.nextRound()) break; // if everyone ahs called/folded, proceed to next round

                for(int playerIndex = 0; playerIndex < game.numberOfPlayers; playerIndex++){
                    if(game.checkFolded()) break mainLoop;
                    game.playerTurn(playerIndex);
                }
            }

            game.dealCommunity(); //deal one card to community hand after the end of each round
        }

    }
}
