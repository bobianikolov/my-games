package Tetris;

import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame{
    private JLabel statusBar;

    public Tetris(){
        initializationOfFrames();
    }

    private void initializationOfFrames(){
        statusBar = new JLabel(" 0");
        add(statusBar, BorderLayout.SOUTH);
        Board board = new Board(this);
        add(board);
        board.start();

        setSize(200,400);
        setTitle("Tetris");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JLabel getStatusBar(){
        return this.statusBar;
    }
}
