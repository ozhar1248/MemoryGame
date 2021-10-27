package MemoryGame;

public class InterperterClient implements IObserver, ISubject{
    private ISubject m_player;
    private int m_id;
    private IObserver m_game;

    public InterperterClient(ISubject player, int id) {
        m_player = player;
        player.registerObserver(this);
        m_id = id;
    }
    public void update(Object obj) {
        String message = (String)obj;
    }

    public void registerObserver(IObserver o) {
        m_game = o;
    }
    public void removeObserver(IObserver o) {
        m_game = null;
    }
    public void notifyObservers() {
        m_game.update();
    }
}
