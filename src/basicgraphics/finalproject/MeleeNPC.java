package basicgraphics.finalproject;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.images.Picture;
import java.util.Random;
public class MeleeNPC extends EnemyAI{
    static int numMeleeNPCAlive;
    Picture meleeDownSheathed;
    Picture meleeDownDrawn;
    Picture meleeUpSheathed;
    Picture meleeUpDrawn;
    Picture meleeLeftSheathed;
    Picture meleeLeftDrawn;
    Picture meleeRightSheathed;
    Picture meleeRightDrawn;
    public MeleeNPC() {
        enemyHealth = 25;
        setWalkSpeed(0.5);
        setRunSpeed(1);
        MIN_RANGE = 5;
        meleeDownSheathed = new Picture("gladiator_facingdown_sheathed.png");
        meleeDownDrawn = new Picture("gladiator_facingdown_drawn.png");
        meleeUpSheathed = new Picture("gladiator_facingup_sheathed.png");
        meleeUpDrawn = new Picture("gladiator_facingdown_drawn.png");
        meleeLeftSheathed = new Picture("gladiator_facingleft_sheathed.png");
        meleeLeftDrawn = new Picture("gladiator_facingdown_drawn.png");
        meleeRightSheathed = new Picture("gladiator_facingright_sheathed.png");
        meleeRightDrawn = new Picture("gladiator_facingdown_drawn.png");
    }
    public void combatMovement(double xDifference, double yDifference, double absXDifference, double absYDifference){
        double rad = Math.atan2(yDifference , xDifference);
        if(absXDifference >= MIN_RANGE + getWidth() / 2 || absYDifference >= MIN_RANGE + getHeight() / 2){
            setPicture(meleeDownSheathed);
            setVelXY(Math.cos(rad) * runSpeed, Math.sin(rad) * runSpeed);
        } else 
            setPicture(meleeDownDrawn);
    }
    public void processEvent(SpriteCollisionEvent se) {
        if(se.sprite2 instanceof Projectile || se.sprite2 instanceof Bomb || se.sprite2 instanceof EmpoweredProjectile) {
            DamageSplat damage = new DamageSplat();
            if(se.sprite2 instanceof Projectile){
                setEnemyHealthLost(5);
                damage.create(sc, new Picture("five.jpg"), rand.nextDouble() * getWidth() + getX(), getY() - 50, 3);
            }
            if(se.sprite2 instanceof Bomb){
                setEnemyHealthLost(9);
                damage.create(sc, new Picture("nine.jpg"), rand.nextDouble() * getWidth() + getX(), getY() - 50, 3);
            } 
            if(se.sprite2 instanceof EmpoweredProjectile) {
                setEnemyHealthLost(7);
                damage.create(sc, new Picture("seven.jpg"), rand.nextDouble() * getWidth() + getX(), getY() - 50, 3);
            }
            damage.setVelX((rand.nextInt(3) - 1) * 2);
            damage.setVelY(-1);
            if(enemyHealth <= 0){
                if(rand.nextInt(2) == 0)
                    new HealthBox().create(sc, new Picture("HealthBox.png"), getX(), getY(), 2);
                numMeleeNPCAlive--;
                setActive(false);
            }
        }
        if(getX() <= 0)
            setX(0 + walkSpeed);
        if (getX() + getWidth() >= sc.getSize().width)
            setX(sc.getSize().width - getWidth() - walkSpeed);   
        if(getY() <= 0)
            setY(0 + walkSpeed);
        if (getY() + getHeight() >= sc.getSize().height)
            setY(sc.getSize().height - getHeight() - walkSpeed);
    }
}