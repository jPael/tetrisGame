/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jason
 */
public class TetrisNext extends JPanel {

    //borderHeight is the amount of boxes that can be fitted vertically
    //borderWidth is the amounth of boxes that can be fitted horizontally
    int borderHeight = 8, borderWidth = 8, sqHW, width, height;
    Color wells[][];
    Point pieceOrigin = new Point(3, 2);
    Color currentColor = Color.BLACK;
    int currentI = 7;
    boolean playing = false;
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
        },
        // all black
        {
            {new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)},
            {new Point(0, 0), new Point(0, 1), new Point(0, 0), new Point(0, 0)},
            {new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)},
            {new Point(0, 0), new Point(0, 1), new Point(0, 0), new Point(0, 0)}
        }
    };

    public TetrisNext() {

        initWalls();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBorder(g);
        if (playing) {
            draw(g);
        }
        drawLines(g);

    }

    private void initWalls() {
        wells = new Color[borderWidth][borderHeight];
        for (int x = 0; x < borderWidth; x++) {
            for (int y = 0; y < borderHeight; y++) {
                if (x == 0 || x == wells.length - 1 || y == 0 || y == wells[x].length - 1) {
                    wells[x][y] = Color.GRAY;
                } else {
                    wells[x][y] = Color.BLACK;
                }
            }
        }
    }

    private void wall_playmode() {
        if (playing) {

            for (int x = 0; x < wells.length; x++) {
                for (int y = 0; y < wells[x].length; y++) {
                    if (x == 0 || x == wells.length - 1 || y == 0 || y == wells[x].length - 1) {
                        wells[x][y] = currentColor;
                    }
                }
            }
        } else if (!playing) {
            for (int x = 0; x < wells.length; x++) {
                for (int y = 0; y < wells[x].length; y++) {
                    if (x == 0 || x == wells.length - 1 || y == 0 || y == wells[x].length - 1) {
                        wells[x][y] = Color.GRAY;
                    }
                }
            }
        } else {
            initWalls();
        }
        repaint();

    }

    private void drawBorder(Graphics g) {
        width = getSize().width;
        height = getSize().height;
        sqHW = width / borderWidth;

        if (playing) {
            wall_playmode();

            for (int x = 0; x < wells.length; x++) {
                for (int y = 0; y < wells[x].length; y++) {
                    g.setColor(wells[x][y]);
                    g.fillRect(x * sqHW, y * sqHW, sqHW, sqHW);
                }
            }
        } else if (!playing) {
            wall_playmode();

            for (int x = 0; x < wells.length; x++) {
                for (int y = 0; y < wells[x].length; y++) {
                    g.setColor(wells[x][y]);
                    g.fillRect(x * sqHW, y * sqHW, sqHW, sqHW);
                }
            }
        } else {
            for (int x = 0; x < wells.length; x++) {
                for (int y = 0; y < wells[x].length; y++) {
                    g.setColor(wells[x][y]);
                    g.fillRect(x * sqHW, y * sqHW, sqHW, sqHW);
                }
            }
        }

        for (int x = 0; x < wells.length; x++) {
            for (int y = 0; y < wells[x].length; y++) {
                g.setColor(wells[x][y]);
                g.fillRect(x * sqHW, y * sqHW, sqHW, sqHW);
            }
        }
    }

    private void drawLines(Graphics g) {
        g.setColor(Color.gray);
        for (int x = 0; x < borderWidth + 1; x++) {
            g.drawLine(x * sqHW, 0, x * sqHW, sqHW * borderWidth);
        }
        for (int y = 0; y < borderHeight + 1; y++) {
            g.drawLine(0, y * sqHW, sqHW * borderHeight, y * sqHW);
        }
    }

    public void draw(Graphics g) {
        g.setColor(currentColor);
        for (Point p : Tetraminos[this.currentI][1]) {
            g.fillRect((p.x + pieceOrigin.x) * sqHW, (p.y + pieceOrigin.y) * sqHW, sqHW, sqHW);
        }
    }

    public void update(int i, Color c, boolean playing) {
        this.currentI = i;
        this.currentColor = c;
        this.playing = playing;
        repaint();
    }

}
