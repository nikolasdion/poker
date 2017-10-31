package com.example.lib;
import java.util.Scanner;

/**
 * The main building blocks of a poker game. It contains parameters which are modified through its
 * functions. Most functions modify only the state of the game. The game is initialised and its flow
 * dictated in the Poker class.
 */

public class Game {

    /** Variables that do not change between games. */
    Scanner scanner = new Scanner( System.in );
    private int numberOfPlayers;
    private Player[] players;
    private boolean isPlaying = true; //whether players want to continue playing

    /** Variables that reset every game. */
    private int pot;
    private int currentBet; //Current bet (if player checks, her bet is matched with this value)
    private Deck deck;
    private Hand communityHand = new Hand();
    private boolean showdown = false; // Whether showdown is required at the end
    private int winner; // Index of the winner, set to -1 on construction

    /**
     * Initialise game, creating array of players and shuffled deck,
     * setting pot and currentBet to 0.
     * @param numberOfPlayers number of players in the game
     * @param initialMoney initial money that each player has
     */
    Game(int numberOfPlayers, int initialMoney) {
        this.numberOfPlayers = numberOfPlayers;
        this.players = new Player[numberOfPlayers];
        isPlaying = true;
        for (int i = 0; i < this.numberOfPlayers; i++) {
            this.players[i] = new Player (initialMoney, String.valueOf(i+1));
        }
        reset();
    }

    /* GETTER METHODS */

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean getShowdown() {
        return showdown;
    }


    /* METHODS */

    /**
     *  Prints game status: community hand, current bet, and pot.
     */
    public void displayStatus() {
        System.out.println("Community Hand   : " + communityHand.show());
        System.out.println("Current bet      : " + currentBet);
        System.out.println("Pot              : " + pot);
    }

    /** Deal 2 cards to each player's hand.*/
    public void dealPlayers() {
        for (Player player:players) {
            Hand tempHand = new Hand();
            for (int i = 0; i<2; i++) {
                deck.dealTo(tempHand);
            }
            player.setHand(tempHand);
        }
        System.out.println("Two cards were dealt to each player.");
    }

    /** Deal a card to community hand. */
    public void dealCommunity() {
        deck.dealTo(communityHand);
        System.out.println("A card was dealt to community hand.");
    }

    /**
     * The betting stage of the game, consisting of maximum 3 rounds of 3 turns each.
     * This ends after either a winner has been declared or the maximum number of rounds has
     * been played
     */
    public void bettingStage() {
        for (int round = 1; round < 4; round++) {

            bettingRound();

            if (winner != -1) {
                return; //end betting stage if there is already a winner
            }

            System.out.println();
            System.out.println("Round has ended.");

            if (round <3) {
                dealCommunity(); //deal one card to community hand after the end of each round
            }
            displayStatus();
            resetChoices(); // folded players are still recorded in hasFolded attribute

            if (round == 3) {
                showdown = true;
            }
        }
    }

    /**
     * A betting round. Each round consists of at most 3 turns.
     * */
    public void bettingRound() {
        int currentRaiser = -1;
        int noOfRaises = 0;

        while (noOfRaises < 4) {
            for (int index = 0; index < numberOfPlayers; index++) {
                /* End betting round (and betting stage) if every player but one has folded. */
                if (checkFolded()) {
                    winner = index;
                    return;
                }

                /* End betting round if everyone has called or folded. */
                if (currentRaiser == index || checkCalledFolded()) {
                    return;
                }

                playerTurn(index, noOfRaises);
                /*If player raises, change the status of current raiser to this player
                 * and track number of raises. Maximum number of raises per round is 3.*/
                if (players[index].getChoice()== 1) {
                    currentRaiser = index;
                    noOfRaises ++;

            }
            }

        }
    }

    /**
     * A player's turn. At each player
     */
    public void playerTurn(int index, int noOfRaises) {
        /* Skip player if she has folded. */
        if (players[index].hasFolded()) {
            return;
        }

        /* Set player's choice to 0. */
        players[index].setChoice(0);

        /* Display status of the game for player's information. */
        System.out.println();
        System.out.println("!!!PLAYER " + players[index].getName() + "'s TURN!!!");
        System.out.println("Community hand   : " + communityHand.show());
        System.out.println("Current game bet : " + currentBet);
        System.out.println("Pot              : " + pot);
        System.out.println("Player " + players[index].getName() + "'s hand  : "
                + players[index].getHand().show());
        System.out.println("Player " + players[index].getName() + "'s money : "
                + players[index].getMoney());
        System.out.println("Player " + players[index].getName() + "'s bet : "
                + players[index].getBet());

        /* Player makes a choice. */
        while (players[index].getChoice() == 0) {
            System.out.println("(1: raise, 2: call, 3: fold)");
            System.out.print("Player " + players[index].getName() + "'s move  : ");
            players[index].setChoice(scanner.nextInt());
            switch (players[index].getChoice()) {
                /* Player raises.*/
                case 1:
                    if (noOfRaises >2) {
                        System.out.println("Cannot raise further in this round.");
                        players[index].setChoice(0);
                        break;
                    }
                    System.out.print("Raise by: ");
                    int raise = scanner.nextInt();
                    if (players[index].getMoney() + players[index].getBet() < currentBet + raise) {
                        System.out.println("Insufficient money to raise.");
                        players[index].setChoice(0);
                        break;
                    }
                    currentBet = currentBet + raise;
                    players[index].setMoney(players[index].getMoney() + players[index].getBet() - currentBet );
                    players[index].setBet(currentBet);
                    noOfRaises++;
                    break;

                /* Player checks. */
                case 2:
                    if (players[index].getMoney() + players[index].getBet() < currentBet) {
                        System.out.println("Insufficient money to call.");
                        players[index].setChoice(0);
                        break;
                    }
                    players[index].setMoney(players[index].getMoney() + players[index].getBet()- currentBet);
                    players[index].setBet(currentBet);
                    System.out.println("Called.");
                    break;

                /* Player folds. */
                case 3:
                    pot = pot + players[index].getBet();
                    players[index].setBet(0);
                    players[index].setFolded(true);
                    System.out.println("Folded.");
                    break;

                default:
                    System.out.println("Invalid input.");
                    players[index].setChoice(0);

            }
        }


    }

    /** Reset the choice parameter of players after each round of betting. */
    public void resetChoices() {
        for (Player player:players) {
            player.setChoice(0);
        }
    }

    /** Compare remaining players' hands. Executed if no winner is announced after betting stage. */
    public void showdown() {
        int highest = 0;
        System.out.println();
        System.out.println("!!! SHOWDOWN !!!");
        for (int index = 0; index < numberOfPlayers; index++) {
            if (players[index].hasFolded() == false) {

                /* Create a hand which contains both the player's hand and community hand. */
                Hand tempHand = players[index].getHand();
                for (Card communityCard:communityHand.getCards()) {
                    tempHand.add(communityCard);
                }

                /* Select the best 5 card hand out of the available combination. */
                Hand bestHand = tempHand.getBestHand();
                System.out.println("Player " + players[index].getName() + "'s best hand: "+ bestHand.show());
                System.out.println("Player " + players[index].getName() + "'s absolute : "+ bestHand.absoluteRank());

                /* If the best hand of current player is higher than the current highest,
                 * set winner to current player and highest rank to current hand. */
                if (bestHand.absoluteRank() > highest) {
                    winner = index;
                    highest = bestHand.absoluteRank();
                }
            }
        }
    }

    /**
     * Check if everyone has either called or folded.
     * Checked at the start of every player's turn, in case a full cycle has been completed and
     * everyone has called/folded.
     */
    public boolean checkCalledFolded() {
        int numberFolded = 0;
        int numberCalled = 0;
        for (Player player:players) {
            if (player.hasFolded()) {
                numberFolded++;
            } else if (player.getChoice() == 2) {
                numberCalled++;
            }
        }
        if (numberOfPlayers == (numberCalled + numberFolded)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *  Check if everyone has folded.
     *  Checked at the start of every player's turn, and if this is true, then current player is
     *  the winner
     */
    public boolean checkFolded() {
        int numberFolded = 0;
        for (Player player:players) {
            if (player.hasFolded()) {
                numberFolded++;
            }
        }
        if (numberOfPlayers == numberFolded + 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /** Reward winner of current game with money in the pot. */
    public void reward() {
        /* Empty every player's bet into the pot (later given to the winner). */
        for (Player player:players) {
            pot += player.getBet();
            player.setBet(0);
        }

        System.out.println();
        System.out.println("!!!!!!THE GAME HAS ENDED!!!!!");
        System.out.println("The winner is Player " + players[winner].getName());
        System.out.println(pot + " has been added to Player " + players[winner].getName());
        players[winner].setMoney(players[winner].getMoney() + pot);
        pot = 0;
        System.out.println();
        for (Player player:players) {
            System.out.println("Player " + player.getName() + "'s money : "  + player.getMoney());
        }
        System.out.println();
    }

    /** Setup for a new game after continuing. */
    public void reset() {
        resetChoices();
        winner = -1;
        showdown = false;
        currentBet = 0 ;
        pot = 0;

        Deck tempDeck = new Deck();
        tempDeck.shuffle();
        deck = tempDeck;

        Hand emptyHand = new Hand();
        for (Player player:players) {
            player.setHand(emptyHand);
            player.setChoice(0);
            player.setFolded(false);
        }

        communityHand = emptyHand;
    }

    /** Checks whether players want to continue playing after someone wins current game. */
    public void cont() {
        int cont = 0;
        while (cont == 0) {
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

    /** Print everyone's money at the end of a game. */
    public void statusEnd() {
        System.out.println("!!!!!! GAME HAS ENDED !!!!!!");
        for (Player player:players) {
            System.out.println("Player " + player.getName() + "'s money : " + player.getMoney());
        }
        System.out.println("Thank you for playing!");
    }




}
