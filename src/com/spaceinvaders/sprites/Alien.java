package com.spaceinvaders.sprites;

import javax.swing.*;

public class Alien extends Sprite {

    private static final String IMAGE_LOCATION = "src/resources/images/alien.png";

    private Bomb bomb;

    public Alien(final int x, final int y) {
        this.initAlien(x, y);
    }

    private void initAlien(final int x, final int y) {
        final ImageIcon imageIcon = new ImageIcon(IMAGE_LOCATION);
        this.setImage(imageIcon.getImage());
        this.x = x;
        this.y = y;
        this.bomb = new Bomb(x, y);
    }

    public void act(final int direction) {
        this.x = this.x + direction;
    }

    public Bomb getBomb() {
        return this.bomb;
    }
}
