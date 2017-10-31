package com.example.lib;

/**
 * A poker game. Number of players and the initial money of each player can be changed.
 *
 * The game starts with dealing 3 cards to community hand and 2 cards to each player's hand. The
 * betting stage commences after that, the deck dealing a card to community hand after each round.
 * Showdown is triggered if no winner has been declared at the end of the betting stage. Money is
 * then rewarded to the winner. If the players wish to continue, a new game is started (players
 * retain their wins and losses from the previous game).
 */

public class Poker {

    public static void main(String[] args) {

        /* Game settings. */
        int numberOfPlayers = 3;
        int initialMoney = 1000;

        /* Game starts here. */
        Game game = new Game(numberOfPlayers,initialMoney);

        while (game.isPlaying()) {
            game.reset();

            /* Deal cards to players and to community hand. */
            game.dealPlayers();
            game.dealCommunity();
            game.dealCommunity();
            game.dealCommunity();
            game.displayStatus();

            /* Betting stage. */
            game.bettingStage();

            /* Showdown (if needed). */
            if (game.getShowdown()) {
                game.showdown();
            }

            /* Reward winner with money in the pot. */
            game.reward();

            /* Ask if players want to play another game. */
            game.cont();

        }

        /* Display players' money at the end of the game.  */
        game.statusEnd();
    }
}
