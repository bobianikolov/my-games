package Tetris;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Tetris tetris = new Tetris();
                tetris.setVisible(true);
            }
        });
    }
}
