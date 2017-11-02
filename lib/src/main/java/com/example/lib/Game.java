package com.example.lib;
import java.util.Scanner;

/**
 * The main building blocks of a poker game. It contains parameters which are modified through its
 * functions. Most functions modify only the state of the game. The game is initialised and its flow
 * dictated in the Poker class.
 */

public class Game {

    /** Variables that do not change between games. */
    Scanner mScanner = new Scanner( System.in );
    private int mNumberOfPlayers;
    private Player[] mPlayers;
    private boolean mIsPlaying = true; // Whether players want to continue playing

    /** Variables that reset every game. */
    private int mPot;
    private int mCurrentBet; // Current bet (if player checks, her bet is matched with this value)
    private Deck mDeck;
    private Hand mCommunityHand = new Hand();
    private boolean mShowdown = false; // Whether showdown is required at the end
    private int mWinner; // Index of the winner, set to -1 on construction

    /**
     * Initialise game, creating array of players and shuffled deck,
     * setting pot and currentBet to 0.
     * @param numberOfPlayers number of players in the game
     * @param initialMoney initial money that each player has
     */
    Game(int numberOfPlayers, int initialMoney) {
        mNumberOfPlayers = numberOfPlayers;
        mPlayers = new Player[numberOfPlayers];
        mIsPlaying = true;

        /* Create Player objects in mPlayers array. */
        for (int playerIndex = 0; playerIndex < mNumberOfPlayers; playerIndex++) {
            mPlayers[playerIndex] = new Player(initialMoney, String.valueOf(playerIndex + 1));
        }

        reset();
    }

    /* GETTER METHODS */

    public boolean isPlaying() {
        return mIsPlaying;
    }

    public boolean getShowdown() {
        return mShowdown;
    }

    /* METHODS */

    /**
     *  Prints game status: community hand, current bet, and pot.
     */
    public void displayStatus() {
        System.out.println("Community Hand   : " + mCommunityHand.show());
        System.out.println("Current bet      : " + mCurrentBet);
        System.out.println("Pot              : " + mPot);
    }

    /** Deal 2 cards to each player's hand.*/
    public void dealPlayers(int numberOfCards) {
        for (Player player : mPlayers) {
            for (int ii = 0; ii < numberOfCards; ii++) {
                mDeck.dealTo(player.getHand());
            }
        }
        System.out.println(numberOfCards + " card(s) were dealt to each player.");
    }

    /** Deal a card to community hand. */
    public void dealCommunity(int numberOfCards) {
        for(int ii = 0; ii < numberOfCards; ii++){
            mDeck.dealTo(mCommunityHand);
        }
        System.out.println(numberOfCards + " card(s) were dealt to community hand.");
    }

    /**
     * The betting stage of the game, consisting of maximum 3 rounds of 3 turns each.
     * This ends after either a winner has been declared or the maximum number of rounds has
     * been played
     */
    public void bettingStage() {
        for (int round = 1; round < 4; round++) {

            resetChoices();

            bettingRound();

            /* End betting stage if there is already a winner. */
            if (mWinner != -1) {
                return;
            }

            System.out.println();
            System.out.println("Round has ended.");

            /* Deal one card to community hand after the end of each round except the final one. */
            if (round <3) {
                dealCommunity(1);
            }

            displayStatus();
            resetChoices(); // Folded players are still recorded in hasFolded attribute

            /* Set showdown if no winner has been decided after the third round. */
            if (round == 3) {
                mShowdown = true;
            }
        }
    }

    /**
     * A betting round. Each round consists of at most 3 turns.
     * */
    public void bettingRound() {
        int noOfRaises = 0;

        while (noOfRaises < 4) {

            /* Loop through all players. */
            for (int index = 0; index < mNumberOfPlayers; index++) {

                /* Check that the player has not folded. */
                if (!mPlayers[index].hasFolded()){

                    /* End betting round and declare winner if everyone but current player has folded. */
                    if (checkOthersFolded(index)) {
                        mWinner = index;
                        return;
                    }

                    /* End betting round everyone else has either called or folded EXCEPT during
                     * the first turn. */
                    if (checkOthersCalledFolded(index)
                            && (mPlayers[index].getChoice() != Player.Choice.UNDECLARED) ) { // Choice is set to 0 at the start of every round.
                        return;
                    }

                    /* Initiate the current player's turn.*/
                    System.out.println();
                    displayStatus();
                    int raise = mPlayers[index].turn(mCurrentBet, noOfRaises);
                    System.out.println();

                    /*If player raises, change the status of current raiser to this player
                     * and track number of raises. Maximum number of raises per round is 3.*/
                    if (raise > 0) {
                        mCurrentBet = mPlayers[index].getBet();
                        noOfRaises++;
                    } else if(raise == -1){
                        mPot += mPlayers[index].getBet();
                        mPlayers[index].setBet(0);
                    }
                }
            }
        }
    }

    /** Reset the choice parameter of players after each round of betting, except for FOLD. */
    public void resetChoices() {
        for (Player player : mPlayers) {
            if(!player.hasFolded()) {
                player.setChoice(Player.Choice.UNDECLARED);
            }
        }
    }

    /** Compare remaining players' hands. Executed if no winner is announced after betting stage. */
    public void showdown() {
        int highest = 0;
        System.out.println();
        System.out.println("!!! SHOWDOWN !!!");

        /* Loop through every player who has not folded. */
        for (int index = 0; index < mNumberOfPlayers; index++) {

            /* Check that player has not folded. */
            if (!mPlayers[index].hasFolded()) {

                /* Create a hand which contains both the player's hand and community hand. */
                Hand tempHand = mPlayers[index].getHand();
                for (Card communityCard : mCommunityHand.getCards()) {
                    tempHand.add(communityCard);
                }

                /* Select the best 5 card hand out of the available combination. */
                Hand bestHand = tempHand.getBestHand();
                System.out.println("Player " + mPlayers[index].getName() + "'s best hand: "+ bestHand.show());
                System.out.println("Player " + mPlayers[index].getName() + "'s absolute : "+ bestHand.absoluteRank());
                System.out.println(bestHand.getType());
                System.out.println();

                /* If the best hand of current player is higher than the current highest,
                 * set winner to current player and highest rank to current hand. */
                if (bestHand.absoluteRank() > highest) {
                    mWinner = index;
                    highest = bestHand.absoluteRank();
                }
            }
        }
    }

    /**
     *  Check if everyone else has folded except the player whose index is the parameter.
     *  Checked at the start of every player's turn, and if this is true, then current player is
     *  the winner.
     */
    public boolean checkOthersFolded(int index) {
        int numberFolded = 0;

        /* Loop through every player except current player. */
        for (Player player : mPlayers) {

            /* Only add to counter if player*/
            if (player != mPlayers[index]){
                if (player.hasFolded()) {
                    numberFolded++;
                }
            }
        }

        /* If everyone has folded but one (i.e. current player), return true. */
        return (mNumberOfPlayers == (numberFolded + 1) );
    }

    /* Check if everyone has either called or folded. Used at the start of a player's turn to ensure
     * when a one round has been completed and no one raises, the round ends.*/
    public boolean checkOthersCalledFolded(int index) {
        int countFolded = 0;
        int countCalled = 0;

        for (Player player : mPlayers) {
            if (player != mPlayers[index]) {
                if (player.hasFolded()) {
                    countFolded++;
                } else if (player.hasCalled()) {
                    countCalled++;
                }
            }
        }

        return ( (mNumberOfPlayers - 1) == (countCalled + countFolded) );
    }

    /** Reward winner of current game with money in the pot. */
    public void reward() {

        /* Empty every player's bet into the pot (later given to the winner). */
        for (Player player : mPlayers) {
            mPot += player.getBet();
            player.setBet(0);
        }

        System.out.println();
        System.out.println("!!!!!!THE GAME HAS ENDED!!!!!");
        System.out.println("The winner is Player " + mPlayers[mWinner].getName());
        System.out.println(mPot + " has been added to Player " + mPlayers[mWinner].getName());
        mPlayers[mWinner].setMoney(mPlayers[mWinner].getMoney() + mPot);
        mPot = 0;

        /* Display everyone's money after the winner has been rewarded. */
        System.out.println();
        for (Player player : mPlayers) {
            System.out.println("Player " + player.getName() + "'s money : "  + player.getMoney());
        }
        System.out.println();
    }

    /** Setup for a new game after continuing. */
    public void reset() {
        /* Reset game variables. */
        resetChoices();
        mWinner = -1;
        mShowdown = false;
        mCurrentBet = 0 ;
        mPot = 0;

        /* Create a new deck and empty the players' and community hands. */
        mDeck = new Deck();
        mDeck.shuffle();

        for (Player player : mPlayers) {
            player.setHand(new Hand());
            player.setChoice(Player.Choice.UNDECLARED);
        }

        mCommunityHand = new Hand();
    }

    /** Checks whether players want to continue playing after someone wins current game. */
    public void cont() {
        int cont = 0;

        /* Loop until player inputs acceptable response. */
        while (cont == 0) {
            System.out.println("Do you want to continue? (1: yes, 2: no)");
            cont = mScanner.nextInt();

            /* Update mIsPlaying based on user's input. Force user to choose again if input was neither 1 nor 2. */
            switch (cont) {
                case 1:
                    mIsPlaying = true;
                    break;
                case 2:
                    mIsPlaying = false;
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

        for (Player player : mPlayers) {
            System.out.println("Player " + player.getName() + "'s money : " + player.getMoney());
        }

        System.out.println("Thank you for playing!");
    }

}
