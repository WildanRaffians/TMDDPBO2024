package viewmodel;

import java.util.Random;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
// mengakses konstanta
import static viewmodel.Constants.gameOption.GAME_HEIGHT;
import static viewmodel.Constants.gameOption.GAME_WIDTH;
// mengakses model
import model.Obstacle;

/**
 *
 * @author Wildan Hafizh Raffianshar
 */
public class ObstacleHandler {
    private final int MIN_Y = GAME_HEIGHT/2; // posisi y minimal
    private final int MAX_Y = GAME_HEIGHT; // posisi y maksimal (batasan bawah)
    private final int MAX_X = GAME_WIDTH; // posisi x maksimal (batasan kanan)
    private final Random rand = new Random(); // inisialisasi library random
    private final int MAX_OBSTACLE = 15; // jumlah maks obstacle dlm 1 frame
    private final int GAP = 100; // lebar gap minimum antar obstacle
    private int OBS_WIDTH = 50; // lebar obstacle
    private int obstacleNumber = 0; // jumlah obstacle
    private final ArrayList<Obstacle> obstacles = new ArrayList<>(); // list obstacle
    
    public ObstacleHandler() {
        // konstruktor
    }

    public synchronized void updateObstacle(){
        // mengupdate kondisi obstacle
        Iterator<Obstacle> itr = obstacles.iterator(); // iterator untuk setiap obstacle
        while(itr.hasNext()) {
            // selama obstacle ada
            Obstacle ob = itr.next();
            if(ob.getX() < -50){
                // jika posisi y obstacle melebihi batas y frame
                itr.remove(); // hilangkan obstacle
                obstacleNumber--; // decrement jumlah obstacle
            } else{
                // jika tidak, update posisi obstacle
                ob.update();
            }
        }
    }
    
    public synchronized void renderObstacle(Graphics g){
        // merender obstacle
        for (Obstacle ob : obstacles) {
            // untuk setiap obstacle
            ob.render(g); // gambar objeknya
        }
    }
     
    public synchronized void addObstacle(){
        // menambah jumlah obstacle
        if(obstacleNumber < MAX_OBSTACLE){
            // jika jumlah obstacle dalam frame
            // masih kurang dari batas maks obstacle

           // buat obstacle baru (pilar)
            float x = (MAX_X / 2) - OBS_WIDTH; // posisi x tengah layar (untuk yg pertama)
            float y = rand.nextInt(MAX_Y - 100 - (MIN_Y+50)) + (MIN_Y+50); // posisi y sesuai batas (random dengan minimal tengah layar sampai batas bawah - 150)
            if(obstacleNumber >= 1){
                // jika jumlah obstacle lebih dari 1
                // x akan berada dibelakang obstacle yg telah ada sebelumnya
                x =  obstacles.get(obstacles.size() - 1).getX() + GAP;
            }
            Obstacle obstacleDown = new Obstacle(x, y, OBS_WIDTH, (int) (MAX_Y - y));

            obstacles.add(obstacleDown); // tambahkan ke list
            obstacleNumber++; // increment jumlah obstacle

            //obstacle baru (tanah melayang)
            float xUp = obstacles.get(obstacles.size() - 1).getX() + GAP;
            int minY = (int) (MIN_Y-50);
            float yUp = rand.nextInt(minY-55) + 55; // random dari 55 sampai batas tengah - 50
            int heightUp = 10; // ukuran tinggi tanah melayang
            Obstacle obstacleUp = new Obstacle(xUp, yUp, OBS_WIDTH, heightUp);

            obstacles.add(obstacleUp); // tambahkan ke list
            obstacleNumber++; // increment jumlah obstaclea
        }
    }

    public synchronized ArrayList<Obstacle> getObstacles() {
        // mengambil obstacle
        return obstacles;
    }
    
}
