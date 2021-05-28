/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jason
 */
public class Tetris extends JPanel {

    private static int width, height, sqHW, margin, blockHeight, blockWidth, currentPiece, rotation;

    private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    Color[] tetrisColor = {Color.decode("#00ffff"), Color.decode("#FFD500"), Color.decode("#0341AE"), Color.decode("#72CB3B"), Color.decode("#FF971C"), Color.decode("#FF3213"), Color.decode("#FBECD5")};

    private final Point[][][] Tetraminos = {
        // I-Piece
        {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
        },
        // J-Piece
        {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0)}
        },
        // L-Piece
        {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0)}
        },
        // O-Piece
        {
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}
        },
        // S-Piece
        {
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)}
        },
        // T-Piece
        {
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)}
        },
        // Z-Piece
        {
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)},
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)}
        }
    };
    int score = 0;
    private Color[][] well;
    Point pieceOrigin;
    nextTetris nextTetris;
    int distance;
    Timer timer;
    boolean isAbleToMove;

    public Tetris() {
        isAbleToMove = false;
        margin = 5;
        blockHeight = 23;
        blockWidth = 12;
        rotation = 1;
        initWall();
    }

    private void initWall() {
        well = new Color[blockWidth][blockHeight];
        for (int x = 0; x < well.length; x++) {
            for (int y = 0; y < well[x].length; y++) {
                if (x == 0 || x == well.length - 1 || y == 0 || y == well[x].length - 1) {
                    well[x][y] = Color.GRAY;
                } else {
                    well[x][y] = Color.BLACK;
                }
            }
        }
        nextPiece();
        timer = new Timer(1000, (e) -> {
            moveDown();
            repaint();
        });
    }

    private void nextPiece() {
        pieceOrigin = new Point(5, 1);
        distance = 0;
        int nPieces = nextPieces.size();
        if (nPieces == 0 || nPieces - 1 == 0) {
            Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
            Collections.shuffle(nextPieces);
        }
        currentPiece = nextPieces.get(0);
        nextPieces.remove(0);
    }

    public JPanel getNextPanel() {
        return nextTetris;
    }

    private void createWall(Graphics g) {
        width = getSize().width;
        height = getSize().height;
        sqHW = width / blockWidth;

        for (int x = 0; x < well.length; x++) {
            for (int y = 0; y < well[x].length; y++) {
                g.setColor(well[x][y]);
                g.fillRect(x * sqHW, y * sqHW, sqHW, sqHW);
            }
        }
    }

    public void createLines(Graphics g) {
        g.setColor(Color.gray);
        for (int x = 0; x < blockWidth + 1; x++) {
            g.drawLine(x * sqHW, 0, x * sqHW, sqHW * blockHeight);
        }
        for (int y = 0; y < blockHeight + 1F; y++) {
            g.drawLine(0, y * sqHW, sqHW * blockWidth, y * sqHW);
        }

    }

    public void drawPiece(Graphics g) {
        g.setColor(tetrisColor[currentPiece]);
        for (Point p : Tetraminos[currentPiece][rotation]) {
            g.fillRect((p.x + pieceOrigin.x) * sqHW, (p.y + pieceOrigin.y) * sqHW, sqHW, sqHW);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        createWall(g);
        drawPiece(g);
        createLines(g);
    }

    private boolean collidesAt(int x, int y, int rotation) {
        for (Point p : Tetraminos[currentPiece][rotation]) {
            if (well[p.x + x][p.y + y] != Color.BLACK) {
                return true;
            }
        }
        return false;
    }

    private void moveDown() {
        if (!collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotation)) {
            pieceOrigin.y++;
            distance++;
        } else {
            if (distance < 1) {
                timer.stop();
                isAbleToMove = false;
            } else {
                touchDown();
                nextPiece();
            }
        }
    }

    private void touchDown() {
        for (Point p : Tetraminos[currentPiece][rotation]) {
            well[p.x + pieceOrigin.x][p.y + pieceOrigin.y] = tetrisColor[currentPiece];
        }
        clearRows();
        repaint();
    }

    public void rotate(int i) {
        int newRotation = (rotation + i) % 4;
        if (newRotation < 0) {
            newRotation = 3;
        }
        if (!collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) {
            rotation = newRotation;
        }
        repaint();
    }

    public void deleteRow(int row) {
        for (int y = row - 1; y > 0; y--) {
            for (int x = 1; x < 11; x++) {
                well[x][y + 1] = well[x][y];
            }
        }
    }

    public void clearRows() {
        boolean gap;
        int numClears = 0;

        for (int y = 21; y > 0; y--) {
            gap = false;
            for (int x = 1; x < blockWidth; x++) {
                if (well[x][y] == Color.BLACK) {
                    gap = true;
                    break;
                }
            }
            if (!gap) {
                deleteRow(y);
                y += 1;
                numClears += 1;
            }
        }
        switch (numClears) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
        }
    }

    public void move(String s) {
        if (isAbleToMove) {

            switch (s) {
                case "ROTATE":
                    rotate(1);
                    break;
                case "LEFT":
                    if (!collidesAt(pieceOrigin.x - 1, pieceOrigin.y, rotation)) {
                        pieceOrigin.x--;
                        repaint();
                    }
                    break;
                case "RIGHT":
                    if (!collidesAt(pieceOrigin.x + 1, pieceOrigin.y, rotation)) {
                        pieceOrigin.x++;
                        repaint();
                    }
                    break;

                case "DOWN":
                    moveDown();
                    break;
            }
            repaint();
        }
    }

    public int getScore() {
        return this.score;
    }

    public int getNextPiece() {
        return nextPieces.get(currentPiece);
    }
public ArrayList getLists() {
    return nextPieces;
}
    public Color getNextColor() {
        return tetrisColor[currentPiece];
    }
    String status;

    public void startPause() {
        if (status == null || status.equals("PAUSE")) {
            timer.start();
            isAbleToMove = true;
            status = "START";
        } else {
            timer.stop();
            isAbleToMove = false;
            status = "PAUSE";
        }
    }

    public String getStatus() {
        return status;
    }

    public void restart() {
        nextPieces.removeAll(nextPieces);
        timer.stop();
        this.score = 0;
        nextPiece();
        status = "PAUSE";
        initWall();
        repaint();
    }
}
