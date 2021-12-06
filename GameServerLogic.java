package MemoryGame;

import java.util.Arrays;
import java.util.Random;

public class GameServerLogic {
    private final int[] board;
    private final Player[] players;
    private int lastCardIndex;
    private final int[] points;
    private int totalPoints;
    private boolean exited;

    public GameServerLogic(int level, Player[] players) {
        this.players = players;
        int size = MemoryGame.Sizes[level]*MemoryGame.Sizes[level];
        board = new int[size];
        points = new int[players.length];
        lastCardIndex = -1;
        totalPoints = 0;

        resetBoard();
        shuffle();
        resetPoints();
        informNames();
        giveFirstTurn();
    }

    private void giveFirstTurn() {
        players[0].send(SenderToClient.turnOn());
    }

    private void informNames() {
        String[] names = new String[players.length];
        for (int i=0; i<players.length; ++i) {
            names[i] = players[i].getName();
        }
        for (int i=0; i<players.length; ++i) {
            players[i].send(SenderToClient.informNames(names));
        }
    }

    private void resetPoints() {
        Arrays.fill(points,0);
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

    public void cardChosen(int idPlayer, int cardIndex) {
        int indexPlayer = indexOfPlayer(idPlayer);

        if (lastCardIndex == -1) {
            for (int i=0; i<players.length; ++i) {
                players[i].send(SenderToClient.flipCard(cardIndex,board[cardIndex]));
            }
            lastCardIndex = cardIndex;
            return;
        }

        if (board[cardIndex] == board[lastCardIndex]) {
            points[indexPlayer]++;
            for (int i=0; i<players.length; ++i) {
                players[i].send(SenderToClient.flipCardsPermanently(cardIndex,lastCardIndex,board[cardIndex],indexPlayer));
            }
            totalPoints++;
            if (totalPoints == board.length/2) {
                exit(-1);
            }
            lastCardIndex = -1;
            return;
        }

        //in case of wrong choice
        for (int i=0; i<players.length; ++i) {
            players[i].send(SenderToClient.flipCard(cardIndex,board[cardIndex]));
        }
        lastCardIndex = -1;
        players[nextIndexPlayer(indexPlayer)].send(SenderToClient.turnOn());
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

    public void exit(int id) {
        if (exited) {
            return;
        }
        if (id >= 0) {
            informQuitter(id);
        }
        exited = true;
    }

    private void informQuitter(int id) {
        for (int i=0; i<players.length; ++i) {
            if (players[i].getId() == id) continue;
            players[i].send(SenderToClient.exit(indexOfPlayer(id)));
        }
    }

    public boolean isFinished() {
        return exited;
    }
}
