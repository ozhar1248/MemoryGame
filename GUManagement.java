package MemoryGame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUManagement {
    private GameGUI gameWindow;
    private HomeWindow homeWindow;

    public void openHomeWindow(GameClient client) {
        homeWindow = new  HomeWindow(client);
        homeWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        homeWindow.setVisible(true);
    }

    private void restoreHomeWindow() {
        gameWindow.setVisible(false);
        gameWindow = null;
        homeWindow.setVisible(true);
    }

    public void endGame() {
        restoreHomeWindow();
    }

    public void openGameWindow(GameClientLogic game, int sizeRow) {
        if (homeWindow != null) {
            homeWindow.setVisible(false);
        }
        gameWindow = new GameGUI(game,sizeRow);
        gameWindow.setVisible(true);
    }

    public GameGUI getGameGUI() {
        return gameWindow;
    }

    public boolean isGameOn() {
        if (gameWindow == null) {
            return false;
        }
        return gameWindow.isVisible();
    }
}
