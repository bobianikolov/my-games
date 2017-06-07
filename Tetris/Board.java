package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    private static final int BOARDWIDTH = 10;
    private static final int BOARDHEIGHT = 22;

    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private int numLinesRemoved = 0;
    private int currentX = 0;
    private int currentY = 0;
    private JLabel statusBar;
    private Shape currentPiece;
    private Tetrominoes[] board;

    public Board(Tetris parent) {
        initializationBoard(parent);
    }

    private void initializationBoard(Tetris parent) {
        setFocusable(true);
        addKeyListener(new TAdapter());
        setBackground(Color.lightGray);

        this.currentPiece = new Shape();
        this.timer = new Timer(400, this);
        this.timer.start();
        this.statusBar = parent.getStatusBar();
        this.board = new Tetrominoes[BOARDWIDTH * BOARDHEIGHT];
        clearBoard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isFallingFinished) {
            this.isFallingFinished = false;
            newPiece();
        }
        else {
            oneLineDown();
        }
    }

    private int squareWidth() {
        return (int) getSize().getWidth() / BOARDWIDTH;
    }

    private int squareHeight() {
        return (int) getSize().getHeight() / BOARDHEIGHT;
    }

    private Tetrominoes shapeAt(int x, int y) {
        return this.board[(y * BOARDWIDTH) + x];
    }

    public void start() {
        if (this.isPaused) {
            return;
        }

        this.isStarted = true;
        this.isFallingFinished = false;
        this.numLinesRemoved = 0;
        clearBoard();

        newPiece();
        this.timer.start();
    }

    private void pause() {

        if (!this.isStarted) {
            return;
        }
        this.isPaused = !this.isPaused;

        if (this.isPaused) {
            this.timer.stop();
            this.statusBar.setText("paused");
        } else {
            this.timer.start();
            this.statusBar.setText(String.valueOf(this.numLinesRemoved));
        }
        repaint();
    }

    private void doDrawing(Graphics g) {

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BOARDHEIGHT * squareHeight();

        for (int i = 0; i < BOARDHEIGHT; ++i) {
            for (int j = 0; j < BOARDWIDTH; ++j) {

                Tetrominoes shape = shapeAt(j, BOARDHEIGHT - i - 1);

                if (shape != Tetrominoes.NOSHAPE) {
                    drawSquare(g, j * squareWidth(), boardTop + i * squareHeight(), shape);
                }
            }
        }

        if (this.currentPiece.getPieceShape() != Tetrominoes.NOSHAPE) {
            for (int i = 0; i < 4; ++i) {
                int x = this.currentX + this.currentPiece.x(i);
                int y = this.currentY + this.currentPiece.y(i);
                drawSquare(g, x * squareWidth(), boardTop + (BOARDHEIGHT - y - 1) * squareHeight(),
                        this.currentPiece.getPieceShape());
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void dropDown() {
        int newY = this.currentY;

        while (newY > 0) {
            if (!tryMove(this.currentPiece, this.currentX,newY - 1)) {
                break;
            }
            --newY;
        }
        pieceDropped();
    }

    private void oneLineDown() {
        if (!tryMove(this.currentPiece, this.currentX, this.currentY - 1)) {
            pieceDropped();
        }
    }

    private void clearBoard() {
        for (int i = 0; i < BOARDHEIGHT * BOARDWIDTH; ++i) {
            board[i] = Tetrominoes.NOSHAPE;
        }
    }

    private void pieceDropped() {
        for (int i = 0; i < 4; ++i) {
            int x = this.currentX + this.currentPiece.x(i);
            int y = this.currentY - this.currentPiece.y(i);
            this.board[(y * BOARDWIDTH) + x] = this.currentPiece.getPieceShape();
        }
        removeFullLines();

        if (!this.isFallingFinished) {
            newPiece();
        }
    }

    private void newPiece() {
        this.currentPiece.setRandomShape();
        this.currentX = BOARDWIDTH / 2 + 1;
        this.currentY = BOARDHEIGHT - 1 + currentPiece.minY();

        if (!tryMove(this.currentPiece, this.currentX, this.currentY)) {

            this.currentPiece.setShape(Tetrominoes.NOSHAPE);
            this.timer.stop();
            this.isStarted = false;
            this.statusBar.setText("game over");
        }
    }

    private boolean tryMove(Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 4; ++i) {

            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);

            if (x < 0 || x >= BOARDWIDTH || y < 0 || y >= BOARDHEIGHT) {
                return false;
            }

            if (shapeAt(x, y) != Tetrominoes.NOSHAPE) {
                return false;
            }
        }
        this.currentPiece = newPiece;
        this.currentX = newX;
        this.currentY = newY;

        repaint();
        return true;
    }

    private void removeFullLines() {
        int numFullLines = 0;

        for (int i = BOARDHEIGHT - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < BOARDWIDTH; ++j) {
                if (shapeAt(j, i) == Tetrominoes.NOSHAPE) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                ++numFullLines;
                for (int k = i; k < BOARDHEIGHT - 1; ++k) {
                    for (int j = 0; j < BOARDWIDTH - 1; ++j) {
                        board[(k * BOARDWIDTH) + j] = shapeAt(j, k + 1);
                    }
                }
            }

            if (numFullLines > 0) {
                this.numLinesRemoved += numFullLines;
                this.statusBar.setText(String.valueOf(numLinesRemoved));
                this.isFallingFinished = true;
                this.currentPiece.setShape(Tetrominoes.NOSHAPE);
                repaint();
            }
        }
    }

    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {

        Color colors[] = {
                new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(204, 204, 102), new Color(204, 102, 204),
                new Color(102, 204, 204), new Color(218, 170, 0)
        };

        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {

            if (!isStarted || currentPiece.getPieceShape() == Tetrominoes.NOSHAPE) {
                return;
            }

            int keyCode = event.getKeyCode();

            if (keyCode == 'p' || keyCode == 'P') {
                pause();
                return;
            }

            if (isPaused) {
                return;
            }

            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    tryMove(currentPiece, currentX - 1, currentY);
                    break;
                case KeyEvent.VK_RIGHT:
                    tryMove(currentPiece, currentX + 1, currentY);
                    break;
                case KeyEvent.VK_DOWN:
                    tryMove(currentPiece.rotateRight(), currentX, currentY);
                    break;
                case KeyEvent.VK_UP:
                    tryMove(currentPiece.rotateLeft(), currentX, currentY);
                    break;
                case KeyEvent.VK_SPACE:
                    dropDown();
                    break;
                case 'd':
                    oneLineDown();
                    break;
                case 'D':
                    oneLineDown();
                    break;
            }
        }
    }
}


