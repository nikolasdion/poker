package com.example.lib;

import java.util.Scanner;

/**
 * Created by NDS on 27/10/2017.
 */

public class Game {
    /*variables that do not change between games*/

    public int numberOfPlayers;
    public Player[] players;
    public boolean isPlaying = true;

    /*variables that reset every game*/
    public int pot;
    public int currentBet;
    public Deck deck;
    public Hand communityHand = new Hand();
    Scanner scanner = new Scanner( System.in );
    public boolean showdown = false;
    public int winner;


    /*Initialise game, creating array of players and shuffled deck,
    setting pot and currentBet to 0*/
    Game(int numberOfPlayers, int initialMoney){
        setNumberOfPlayers(numberOfPlayers);
        setPlayers(new Player[numberOfPlayers]);
        for(int i = 0; i < this.numberOfPlayers; i++){
            this.players[i] = new Player (initialMoney, String.valueOf(i+1));
        }
        reset();
    }

    public void setNumberOfPlayers(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setPot(int pot){
        this.pot = pot;
    }

    public void setCurrentBet(int currentBet){
        this.currentBet = currentBet;
    }

    public void setWinner(int winner){
        this.winner = winner;
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

        players[index].setChoice(0);
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
                    if (this.players[index].money < this.currentBet + raise) {
                        System.out.println("Insufficient money to raise.");
                        this.players[index].setChoice(0);
                        break;
                    }
                    this.setCurrentBet(this.currentBet + raise);
                    this.setPot(pot + this.currentBet);
                    this.players[index].setMoney(this.players[index].money - this.currentBet);
                    break;
                }
                case 2: {
                    if (this.players[index].money < this.currentBet) {
                        System.out.println("Insufficient money to call.");
                        this.players[index].setChoice(0);
                        break;
                    }
                    this.setPot(this.pot + this.currentBet);
                    this.players[index].setMoney(this.players[index].money - this.currentBet);
                    System.out.println("Called.");
                    break;
                }
                case 3: {
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
        for(Player player:players){
            if(player.choice == 3) numberFolded++;
            else if(player.choice == 2) numberCalled++;
        }
        if(numberOfPlayers == (numberCalled + numberFolded + 1)) {return true;
        }
        //checked at the start of every player's turn, in case a full cycle has been completed and/*
            // everyone has called/folded. -1 because the current player would've been the one raised*/
        else return false;
    }

    /*Check if everyone has folded (used at the start of every playerturn)*/
    public boolean checkFolded(){
        int numberFolded = 0;
        for(Player player:players){
            if(player.choice == 3) numberFolded++;
        }
        if(numberOfPlayers == numberFolded + 1) return true;
        else return false;
    }

    public void resetChoices(){
        for(Player player:players){
            player.setChoice(0);
        }
    }

    public void bettingStage(){
        for(int round = 1; round < 4; round++){

            thisRound: //break if everyone has called or folded
            for(int turn = 1; turn < 4; turn++){



                for(int index = 0; index < numberOfPlayers; index++){
                    if(players[index].choice == 3) return; // skip player if she has folded

                    if(checkFolded()){
                        winner = index;
                        return; // end betting stage if every player but one has folded
                    }

                    if(nextRound()) break thisRound; // if everyone has called/folded but one, proceed to next round


                    playerTurn(index);
                }

            }

            dealCommunity(); //deal one card to community hand after the end of each round

            System.out.println();
            System.out.println("Round has ended. Another card dealt to community hand.");
            displayStatus();
            resetChoices(); //reset everyone's choice to 0

            if(round == 3) showdown = true;


        }
    }

    public void showdown(){
        int highest = 0;
        System.out.println("SHOWDOWN");
        for(int index = 0; index < numberOfPlayers; index++) {
            System.out.println("Player abs: "+ players[index].hand.absoluteRank());
            if (players[index].hand.absoluteRank() > highest) {
                winner = index;
            }
        }
    }

    public void reward(){
        System.out.println("WINNER" + winner);
        System.out.println("The winner is Player " + players[winner].name);
        players[winner].setMoney(players[winner].money + pot);
    }

    public void reset(){
        resetChoices();
        setWinner(-1);
        showdown = false;
        setCurrentBet(0);
        setPot(0);

        Deck tempDeck = new Deck();
        tempDeck.shuffle();
        setDeck(tempDeck);

        Hand emptyHand = new Hand();
        for(Player player:players){
            player.setHand(emptyHand);
            player.setChoice(0);
        }

        communityHand = emptyHand;
    }

    public void cont(){
        int cont = 0;
        while(cont == 0) {
            System.out.println("Do you want to continue? (1: yes, 2: no)");
            cont = scanner.nextInt();
            switch (cont) {
                case 1:
                    isPlaying = true;
                    break;
                case 2:
                    isPlaying = false;
                    break;
                default:
                    System.out.println("Invalid input.");
                    cont = 0;

            }
        }
    }




}
