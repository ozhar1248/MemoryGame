package MemoryGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameGUI extends JFrame {

    private final JLabel text;
    private  JLabel[] scores;
    private final JPanel[] cards;
    private final GameClientLogic logic;
    private final JPanel scoresPanel;
    private final JButton exitButton;

    public GameGUI(GameClientLogic logic, int sizeRow) {
        super("Memory game");
        this.logic = logic;

        JPanel textPanel;
        JPanel board;

        textPanel = new JPanel();
        text = new JLabel();
        scoresPanel = new JPanel();

        textPanel.setLayout(new GridLayout(2,5,1,5));
        textPanel.add(text);
        textPanel.add(scoresPanel);

        board = new JPanel();
        GridLayout layout = new GridLayout(sizeRow,10,sizeRow,10);
        board.setLayout(layout);
        cards = new JPanel[sizeRow*sizeRow];
        for (int i=0; i<sizeRow*sizeRow; ++i) {
            cards[i] = new JPanel();
            cards[i].setSize(40, 40);
            cards[i].setBackground(MemoryGame.defaultColor);
            board.add(cards[i]);
            setCardEvent(i);
        }

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.exitGame();
            }
        });
        add(textPanel,BorderLayout.NORTH);
        add(board,BorderLayout.CENTER);
        add(exitButton,BorderLayout.SOUTH);

        setSize(800,800);
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

    public void createNames(String[] names) {
        scores = new JLabel[names.length];


        for (int i=0; i<names.length; ++i) {
            scores[i] = new JLabel("0");
            scoresPanel.add(new JLabel(names[i]+": "));
            scoresPanel.add(scores[i]);
            scoresPanel.add(new JLabel(" "));
        }
    }

    public void updatePoints(int index, int points) {
        System.out.println("the array is len "+scores.length+ "index is "+index+" points"+points);
        scores[index].setText(""+points);
    }




}
