package com.example.lib;

import java.util.Scanner;

/**
 * Created by NDS on 27/10/2017.
 */

public class Game {
    public int numberOfPlayers;
    public Player[] players;
    public int pot;
    public int currentBet;
    public Deck deck;
    public Hand communityHand = new Hand();
    Scanner scanner = new Scanner( System.in );
    public Player winner;


    /*Initialise game, creating array of players and shuffled deck,
    setting pot and currentBet to 0*/
    Game(int numberOfPlayers, int initialMoney){
        this.numberOfPlayers = numberOfPlayers;
        this.setPlayers(new Player[numberOfPlayers]);
        this.setCurrentBet(0);
        this.setPot(0);
        Deck tempDeck = new Deck();
        tempDeck.shuffle();
        Hand emptyHand = new Hand();
        this.setDeck(tempDeck);
        for(int i = 0; i < this.numberOfPlayers; i++){
            this.players[i] = new Player (initialMoney, emptyHand, String.valueOf(i+1));
        }
    }

    public void setNumberOfPlayers(int n){
        this.numberOfPlayers = n;
    }

    public void setPot(int pot){
        this.pot = pot;
    }

    public void setCurrentBet(int currentBet){
        this.currentBet = currentBet;
    }

    public void setDeck(Deck deck){
        this.deck = deck;
    }

    public void setPlayers(Player[] players){
        this.players = players;
    }

    public void displayStatus(){
        System.out.println("Community Hand   : " + this.communityHand.show());
        System.out.println("Current bet      : " + currentBet);
        System.out.println("Pot              : " + pot);
    }

    /* Deals 2 cards to each player's hand.*/
    public void dealPlayers(){
        for(int playerIndex = 0; playerIndex < this.numberOfPlayers; playerIndex++ ) {
            Hand tempHand = new Hand();
            for(int i = 0; i<2; i++) {
                this.deck.dealTo(tempHand);
            }
            this.players[playerIndex].setHand(tempHand);
        }
    }

    public void dealCommunity(){
        this.deck.dealTo(this.communityHand);
    }

    public void playerTurn(int index){
//      check if player has folded
        if(this.players[index].hasFolded) return;

        this.players[index].setChoice(0);
        System.out.println();
        System.out.println("!!!PLAYER " + this.players[index].name + "'s TURN!!!");
        System.out.println("Community hand   : " + communityHand.show());
        System.out.println("Current bet      : " + currentBet);
        System.out.println("Pot              : " + pot);
        System.out.println("Player " + this.players[index].name + "'s hand  : "
                + this.players[index].hand.show());
        System.out.println("Player " + this.players[index].name + "'s money : "
                + this.players[index].money);
        while(this.players[index].choice == 0) {
            System.out.println("(1: raise, 2: call, 3: fold)");
            System.out.print("Player " + this.players[index].name + "'s move  : ");
            this.players[index].setChoice(scanner.nextInt());
            switch (this.players[index].choice) {
                case 1: {
                    System.out.print("Raise by: ");
                    int raise = scanner.nextInt();
                    if (this.players[index].money < currentBet + raise) {
                        System.out.println("Insufficient money to raise.");
                        this.players[index].setChoice(0);
                        break;
                    }
                    currentBet = currentBet + scanner.nextInt();
                    pot = pot + currentBet;
                    this.players[index].setMoney(this.players[index].money - currentBet);
                    break;
                }
                case 2: {
                    if (this.players[index].money < currentBet) {
                        System.out.println("Insufficient money to call.");
                        this.players[index].setChoice(0);
                        break;
                    }
                    pot = pot + currentBet;
                    this.players[index].setMoney(this.players[index].money - currentBet);
                    System.out.println("Called.");
                    break;
                }
                case 3: {
                    this.players[index].setFolded(true);
                    System.out.println("Folded.");
                    break;
                }
                default: {
                    System.out.println("Invalid input.");
                    this.players[index].setChoice(0);
                }
            }
        }


    }

    /*Check if after a turn proceeds to the next turn (no extra community card dealt)
    or next round (deal one more to community hand). True if everyone has either called or folded*/
    public boolean nextRound(){
        int numberFolded = 0;
        int numberCalled = 0;
        for(Player player:this.players){
            if(player.choice == 3) numberFolded++;
            else if(player.choice == 2) numberCalled++;
        }
        if(this.numberOfPlayers == numberCalled + numberFolded) return true;
        else return false;
    }

    public boolean checkFolded(){
        int numberFolded = 0;
        for(Player player:this.players){
            if(player.choice == 3) numberFolded++;
        }
        if(this.numberOfPlayers == numberFolded) return true;
        else return false;
    }




}
