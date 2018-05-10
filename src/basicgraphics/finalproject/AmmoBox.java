package basicgraphics.finalproject;
import basicgraphics.SpriteCollisionEvent;
public class AmmoBox extends CreateSprite{
    @Override
    public void processEvent(SpriteCollisionEvent se) {
        if(se.sprite2 instanceof Ninja) 
            setActive(false);
    }
}