package com.spaceinvaders.sprites;

import javax.swing.*;

public class Bomb extends Sprite {

    private static final String IMAGE_LOCATION = "src/resources/images/bomb.png";

    private boolean destroyed;

    public Bomb(final int x, final int y) {
        this.initBomb(x, y);
    }

    private void initBomb(final int x, final int y) {
        final ImageIcon imageIcon = new ImageIcon(IMAGE_LOCATION);
        this.setImage(imageIcon.getImage());
        this.setDestroyed(true);
        this.x = x;
        this.y = y;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public void setDestroyed(final boolean destroyed) {
        this.destroyed = destroyed;
    }
}
