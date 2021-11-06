package MemoryGame;

public class CardPressedAction implements IGameAction{
    public void sendAction(Object o) {
        assert o instanceof Integer;
        Integer cardNumber = (Integer)o;
    }
}
