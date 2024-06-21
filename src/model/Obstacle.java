package model;

import java.awt.*;
import java.util.Random;
// mengakses viewmodel
import viewmodel.Constants;

import static viewmodel.Constants.gameOption.*;

/**
 *
 * @author Wildan Hafizh Raffianshar
 */
public class Obstacle extends GameObject{
    // properti obstacle
    private int scoreValue;
    
    // inisialisasi library
    Random rand = new Random(); // fungsi random
    Image asset;
    
    public Obstacle(float x, float y, int width, int height) {
        // konstruktor
        super(x, y, width, height);
        this.scoreValue = calculateScore(height, (int) y);
    }

    
    public void update() {
        // mengupdate posisi dan collisionBox
        updatePos();
        updateCollisionBox();
    }
    
    @Override
    public void render(Graphics g) {
        // mengoverride fungsi render di parent
        g.fillRect((int)x, (int)y, width, height); // membuat persegi panjang

        if(height == 10){
            //tambah asset tanah melayang
            //ranting1
            asset = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/ranting1.png"));
            g.drawImage(asset, (int)x-3, 20, 15, (int)y-10,  null);

            //ranting2
            asset = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/ranting1.png"));
            g.drawImage(asset, (int)x+37, 20, 15, (int)y-10,  null);

            //awan
            asset = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/awan1.png"));
            g.drawImage(asset, (int)x-8, 5, width+15, height+20,  null);

            //tanah
            asset = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/lantai1.png"));
            g.drawImage(asset, (int)x-4, (int)y-4, width+8, height+10,  null);

            //skor tanah melayang
            g.setColor(Color.decode("#3A1070"));
            g.drawString(Integer.toString(getScoreValue()), (int)x + 15, 28);
        } else{
            //tambah asset pilar
            asset = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/tiang1.png"));
            g.drawImage(asset, (int)x-5, (int)y-4, width+8, height+5,  null);

            //skor tiap pilar
            g.setColor(Color.WHITE);
            g.fillRoundRect((int)x + 12, GAME_HEIGHT-65, 25, 25, 15, 15);
            g.setColor(Color.decode("#3A1070"));
            g.drawString(Integer.toString(getScoreValue()), (int)x + 18, GAME_HEIGHT-50);
        }
    }

    private void updatePos(){
        // mengupdate posisi
        x += GAME_SPEED;
    }

    public float getX(){
        // mengambil nilai x si obstacle
        return x;
    }

    private int calculateScore(int height, int y){
        if(height == 10){
            // jika tanah melayang
            return 1000/y + 23;
        }
        //jika pilar bawah
        return 1000/height + 18;
    }

    public int getScoreValue() {
        return scoreValue;
    }

}
