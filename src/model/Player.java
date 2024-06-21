package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
// mengakses konstanta
import static viewmodel.Constants.gameOption.*;

/**
 *
 * @author Wildan Hafizh Raffianshar
 */
public class Player extends GameObject{
    private boolean left;
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean inAir = true;
    
    private float playerSpeed = 5.0f;
    private float jumpStrength = 15.0f;
    private float airSpeed = 0;
    private float gravity = 0.4f;
    private float xSpeed = 0;
    
    private int scoreTotal = 0;
    private int scoreUp = 0;
    private int scoreDown = 0;

    private int count = 0;
    private int tempY = 0;

    private Image knightR;
    private Image knightL;
    private boolean facingR = true;

    
    public Player(int x, int y) {
        // konstruktor, set properti parent
        super(x, y, 50, 50);
        // Muat gambar knight
        knightR = new ImageIcon(getClass().getResource("/assets/knightR1.png")).getImage();
        knightL = new ImageIcon(getClass().getResource("/assets/knightL1.png")).getImage();
    }
    
    public void update(ArrayList<Obstacle> ob){
        // mengupdate posisi player
        updatePos(ob);
        updateCollisionBox();
    }
    
    @Override
    public void render(Graphics g){
        // mengoverride method parent

        // siapkaan avatar/karakter/gambar knight
        if (facingR) {
            g.drawImage(knightR, (int) x, (int) y, width, height, null);
        } else {
            g.drawImage(knightL, (int) x, (int) y, width, height, null);
        }

        // tampilkan skorTotal, scoreDown dan scoreUp
        g.setFont(new java.awt.Font("Segoe UI", 1, 13));
        g.setColor(Color.WHITE);
        g.fillRoundRect(15, 235, 100, 25, 15, 15);
        g.fillRoundRect(15, 265, 100, 25, 15, 15);
        g.fillRoundRect(15, 295, 100, 25, 15, 15);
        g.setColor(Color.decode("#3A1070"));
        g.drawString("Score : " + Integer.toString(this.scoreTotal), 20, 250);
        g.drawString("Up : " + Integer.toString(this.scoreUp), 20, 280);
        g.drawString("Down : " + Integer.toString(this.scoreDown), 20, 310);
    }
    
    public void updatePos(ArrayList<Obstacle> AOb){
        // mengupdate kondisi obstacle dan player
        if(left && right || !left && !right) {
            // jika klik kanan dan kiri atau tidak klik kanan dan kiri
            // kecepatan player biasa (mengikuti game)
            xSpeed = GAME_SPEED;
        } else if(left) {
            // jika klik kiri
            // kecepatan player berkurang
            xSpeed -= playerSpeed;
            facingR = false;
        } else if(right) {
            // jika klik kanan
            // kecepatan player bertambah
            xSpeed += playerSpeed + 1;
            facingR = true;
        }

        if(xSpeed > 3) {
            // kecepatan maks 3
            xSpeed = 3;
        } else if(xSpeed < -6) {
            // kecepatan min -6
            xSpeed = -6;
        }
        
        // lompat
        if(up && !inAir){
            inAir = true;
            airSpeed -= jumpStrength;
        }
        
        // di lantai
        if(!inAir && !isOnFloor(AOb)){
            inAir = true;
        }
        
        // di udara
        if(inAir){
            airSpeed += gravity;
        }
        
        for(Obstacle ob : AOb){
            // untuk setiap obstacle
            if(getBoundBottom().intersects(ob.getCollisionBox())){
                // jika bound bawah player beririsan dengan bound collisionBox
                inAir = false; // berarti tidak di udara
                airSpeed = 0; // kecepatan udara 0
                y = ob.getCollisionBox().y - height; // y tempat collision
                
                if(scoreUp == 0 && count == 0) {
                    // jika game baru dimulai
                    tempY = (int) y;
                    count++;
                }
                
                if(tempY != y) {
                    // jika y player bertabrakan sebelumnya
                    // tidak sama dengan y sekarang
                    if(y < 300) {
                        // jika y kurang dari 300 berarti collision dengan tanah melayang
                        // scoretotal dan up bertambah
                        scoreUp++;
                        scoreTotal += ob.getScoreValue();
                    }
                    if(y > 300) {
                        // jika y lebih dari 300 berarti collision dgn pilar
                        // scoretotal dan down bertambah
                        scoreDown++;
                        scoreTotal += ob.getScoreValue();
                    }
                    tempY = (int) y;
                }
            }
            
            // jika player nabrak
            // kembalikan ke speed normal
            if(getBoundRight().intersects(ob.getCollisionBox())){
                // jika nabrak kanan
                xSpeed = GAME_SPEED;
                x = ob.getCollisionBox().x - width - 1;
            }
            if(getBoundLeft().intersects(ob.getCollisionBox())){
                // jika nabrak kiri
                xSpeed = GAME_SPEED;
                x = ob.getCollisionBox().x + ob.getCollisionBox().width + 1;
            }
            if(getBoundRight().x > GAME_WIDTH){
                // jika nabrak kanan sampai mentok ke frame game
                x = GAME_WIDTH - width - 1;
            }
        }
        x += xSpeed;
        y += airSpeed;
    }
    
    public boolean isOnFloor(ArrayList<Obstacle> AOb){
        // method mengecek apakah di lantai
        for(Obstacle ob : AOb){
            // jika batas bawah player beririsan dgn collision box
            if(getBoundBottom().intersects(ob.getCollisionBox())) return true;
        }
        return false;
    }
    
    public Rectangle getBoundBottom(){
        // membuat batas bawah
        return new Rectangle((int) (x+(width/2)-(width/4)), (int) (y+(height/2)), width/2, height/2);
    }
    
    public Rectangle getBoundTop(){
        // membuat batas atas
        return new Rectangle((int) (x+(width/2)-(width/4)), (int) (y), width/2, height/2);
    }
    
    public Rectangle getBoundRight(){
        // membuat batas kanan
        return new Rectangle((int) x+width-5, (int)y + 5, 5, height-10);
    }
    
    public Rectangle getBoundLeft(){
        // membuat batas kiri
        return new Rectangle((int) x, (int)y + 5, 5, height-10);
    }

    public void setLeft(boolean left) {
        // set player ke kiri
        this.left = left;
    }

    public void setUp(boolean up) {
        // set player ke atas
        this.up = up;
    }

    public void setRight(boolean right) {
        // set player ke kanan
        this.right = right;
    }
    
    public void setDown(boolean down) {
        // set player ke bawah
        this.down = down;
    }
    
    public void setScoreDown(int scoreDown) {
        // set skor scoreDown
        this.scoreDown = scoreDown;
    }
    
    public void setscoreUp(int scoreUp) {
        // set skor scoreUp
        this.scoreUp = scoreUp;
    }
    
    public int getscoreDown() {
        // mendapatkan skor scoreDown
        return this.scoreDown;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public int getscoreUp() {
        // mendapatkan skor scoreUp
        return this.scoreUp;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }
}
