package Tetris;

import java.util.Random;

public class Shape {

    private Tetrominoes pieceShape;
    private int coordinates[][];
    private int[][][] coordinatesTable;

    public Shape() {
        this.coordinates = new int[4][2];
        setShape(Tetrominoes.NOSHAPE);
    }

    public void setShape(Tetrominoes shape) {
        coordinatesTable = new int[][][]{
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}},
        };

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 2; ++col) {
                this.coordinates[row][col] = this.coordinatesTable[shape.ordinal()][row][col];
            }
        }

        this.pieceShape = shape;
    }

    private void setX(int index, int x){
        this.coordinates[index][0] = x;
    }

    private void setY(int index, int y){
        this.coordinates[index][1] = y;
    }

    public int x(int index){
        return this.coordinates[index][0];
    }

    public int y(int index){
        return this.coordinates[index][1];
    }

    public Tetrominoes getPieceShape(){
        return this.pieceShape;
    }

    public void setRandomShape(){
        Random random = new Random();
        int x = Math.abs(random.nextInt()) % 7 + 1;
        Tetrominoes[] values = Tetrominoes.values();
        setShape(values[x]);
    }

    public int minX(){
        int min = this.coordinates[0][0];

        for (int i = 0; i < 4; i++) {
            min = Math.min(min,this.coordinates[i][0]);
        }
        return min;
    }

    public int minY(){
        int min = this.coordinates[0][1];

        for (int i = 0; i < 4; i++) {
            min = Math.min(min,this.coordinates[i][1]);
        }
        return min;
    }

    public Shape rotateLeft(){
        if(this.pieceShape == Tetrominoes.SQUARESHAPE){
            return this;
        }

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, y(i));
            result.setY(i, -x(i));
        }
        return result;
    }

    public Shape rotateRight(){
        if(this.pieceShape == Tetrominoes.SQUARESHAPE){
            return this;
        }

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, -y(i));
            result.setY(i, x(i));
        }
        return result;
    }
}

