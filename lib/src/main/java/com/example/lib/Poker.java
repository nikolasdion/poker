package com.example.lib;
import java.util.*;

public class Poker {




    public static void main(String[] args){


        Hand emptyHand = new Hand();
        int numberOfPlayers = 2;
        int startingMoney = 100;
        boolean isPlaying = true;
        int currentBet = 10;
        int pot = 0;
        Scanner scanner = new Scanner( System.in );
        int choice = 0;
        Player winner = new Player(startingMoney,emptyHand, "NONE");


        /* Initialise and shuffle deck */
        Deck deck = new Deck();
        deck.shuffle();

//        /* Initialise and deal 5 cards to community hand */
//        Hand communityHand = new Hand();
//        for(int i = 0; i<5; i++){
//            deck.dealTo(communityHand);
//        }

        /* Initialise players (player name is a number) and deal 2 cards to every player's hand */
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
            players.get(i).hand.checkTypeRank();
        }





        while(isPlaying){
            for(Player player:players){
                if(player.hasFolded) break;
                System.out.println();
                System.out.println("!!!PLAYER " + player.name + "'s TURN!!!");
//                System.out.println("Community hand   : " + communityHand.show());
                System.out.println("Current bet      : " + currentBet);
                System.out.println("Pot              : " + pot);
                System.out.println("Player " + player.name + "'s hand  : " + player.hand.show());
                System.out.println("Player " + player.name + "'s money : " + player.money);
                while(player.choice == 0){
                    System.out.println("(1: raise, 2: call, 3: fold)");
                    System.out.print("Player " + player.name + "'s move  : ");
                    player.setChoice(scanner.nextInt());
                    switch(player.choice) {
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
                            player.setFolded(true);
                            break;
                        }
                        default:{
                            System.out.println("Invalid input.");
                            player.setChoice(0);
                        }
                    }
                }
                if(isPlaying == false) break;

            }


        }

        System.out.println("The winner of this round is Player " + winner.name);




















    }
}
