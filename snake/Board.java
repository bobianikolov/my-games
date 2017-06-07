package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel implements ActionListener, KeyListener {

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int SCALE = 10;
    private static final int DELAY = 80;

    private int direction;
    private int tailLenght;
    private int scores;
    private boolean gameOver;
    private boolean pause;
    private List<Point> partsOfTheSnake;
    private Dimension dimension;
    private JLabel statusBar;
    private Point apple;
    private Point head;
    private Random random;
    private Timer timer;
    private Frame frame;

    public Board(Frame parent) {
        initialization(parent);
    }

    private void initialization(Frame parent) {
        start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
        this.random = new Random();
        this.gameOver = false;
        this.pause = false;
        this.scores = 0;
        this.statusBar = parent.getStatusBar();
        this.tailLenght = 6;
        this.direction = DOWN;
        this.partsOfTheSnake = new ArrayList<>();
        this.partsOfTheSnake.clear();
        this.dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.head = new Point(0, -1);
        this.apple = new Point(random.nextInt(42), random.nextInt(42));
    }

    private void start() {
        this.timer = new Timer(DELAY, this);
        this.timer.start();
    }

    public void newGame() {
        this.frame = new Frame();
        this.frame.dispose();
        this.frame.setVisible(true);
    }

    public void bestResult() {
        int maxResult = Integer.MIN_VALUE;
        if (this.scores > maxResult) {
            maxResult = this.scores;
        }

        JOptionPane.showMessageDialog(this, " " + maxResult + " scores");
    }

    public void help() {
        JOptionPane.showMessageDialog(this, "1. New Game - Ctrl+N\n2. Pause - SPACE\n3. Best Result - Ctrl+B\n4. Help" +
                " - Ctrl+H\n5. EXIT - ESCAPE");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawingSnake(g2);
        drawingApple(g2);
        calculateScores();

        if (this.gameOver) {

            g.setFont(new Font("Algerian", 1, 60));
            g.setColor(Color.orange);
            g.drawString("Game Over", 54, 145);
            g.setFont(new Font("typewriter", 1, 16));
            g.setColor(Color.orange);
            g.drawString("This game is made by", 147, 210);
            g.setColor(Color.orange);
            g.drawString("Borislav Nikolov", 166, 270);
            g.setColor(Color.white);
            g.drawString("E-mail: bobianikolov@gmail.com", 100, 330);
        }
    }

    private void drawingSnake(Graphics g) {

        for (Point point : partsOfTheSnake) {
            g.setColor(Color.red);
            g.fillRect(point.x * SCALE, point.y * SCALE, SCALE, SCALE);
        }

        g.setColor(Color.red);
        g.fillRect(this.head.x * SCALE, this.head.y * SCALE, SCALE, SCALE);
    }

    private void drawingApple(Graphics g) {

        g.setColor(Color.GREEN);
        g.fillRect(this.apple.x * SCALE, this.apple.y * SCALE, SCALE, SCALE);
        repaint();
    }

    private void calculateScores(){
        this.statusBar.setText(" Scores: " + this.scores);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();

        if (this.head != null && !this.gameOver && !this.pause) {

            this.partsOfTheSnake.add(new Point(this.head.x, this.head.y));

            if (this.direction == UP) {
                if (this.head.y - 1 >= 0 && checkingTail(this.head.x, this.head.y - 1)) {
                    this.head = new Point(this.head.x, this.head.y - 1);
                } else {
                    this.gameOver = true;
                }
            }

            if (this.direction == DOWN) {
                if (this.head.y + 1 < 42 && checkingTail(this.head.x, this.head.y + 1)) {
                    this.head = new Point(this.head.x, this.head.y + 1);
                } else {
                    this.gameOver = true;
                }
            }

            if (this.direction == LEFT) {
                if (this.head.x - 1 >= 0 && checkingTail(this.head.x - 1, this.head.y)) {
                    this.head = new Point(this.head.x - 1, this.head.y);
                } else {
                    this.gameOver = true;
                }
            }

            if (this.direction == RIGHT) {
                if (this.head.x + 1 < 48 && checkingTail(this.head.x + 1, this.head.y)) {
                    this.head = new Point(this.head.x + 1, this.head.y);
                } else {
                    this.gameOver = true;
                }
            }

            if (this.partsOfTheSnake.size() > this.tailLenght) {
                this.partsOfTheSnake.remove(0);
            }

            if (this.apple != null) {
                if (this.head.equals(this.apple)) {
                    this.scores += 1;
                    this.tailLenght++;
                    this.apple.setLocation(this.random.nextInt(42), this.random.nextInt(42));
                }
            }
        }
    }

    public boolean checkingTail(int x, int y) {
        for (Point point : partsOfTheSnake) {
            if (point.equals(new Point(x, y))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT && direction != RIGHT) {
            direction = LEFT;
        }

        if (key == KeyEvent.VK_RIGHT && direction != LEFT) {
            direction = RIGHT;
        }

        if (key == KeyEvent.VK_UP && direction != DOWN) {
            direction = UP;
        }

        if (key == KeyEvent.VK_DOWN && direction != UP) {
            direction = DOWN;
        }

        if (key == KeyEvent.VK_SPACE) {
            if (this.gameOver) {
                start();
            } else {
                this.pause = !this.pause;
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
