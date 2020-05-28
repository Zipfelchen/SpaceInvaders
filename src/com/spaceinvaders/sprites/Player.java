package com.spaceinvaders.sprites;

import com.spaceinvaders.commons.Constants;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

    private static final String IMAGE_LOCATION = "src/resources/images/player.png";
    private static final int START_POSITION_X = 270;
    private static final int START_POSITION_Y = 280;

    private int width;

    public Player() {
        this.initPlayer();
    }

    private void initPlayer() {
        final ImageIcon imageIcon = new ImageIcon(IMAGE_LOCATION);
        this.width = imageIcon.getImage().getWidth(null);
        this.setImage(imageIcon.getImage());
        this.x = START_POSITION_X;
        this.y = START_POSITION_Y;
    }

    public void act() {
        this.x = this.x + this.dx;

        if (this.x <= 2) {
            this.x = 2;
        }

        if (this.x >= Constants.BOARD_WIDTH - 2 * this.width) {
            this.x = Constants.BOARD_WIDTH - 2 * this.width;
        }
    }

    public void keyPressed(final KeyEvent e) {
        final int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            this.dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            this.dx = 2;
        }
    }

    public void keyReleased(final KeyEvent e) {
        final int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            this.dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            this.dx = 0;
        }
    }
}
