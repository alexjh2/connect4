package connect4;


import java.awt.Cursor;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;



/**
 *  Class that makes IntroPanel that is the intro page of the game
 *
 *  @author alexj0523
 *  @version May, 2025
 */
public class IntroPanel extends JPanel {

    /**
     * Background image
     */
    Image intro_image;
    private ImageIcon quitButtonEnteredImage, quitButtonBasicImage, startButtonEnteredImage, startButtonBasicImage;
    private JButton quitButton, startButton;
    private Connect4 frame;

    /**
     * Constructor that makes  intropanel that fits the screen
     * @param frame frame
     */
    public IntroPanel(Connect4 frame) {
        setPreferredSize( new Dimension( 670,420 ) );
        intro_image = new ImageIcon("images/connect4.jpg").getImage();
        this.frame = frame;
        setLayout( null );
        setButton();

    }

    /**
     * Drawing background image
     * @param g graphics
     */
    public void paintComponent(Graphics g) {
        super.paintComponent( g );
        g.drawImage(intro_image,0,0, 670,420,this);
    }

    private void setButton() {

        quitButtonEnteredImage = new ImageIcon("images/quit_hover.png"); //when mouse is over it
        quitButtonBasicImage = new ImageIcon("images/quit_normal.png"); //when mouse is not over it
        startButtonEnteredImage = new ImageIcon("images/start_hover.png");
        startButtonBasicImage = new ImageIcon("images/start_normal.png");
        quitButton = new JButton(quitButtonBasicImage);
        startButton = new JButton(startButtonBasicImage);

        quitButton.setBounds(400, 400, 209, 96);
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setFocusPainted(false);
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                quitButton.setIcon(quitButtonEnteredImage);
                quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //changing shape of the cursor
            }
            @Override
            public void mouseExited(MouseEvent e) {
                quitButton.setIcon(quitButtonBasicImage);
                quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    Thread.sleep(1000); //after 1 second
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
        add(quitButton);


        startButton.setBounds(40, 400, 248, 96);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setIcon(startButtonEnteredImage);
                startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setIcon(startButtonBasicImage);
                startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                frame.changeToboard(); //intropanel to gamepanel
            }
        });
        add(startButton);
    }


}



