package MemoryGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameGUI extends JFrame {
    private static final int NUM_ITEMS_IN_ROW = 4;
    private Command command;

    public GameGUI(Command command) {
        super("Memory game");
        this.command = command;
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(NUM_ITEMS_IN_ROW,10,NUM_ITEMS_IN_ROW,10);
        panel.setLayout(layout);

        for (int i=0; i<NUM_ITEMS_IN_ROW*NUM_ITEMS_IN_ROW; ++i) {
            JPanel card = new JPanel();
            card.setSize(40, 40);
            card.setBackground(Color.BLACK);
            int finalI = i;
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    command.execute(finalI);
                    System.out.println("in mouse clicked");
                }
            });
            panel.add(card);
        }
        add(panel);
        setSize(800,800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
