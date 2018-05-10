package basicgraphics.finalproject;
import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.util.Random;
public class CreateSprite extends Sprite{
    Random rand = new Random();
    SpriteComponent sc;
    double duration;
    double walkSpeed;
    double runSpeed;
    public void create(SpriteComponent sc, Picture picture, double x, double y, int priority){
        setPicture(picture);
        setX(x);
        setY(y);
        this.sc = sc;
        sc.addSprite(this);
        setDrawingPriority(priority);
    }
    public void setWalkSpeed(double x){
        this.walkSpeed = x;
    }
    public void setRunSpeed(double x){
        this.runSpeed = x;
    }
    public void setAllSpeed(double x, double y){
        setWalkSpeed(x);
        setRunSpeed(y);
    }
    public void setVelXY(double x, double y){
        setVelX(x);
        setVelY(y);
    }
    public void stopMoving(){
        setVelX(0);
        setVelY(0);
    }
}