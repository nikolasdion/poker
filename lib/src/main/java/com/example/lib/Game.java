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
            Hand tempHand = new Hand();
            for (int ii = 0; ii < numberOfCards; ii++) {
                mDeck.dealTo(tempHand);
            }
            player.setHand(tempHand);
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
        int currentRaiser = -1;
        int noOfRaises = 0;

        while (noOfRaises < 4) {
            for (int index = 0; index < mNumberOfPlayers; index++) {

                /* End betting round (and betting stage) if every player but one has folded. */
                if (checkFolded()) {
                    mWinner = index;
                    return;
                }

                /* End betting round if everyone has called or folded. */
                if (currentRaiser == index || checkCalledFolded()) {
                    return;
                }

                /* Initiate the current player's turn. */
                playerTurn(index, noOfRaises);

                /*If player raises, change the status of current raiser to this player
                 * and track number of raises. Maximum number of raises per round is 3.*/
                if (mPlayers[index].getChoice()== 1) {
                    currentRaiser = index;
                    noOfRaises ++;

            }
            }

        }
    }

    /**
     * A player's turn. At each turn, a player can either raise, check, or fold.
     * @param index index of player whose turn is currently at play
     * @param noOfRaises no of raises in the round so far
     */
    public void playerTurn(int index, int noOfRaises) {

        /* Skip player if she has folded. */
        if (mPlayers[index].hasFolded()) {
            return;
        }

        /* Set player's choice to 0. */
        mPlayers[index].setChoice(0);

        /* Display status of the game for player's information. */
        System.out.println();
        System.out.println("!!!PLAYER " + mPlayers[index].getName() + "'s TURN!!!");
        System.out.println("Community hand   : " + mCommunityHand.show());
        System.out.println("Current game bet : " + mCurrentBet);
        System.out.println("Pot              : " + mPot);
        System.out.println("Player " + mPlayers[index].getName() + "'s hand  : "
                + mPlayers[index].getHand().show());
        System.out.println("Player " + mPlayers[index].getName() + "'s money : "
                + mPlayers[index].getMoney());
        System.out.println("Player " + mPlayers[index].getName() + "'s bet : "
                + mPlayers[index].getBet());

        /* Player makes a choice. */
        while (mPlayers[index].getChoice() == 0) {
            System.out.println("(1: raise, 2: call, 3: fold)");
            System.out.print("Player " + mPlayers[index].getName() + "'s move  : ");
            mPlayers[index].setChoice(mScanner.nextInt());

            switch (mPlayers[index].getChoice()) {

                /* Player raises.*/
                case 1:

                    /* Give error message and force player to choose again if there has been 3 raises in a round. */
                    if (noOfRaises > 2) {
                        System.out.println("Cannot raise further in this round.");
                        mPlayers[index].setChoice(0);
                        break;
                    }

                    /* Get the amount by which the player wishes to raise. */
                    System.out.print("Raise by         : ");
                    int raise = mScanner.nextInt();

                    /* Give error message and force player to choose again if she has insufficient money. */
                    if (mPlayers[index].getMoney() + mPlayers[index].getBet() < mCurrentBet + raise) {
                        System.out.println("Insufficient money to raise.");
                        mPlayers[index].setChoice(0);
                        break;
                    }

                    /* If raise was successful, update game and player variables. */
                    mCurrentBet = mCurrentBet + raise;
                    mPlayers[index].setMoney(mPlayers[index].getMoney() + mPlayers[index].getBet() - mCurrentBet );
                    mPlayers[index].setBet(mCurrentBet);
                    noOfRaises++;
                    break;

                /* Player checks. */
                case 2:

                    /* Give error message and force player to choose again if she has insufficient money. */
                    if (mPlayers[index].getMoney() + mPlayers[index].getBet() < mCurrentBet) {
                        System.out.println("Insufficient money to call.");
                        mPlayers[index].setChoice(0);
                        break;
                    }

                    /* If check was successful, update game and player variables. */
                    mPlayers[index].setMoney(mPlayers[index].getMoney() + mPlayers[index].getBet()- mCurrentBet);
                    mPlayers[index].setBet(mCurrentBet);
                    System.out.println("Called.");
                    break;

                /* Player folds. */
                case 3:
                    mPot = mPot + mPlayers[index].getBet();
                    mPlayers[index].setBet(0);
                    mPlayers[index].setFolded(true);
                    System.out.println("Folded.");
                    break;

                default:
                    System.out.println("Invalid input.");
                    mPlayers[index].setChoice(0);

            }
        }


    }

    /** Reset the choice parameter of players after each round of betting. */
    public void resetChoices() {
        for (Player player : mPlayers) {
            player.setChoice(0);
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
            if (mPlayers[index].hasFolded() == false) {

                /* Create a hand which contains both the player's hand and community hand. */
                Hand tempHand = mPlayers[index].getHand();
                for (Card communityCard : mCommunityHand.getCards()) {
                    tempHand.add(communityCard);
                }

                /* Select the best 5 card hand out of the available combination. */
                Hand bestHand = tempHand.getBestHand();
                System.out.println("Player " + mPlayers[index].getName() + "'s best hand: "+ bestHand.show());
                System.out.println("Player " + mPlayers[index].getName() + "'s absolute : "+ bestHand.absoluteRank());

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
     * Check if everyone has either called or folded.
     * Checked at the start of every player's turn, in case a full cycle has been completed and
     * everyone has called/folded.
     */
    public boolean checkCalledFolded() {
        int numberFolded = 0;
        int numberCalled = 0;

        /* Loop through every player. */
        for (Player player : mPlayers) {
            if (player.hasFolded()) {
                numberFolded++;
            } else if (player.getChoice() == 2) {
                numberCalled++;
            }
        }

        /* If everyone has either called or folded, return true. */
        if (mNumberOfPlayers == (numberCalled + numberFolded)) {
            return true;
        } else {
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

        /* Loop through every player. */
        for (Player player : mPlayers) {
            if (player.hasFolded()) {
                numberFolded++;
            }
        }

        /* If everyone has folded but one (i.e. current player), return true. */
        if (mNumberOfPlayers == numberFolded + 1) {
            return true;
        } else {
            return false;
        }
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
        Deck tempDeck = new Deck();
        tempDeck.shuffle();
        mDeck = tempDeck;

        Hand emptyHand = new Hand();
        for (Player player : mPlayers) {
            player.setHand(emptyHand);
            player.setChoice(0);
            player.setFolded(false);
        }

        mCommunityHand = emptyHand;
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
