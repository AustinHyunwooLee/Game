package basicgraphics.finalproject;
import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.images.Picture;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
public class Ninja extends CreateSprite{
    int numSpawnRangedNPC;
    int numSpawnMeleeNPC;
    int invulnerableTimer;
    final int MAX_NORMAL_ATTACK_AMMO;
    final int INVULNERABLE_DURATION;
    int currentEmpoweredAttackAmmo;
    Picture ninjaSpriteSheet = new Picture("Died.png");
    BufferedImage walkSpriteSheet = (BufferedImage)ninjaSpriteSheet.getImage();
    Picture rwalk1, rwalk2, rwalk3, rwalk4, rwalk5, rwalk6, rwalk7, rwalk8, rwalk9;
    Picture lwalk1, lwalk2, lwalk3, lwalk4, lwalk5, lwalk6, lwalk7, lwalk8, lwalk9; 
    Picture uwalk1, uwalk2, uwalk3, uwalk4, uwalk5, uwalk6; 
    Picture dwalk1, dwalk2, dwalk3, dwalk4, dwalk5, dwalk6;
    private int column;
    private int row;
    private int widthWalk;
    private int heightWalk;
    static int frame;
    private final int fps;
    private boolean up, down, left, right, isInvulnerable;
    private final int NORMAL_ATTACK_COOLDOWN;
    private final int MAX_RUN_DURATION;
    private final int BOMB_ATTACK_COOLDOWN;
    private static boolean normalAttackOnCoolDown;
    private static boolean bombAttackOnCoolDown;
    private static double currentNormalAttackCoolDown;
    private static double currentBombAttackCoolDown;
    private final double walkSpeed;
    private final double runSpeed;
    public double directionX;
    public double directionY;
    public double runDuration;
    private boolean run;
    private int ninjaHealth;
    private CreateSprite healthBar1 = new CreateSprite();
    private CreateSprite healthBar2 = new CreateSprite();
    private CreateSprite healthBar3 = new CreateSprite();
    Picture one = new Picture("one.jpg").resize(.5); 
    Picture two = new Picture("two.jpg").resize(.5); 
    Picture three = new Picture("three.jpg").resize(.5);
    Picture four = new Picture("four.jpg").resize(.5); 
    Picture five = new Picture("five.jpg").resize(.5); 
    Picture six = new Picture("six.jpg").resize(.5); 
    Picture seven = new Picture("seven.jpg").resize(.5); 
    Picture eight = new Picture("eight.jpg").resize(.5); 
    Picture nine = new Picture("nine.jpg").resize(.5);
    Picture zero = new Picture("zero.jpg").resize(.5);
    Picture hundredPlace = one;
    Picture tenPlace = zero;
    Picture onePlace = zero;
    int healthOnesPlace;
    int healthTensPlace;
    int healthHundredPlace;
    public Ninja(){
        run = false;
        walkSpeed = 3.5;
        runSpeed = 10;
        directionX = 1;
        directionY = 0;
        ninjaHealth = 100;
        MAX_RUN_DURATION = 500;
        runDuration = MAX_RUN_DURATION;
        NORMAL_ATTACK_COOLDOWN = 250;
        currentNormalAttackCoolDown = NORMAL_ATTACK_COOLDOWN;
        BOMB_ATTACK_COOLDOWN = 1000;
        currentBombAttackCoolDown = BOMB_ATTACK_COOLDOWN;
        setWalkingAnimation(80,80);
        MAX_NORMAL_ATTACK_AMMO = 100;
        INVULNERABLE_DURATION = 800;
        currentEmpoweredAttackAmmo = 0;
        fps =50;
        normalAttackOnCoolDown = true;
        bombAttackOnCoolDown = true;
        healthOnesPlace = 0;
        healthTensPlace = 0;
        healthHundredPlace = 1;
    }
    public FlipLeftRightPicture setWalkingAnimation(int column, int row, int width, int height) {
        return new FlipLeftRightPicture(walkSpriteSheet.getSubimage(column * width - width, row * height - height, width, height));
    }
    public void setWalkingAnimation(int width, int height) {
        uwalk1 =  setWalkingAnimation(1, 7, width, height);
        uwalk2 =  setWalkingAnimation(2, 7, width, height);
        uwalk3 =  setWalkingAnimation(3, 7, width, height);
        uwalk4 =  setWalkingAnimation(4, 7, width, height);
        uwalk5 =  setWalkingAnimation(5, 7, width, height);
        uwalk6 =  setWalkingAnimation(6, 7, width, height);
        lwalk1 =  setWalkingAnimation(1, 1, width, height).flipLeftRight();
        lwalk2 =  setWalkingAnimation(2, 1, width, height).flipLeftRight();
        lwalk3 =  setWalkingAnimation(3, 1, width, height).flipLeftRight();
        lwalk4 =  setWalkingAnimation(4, 1, width, height).flipLeftRight();
        lwalk5 =  setWalkingAnimation(5, 1, width, height).flipLeftRight();
        lwalk6 =  setWalkingAnimation(6, 1, width, height).flipLeftRight();
        lwalk7 =  setWalkingAnimation(7, 1, width, height).flipLeftRight();
        lwalk8 =  setWalkingAnimation(8, 1, width, height).flipLeftRight();
        lwalk9 =  setWalkingAnimation(9, 1, width, height).flipLeftRight();
        dwalk1 =  setWalkingAnimation(7, 6, width, height);
        dwalk2 =  setWalkingAnimation(8, 6, width, height);
        dwalk3 =  setWalkingAnimation(9, 6, width, height);
        dwalk4 =  setWalkingAnimation(10, 6, width, height);
        dwalk5 =  setWalkingAnimation(11, 6, width, height);
        dwalk6 =  setWalkingAnimation(12, 6, width, height);
        rwalk1 =  setWalkingAnimation(1, 1, width, height);
        rwalk2 =  setWalkingAnimation(2, 1, width, height);
        rwalk3 =  setWalkingAnimation(3, 1, width, height);
        rwalk4 =  setWalkingAnimation(4, 1, width, height);
        rwalk5 =  setWalkingAnimation(5, 1, width, height);
        rwalk6 =  setWalkingAnimation(6, 1, width, height);
        rwalk7 =  setWalkingAnimation(7, 1, width, height);
        rwalk8 =  setWalkingAnimation(8, 1, width, height);
        rwalk9 =  setWalkingAnimation(9, 1, width, height);
    }
    public void setHP() {
        healthBar1.create(sc, hundredPlace, getX() + 13, getY() - getHeight(), 2);
        healthBar2.create(sc, tenPlace, getX() + getWidth() / 2 - tenPlace.getWidth() / 2, getY() - getHeight(), 2);
        healthBar3.create(sc, onePlace, getX() + getWidth() - onePlace.getWidth() - 11, getY() - getHeight(), 2);
    }
    public boolean compareRight() {
        if(getPicture() == rwalk1 || getPicture() == rwalk2 || getPicture() == rwalk3 || getPicture() == rwalk4 || 
                getPicture() == rwalk5 || getPicture() == rwalk6 || getPicture() == rwalk7 || 
                getPicture() == rwalk8 || getPicture() == rwalk9 )
            return true;
        return false;
    }
    public boolean compareLeft() {
        if(getPicture() == lwalk1 || getPicture() == lwalk2 || getPicture() == lwalk3 || getPicture() == lwalk4 || 
                getPicture() == lwalk5 || getPicture() == lwalk6 || getPicture() == lwalk7 || 
                getPicture() == lwalk8 || getPicture() == lwalk9 )
            return true;
        return false;
    }
    public boolean compareUp() {
        if(getPicture() == uwalk1 || getPicture() == uwalk2 || getPicture() == uwalk3 || getPicture() == uwalk4 || 
                getPicture() == uwalk5 || getPicture() == uwalk6)
            return true;
        return false;
    }
    public boolean compareDown() {
        if(getPicture() == dwalk1 || getPicture() == dwalk2 || getPicture() == dwalk3 || getPicture() == dwalk4 || 
                getPicture() == dwalk5 || getPicture() == dwalk6)
            return true;
        return false;
    }
    public boolean getNormalAttackOnCoolDown (){ 
        return normalAttackOnCoolDown; 
    }
    public boolean getBombAttackOnCoolDown () { 
        return bombAttackOnCoolDown; 
    }
    public Picture getFacingRightPicture() { 
        return rwalk1; 
    }
    public Picture getFacingLeftPicture() { 
        return lwalk1; 
    }
    public Picture getFacingUpPicture() { 
        return uwalk1; 
    }
    public Picture getFacingDownPicture() { 
        return dwalk1; 
    }
    public void setUp(boolean a){ 
        up = a;
    }
    public void setDown(boolean a){ 
        down = a;
    }
    public void setLeft(boolean a){ 
        left = a;
    }
    public void setRight(boolean a){ 
        right = a; 
    }
    public void setRun(boolean a) { 
        run = a;
    }
    public void rangedAttack(MouseEvent me){
        int mouseX = me.getX();
        int mouseY = me.getY();
        currentNormalAttackCoolDown = 0;
        Projectile attack1 = new Projectile();
        Picture attack1Graphics = new Picture("star.png").resize(3);
        Picture directionFacing = getPicture();
        if(compareRight())
            attack1.create(sc, attack1Graphics, getX() + getWidth(), getY() + (getHeight() / 4), 2);
        else if(compareLeft())
            attack1.create(sc, attack1Graphics, getX() - attack1Graphics.getWidth(), getY() + (getHeight() / 4), 2);
        else if(compareUp())
            attack1.create(sc, attack1Graphics, getX() + getWidth() / 2 - attack1Graphics.getWidth() / 2, getY(), 2);
        else if(compareDown()) 
            attack1.create(sc, attack1Graphics, getX() + getWidth() / 2 - attack1Graphics.getWidth() / 2, getY() + (getWidth() / 2) + (getHeight() / 4), 2);
        direction();
        double rad = Math.atan2(attack1.getY() - mouseY, attack1.getX() - mouseX);
        attack1.setVelXY(-Math.cos(rad) * attack1.SPEED, -Math.sin(rad) * attack1.SPEED);
    }
     public void rangedAttack(){
        currentNormalAttackCoolDown = 0;
        Projectile attack1 = new Projectile();
        Picture attack1Graphics = new Picture("star.png").resize(3);
        if(compareRight())
            attack1.create(sc, attack1Graphics, getX() + getWidth(), getY() + (getHeight() / 4), 2);
        else if (compareLeft())
            attack1.create(sc, attack1Graphics, getX() - attack1Graphics.getWidth(), getY() + (getHeight() / 4), 2);
        else if (compareUp())
            attack1.create(sc, attack1Graphics, getX() + getWidth() / 2 - attack1Graphics.getWidth() / 2, getY(), 2);
        else if(compareDown())
            attack1.create(sc, attack1Graphics, getX() + getWidth() / 2 - attack1Graphics.getWidth() / 2, getY() + (getWidth() / 2) + (getHeight() / 4), 2);
        direction();
        attack1.setVelXY(directionX * attack1.SPEED, directionY * attack1.SPEED);
    }
     public void rangedEmpoweredAttack(MouseEvent me){
        int mouseX = me.getX();
        int mouseY = me.getY();
        currentNormalAttackCoolDown = 0;
        currentEmpoweredAttackAmmo--;
        EmpoweredProjectile attack1 = new EmpoweredProjectile();
        Picture attack1Graphics = new Picture("Fireball.png").resize(.2);
        Picture directionFacing = getPicture();
        if(compareRight())
            attack1.create(sc, attack1Graphics, getX() + getWidth(), getY() + (getHeight() / 4), 2);
        else if(compareLeft())
            attack1.create(sc, attack1Graphics, getX() - attack1Graphics.getWidth(), getY() + (getHeight() / 4), 2);
        else if(compareUp())
            attack1.create(sc, attack1Graphics, getX() + getWidth() / 2 - attack1Graphics.getWidth() / 2, getY(), 2);
        else if(compareDown()) 
            attack1.create(sc, attack1Graphics, getX() + getWidth() / 2 - attack1Graphics.getWidth() / 2, getY() + (getWidth() / 2) + (getHeight() / 4), 2);
        direction();
        double rad = Math.atan2(attack1.getY() - mouseY, attack1.getX() - mouseX);
        attack1.setVelXY(-Math.cos(rad) * attack1.SPEED, -Math.sin(rad) * attack1.SPEED);
    }
     public void rangedEmpoweredAttack(){
        currentNormalAttackCoolDown = 0;
        currentEmpoweredAttackAmmo--;
        EmpoweredProjectile attack1 = new EmpoweredProjectile();
        Picture attack1Graphics = new Picture("Fireball.png").resize(.2);
        if(compareRight())
            attack1.create(sc, attack1Graphics, getX() + getWidth(), getY() + (getHeight() / 4), 2);
        else if (compareLeft())
            attack1.create(sc, attack1Graphics, getX() - attack1Graphics.getWidth(), getY() + (getHeight() / 4), 2);
        else if (compareUp())
            attack1.create(sc, attack1Graphics, getX() + getWidth() / 2 - attack1Graphics.getWidth() / 2, getY(), 2);
        else if(compareDown())
            attack1.create(sc, attack1Graphics, getX() + getWidth() / 2 - attack1Graphics.getWidth() / 2, getY() + (getWidth() / 2) + (getHeight() / 4), 2);
        direction();
        attack1.setVelXY(directionX * attack1.SPEED, directionY * attack1.SPEED);
    }
    public void bombAttack(){
        Bomb bomb = new Bomb();
        bomb.create(sc, new Picture("orb.png").resize(.5), getX(), getY(), 2);
        direction();
        currentBombAttackCoolDown = 0;
        bomb.setVelXY(directionX * bomb.SPEED_X, bomb.SPEED_Y);
    }
    public void preMove(){
        if(isInvulnerable) {
            setPicture(setWalkingAnimation(4, 12, 80, 80));
            if(invulnerableTimer < INVULNERABLE_DURATION)
                invulnerableTimer += 10;
            else
                isInvulnerable = false;
        }
        if(healthBar1.isActive()) {
            healthBar1.setX(getX() + 13);
            healthBar1.setY(getY() - getHeight() / 2);
        }
        if(healthBar2.isActive()) {
            healthBar2.setX(getX() + getWidth() / 2 - tenPlace.getWidth() / 2);
            healthBar2.setY(getY() - getHeight() / 2);
        }
        if(healthBar3.isActive()) {
            healthBar3.setX(getX() + getWidth() - onePlace.getWidth() - 11);
            healthBar3.setY(getY() - getHeight() / 2);
        }
        Picture picture;
        if(frame >= 9 * fps)
            frame = 0;
        else 
            frame += 10;
        if(up) {
            if(frame >= 6 * fps) picture = uwalk6;
            else if(frame >= 5 * fps) picture = uwalk5;
            else if(frame >= 4 * fps) picture = uwalk4;
            else if(frame >= 3 * fps) picture = uwalk3;
            else if(frame >= 2 * fps) picture = uwalk2;
            else picture = uwalk1;
            if(!isInvulnerable)
                setPicture(picture);
            if(run) setVelY(-runSpeed);
            else setVelY(-walkSpeed);
        }
        if(down) {
            if(frame >= 6 * fps) picture = dwalk6;
            else if(frame >= 5 * fps) picture = dwalk5;
            else if(frame >= 4 * fps) picture = dwalk4;
            else if(frame >= 3 * fps) picture = dwalk3;
            else if(frame >= 2 * fps) picture = dwalk2;
            else picture = dwalk1;
            if(!isInvulnerable)
                setPicture(picture);
            if(run) setVelY(runSpeed);
            else setVelY(walkSpeed);
        }
        if(left) {
            if(frame >= 8 * fps) picture = lwalk9;
            else if(frame >= 7 * fps) picture = lwalk8;
            else if(frame >= 6 * fps) picture = lwalk6;
            else if(frame >= 5 * fps) picture = lwalk5;
            else if(frame >= 4 * fps) picture = lwalk4;
            else if(frame >= 3 * fps) picture = lwalk3;
            else if(frame >= 2 * fps) picture = lwalk2;
            else picture = lwalk1;
            if(!isInvulnerable)
                setPicture(picture);
            if(run) setVelX(-runSpeed);
            else setVelX(-walkSpeed);
        }
        if(right) {
            if(frame >= 8 * fps) picture = rwalk9;
            else if(frame >= 7 * fps) picture = rwalk8;
            else if(frame >= 6 * fps) picture = rwalk6;
            else if(frame >= 5 * fps) picture = rwalk5;
            else if(frame >= 4 * fps) picture = rwalk4;
            else if(frame >= 3 * fps) picture = rwalk3;
            else if(frame >= 2 * fps) picture = rwalk2;
            else picture = rwalk1;
            if(!isInvulnerable)
                setPicture(picture);
            if(run) setVelX(runSpeed);
            else setVelX(walkSpeed);
        }
        if(run && runDuration >= 0)
            runDuration -= 10;
        else if (!run && runDuration <= MAX_RUN_DURATION)
            runDuration +=5;
        else 
            run = false;
        if(currentNormalAttackCoolDown < NORMAL_ATTACK_COOLDOWN) {
            currentNormalAttackCoolDown += 10;
            normalAttackOnCoolDown = false;
        } else
            normalAttackOnCoolDown = true;
        if(currentBombAttackCoolDown < BOMB_ATTACK_COOLDOWN){
            currentBombAttackCoolDown += 10;
            bombAttackOnCoolDown = false;
        } else
            bombAttackOnCoolDown = true;
        if(RangedNPC.numRangedNPCAlive == 0 && MeleeNPC.numMeleeNPCAlive == 0) {
            if((numSpawnRangedNPC + numSpawnMeleeNPC) % 2 == 0) 
                numSpawnRangedNPC += 1;
            else 
                numSpawnMeleeNPC += 1;
            RangedNPC.numRangedNPCAlive = numSpawnRangedNPC;
            MeleeNPC.numMeleeNPCAlive = numSpawnMeleeNPC;
            System.out.println("numSpawnRanedNPC " + numSpawnRangedNPC);
            if(numSpawnRangedNPC < 4) {
                int x;
                int y;
                for(int i = 0; i < numSpawnRangedNPC; i++) {
                    RangedNPC rangedNPC = new RangedNPC();
                    Picture enemy = new Picture("ReverseWizard.png");
                    x = rand.nextInt(740) + enemy.getWidth() / 2;
                    y = rand.nextInt(740) + enemy.getHeight() / 2;
                    while(Math.abs(getX() - x) < 200 || Math.abs(getY() - y) < 200) {
                        x = rand.nextInt(740) + enemy.getWidth() / 2;
                        y = rand.nextInt(740) + enemy.getHeight() / 2;
                    }
                    rangedNPC.create(sc, enemy, rand.nextInt(740) + enemy.getWidth() / 2, rand.nextInt(740) + enemy.getHeight() / 2, 2);
                    rangedNPC.setNinja(this);
                }
                for(int i = 0; i < numSpawnMeleeNPC; i++) {
                    MeleeNPC meleeNPC = new MeleeNPC();
                    Picture enemy = new Picture("gladiator_facingdown_sheathed.png");
                    x = rand.nextInt(740) + enemy.getWidth() / 2;
                    y = rand.nextInt(740) + enemy.getHeight() / 2;
                    while(Math.abs(getX() - x) < 200 || Math.abs(getY() - y) < 200) {
                        x = rand.nextInt(740) + enemy.getWidth() / 2;
                        y = rand.nextInt(740) + enemy.getHeight() / 2;}
                    meleeNPC.create(sc, enemy, rand.nextInt(740) + enemy.getWidth() / 2, rand.nextInt(740) + enemy.getHeight() / 2, 2);
                    meleeNPC.setNinja(this);}
            } else{
                Boss boss = new Boss();
                Picture bossPicture = new Picture("Ganon.png");
                boss.create(sc, bossPicture, 400 - bossPicture.getWidth() / 2 , 200 - bossPicture.getHeight() / 2, 2);
                boss.setNinja(this);
                boss.healthBar();   
                HealthBox hb1 = new HealthBox();
                HealthBox hb2 = new HealthBox();
                HealthBox hb3 = new HealthBox();
                HealthBox hb4 = new HealthBox();
                Picture healthBox = new Picture("HealthBox.png");
                hb1.create(sc, healthBox, healthBox.getWidth() , healthBox.getHeight(), 2);
                hb2.create(sc, healthBox, healthBox.getWidth() , 800 - 2 * healthBox.getHeight(), 2);
                hb3.create(sc, healthBox, 800 - 2 * healthBox.getWidth() , healthBox.getHeight(), 2);
                hb4.create(sc, healthBox, 800 - 2 * healthBox.getWidth() , 800 - 2 * healthBox.getHeight(), 2);
            }
        }
    }
    public void direction(){
        if(compareRight()){
            directionX = 1;
            directionY = 0;
        } else if (compareLeft()) {
            directionX = -1;
            directionY = 0;
        } else if (compareUp()) {
            directionX = 0;
            directionY = -1;
        } else if (compareDown()) {
            directionX = 0;
            directionY = 1;
        }
    }
    public double knockBack(Sprite attacker){
        return Math.atan2(attacker.getVelX(), attacker.getVelY());
    }
    @Override
    public void processEvent(SpriteCollisionEvent se) {
        if(se.sprite2 instanceof HealthBox) {
            ninjaHealth += 25;
            System.out.println("Health: " + ninjaHealth);
        }
        if(se.sprite2 instanceof AmmoBox){
            currentEmpoweredAttackAmmo += 10;
            System.out.println("Ammo: " + currentEmpoweredAttackAmmo);
        }
        if(se.sprite2 instanceof MeleeNPC || se.sprite2 instanceof BossAttack1 || se.sprite2 instanceof Boss
                || se.sprite2 instanceof Wall) {
            if(!isInvulnerable){
                invulnerableTimer = 0;
                ninjaHealth -= 10;
                double rad = knockBack(se.sprite2);
                setX(getX() + 100 * Math.sin(rad) );
                setY(getY() + 100 * Math.cos(rad));
                System.out.println("Health: " + ninjaHealth);
                isInvulnerable = true;
            }
        }
        if(ninjaHealth <= 0){
            setActive(false);
            if(!isActive()){
                JOptionPane.showMessageDialog(sc, "You died! Game Over!");
                System.exit(0);
            }
            healthBar1.setActive(false);
            healthBar2.setActive(false);
            healthBar3.setActive(false);
        }
        healthOnesPlace = ninjaHealth % 10;
        healthTensPlace = ninjaHealth / 10 % 10;
        healthHundredPlace = ninjaHealth / 100;
        if(healthOnesPlace == 0)
            healthBar3.setPicture(zero);
        else if (healthOnesPlace == 9) 
            healthBar3.setPicture(nine);
        else if (healthOnesPlace == 8) 
            healthBar3.setPicture(eight);
        else if (healthOnesPlace == 7) 
            healthBar3.setPicture(seven);
        else if (healthOnesPlace == 6) 
            healthBar3.setPicture(six);
        else if (healthOnesPlace == 5) 
            healthBar3.setPicture(five);
        else if (healthOnesPlace == 4) 
            healthBar3.setPicture(four);
        else if (healthOnesPlace == 3) 
            healthBar3.setPicture(three);
        else if (healthOnesPlace == 2) 
            healthBar3.setPicture(two);
        else if (healthOnesPlace == 1) 
            healthBar3.setPicture(one);
        if(healthTensPlace == 0)
            healthBar2.setPicture(zero);
        else if (healthTensPlace == 9) 
            healthBar2.setPicture(nine);
        else if (healthTensPlace == 8) 
            healthBar2.setPicture(eight);
        else if (healthTensPlace == 7) 
            healthBar2.setPicture(seven);
        else if (healthTensPlace == 6) 
            healthBar2.setPicture(six);
        else if (healthTensPlace == 5) 
            healthBar2.setPicture(five);
        else if (healthTensPlace == 4) 
            healthBar2.setPicture(four);
        else if (healthTensPlace == 3) 
            healthBar2.setPicture(three);
        else if (healthTensPlace == 2) 
            healthBar2.setPicture(two);
        else if (healthTensPlace == 1) 
            healthBar2.setPicture(one);
        if(healthHundredPlace == 0)
            healthBar1.setPicture(zero);
        else if (healthHundredPlace == 9)
            healthBar1.setPicture(nine);
        else if (healthHundredPlace == 8) 
            healthBar1.setPicture(eight);
        else if (healthHundredPlace == 7) 
            healthBar1.setPicture(seven);
        else if (healthHundredPlace == 6) 
            healthBar1.setPicture(six);
        else if (healthHundredPlace == 5) 
            healthBar1.setPicture(five);
        else if (healthHundredPlace == 4) 
            healthBar1.setPicture(four);
        else if (healthHundredPlace == 3) 
            healthBar1.setPicture(three);
        else if (healthHundredPlace == 2) 
            healthBar1.setPicture(two);
        else if (healthHundredPlace == 1){
            healthBar1.setPicture(one);
            healthBar1.setActive(true);
        }
        if(se.xlo || getX() <= 0){
            setVelX(0);
            setX(0 + walkSpeed);
        }
        if(se.ylo || getY() <= 0){
            setVelY(0);
            setY(0 + walkSpeed);
        }
        if(se.xhi || (getX() + getWidth()) >= sc.getSize().width){
            setVelX(0);
            setX(sc.getSize().width - getWidth() - walkSpeed);
        }
        if(se.yhi || (getY() + getHeight()) >= sc.getSize().height){
            setVelY(0);
            setY(sc.getSize().height - getHeight() - walkSpeed);
        }
    }
}