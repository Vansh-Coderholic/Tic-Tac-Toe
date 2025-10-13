package com.vansh.tictactoe.ui;

import javax.swing.*;
import java.awt.*;
import com.vansh.tictactoe.ui.components.*;
import static com.vansh.tictactoe.theme.Theme.*;

public class TicTacToeFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GameButton[][] buttons = new GameButton[3][3];
    private GameButton[] winningCombination = new GameButton[3];
    private boolean xTurn = true;
    private JLabel statusLabel;
    private JTextField player1Field, player2Field;
    private String player1Name = "Player X";
    private String player2Name = "Player O";

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setSize(700, 700);
        setMinimumSize(new Dimension(500, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createTitleScreen(), "Title");
        mainPanel.add(createGameScreen(), "Game");

        add(mainPanel);
        cardLayout.show(mainPanel, "Title");
    }

    /* ---- Title Screen ---- */
    private JPanel createTitleScreen() {
        JPanel titleScreen = new JPanel(new GridBagLayout());
        titleScreen.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        titleScreen.add(title, gbc);

        gbc.gridwidth = 1;
        JLabel p1Label = new JLabel("Player 1 (X): ");
        p1Label.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        titleScreen.add(p1Label, gbc);

        player1Field = new JTextField(12);
        gbc.gridx = 1; gbc.gridy = 1;
        titleScreen.add(player1Field, gbc);

        JLabel p2Label = new JLabel("Player 2 (O): ");
        p2Label.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        titleScreen.add(p2Label, gbc);

        player2Field = new JTextField(12);
        gbc.gridx = 1; gbc.gridy = 2;
        titleScreen.add(player2Field, gbc);

        JButton playButton = new StyledButton("Start Game");
        playButton.addActionListener(e -> {
            String p1 = player1Field.getText().trim();
            String p2 = player2Field.getText().trim();
            player1Name = p1.isEmpty() ? "Player X" : p1;
            player2Name = p2.isEmpty() ? "Player O" : p2;

            resetGame();
            cardLayout.show(mainPanel, "Game");
        });

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        titleScreen.add(playButton, gbc);

        return titleScreen;
    }

    /* ---- Game Screen ---- */
    private JPanel createGameScreen() {
        JPanel gameScreen = new JPanel(new BorderLayout());
        gameScreen.setBackground(BACKGROUND_COLOR);

        SquarePanel boardPanel = new SquarePanel(new GridLayout(3, 3, 12, 12));
        boardPanel.setBackground(BACKGROUND_COLOR);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new GameButton();
                final int row = i, col = j;
                buttons[i][j].addActionListener(e -> handleMove(row, col));
                boardPanel.add(buttons[i][j]);
            }
        }

        JPanel boardWrapper = new JPanel(new GridBagLayout());
        boardWrapper.setBackground(BACKGROUND_COLOR);
        boardWrapper.add(boardPanel, new GridBagConstraints());
        gameScreen.add(boardWrapper, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 20, 20));

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(STATUS_FONT);
        statusLabel.setForeground(Color.WHITE);
        bottomPanel.add(statusLabel, BorderLayout.CENTER);

        JPanel buttonBar = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonBar.setOpaque(false);
        JButton menuBtn = new StyledButton("Back to Menu");
        menuBtn.addActionListener(e -> cardLayout.show(mainPanel, "Title"));
        JButton resetBtn = new StyledButton("Play Again");
        resetBtn.addActionListener(e -> resetGame());
        buttonBar.add(menuBtn);
        buttonBar.add(resetBtn);

        bottomPanel.add(buttonBar, BorderLayout.SOUTH);
        gameScreen.add(bottomPanel, BorderLayout.SOUTH);

        return gameScreen;
    }

    /* ---- Game Logic ---- */
    private void handleMove(int row, int col) {
        if (!buttons[row][col].getText().isEmpty()) return;

        buttons[row][col].setText(xTurn ? "X" : "O");
        buttons[row][col].setForeground(xTurn ? X_COLOR : O_COLOR);

        if (checkWin()) {
            statusLabel.setText((xTurn ? player1Name : player2Name) + " Wins!");
            highlightWin();
            disableBoard();
        } else if (isBoardFull()) {
            statusLabel.setText("It's a Draw!");
        } else {
            xTurn = !xTurn;
            statusLabel.setText((xTurn ? player1Name : player2Name) + "'s Turn");
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++)
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2])) return true;

        for (int i = 0; i < 3; i++)
            if (checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) return true;

        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2])) return true;
        if (checkLine(buttons[0][2], buttons[1][1], buttons[2][0])) return true;
        return false;
    }

    private boolean checkLine(GameButton b1, GameButton b2, GameButton b3) {
        String text = b1.getText();
        if (!text.isEmpty() && text.equals(b2.getText()) && text.equals(b3.getText())) {
            winningCombination[0] = b1;
            winningCombination[1] = b2;
            winningCombination[2] = b3;
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (GameButton[] row : buttons)
            for (GameButton b : row)
                if (b.getText().isEmpty()) return false;
        return true;
    }

    private void highlightWin() {
        for (GameButton b : winningCombination)
            if (b != null) b.setBackground(WIN_HIGHLIGHT_COLOR);
    }

    private void disableBoard() {
        for (GameButton[] row : buttons)
            for (GameButton b : row)
                b.setEnabled(false);
    }

    private void resetGame() {
        for (GameButton[] row : buttons)
            for (GameButton b : row) {
                b.setText("");
                b.setEnabled(true);
                b.setBackground(BUTTON_COLOR);
            }
        winningCombination = new GameButton[3];
        xTurn = true;
        statusLabel.setText(player1Name + "'s Turn");
    }
}





