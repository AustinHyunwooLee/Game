/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package walkthedog;

import basicgraphics.BasicFrame;
import basicgraphics.CollisionEventType;
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
class Dog extends Sprite {
    Picture basePic;
    Dog() throws IOException {
        basePic = new Picture("dog.jpg");
        setPicture(basePic);
    }
    int count = 0;
    @Override
    public void preMove() {
        count++;
        if(count == 100)
            setPicture(basePic.rotate(.1));
        else if(count == 200) {
            setPicture(basePic.rotate(-.1));
            count=0;
        }
    }
    @Override
    public void processEvent(SpriteCollisionEvent ev) {
        if(ev.eventType == CollisionEventType.WALL_INVISIBLE) {
            setX(800);
            basePic = basePic.resize(1.1);
        }
    }
}
public class WalkDog {
    public static void main(String[] args) throws IOException {
        Dog dog = new Dog();
        dog.setVelX(-1.0);
        dog.setX(750);
        
        BasicFrame bf = new BasicFrame("Walk the Dog");
        SpriteComponent sc = new SpriteComponent();
        bf.add("dog",sc,0,0,1,1);
        sc.setPreferredSize(new Dimension(800,400));
        sc.addSprite(dog);
        bf.show();
        sc.start(0, 10);
    }
}
