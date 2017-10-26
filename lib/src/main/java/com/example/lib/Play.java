package com.example.lib;
import java.util.*;

public class Play {
    public static void main(String[] args){

//      ******TESTING*****
//        Card card = new Card(1,2);
//        System.out.println(card.strCard());
//        ArrayList<Card> hand1 = new ArrayList();
//        hand1.add(card);
//        Player player1 = new Player( 100, hand1 ) ;
//        player1.showHand();

        Hand emptyHand = new Hand();
        int numberOfPlayers = 2;
        int startingMoney = 100;
        boolean isPlaying = true;
        int currentBet = 10;
        int pot = 0;
        Scanner scanner = new Scanner( System.in );
        int choice = 0;
        Player winner = new Player(startingMoney,emptyHand, "NONE");


//      initialise deck and shuffle deck
        Deck deck = new Deck();
        deck.shuffle();

//      initialise community hand
//        Hand communityHand = new Hand();

//      deal 5 cards to community hand
//        for(int i = 0; i<5; i++){
//            deck.dealTo(communityHand);
//        }

//      create players with numbers as their name and deal 2 cards to each player
        ArrayList<Player> players = new ArrayList<>();
        for(int i = 0; i < numberOfPlayers; i++){
            Hand tempHand = new Hand();
            deck.dealTo(tempHand);
            deck.dealTo(tempHand);
            deck.dealTo(tempHand);
            deck.dealTo(tempHand);
            deck.dealTo(tempHand);
            Player playerTemp = new Player(startingMoney, tempHand, String.valueOf(i+1));
            players.add(playerTemp);
            System.out.println("Player " + players.get(i).name + "'s hand  : " + players.get(i).hand.show());
            System.out.println("Straight flush      : " + players.get(i).hand.isStraightFlush());
            System.out.println("Four of a Kind      : " + players.get(i).hand.isFourOfAKind());
            System.out.println("Full House          : " + players.get(i).hand.isFullHouse());
            System.out.println("Flush               : " + players.get(i).hand.isFlush());
            System.out.println("Straight            : " + players.get(i).hand.isStraight());
            System.out.println("Three of a Kin      : " + players.get(i).hand.isThreeOfAKind());
            System.out.println("Double Pair         : " + players.get(i).hand.isDoublePair());
            System.out.println("Pair                : " + players.get(i).hand.isPair());
            System.out.println("Highest Card        : " + players.get(i).hand.highestCard().strCard());
            players.get(i).hand.checkTypeRank();
            System.out.println("ABSOLUTE RANK:" + players.get(i).hand.absoluteRank());
        }





        while(isPlaying){
            for(Player player:players){
                choice = 0; //resets choice
                System.out.println();
                System.out.println("!!!PLAYER " + player.name + "'s TURN!!!");
//                System.out.println("Community hand   : " + communityHand.show());
                System.out.println("Current bet      : " + currentBet);
                System.out.println("Pot              : " + pot);
                System.out.println("Player " + player.name + "'s hand  : " + player.hand.show());
                System.out.println("Player " + player.name + "'s money : " + player.money);
                while(choice == 0){
                    System.out.println("(1: raise, 2: call, 3: fold)");
                    System.out.print("Player " + player.name + "'s move  : ");
                    choice = scanner.nextInt();
                    switch(choice) {
                        case 1: {
                            System.out.print("Raise by: ");
                            currentBet = currentBet + scanner.nextInt();
                            pot = pot + currentBet;
                            player.setMoney(player.money - currentBet);
                            System.out.println(""+ pot + currentBet);
                            break;
                        }
                        case 2: {
                            pot = pot + currentBet;
                            player.setMoney(player.money - currentBet);
                            System.out.println("Called.");
                            System.out.println(""+ pot + currentBet);
                            break;
                        }
                        case 3: {
                            isPlaying = false;
                            winner = player;
                            break;
                        }
                        default:{
                            System.out.println("Invalid input.");
                            choice = 0;
                        }
                    }
                }
                if(isPlaying == false) break;

            }


        }

        System.out.println("The winner of this round is Player " + winner.name);




















    }
}
