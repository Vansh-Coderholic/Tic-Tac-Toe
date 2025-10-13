package com.vansh.tictactoe;

import javax.swing.SwingUtilities;
import com.vansh.tictactoe.ui.TicTacToeFrame;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeFrame().setVisible(true));
    }
}
