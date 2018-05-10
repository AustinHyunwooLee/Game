package basicgraphics.finalproject;
import basicgraphics.SpriteCollisionEvent;
public class Bomb extends CreateSprite{
    final double SPEED_X;
    final double SPEED_Y;
    final double GRAVITY;
    public Bomb () {
        SPEED_X = 2;
        SPEED_Y = -2;
        GRAVITY = 0.03;
    }
    public void preMove(){
        setVelY(getVelY() + GRAVITY);
    }
    @Override
    public void processEvent(SpriteCollisionEvent se) {
        if(se.sprite2 instanceof Boss || se.sprite2 instanceof MeleeNPC) 
            setActive(false);
    }
}