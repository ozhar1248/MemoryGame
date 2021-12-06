package MemoryGame;

import java.util.Arrays;
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
public class GameClientLogic implements IObserver{
    private final ConnectionClient connection;
    private final ReentrantLock lock;
    private final boolean[] flipped;
    private boolean firstCardOn;
    private int firstCard;
    private int countClicks;
    private boolean enableBoard;
    private boolean turn;
    private String[] names;
    private int[] points;
    private int totalPoints;
    private int max;
    private int maxIndex;
    private boolean exited = false;
    private final int sizeRow;
    private final IInterpreter interpreter;
    private final GUManagement gui;

    public GameClientLogic(ConnectionClient connection, GUManagement gui, String name, int level) {
        this.connection = connection;
        this.gui = gui;
        lock = new ReentrantLock();

        sizeRow = MemoryGame.Sizes[level];
        int size = sizeRow*sizeRow;
        flipped = new boolean[size];

        firstCardOn = false;
        enableBoard = true;
        totalPoints = 0;
        max = 0;
        maxIndex = -1;
        Arrays.fill(flipped,false);
        this.interpreter = new InterpretServer(this);
        connection.registerObserver(this);
        connection.startListening();

        connection.send(SenderToServer.startGame(level, name));
    }

    public void waitForOpponents() {
        gui.openGameWindow(this, sizeRow);
        turn = false;
        printToUser("Please wait for opponent");
    }

    public void startGame() {
        if (!gui.isGameOn()) {
            gui.openGameWindow(this, sizeRow);
            turn = false;
        }
        printToUser("Game started. Wait for your turn");
    }

    public void turnOn() {
        lock.lock();
        try {
            resetTurn();
            enableBoard = true;
            turn = true;
            printToUser("Its your turn. Please choose first card");
        }
        finally {
            lock.unlock();
        }
    }

    public void flipCard(int cardIndex, int value) {
        boolean isMyTurn = turn;
        gui.getGameGUI().flipCard(cardIndex, value);
        new Thread(() -> {
            try {
                Thread.sleep(MemoryGame.TIME_CARD_EXPOSED);
                lock.lock();
                try {
                    if (flipped[cardIndex]) {
                        return;
                    }
                    gui.getGameGUI().coverCard(cardIndex);
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

    public void flipCardPermanently(int cardIndex1, int cardIndex2, int cardValue, int indexPlayer) {
        lock.lock();
        try {
            flipped[cardIndex1] = true;
            flipped[cardIndex2] = true;
            gui.getGameGUI().flipCard(cardIndex1, cardValue);
            gui.getGameGUI().flipCard(cardIndex2, cardValue);

            totalPoints++;
            gui.getGameGUI().updatePoints(indexPlayer, ++points[indexPlayer]);
            if (points[indexPlayer] > max) {
                max = points[indexPlayer];
                maxIndex = indexPlayer;
            }
            if (isFinished()) {
                finishGame();
                return;
            }
            if (turn) {
                resetTurn();
                printToUser("Bingo!");
                try {
                    Thread.sleep(MemoryGame.TIME_FOR_MESSAGE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printToUser("Please choose first card");
                enableBoard = true;
            }
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
            enable = !flipped[index] && enableBoard && index != firstCard;
            if (enable) {
                countClicks++;
                if (countClicks == 2) {
                    enableBoard = false;
                    printToUser("");
                }
                if (countClicks == 1) {
                    firstCardOn = true;
                    firstCard = index;
                    printToUser("Please select second card");
                }
            }
        }
        finally {
            lock.unlock();
        }
        if (enable) {
            connection.send(SenderToServer.chooseCard(index));
        }
    }

    private void resetTurn() {
        countClicks = 0;
        firstCard = -1;
    }

    private void failedTurn() {
        turn = false;
        printToUser("wrong");
        try {
            Thread.sleep(MemoryGame.TIME_FOR_MESSAGE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printToUser("Please wait for your turn");
    }

    public void updateNames(String[] names) {
        this.names = names;
        points = new int[names.length];
        Arrays.fill(points, 0);
        gui.getGameGUI().createNames(names);
    }

    public void playersDisconnected(int index) {
        if (exited) return;
        turn = false;
        if (index==-1) {
            printToUser("Lost contact with server");
        }
        else {
            printToUser("Player "+names[index]+" quited the game");
        }
        exited = true;
    }

    public void exitGame() {
        connection.stopListening();
        if (!isFinished()) {
            connection.send(SenderToServer.exitGame());
        }
        gui.endGame();
    }

    @Override
    public void update(Object message) {
        assert interpreter != null;
        interpreter.interpret((String)message);
    }

    private void printToUser(String str) {
        gui.getGameGUI().writeMessage(str);
    }

    private void finishGame() {
        gui.getGameGUI().writeMessage(names[maxIndex]+" Won!!");
    }

    private boolean isFinished() {
        return totalPoints == sizeRow*sizeRow/2;
    }

}
