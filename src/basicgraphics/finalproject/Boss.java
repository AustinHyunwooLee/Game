package basicgraphics.finalproject;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
public class Boss extends EnemyAI{
    private boolean phase1;
    private boolean phase2;
    private boolean phase3;
    private static double BOSS_ATTACK_1_MAX_COOLDOWN;
    private Picture healthBarPicture;
    double xDifference;
    double yDifference;
    double absXDifference;
    double absYDifference;
    boolean bossAttack1CoolDown;
    double currentBossAttack1;
    CreateSprite healthBar;
    public Boss() {
        maxEnemyHealth = 500;
        enemyHealth = maxEnemyHealth;
        setWalkSpeed(1);
        setRunSpeed(2.5);
        BOSS_ATTACK_1_MAX_COOLDOWN = 2000;
        currentBossAttack1 = BOSS_ATTACK_1_MAX_COOLDOWN;
        healthBarPicture = new Picture("health_meter.png");
    }
    public void bossAttack1(){
        currentBossAttack1 = 0;
        Picture bossAttack1 = new Picture("UnguardedBoss.png");
        BossAttack1 attack1 = new BossAttack1();
        attack1.create(sc, bossAttack1, getX() + getWidth() / 2 - bossAttack1.getWidth() / 2, getY() + getHeight(), 2);
        double rad = angleCalculator(ninja, this);
        if(isCharging) {
            BOSS_ATTACK_1_MAX_COOLDOWN = 500;
            attack1.setVelXY(Math.cos(rad) * attack1.getBossFastAttack1TravelSpeed(), Math.sin(rad) * attack1.getBossFastAttack1TravelSpeed());
        } else 
            attack1.setVelXY(Math.cos(rad) * attack1.getBossAttack1TravelSpeed(), Math.sin(rad) * attack1.getBossAttack1TravelSpeed());
    }
    int wallSpawnTimer = 0;
    boolean isWallSpawn;
    static int wallCounter = 0;
    public void bossPhase2() {
        isWallSpawn = true;
        List<Integer> list = new ArrayList<>();
        Picture wall = new Picture("spike3.png");
        int openPosition = rand.nextInt(5);
        while(list.size() < 5){
            int spawnPosition = rand.nextInt(6);
            if(spawnPosition != openPosition){
                int i;
                for(i = 0; i < list.size(); i++)
                    if(list.get(i) == spawnPosition)
                        break;
                if(i == list.size())
                    list.add(spawnPosition);
            }
        }
        while(wallCounter < 5){
            Wall wallSprite = new Wall();
            wallSprite.create(sc, wall, list.get(wallCounter) * wall.getWidth() , 250, 3);
            wallSprite.setVelXY(0, 1.5);
            wallCounter++;
        }
    }
    public void bossSpecialAttack() {
        BOSS_ATTACK_1_MAX_COOLDOWN = 1500;
        currentBossAttack1 = 0;
        Picture bossAttack1 = new Picture("ganonball.png");
        BossAttack1 attack1 = new BossAttack1();
        attack1.create(sc, bossAttack1, getX() + getWidth() / 2 - bossAttack1.getWidth() / 2, getY() + getHeight(), 2);
        double rad = angleCalculator(ninja, this);
        attack1.setVelXY(Math.cos(rad) * attack1.getBossAttack1TravelSpeed(), Math.sin(rad) * attack1.getBossAttack1TravelSpeed());
    }
    public void healthBar(){
        healthBar = new CreateSprite();
        double setX = getX() + getWidth() / 2 - healthBarPicture.getWidth() / 2;
        double setY = getY() - healthBarPicture.getHeight() / 2;
        healthBar.create(sc, healthBarPicture, 150, 0, 2);
    }
    public void bossCombatMovement(double xDifference, double yDifference, double absXDifference, double absYDifference){
        double rad = Math.atan2(yDifference , xDifference);
        if(enemyHealth >= 2 * maxEnemyHealth / 3) {
            if(bossAttack1CoolDown){
                stopMoving();
                bossAttack1();
            }
        } else if (enemyHealth >= maxEnemyHealth /3) {
            if(bossAttack1CoolDown)
                bossAttack1();
            if(!isWallSpawn) {
                stopMoving();
                setX(400 - getWidth() / 2);
                setY(200 - getHeight() / 2);
                bossPhase2();
            }
        } else {
            if(bossAttack1CoolDown && !isWallSpawn)
                bossSpecialAttack();
            if(!isCharging && !isWallSpawn) {
                if(rand.nextInt(2) == 0)    
                    bossChargeToNinja(xDifference, yDifference);
                else    
                    bossChargeToCorner(xDifference, yDifference);
            }
        }
    }
    double targetX, targetY;
    boolean isCharging;
    public void bossChargeToCorner(double xDifference, double yDifference) {
        double xLeft = 0;
        double xRight = 800 - getWidth();
        double yUp = 0;
        double yDown = 800 - getHeight();
        isCharging = true;
        int xDirection = rand.nextInt(2);
        int yDirection = rand.nextInt(2);
        if(xDirection == 0)
            targetX = xLeft;
        else 
            targetX = xRight;
        if(yDirection == 0)
            targetY = yUp;
        else
            targetY = yDown;
        xDifference = targetX - getX();
        yDifference = targetY - getY();
        double rad = Math.atan2(yDifference, xDifference);
        setVelXY(Math.cos(rad) * runSpeed, Math.sin(rad) * runSpeed);
    }
    public void bossChargeToNinja(double xDifference, double yDifference) {
        isCharging = true;
        int xDirection = rand.nextInt(2);
        int yDirection = rand.nextInt(2);
        targetX = ninja.getX();
        targetY = ninja.getY();
        xDifference = targetX - getX();
        yDifference = targetY - getY();
        double rad = Math.atan2(yDifference, xDifference);
        setVelXY(Math.cos(rad) * runSpeed, Math.sin(rad) * runSpeed);
    }
    public double getAttackCoolDown () {
        return BOSS_ATTACK_1_MAX_COOLDOWN;
    }
    @Override
    public void preMove(){
        if(wallCounter == 0)
            isWallSpawn = false;
        if(Math.abs(getX() - targetX) < 10 && Math.abs(getY() - targetY) < 10)
            isCharging = false;
        if(Math.abs(getX()) < 20 || Math.abs(800 - getX()) < getWidth() + 50 || 
                Math.abs(getY()) < 50 || Math.abs(800 - getY()) < getHeight())
            isCharging = false;
        if(currentBossAttack1 < BOSS_ATTACK_1_MAX_COOLDOWN){
            currentBossAttack1 += 10;
            bossAttack1CoolDown = false;
        } else 
            bossAttack1CoolDown = true;
        xDifference = (ninja.getX() + ninja.getWidth() / 2) - (getX() + getWidth() / 2);
        yDifference = (ninja.getY() + ninja.getHeight() / 2) - (getY() + getHeight() / 2);
        absXDifference = Math.abs(xDifference);
        absYDifference = Math.abs(yDifference);
        bossCombatMovement(xDifference, yDifference, absXDifference, absYDifference);
    }
    @Override
    public void processEvent(SpriteCollisionEvent se) {
        if(se.sprite2 instanceof Projectile || se.sprite2 instanceof Bomb || se.sprite2 instanceof EmpoweredProjectile){
            DamageSplat damage = new DamageSplat();
            if(se.sprite2 instanceof Projectile){
                enemyHealth -= 5;
                System.out.println("Boss Health " + enemyHealth);
                damage.create(sc, new Picture("five.jpg"), rand.nextDouble() * getWidth() + getX(), getY() - 50, 3);
            }
            if(se.sprite2 instanceof Bomb) {
                enemyHealth -= 9;
                System.out.println("Boss Health " + enemyHealth);
                damage.create(sc, new Picture("nine.jpg"), rand.nextDouble() * getWidth() + getX(), getY() - 50, 3);
            }
            if(se.sprite2 instanceof EmpoweredProjectile){
                enemyHealth -= 7;
                System.out.println("Boss Health " + enemyHealth);
                damage.create(sc, new Picture("seven.jpg"), rand.nextDouble() * getWidth() + getX(), getY() - 50, 3);
            }
            damage.setVelX((rand.nextInt(3) - 1) * 2);
            damage.setVelY(-1);
            if(enemyHealth >=  9 * maxEnemyHealth / 10 - 1)
                healthBar.setPicture(new Picture("health_meter_1hit.png"));
            else if(enemyHealth >= 8 * maxEnemyHealth / 10 - 1)
                healthBar.setPicture(new Picture("health_meter_2hit.png"));
            else if(enemyHealth >= 7 * maxEnemyHealth / 10 - 1)
                healthBar.setPicture(new Picture("health_meter_3hit.png"));
            else if(enemyHealth >= 6 * maxEnemyHealth / 10 - 1)
                healthBar.setPicture(new Picture("health_meter_4hit.png"));
            else if(enemyHealth >= 5 * maxEnemyHealth / 10 - 1)
                healthBar.setPicture(new Picture("health_meter_5hit.png"));
            else if(enemyHealth >= 4 * maxEnemyHealth / 10 - 1)
                healthBar.setPicture(new Picture("health_meter_6hit.png"));
            else if(enemyHealth >= 3 * maxEnemyHealth / 10 - 1)
                healthBar.setPicture(new Picture("health_meter_7hit.png"));
            else if(enemyHealth >= 2 * maxEnemyHealth / 10 - 1)
                healthBar.setPicture(new Picture("health_meter_8hit.png"));
            else if(enemyHealth >= 1 * maxEnemyHealth / 10 - 1)
                healthBar.setPicture(new Picture("health_meter_9hit.png"));
            if(enemyHealth <= 0){
                System.out.println("The boss has been defeated!");
                healthBar.setActive(false);
                setActive(false);
                if(!isActive()){
                    JOptionPane.showMessageDialog(sc, "You win! Game Over!");
                    System.exit(0);
                }
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
    public void setMaxEnemyHealth(int i ) {
        maxEnemyHealth = i;
    }
    public void setEnemyHealth(int i) {
        enemyHealth = i;
    }
    public int getEnemyHealth() {
        return enemyHealth;
    }
}