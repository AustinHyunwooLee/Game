package basicgraphics.finalproject;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.images.Picture;
public final class Projectile extends CreateSprite{
    static double coolDown;
    final double SPEED;
    final double MAX_RANGE;
    boolean fire;
    public Projectile() {
        SPEED = 5;
        MAX_RANGE = 200;
        coolDown = 500;
    }
    @Override
    public void processEvent(SpriteCollisionEvent se) {
        if(se.sprite2 instanceof Boss) 
            setActive(false);
        if(se.sprite2 instanceof MeleeNPC) 
            setActive(false);
        if(se.xhi || se.xlo || se.yhi || se.ylo)
            setActive(false);
    }
}