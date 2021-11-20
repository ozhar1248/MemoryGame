package MemoryGame;

public class CommandCard implements Command{
    private Connection connection;
    private boolean turn;
    private int counter;

    public CommandCard(Connection connection) {
        this.connection = connection;
    }

    //what happens when we click few times before the card is disabled?
    public void execute(Object obj) {
        //lock
        int cardNum = (Integer) obj;


    }
}
