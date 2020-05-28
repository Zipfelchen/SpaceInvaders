package com.spaceinvaders;

import com.spaceinvaders.commons.Board;
import com.spaceinvaders.commons.Constants;

import javax.swing.*;
import java.awt.*;

public class SpaceInvaders extends JFrame {

    private static final long serialVersionUID = 2844234625522527696L;

    public SpaceInvaders() {
        this.initUI();
    }

    public static void main(final String[] args) {
        EventQueue.invokeLater(() -> {
            final SpaceInvaders spaceInvaders = new SpaceInvaders();
            spaceInvaders.setVisible(true);
        });
    }

    private void initUI() {
        this.add(new Board());
        this.setTitle("Space Invaders");
        this.setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
}
