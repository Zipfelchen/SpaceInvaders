package com.spaceinvaders.sprites;

import javax.swing.ImageIcon;

public class Shot extends Sprite {

    private static final String IMAGE_LOCATION = "src/resources/images/shot.png";
    private static final int H_SPACE = 6;
    private static final int V_SPACE = 1;

    public Shot() {

    }

    public Shot(final int x, final int y) {
        this.initShot(x, y);
    }

    private void initShot(final int x, final int y) {
        final ImageIcon imageIcon = new ImageIcon(IMAGE_LOCATION);
        this.setImage(imageIcon.getImage());
        this.x = (x + H_SPACE);
        this.y = (y - V_SPACE);
    }
}
