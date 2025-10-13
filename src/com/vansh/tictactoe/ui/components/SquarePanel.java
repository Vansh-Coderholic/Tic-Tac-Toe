package com.vansh.tictactoe.ui.components;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class SquarePanel extends JPanel {
    public SquarePanel(LayoutManager layout) { super(layout); }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = getParent().getSize();
        int size = Math.min(d.width, d.height);
        return new Dimension(size, size);
    }
}
