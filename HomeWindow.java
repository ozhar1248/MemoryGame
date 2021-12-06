package MemoryGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeWindow extends JFrame implements ActionListener {
        private final JTextField nameField;
        private final JRadioButton easy;
        private final JRadioButton medium;
        private final JRadioButton hard;
        private final GameClient client;
        private final JLabel message;



    public HomeWindow(GameClient client) {
        super("Memory Game");
        this.client = client;

        JPanel form = new JPanel();
        form.setLayout(new GridLayout(4, 1));
        nameField = new JTextField();
        form.add(nameField);
        ButtonGroup group = new ButtonGroup();
        easy = new JRadioButton("EASY", true);
        medium = new JRadioButton("MEDIUM");
        hard = new JRadioButton("HARD");
        group.add(easy);
        group.add(medium);
        group.add(hard);
        form.add(easy);
        form.add(medium);
        form.add(hard);
        JPanel buttons = new JPanel();
        JButton startButton = new JButton("START");
        buttons.add(startButton);
        message = new JLabel();
        add(message, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        startButton.addActionListener(this);
        setSize(300,300);
    }

    public void actionPerformed(ActionEvent e) {
        int level = -1;
        if (easy.isSelected()) {
            level = MemoryGame.EASY;
        }
        else if (medium.isSelected()) {
            level = MemoryGame.MEDIUM;
        }
        else if (hard.isSelected()) {
            level = MemoryGame.HARD;
        }

        if (nameField.getText().trim().equals("")) {
            message.setText("Please fill your name");
            return;
        }
        message.setText("");
        client.connectToServer(nameField.getText(), level);
    }

}
