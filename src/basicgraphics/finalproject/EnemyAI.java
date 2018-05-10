package basicgraphics.finalproject;
import basicgraphics.Sprite;
public class EnemyAI extends CreateSprite{
    int enemyHealth;
    int maxEnemyHealth;
    Ninja ninja;
    int MAX_WALKDURATION;
    int MAX_IDLEDURATION;
    int AGGRO_RANGE;
    int MIN_RANGE;
    int walkDuration, idleDuration, directionX, directionY;
    boolean isWalking, inCombat;
    double xDifference, absXDifference, yDifference, absYDifference;
    public EnemyAI(){
        isWalking = true;
        inCombat = false;
        MAX_WALKDURATION = 2000;
        MAX_IDLEDURATION = 1000;
        AGGRO_RANGE = 500;
        MIN_RANGE = 400;
        walkDuration = rand.nextInt(MAX_WALKDURATION);
        idleDuration = rand.nextInt(MAX_IDLEDURATION);
        directionX = rand.nextInt(3) - 1; // Generate random direction
        directionY = rand.nextInt(3) - 1; 
    }
    public double angleCalculator(Sprite target, Sprite attacker) {
        double xDifference = (target.getX() + target.getWidth() / 2) - (attacker.getX() + attacker.getWidth() / 2);
        double yDifference = (target.getY() + target.getHeight() / 2) - (attacker.getY() + attacker.getHeight());
        double absXDifference = Math.abs(xDifference);
        double absYDifference = Math.abs(yDifference);
        double rad = Math.atan2(yDifference , xDifference);
        return rad;
    }
    public void generateRandomDirection(){
        walkDuration = rand.nextInt(MAX_IDLEDURATION) + MAX_IDLEDURATION; // Walk max of 2 seconds and min of 1 seconds
        directionX = rand.nextInt(3) - 1; 
        directionY = rand.nextInt(3) - 1; 
    }
    public void setEnemyHealthLost(int i) {
        enemyHealth -= i;
    }
    public void setNinja(Ninja ninja){ 
        this.ninja = ninja; 
    }
    public void detectCombat(double absXDifference, double absYDifference){
        if(absXDifference <= AGGRO_RANGE && absYDifference <= AGGRO_RANGE)
            inCombat = true;
        else 
            inCombat = false;
    }
    public void combatMovement(double xDifference, double yDifference, double absXDifference, double absYDifference){
        double rad = Math.atan2(yDifference , xDifference);
        setVelXY(Math.cos(rad) * runSpeed, Math.sin(rad) * runSpeed);
    }
    public void outOfCombatMovement(double xDifference, double yDifference){
        if(isWalking){
            walkDuration -= 10;
            if(walkDuration <= 0){
                isWalking = false;
                generateRandomDirection();
            }
            setVelXY(directionX * walkSpeed, directionY * walkSpeed);
        } else {
            this.idleDuration -= 10;
            if(this.idleDuration <= 0){
                isWalking = true;
                idleDuration = rand.nextInt(MAX_WALKDURATION);
            }
            stopMoving();
        }       
    }
    @Override
    public void preMove(){
        xDifference = (ninja.getX() + ninja.getWidth() / 2) - (getX() + getWidth() / 2);
        yDifference = (ninja.getY() + ninja.getHeight() / 2) - (getY() + getHeight() / 2);
        absXDifference = Math.abs(xDifference);
        absYDifference = Math.abs(yDifference);
        detectCombat(absXDifference, absYDifference);
        if(inCombat) combatMovement(xDifference, yDifference, absXDifference, absYDifference);
        else outOfCombatMovement(xDifference, yDifference);
    }
}