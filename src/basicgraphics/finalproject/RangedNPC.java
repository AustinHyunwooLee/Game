package basicgraphics.finalproject;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.images.Picture;
public class RangedNPC extends Boss{
    static int numRangedNPCAlive;
    public RangedNPC(){
        setMaxEnemyHealth(25);
        setEnemyHealth(25);
    }
    public void rangedNPCMovement(double xDifference, double yDifference, double absXDifference, double absYDifference){
        double rad = Math.atan2(yDifference , xDifference);
        if(!isCharging && absXDifference >= MIN_RANGE + getWidth() / 2 || absYDifference >= MIN_RANGE + getHeight() / 2)
            setVelXY(Math.cos(rad) * walkSpeed, Math.sin(rad) * walkSpeed);
        else { 
            stopMoving();
            if(bossAttack1CoolDown)
                bossAttack1();
        }
    }
    @Override
    public void preMove(){
        if(Math.abs(getX() - targetX) < 10 && Math.abs(getY() - targetY) < 10)
            isCharging = false;
        if(currentBossAttack1 < getAttackCoolDown()){
            currentBossAttack1 += 10;
            bossAttack1CoolDown = false;
        } else
            bossAttack1CoolDown = true;
            xDifference = (ninja.getX() + ninja.getWidth() / 2) - (getX() + getWidth() / 2);
            yDifference = (ninja.getY() + ninja.getHeight() / 2) - (getY() + getHeight() / 2);
            absXDifference = Math.abs(xDifference);
            absYDifference = Math.abs(yDifference);
            rangedNPCMovement(xDifference, yDifference, absXDifference, absYDifference);
    }
    public void processEvent(SpriteCollisionEvent se) {
        if(se.sprite2 instanceof Projectile || se.sprite2 instanceof Bomb || se.sprite2 instanceof EmpoweredProjectile) {
            
            DamageSplat damage = new DamageSplat();
            if(se.sprite2 instanceof Projectile) {
                setEnemyHealthLost(5);
                damage.create(sc, new Picture("five.jpg"), rand.nextDouble() * getWidth() + getX(), getY() - 50, 3);
            }
            if(se.sprite2 instanceof Bomb) {
                setEnemyHealthLost(9);
                damage.create(sc, new Picture("nine.jpg"), rand.nextDouble() * getWidth() + getX(), getY() - 50, 3);
            }
            if(se.sprite2 instanceof EmpoweredProjectile) {
                setEnemyHealthLost(7);
                damage.create(sc, new Picture("seven.jpg"), rand.nextDouble() * getWidth() + getX(), getY() - 50, 3);
            }
            damage.setVelX((rand.nextInt(3) - 1) * 2);
            damage.setVelY(-1);
            if(getEnemyHealth() <= 0){
                if(rand.nextInt(2) == 0)
                    new AmmoBox().create(sc, new Picture("ManaBox.png"), getX(), getY(), 2);
                numRangedNPCAlive --;
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