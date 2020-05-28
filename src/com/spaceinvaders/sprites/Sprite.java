package com.spaceinvaders.sprites;

import java.awt.*;

public abstract class Sprite {

    protected int y;
    protected int x;
    protected int dx;

    private boolean visible;
    private Image image;
    private boolean dying;

    public Sprite() {
        this.visible = true;
    }

    public void die() {
        this.visible = false;
    }

    public boolean isVisible() {
        return this.visible;
    }

    protected void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public boolean isDying() {
        return this.dying;
    }

    public void setDying(final boolean dying) {
        this.dying = dying;
    }

    public int getX() {
        return this.x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(final int y) {
        this.y = y;
    }
}

