package MemoryGame;

/*Receive messages from player, interpret them and call methods of game according to the messages*/
/*Maybe change the name*/
public class InterperterClient implements IObserver{
    private ISubject m_player;
    private int m_id;
    private Game m_game;

    public InterperterClient(ISubject player, int id, Game game) {
        m_player = player;
        player.registerObserver(this);
        m_id = id;
        this.m_game = game;
    }

    public void update(Object obj) {
        String message = (String)obj;
        Package pack = new Package(message);
        if (pack.getOperation()==0) {
            m_game.cardChosen(Integer.parseInt(pack.getParameter(1)),m_id);
        }
    }
}
