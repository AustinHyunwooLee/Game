package basicgraphics.finalproject;
public class DamageSplat extends CreateSprite{
    private double duration;
    public DamageSplat() {
        duration = 500;
    }
    @Override
    public void preMove(){
        if(duration > 0) duration -= 10;
        else setActive(false);
        setVelY(getVelY() + 0.05);
    }
}