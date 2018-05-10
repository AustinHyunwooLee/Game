package basicgraphics.finalproject;
import basicgraphics.SpriteCollisionEvent;
public class BossAttack1 extends CreateSprite{
    private final int BOSS_ATTACK1_TRAVEL_SPEED;
    private final int BOSS_ATTACK_FAST_TRAVEL_SPEED;
    public BossAttack1() {
        BOSS_ATTACK1_TRAVEL_SPEED =3;
        BOSS_ATTACK_FAST_TRAVEL_SPEED = 6;
    }
    @Override
    public void processEvent(SpriteCollisionEvent se) {
        if(se.sprite2 instanceof Ninja) setActive(false);
        if(se.xhi || se.xlo || se.yhi || se.ylo) setActive(false);
    }
    public int getBossAttack1TravelSpeed() {
        return BOSS_ATTACK1_TRAVEL_SPEED;
    }
    public int getBossFastAttack1TravelSpeed() {
        return BOSS_ATTACK_FAST_TRAVEL_SPEED;
    }
}