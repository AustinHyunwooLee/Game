package basicgraphics.finalproject;
import basicgraphics.CollisionEventType;
import basicgraphics.SpriteCollisionEvent;
public class Wall extends Boss {
    public void preMove() { }
    @Override
    public void processEvent(SpriteCollisionEvent se) {
        if(se.eventType == CollisionEventType.WALL){
            Boss.wallCounter--;
            setActive(false);
        }
    }
}