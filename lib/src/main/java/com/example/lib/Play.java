package com.example.lib;

public class Play {
    public static void main(String[] args){
        Card card = new Card(1,2);
        System.out.println(card.strCard());
        Card[] hand1 = {card,card};
        Player player1 = new Player( 100, hand1 ) ;
        player1.displayHand();
    }
}
