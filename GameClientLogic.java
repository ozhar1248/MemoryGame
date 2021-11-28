package MemoryGame;

import javax.swing.*;
import java.util.concurrent.locks.ReentrantLock;

//problems:
// 1. you cant press 3 times or more if you press quickly - done
// 2. if card flipped you cant press it anymore - done
// 3. you cant press on the same card twice
// 4. only when 2 turns were activated, so the user can choose another two
// 5. if two cards were flipped it wont become dark again
// 6. message "wrong" will be shown only if he tried two turns
// 7. if you press first card, press second card, the first card is covered and the board is enabled and then you can press third card and then the second is covered
// PAY ATTENTION that we have 3 threads here- one from server, 2 from mouse-clicks and 3 from thread in "flipCard" function
public class GameClientLogic {
    private GameGUI game;
    private final ConnectionClient connection;
    private final ReentrantLock lock;
    private final boolean[] flipped;
    private boolean firstCardOn;
    //private boolean secondCardOn;
    private int firstCard;
    //private final int[] turnIndexes;
    private int countClicks;
    private boolean enableBoard;
    private boolean turn;
    //private int[] points;
    String[] names;
    private boolean exited = false;
    private MessagesDisplayer toWrite;
    GameClient client;
    int sizeRow;

    public GameClientLogic(GameClient client, ConnectionClient connection, String name, int level) { // why it needs to get this parameter anyway?
        this.connection = connection;
        this.client = client;
        lock = new ReentrantLock();
        sizeRow = MemoryGame.sizeOfRow(level);
        int size = sizeRow*sizeRow;
        flipped = new boolean[size];
        firstCardOn = false;
        for (int i=0; i<size; ++i) {
            flipped[i] = false;
        }
        disableBoard();

        connection.send(ProtocolWithServer.startGame(sizeRow, name).toString());
    }

    public void waitForOpponents() {
        game = new GameGUI(this, sizeRow);
        turn = false;
        game.writeMessage("Please wait for opponent");
    }

    public void startGame() {
        if (game == null) {
            game = new GameGUI(this, sizeRow);
            turn = false;
        }
        game.writeMessage("Game started. Wait for your turn");
    }

    public void turnOn() {
        if (game == null) {
            return;
        }
        lock.lock();
        try {
            assert(!isEnabled());
            turn = true;
            game.writeMessage("Its your turn. Please choose first card");
            resetTurn();
            enableBoard();
        }
        finally {
            lock.unlock();
        }
    }

    public void flipCard(int cardIndex, int value) {
        boolean isMyTurn = turn;
        game.flipCard(cardIndex, value);
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                lock.lock();
                try {
                    if (flipped[cardIndex]) {
                        return;
                    }
                    game.coverCard(cardIndex);
                    if (!isMyTurn) {
                        return;
                    }
                    if (firstCardOn) {
                        firstCardOn = false;
                        return;
                    }
                    failedTurn();
                }
                finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void flipCardPermanently(int cardIndex1, int cardIndex2, int cardValue) {
        lock.lock();
        try {
            flipped[cardIndex1] = true;
            flipped[cardIndex2] = true;
            game.flipCard(cardIndex1, cardValue);
            game.flipCard(cardIndex2, cardValue);
            if (!turn) {
                return;
            }
            resetTurn();
            game.writeMessage("Bingo!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.writeMessage("Please choose first card");
            enableBoard();
        }
        finally {
            lock.unlock();
        }
    }

    public void clickOnCard(int index) {
        if (!turn) {
            return;
        }
        boolean enable;
        lock.lock();
        try {
            enable = !flipped[index] && isEnabled()  && !isRepeatedIndex(index);
            if (enable) {
                countClicks++;
                if (countClicks == 2) {

                    disableBoard(); // is it nessesery?
                    game.writeMessage("");
                }
                if (countClicks == 1) {
                    firstCardOn = true;
                    firstCard = index;
                    game.writeMessage("Please select second card");
                }
            }
        }
        finally {
            lock.unlock();
        }
        if (enable) {
            connection.send(ProtocolWithServer.chooseCard(index).toString());
        }
    }

    private boolean isRepeatedIndex(int index) {
        return index == firstCard;
    }

    private void enableBoard() {
        enableBoard = true;
    }

    private void disableBoard() {
        enableBoard = false;
    }

    private boolean isEnabled() {
        return enableBoard;
    }

    private void resetTurn() {
        countClicks = 0;
        firstCard = -1;
    }

    private void failedTurn() {
        turn = false;
        game.writeMessage("wrong");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.writeMessage("Please wait for your turn");
    }

    public void updateNames(String[] names) {
//        points = new int[names.length];
//        for (int i=0; i<names.length; ++i){
//            points[i] = 0;
//        }
        this.names = names;
        game.createNames(names);
    }

    public void updatePoints(int index, int points) {
        game.updatePoints(index, points);
    }

    public void playersDisconnected(int index) {
        if (exited) return;
        turn = false;
        if (index==0) {
            game.writeMessage("Lost contact with server");
        }
        else {
            game.writeMessage("Player "+names[index]+" quited the game");
        }
        exited = true;
    }

    public void exitGame() {
        connection.send(ProtocolWithServer.exitGame());
        game.setVisible(false);
        client.exitGame();
    }
}
