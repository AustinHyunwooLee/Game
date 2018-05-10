/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicshooter;

import basicgraphics.BasicFrame;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author sbrandt
 */
public class Game {
    final public static Random RAND = new Random();
    final public static Color SHOOTER_COLOR = Color.blue;
    final public static Color BULLET_COLOR = Color.blue;
    final public static Color ENEMY_COLOR = Color.red;
    final public static Color EXPLOSION_COLOR = Color.orange;
    final public static int BIG = 20;
    final public static int SMALL = 5;
    final public static int ENEMIES = 10;
    final public static Dimension BOARD_SIZE = new Dimension(800,400);
    
    BasicFrame bf = new BasicFrame("Shooter!");
    
    static Picture makeBall(Color color,int size) {
        Image im = BasicFrame.createImage(size, size);
        Graphics g = im.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        return new Picture(im);
    }
    
    public void run() {
        final SpriteComponent sc = new SpriteComponent();
        sc.setPreferredSize(BOARD_SIZE);
        bf.add("center",sc,0,0,1,1);
        final Shooter shooter = new Shooter();
        shooter.init(sc);
        for(int i=0;i<ENEMIES;i++) {
            Enemy en = new Enemy();
            en.init(sc);
        }
        KeyAdapter key = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                Bullet b = new Bullet();
                b.init(sc,shooter,ke.getKeyCode());
            }
        };
        bf.addKeyListener(key);
        bf.addMenuAction("Help", "About", new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(bf.getContentPane(), "Have fun!");
            }
        });
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                Bullet b = new Bullet();
                b.init(sc,shooter,me.getX(),me.getY());
            }
        };
        sc.addMouseListener(ma);
        bf.show();
        sc.start(0, 10);
    }
    
    public static void main(String[] args) {
        Game g = new Game();
        g.run();
    }
}
