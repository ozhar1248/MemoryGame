package MemoryGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameGUI extends JFrame {
    private static final int NUM_ITEMS_IN_ROW = 4;

    private final JLabel text;
    private final JPanel[] cards;
    private final GameClientLogic logic;

    public GameGUI(GameClientLogic logic) {
        super("Memory game");
        this.logic = logic;

        JPanel textPanel;
        JPanel board;

        textPanel = new JPanel();
        text = new JLabel();
        textPanel.setSize(200,100);
        textPanel.add(text);
        board = new JPanel();
        GridLayout layout = new GridLayout(NUM_ITEMS_IN_ROW,10,NUM_ITEMS_IN_ROW,10);
        board.setLayout(layout);
        cards = new JPanel[16];
        for (int i=0; i<NUM_ITEMS_IN_ROW*NUM_ITEMS_IN_ROW; ++i) {
            cards[i] = new JPanel();
            cards[i].setSize(40, 40);
            cards[i].setBackground(MemoryGame.defaultColor);
            board.add(cards[i]);
            setCardEvent(i);
        }
        add(textPanel,BorderLayout.NORTH);
        add(board,BorderLayout.CENTER);
        setSize(800,800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void flipCard(int index, int value) {
        cards[index].setBackground(MemoryGame.COLORS[value]);
    }

    public void coverCard(int index) {
        cards[index].setBackground(MemoryGame.defaultColor);
    }

    private void setCardEvent(int index) {
        cards[index].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logic.clickOnCard(index);
            }

        });
    }

    public void writeMessage(String message) {
        text.setText(message);
    }






}
