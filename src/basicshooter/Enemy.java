/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicshooter;

import basicgraphics.CollisionEventType;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.sounds.ReusableClip;
import java.applet.AudioClip;
import javax.swing.JOptionPane;

/**
 *
 * @author sbrandt
 */
public class Enemy extends Sprite {
    SpriteComponent sc;
    static int enemyCount;
    
    Enemy() {
        enemyCount++;
    }
    
    @Override
    public void setActive(boolean b) {
        if(isActive() == b)
            return;
        if(b)
            enemyCount++;
        else
            enemyCount--;
        super.setActive(b);
    }

    public void init(SpriteComponent sc) {
        setPicture(Game.makeBall(Game.ENEMY_COLOR, Game.BIG));
        while (true) {
            setX(Game.RAND.nextInt(Game.BOARD_SIZE.width)-Game.SMALL);
            setY(Game.RAND.nextInt(Game.BOARD_SIZE.height)-Game.SMALL);
            if (Math.abs(getX() - Game.BOARD_SIZE.width / 2) < 2 * Game.BIG
                    && Math.abs(getY() - Game.BOARD_SIZE.height / 2) < 2 * Game.BIG) {
                // Overlaps with center, try again
            } else {
                break;
            }
        }
        // A random speed
        setVelX(2 * Game.RAND.nextDouble() - 1);
        setVelY(2 * Game.RAND.nextDouble() - 1);
        sc.addSprite(this);
        this.sc = sc;
    }
    
    AudioClip clip = new ReusableClip("die.wav");

    @Override
    public void processEvent(SpriteCollisionEvent se) {
        if(se.eventType == CollisionEventType.WALL_INVISIBLE) {
            if (se.xlo) {
                setX(sc.getSize().width - getWidth());
            }
            if (se.xhi) {
                setX(0);
            }
            if (se.ylo) {
                setY(sc.getSize().height - getHeight());
            }
            if (se.yhi) {
                setY(0);
            }
        }

        if (se.eventType == CollisionEventType.SPRITE) {
            if (se.sprite2 instanceof Shooter) {
                clip.play();
                se.sprite2.setActive(false);
                JOptionPane.showMessageDialog(sc, "You lose! Game Over!");
                System.exit(0);
            }
            if (se.sprite2 instanceof Bullet) {
                clip.play();
                setActive(false);
                se.sprite2.setActive(false);
                if(enemyCount == 0) {
                    JOptionPane.showMessageDialog(sc, "You win! Game Over!");
                    System.exit(0);
                }
            }
        }
    }
}
