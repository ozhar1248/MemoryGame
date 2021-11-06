package MemoryGame;

public class CommandCard implements Command{
    private Connection connection;
    private boolean turn;

    public CommandCard(Connection connection) {
        this.connection = connection;
        turn = false;
    }

    public void execute(Object obj) {
        int cardNum = (Integer)obj;
        System.out.println("enters execute turn is"+turn);
        if (turn) {
            connection.send(ProtocolWithServer.chooseCard(cardNum).toString());
            System.out.println("in execute "+ProtocolWithServer.chooseCard(cardNum).toString());
        }

    }

    public void changeTurn() {
        turn = !turn;
    }
}
