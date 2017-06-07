package tetris;

import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame{
    private JLabel statusBar;

    public Tetris(){
        initializationOfFrames();
    }

    private void initializationOfFrames(){
        this.statusBar = new JLabel("Scores: 0");
        add(this.statusBar, BorderLayout.SOUTH);
        setBackground(Color.black);
        Board board = new Board(this);
        add(board);
        board.start();

        setSize(196,400);
        setTitle("Tetris");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public JLabel getStatusBar(){
        return this.statusBar;
    }
}
