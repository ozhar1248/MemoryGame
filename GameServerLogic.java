package MemoryGame;

import java.util.Random;

public class GameServerLogic {
    private int[] board;
    private Player[] players;
    private int lastCardIndex;

    public GameServerLogic(int size, Player[] players) {
        board = new int[size];
        resetBoard();
        shuffle();
        printBoard();
        this.players = players;
        lastCardIndex = -1;
        players[0].send(ProtocolWithClient.changeTurn().toString());
    }

    private void resetBoard() {
        for (int i=0; i<board.length/2; ++i) {
            board[2*i] = i;
            board[2*i+1] = i;
        }
    }

    private void swap(int i, int j) {
        int temp = board[i];
        board[i] = board[j];
        board[j] = temp;
    }

    private void shuffle() {
        Random rand = new Random();
        int length = board.length;

        for (int i = length; i>1; --i) {
            int randNum = rand.nextInt(i);
            swap(i-1,randNum);
        }
    }

    private void printBoard() {
        String s = "";
        for (int i=0; i<board.length; ++i) {
            s += board[i]+" ";
        }
        System.out.println(s);
    }

    private int getValue(int card) {
        return board[card];
    }

    public void cardChosen(int idPlayer, int cardIndex) {
        //lock
        if (cardIndex == lastCardIndex) { //not necessery
            return;
        }
        if (lastCardIndex == -1) {
            for (int i=0; i<players.length; ++i) {
                players[i].send(ProtocolWithClient.flipCard(cardIndex,getValue(cardIndex)).toString());
            }
            lastCardIndex = cardIndex;

            return;
        }
        if (getValue(cardIndex) == getValue(lastCardIndex)) {
            for (int i=0; i<players.length; ++i) {
                players[i].send(ProtocolWithClient.flipCardsPermanently(cardIndex,lastCardIndex,getValue(cardIndex)).toString());
            }
            lastCardIndex = -1;
            return;
        }
        //in case of wrong choice
        for (int i=0; i<players.length; ++i) {
            players[i].send(ProtocolWithClient.flipCard(cardIndex,getValue(cardIndex)).toString());
        }
        lastCardIndex = -1;
        players[nextIndexPlayer(indexOfPlayer(idPlayer))].send(ProtocolWithClient.changeTurn().toString());
    }

    private int indexOfPlayer(int idPlayer) {
        for (int i=0; i<players.length; ++i) {
            if (players[i].getId() == idPlayer) return i;
        }
        return -1;
    }

    private int nextIndexPlayer(int index) {
        return (index+1)%players.length;
    }

}