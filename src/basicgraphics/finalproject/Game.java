package basicgraphics.finalproject;
import basicgraphics.BasicFrame;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.Dimension;
import java.awt.PointerInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JOptionPane;
public class Game {
    public void spawnGame() {
        BasicFrame bf = new BasicFrame("Game");
        final SpriteComponent room = new SpriteComponent();
        Dimension arena = new Dimension(800, 800);
        room.setPreferredSize(arena);
        bf.add("Game", room, 0, 0, 1, 1);
        CreateSprite background = new CreateSprite();
        background.create(room, new Picture("bossRoom.png"), 0, 0, 1);
        final Ninja ninja = new Ninja();
        Picture ninjaPicture = ninja.uwalk1;
        int ninjaSpawnX = arena.width / 2 - ninjaPicture.getWidth() / 2;
        int ninjaSpawnY = arena.height - ninjaPicture.getHeight() / 2;
        ninja.create(room, ninjaPicture, ninjaSpawnX, ninjaSpawnY , 2);
        ninja.setHP();
        room.start(0, 10);
        bf.show();
        room.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {  
                if(ninja.getNormalAttackOnCoolDown() == true && ninja.currentEmpoweredAttackAmmo > 0)
                    ninja.rangedEmpoweredAttack(me);
                else if(ninja.getNormalAttackOnCoolDown() == true && ninja.currentEmpoweredAttackAmmo <= 0) 
                    ninja.rangedAttack(me);
            }
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        bf.addKeyListener(new KeyAdapter(){
            int numRangedNPC = 0;
            int numMeleeNPC = 0;
            Random rand = new Random();
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_SHIFT) 
                    ninja.setRun(true);
                if(ke.getKeyCode() == KeyEvent.VK_SPACE && ninja.getNormalAttackOnCoolDown() == true && ninja.currentEmpoweredAttackAmmo > 0)
                    ninja.rangedEmpoweredAttack();
                else if(ke.getKeyCode() == KeyEvent.VK_SPACE && ninja.getNormalAttackOnCoolDown() == true && ninja.currentEmpoweredAttackAmmo <= 0)
                    ninja.rangedAttack();
                if(ke.getKeyCode() == KeyEvent.VK_B && ninja.getBombAttackOnCoolDown() == true)
                    ninja.bombAttack();
                if(ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_W){
                    ninja.setPicture(ninja.getFacingUpPicture());
                    ninja.setUp(true);
                }
                if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_S){
                    ninja.setPicture(ninja.getFacingDownPicture());
                    ninja.setDown(true);
                } 
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyCode() == KeyEvent.VK_D){
                    ninja.setPicture(ninja.getFacingRightPicture());
                    ninja.setRight(true);
                }
                if (ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_A){
                    ninja.setPicture(ninja.getFacingLeftPicture());
                    ninja.setLeft(true);
                } 
                if (ke.getKeyCode() == KeyEvent.VK_E)
                    ninja.setRight(true);
                if (ke.getKeyCode() == KeyEvent.VK_Q)
                    ninja.setLeft(true);
                ninja.direction();
                if(ke.getKeyCode() == KeyEvent.VK_L){
                    Boss boss = new Boss();
                    boss.create(room, new Picture("Ganon.png"), 500, 200, 2);
                    boss.setNinja(ninja);
                    boss.healthBar();
                }
                if(ke.getKeyCode() == KeyEvent.VK_P) {
                    if((numRangedNPC + numMeleeNPC) % 2 == 0) numRangedNPC += 1;
                    else numMeleeNPC += 1;
                    if(numRangedNPC <= 4) {
                        for(int i = 0; i < numRangedNPC; i++) {
                            RangedNPC rangedNPC = new RangedNPC();
                            Picture enemy = new Picture("ReverseWizard.png");
                            rangedNPC.create(room, enemy, rand.nextInt(740) + enemy.getWidth() / 2, rand.nextInt(740) + enemy.getHeight() / 2, 2);
                            rangedNPC.setNinja(ninja);
                        }
                        for(int i = 0; i < numMeleeNPC; i++) {
                            MeleeNPC meleeNPC = new MeleeNPC();
                            Picture enemy = new Picture("gladiator_facingdown_sheathed.png");
                            meleeNPC.create(room, enemy, rand.nextInt(740) + enemy.getWidth() / 2, rand.nextInt(740) + enemy.getHeight() / 2, 2);
                            meleeNPC.setNinja(ninja);
                        }
                    } else {
                        Boss boss = new Boss();
                        boss.create(room, new Picture("Ganon.png"), 500, 200, 2);
                        boss.setNinja(ninja);
                        boss.healthBar();
                    }   
                }
                if(ke.getKeyCode() == KeyEvent.VK_H) {
                    HealthBox hb = new HealthBox();
                    Picture healthBox = new Picture("HealthBox.png");
                    hb.create(room, healthBox, rand.nextInt(800 - healthBox.getWidth()) + healthBox.getWidth() / 2 , rand.nextInt(800 - healthBox.getHeight()) + healthBox.getHeight(), 2);
                }
                if(ke.getKeyCode() == KeyEvent.VK_J) {
                    AmmoBox ab = new AmmoBox();
                    Picture ammoBox = new Picture("ManaBox.png");
                    ab.create(room, ammoBox, rand.nextInt(800 - ammoBox.getWidth()) + ammoBox.getWidth() / 2 , rand.nextInt(800 - ammoBox.getHeight()) + ammoBox.getHeight(), 2);
                }
                if(ke.getKeyCode() == KeyEvent.VK_O) {
                    MeleeNPC meleeNPC = new MeleeNPC();
                    Picture enemy = new Picture("gladiator_facingdown_sheathed.png");
                    meleeNPC.create(room, enemy, rand.nextInt(740) + enemy.getWidth() / 2, rand.nextInt(740) + enemy.getHeight() / 2, 2);
                    meleeNPC.setNinja(ninja);   
                }
            }
            public void keyReleased(KeyEvent ke){
                if(ke.getKeyCode() == KeyEvent.VK_SHIFT)
                    ninja.setRun(false);
                if(ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_W){
                    ninja.setUp(false);
                    ninja.setVelY(0);
                }
                if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_S){
                    ninja.setDown(false);
                    ninja.setVelY(0);
                }
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyCode() == KeyEvent.VK_D){
                    ninja.setRight(false);
                    ninja.setVelX(0);
                }
                if (ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_A){
                    ninja.setLeft(false);
                    ninja.setVelX(0);
                }
                if (ke.getKeyCode() == KeyEvent.VK_E){
                    ninja.setRight(false);
                    ninja.setVelX(0);
                }
                if (ke.getKeyCode() == KeyEvent.VK_Q){
                    ninja.setLeft(false);
                    ninja.setVelX(0);
                }
                ninja.direction();
            }
        });
    }
    
    public static void main(String[] args){
        final BasicFrame menu = new BasicFrame("Start Menu");
        final SpriteComponent start = new SpriteComponent();
        start.setPreferredSize(new Dimension(800, 800));
        menu.add("Start Menu", start, 0, 0, 1, 1);
        final StartMenu startMenu = new StartMenu();
        startMenu.create(start, new Picture("StartMenu.png"), 0, 0, 1);
        start.start(0, 10);
        menu.show();
        MouseAdapter ma = new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent me) {
                if(me.getX() >= 40 && me.getX() <= 190 && me.getY() >= 150 && me.getY() <= 230) {
                    startMenu.setPicture(new Picture("Controls.png"));
                    startMenu.controlMenu = true;
                }
                if(me.getX() >= 40 && me.getX() <= 190 && me.getY() >= 300 && me.getY() <= 360) 
                    System.exit(0);
            }
        };
        start.addMouseListener(ma);
        menu.addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent ke){
                if(ke.getKeyCode() == KeyEvent.VK_SPACE && startMenu.controlMenu){
                    menu.dispose();
                    Game game = new Game();
                    game.spawnGame();
                }
            }
        });
    }
}