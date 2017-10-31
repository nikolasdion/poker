package com.example.lib;
import java.util.*;

public class Poker {

    public static void main(String[] args){

        /* Game setup. */
        int numberOfPlayers = 3;
        int initialMoney = 1000;


        Game game = new Game(numberOfPlayers,initialMoney);

        while(game.isPlaying()){
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
            if(game.getShowdown()) game.showdown();

            /* Reward winner with money in the pot. */
            game.reward();

            /* Ask if players want to play another game. */
            game.cont();

        }

        game.statusEnd();
    }
}
