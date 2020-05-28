package com.spaceinvaders.commons;

import com.spaceinvaders.sprites.Alien;
import com.spaceinvaders.sprites.Bomb;
import com.spaceinvaders.sprites.Player;
import com.spaceinvaders.sprites.Shot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {

    private static final long serialVersionUID = 617176557157352925L;
    private static final String IMAGE_LOCATION = "src/resources/images/explosion.png";
    private static String GAME_OVER_MESSAGE = "Game Over";

    private Dimension dimension;
    private List<Alien> alienFleet;
    private Player player;
    private Shot shot;
    private int direction = -1;
    private int deaths = 0;
    private boolean inGame = true;
    private Timer timer;


    public Board() {
        this.initBoard();
        this.gameInit();
    }

    private void initBoard() {
        this.addKeyListener(new TAdapter());
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.dimension = new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        this.timer = new Timer(Constants.DELAY, new GameCycle());
        this.timer.start();
        this.gameInit();
    }


    private void gameInit() {
        Alien tempAlien;
        this.alienFleet = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                tempAlien = new Alien(Constants.ALIEN_INIT_X + 18 * j,
                        Constants.ALIEN_INIT_Y + 18 * i);
                this.alienFleet.add(tempAlien);
            }
        }

        this.player = new Player();
        this.shot = new Shot();
    }

    private void drawAliens(final Graphics graphics) {
        for (final Alien alien : this.alienFleet) {
            if (alien.isVisible()) {
                graphics.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {
                alien.die();
            }
        }
    }

    private void drawPlayer(final Graphics graphics) {
        if (this.player.isVisible()) {
            graphics.drawImage(this.player.getImage(), this.player.getX(), this.player.getY(), this);
        }

        if (this.player.isDying()) {
            this.player.die();
            this.inGame = false;
        }
    }

    private void drawShot(final Graphics graphics) {
        if (this.shot.isVisible()) {
            graphics.drawImage(this.shot.getImage(), this.shot.getX(), this.shot.getY(), this);
        }
    }

    private void drawBombing(final Graphics graphics) {
        Bomb bomb;
        for (final Alien tempAlien : this.alienFleet) {
            bomb = tempAlien.getBomb();
            if (!bomb.isDestroyed()) {
                graphics.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        this.doDrawing(graphics);
    }

    private void doDrawing(final Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, this.dimension.width, this.dimension.height);
        graphics.setColor(Color.green);

        if (this.inGame) {
            graphics.drawLine(0, Constants.GROUND,
                    Constants.BOARD_WIDTH, Constants.GROUND);

            this.drawAliens(graphics);
            this.drawPlayer(graphics);
            this.drawShot(graphics);
            this.drawBombing(graphics);
        } else {
            if (this.timer.isRunning()) {
                this.timer.stop();
            }
            this.gameOver(graphics);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(final Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);

        graphics.setColor(new Color(0, 32, 48));
        graphics.fillRect(50, Constants.BOARD_WIDTH / 2 - 30, Constants.BOARD_WIDTH - 100, 50);

        graphics.setColor(Color.white);
        graphics.drawRect(50, Constants.BOARD_WIDTH / 2 - 30, Constants.BOARD_WIDTH - 100, 50);

        final Font font = new Font("Helvetica", Font.BOLD, 14);
        final FontMetrics fontMetrics = this.getFontMetrics(font);

        graphics.setColor(Color.white);
        graphics.setFont(font);
        graphics.drawString(this.GAME_OVER_MESSAGE, (Constants.BOARD_WIDTH - fontMetrics.stringWidth(this.GAME_OVER_MESSAGE)) / 2,
                Constants.BOARD_WIDTH / 2);
    }

    private void update() {
        if (this.deaths == Constants.NUMBER_OF_ALIENS_TO_DESTROY) {
            this.inGame = false;
            this.timer.stop();
            this.GAME_OVER_MESSAGE = "Game won!";
        }

        // Update player
        this.player.act();

        // Update shot
        if (this.shot.isVisible()) {
            final int shotX = this.shot.getX();
            final int shotY = this.shot.getY();

            int alienX;
            int alienY;
            final ImageIcon imageIcon = new ImageIcon(this.IMAGE_LOCATION);
            for (final Alien alien : this.alienFleet) {
                alienX = alien.getX();
                alienY = alien.getY();

                if (alien.isVisible() && this.shot.isVisible()) {
                    if (shotX >= (alienX) && shotX <= (alienX + Constants.ALIEN_WIDTH) && shotY >= (alienY)
                            && shotY <= (alienY + Constants.ALIEN_HEIGHT)) {
                        alien.setImage(imageIcon.getImage());
                        alien.setDying(true);
                        this.deaths++;
                        this.shot.die();
                    }
                }
            }

            int y = this.shot.getY();
            y -= 4;

            if (y < 0) {
                this.shot.die();
            } else {
                this.shot.setY(y);
            }
        }

        // Update alien fleet
        int x;
        for (final Alien alien : this.alienFleet) {
            x = alien.getX();

            if (x >= Constants.BOARD_WIDTH - Constants.BORDER_RIGHT && this.direction != -1) {
                this.direction = -1;
                final Iterator<Alien> i1 = this.alienFleet.iterator();

                while (i1.hasNext()) {
                    final Alien a2 = i1.next();
                    a2.setY(a2.getY() + Constants.GO_DOWN);
                }
            }

            if (x <= Constants.BORDER_LEFT && this.direction != 1) {
                this.direction = 1;
                final Iterator<Alien> i2 = this.alienFleet.iterator();

                while (i2.hasNext()) {
                    final Alien a = i2.next();
                    a.setY(a.getY() + Constants.GO_DOWN);
                }
            }
        }

        final Iterator<Alien> it = this.alienFleet.iterator();

        while (it.hasNext()) {
            final Alien alien = it.next();

            if (alien.isVisible()) {
                final int y = alien.getY();
                if (y > Constants.GROUND - Constants.ALIEN_HEIGHT) {
                    this.inGame = false;
                    this.GAME_OVER_MESSAGE = "Invasion!";
                }
                alien.act(this.direction);
            }
        }

        // Update bombs
        final Random randomGenerator = new Random();

        int shot;
        int bombX;
        int bombY;
        int playerX;
        int playerY;
        Bomb bomb;
        ImageIcon imageIcon;
        for (final Alien alien : this.alienFleet) {
            shot = randomGenerator.nextInt(15);
            bomb = alien.getBomb();

            if (shot == Constants.CHANCE && alien.isVisible() && bomb.isDestroyed()) {
                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            bombX = bomb.getX();
            bombY = bomb.getY();
            playerX = this.player.getX();
            playerY = this.player.getY();

            if (this.player.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX) && bombX <= (playerX + Constants.PLAYER_WIDTH) && bombY >= (playerY)
                        && bombY <= (playerY + Constants.PLAYER_HEIGHT)) {

                    imageIcon = new ImageIcon(this.IMAGE_LOCATION);
                    this.player.setImage(imageIcon.getImage());
                    this.player.setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {
                bomb.setY(bomb.getY() + 1);
                if (bomb.getY() >= Constants.GROUND - Constants.BOMB_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }
    }

    public void doGameCycle() {
        this.update();
        this.repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {

            Board.this.doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(final KeyEvent e) {
            Board.this.player.keyReleased(e);
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            Board.this.player.keyPressed(e);
            final int x = Board.this.player.getX();
            final int y = Board.this.player.getY();
            final int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {
                if (Board.this.inGame) {
                    if (!Board.this.shot.isVisible()) {
                        Board.this.shot = new Shot(x, y);
                    }
                }
            }
        }
    }
}
