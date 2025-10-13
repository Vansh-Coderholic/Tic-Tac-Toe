package com.vansh.tictactoe.ui.components;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import static com.vansh.tictactoe.theme.Theme.*;

public class StyledButton extends JButton {
    private final Color defaultColor = ACCENT_DEFAULT;
    private final Color hoverColor = ACCENT_HOVER;

    public StyledButton(String text) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 18));
        setBackground(defaultColor);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { setBackground(hoverColor); }
            @Override public void mouseExited(MouseEvent e) { setBackground(defaultColor); }
        });
    }
}
