package com.vansh.tictactoe.ui.components;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Font;
import static com.vansh.tictactoe.theme.Theme.*;

public class GameButton extends JButton {
    public GameButton() {
        setFont(new Font("Arial", Font.BOLD, 80));
        setBackground(BUTTON_COLOR);
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 6));
    }
}
