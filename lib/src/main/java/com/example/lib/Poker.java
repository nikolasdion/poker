package com.example.lib;
import java.util.*;

public class Poker {




    public static void main(String[] args){


        Game game = new Game(2,100);

        while(game.getIsPlaying()){
            game.reset();

            game.dealPlayers();
            game.dealCommunity();
            game.dealCommunity();
            game.dealCommunity();
            game.displayStatus();

            /*BETTING STAGE*/

            game.bettingStage();

            /*SHOWDOWN (if needed)*/

            if(game.getShowdown()) game.showdown();

            game.reward();

            game.cont();

        }






















    }
}
