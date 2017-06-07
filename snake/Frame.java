package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Frame extends JFrame{

    private JLabel statusBar;
    private JMenuBar menu;
    private JMenu control;
    private JMenuItem play;
    private JMenuItem newGame;
    private JMenuItem bestResult;
    private JMenuItem help;
    private Board board;

    public Frame(){
        initialization();
    }

    private void initialization(){
        this.statusBar = new JLabel(" Score: 0");
        add(this.statusBar, BorderLayout.SOUTH);
        this.menu = new JMenuBar();
        this.control = new JMenu("Controls");
        this.newGame = new JMenuItem("New Game");
        this.bestResult = new JMenuItem("Best Result");
        this.help = new JMenuItem("Help");

        this.control.add(this.newGame);
        this.control.add(this.bestResult);
        this.control.add(this.help);
        this.menu.add(this.control);
        this.newGame.setMnemonic(KeyEvent.VK_N);
        this.newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        this.help.setMnemonic(KeyEvent.VK_H);
        this.help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        this.bestResult.setMnemonic(KeyEvent.VK_B);
        this.bestResult.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        this.setJMenuBar(this.menu);

        this.newGame.addActionListener(ee -> this.board.newGame());
        this.bestResult.addActionListener(e -> this.board.bestResult());
        this.help.addActionListener(eee -> this.board.help());

        setTitle("Snake");
        setSize(497,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        board = new Board(this);
        board.setBackground(Color.black);
        add(board);
    }

    public JLabel getStatusBar(){
        return this.statusBar;
    }
}
