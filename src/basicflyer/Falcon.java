/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicflyer;

import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.Dimension;
import java.io.IOException;

/**
 *
 * @author sbrandt
 */
public class Falcon extends Sprite {
    public Picture initialPic;
    /**
     * Initializes the sprite, setting its picture,
     * position, and speed. It also adds it to the
     * SpriteComponent.
     * 
     * @param sc
     * @throws IOException 
     */
    public void init(SpriteComponent sc) throws IOException {
        initialPic = new Picture("mfalcon.png");
        setPicture(initialPic);
        Dimension d = sc.getSize();
        setX(d.width/2);
        setY(d.height/2);
        setVelX(1);
        setVelY(0);
        this.sc = sc;
        sc.addSprite(this);
    }
    
    public void turn(double incr) {
        double heading = Math.atan2(getVelY(),getVelX());
        heading += incr;
        setVelY(Math.sin(heading));
        setVelX(Math.cos(heading));
        setPicture(initialPic.rotate(heading));
    }
    
    SpriteComponent sc;
    /**
     * Here we update the velocity and picture
     * if they need updating.
     */
    @Override
    public void preMove() {
    }
    /**
     * This sprite only reacts to collisions with the
     * borders of the display region. When it does, it
     * wraps to the other side.
     * @param se 
     */
    @Override
    public void processEvent(SpriteCollisionEvent se) {
        if (se.xlo) {
            setX(sc.getSize().width-getWidth());
        }
        if (se.xhi) {
            setX(0);
        }
        if (se.ylo) {
            setY(sc.getSize().height-getHeight());
        }
        if (se.yhi) {
            setY(0);
        }
    }
}
