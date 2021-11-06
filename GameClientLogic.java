package MemoryGame;

public class GameClientLogic {
    private GameGUI game;
    private Connection connection;
    private CommandCard command;

    public GameClientLogic(Connection connection) {
        this.connection = connection;
        command = new CommandCard(connection);
    }

    public void waitForOpponents() {
        System.out.println("Please wait for opponents");
    }

    public void startGame() {
        game = new GameGUI(command);
    }

    public void changeTurn() {
        command.changeTurn();
    }
}
