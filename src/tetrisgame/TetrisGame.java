package tetrisgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

public class TetrisGame extends JFrame {

    /*
    this part is where instantiate the:
    frame where we can use to display our GUI
    frameH also known as frame height
    frameW also known as frame width
    Dimenson frameSize we use this variable to hold the height and width value of our frameH, and frameW respectively
     */
    JFrame frame;
    int frameH, frameW;
    Dimension frameSize;
    TetrisAudio audio;

    /*
this is the part where we call the constructor to initialize our variables above    
     */
    public TetrisGame() {
        /*
        in here, we are gonna put the height and width value to our frameH and frameW
        then we are gonna initialize the frameSize with the value of frameH and frameW
        then 
         */
        frameW = 530;
        frameH = 777;
        frameSize = new Dimension(frameW, frameH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        panelLayout();

        audio = new TetrisAudio();
        audio.playTheSound();

    }
    Tetris tetris;
    Timer updateScore;

    public void panelLayout() {
        TetrisNext nextTetris = new TetrisNext();
        tetris = new Tetris();
        
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        
        
        Color tetrisC = Color.decode("#8105d8");
        Color GameC = Color.decode("#fe59d7");
        
        
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        
        
        JLabel TetrisTitle = new JLabel("Tetris ");
        TetrisTitle.setForeground(tetrisC);
        TetrisTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        TetrisTitle.setFont(new Font("san serif", Font.BOLD, 25));

        
        JLabel GameTitle = new JLabel("Game");
        GameTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GameTitle.setFont(new Font("san serif", Font.BOLD, 25));
        GameTitle.setForeground(Color.WHITE);
        GameTitle.setBackground(GameC);
        GameTitle.setOpaque(true);

        
        header.add(TetrisTitle, BorderLayout.LINE_START);
        header.add(GameTitle, BorderLayout.CENTER);
        
        
        JPanel scoreboard = new JPanel(new GridLayout(3, 1, 15, 15));
        
        JPanel nextPanel = new JPanel(new BorderLayout());
        JLabel nextLabel = new JLabel("Next");
        nextLabel.setFont(new Font("san serif", Font.BOLD, 25));
        nextLabel.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 50));
        nextPanel.add(nextTetris, BorderLayout.CENTER);
        nextPanel.add(nextLabel, BorderLayout.NORTH);
        nextPanel.setSize(150, 150);
        
        JPanel scorePanel = new JPanel(new BorderLayout());
        JLabel scoreHeader = new JLabel("Score", SwingConstants.CENTER);
        scoreHeader.setFont(new Font("san serif", Font.BOLD, 25));
        JLabel scoreLabel = new JLabel("0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("sans serif", Font.BOLD, 25));

        
        scorePanel.add(scoreHeader, BorderLayout.NORTH);
        scorePanel.add(scoreLabel, BorderLayout.CENTER);


        JPanel actionButtons = new JPanel(new GridLayout(3, 1, 10, 20));
        
        JButton pause_button = new JButton("Play");
        pause_button.setFocusable(false);
        pause_button.setBorderPainted(true);
        pause_button.setFocusPainted(false);
        pause_button.setContentAreaFilled(false);

        pause_button.addActionListener(e -> {
            tetris.startPause();
            if (!tetris.getStatus()) {
                pause_button.setText("PLAY");
//                updateScore.stop();
            } else {
                pause_button.setText("PAUSE");
                if (!updateScore.isRunning()) {
                    updateScore.start();
                }
            }
        });

        
        JButton restart_button = new JButton("RESTART");
        restart_button.setBorderPainted(true);
        restart_button.setFocusPainted(false);
        restart_button.setContentAreaFilled(false);
        restart_button.setFocusable(false);
        
        restart_button.addActionListener(e -> {
//            updateScore.stop();
            tetris.restart();
            pause_button.setText("PLAY");
            audio.stop();
            audio.playTheSound();

        });

        
        JButton exit_button = new JButton("Exit");
        exit_button.setFocusable(false);
        exit_button.setBorderPainted(true);
        exit_button.setFocusPainted(false);
        exit_button.setContentAreaFilled(false);
        exit_button.setBackground(new Color(240, 240, 241));
        
        exit_button.addActionListener(e -> {
            System.exit(0);
        });

        
        actionButtons.add(pause_button);
        actionButtons.add(restart_button);
        actionButtons.add(exit_button);
        
        scoreboard.add(nextPanel);
        scoreboard.add(scorePanel);
        scoreboard.add(actionButtons);

        
        contentPanel.add(header, BorderLayout.NORTH);
        contentPanel.add(tetris, BorderLayout.CENTER);
        contentPanel.add(scoreboard, BorderLayout.EAST);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) {
                    tetris.move("ROTATE");
                }
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    tetris.move("LEFT");
                }
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    tetris.move("RIGHT");
                }
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_SPACE || key == KeyEvent.VK_S) {
                    tetris.move("DOWN");
                }
            }
        });

        add(contentPanel);
        setAlwaysOnTop(true);
        setResizable(false);
        setSize(frameSize);
        setLocationRelativeTo(null);

        updateScore = new Timer(1000 / 60, e -> {
            int score = tetris.getScore();
            scoreLabel.setText(score + "");

            final Color c = tetris.getNextColor();
            final int currentI = tetris.getNextPiece();

            nextTetris.update(currentI, c, tetris.getStatus());
        });
    }

    public static void main(String[] args) {
        new TetrisGame();

    }

}
