package viewmodel;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.sound.sampled.Clip;
// mengakses model
import model.Player;
import model.Score;
import model.TableScore;
// mengakses konstanta
import static viewmodel.Constants.gameOption.GAME_HEIGHT;
import static viewmodel.Constants.gameOption.GAME_WIDTH;
// mengakses view
import view.GameWindow;
import view.Menu;

/**
 *
 * @author Wildan Hafizh Raffianshar
 */
public class Game extends JPanel implements Runnable {
    // mengimplementasikan Runnable java untuk membuat thread

    // properti thread
    private Thread gameThread;
    private boolean running = false; // deteksi game berjalan

    private GameWindow window; // window game
    private Clip audio; // backsound

    // properti objek dlm game
    private final Player player; // player
    private final ObstacleHandler obs_handler; // obstacle
    private String username; // username
    private int score;
    private int up; // skor up
    private int down; // skor down
    private Image backgroundImage;

    public enum STATE{
        Game,
        GameOver
    }

    public Game(){
        // konstruktor

        // siapkan backgorund
        backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/bgsky4.png"));

        // siapkan player dgn posisi pada tiang pertama
        this.player = new Player(GAME_WIDTH/2 - 50, 150);

        // siapkan obstacle
        this.obs_handler = new ObstacleHandler();

        // membuat backsound
        Sound bgm = new Sound();
        audio = bgm.playSound(this.audio, "05_main.wav");
    }

    // mengeset STATE game
    public STATE gameState = STATE.Game;

    public synchronized void StartGame(GameWindow gw){
        // memulai menjalankan game
        this.window = gw; // buat window
        gameThread = new Thread(this); // buat thread baru
        gameThread.start(); // thread dijalankan
        running = true; // set running
    }

    @Override
    public void paint(Graphics g){
        // membuat Component
        super.paint(g); // method parent

        // Gambar latar belakang
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT,  null);
        } else {
            System.out.println("Background image is null.");
        }

        player.render(g); // render objek player
        obs_handler.renderObstacle(g); // render objek obstacle
    }

    @Override
    public void run() {
        // meng override method run dari parent Runnable
        while(true){
            // selama true (game loop)
            try {
                updateGame(); // update objek game
                repaint(); // membuat ulang Component (update paint())
                Thread.sleep(1000L/60L); // pause thread (60 FPS)
                this.score = player.getScoreTotal(); // mengambil skor total
                this.up = player.getscoreUp(); // mengambil skor up
                this.down = player.getscoreDown(); // mengambil skor down
                if(this.player.getBoundLeft().x < -99 || this.player.getBoundBottom().y > GAME_WIDTH+100) {
                    // jika posisi tabrakan player < -99 / tertinggal
                    // atau player terjatuh ke jurang
                    // maka GameOver
                    this.gameState = STATE.GameOver;
                }
                if(gameState == STATE.GameOver) {
                    // jika state saat ini GameOver
                    // stop bgm
                    Sound bgm = new Sound();
                    bgm.stopSound(this.audio);

                    saveScore(); // simpan skor, up dan down
                    close(); // tutup window
                    Menu.main(new String[0]);
                    stopGame(); // stop game
                }
            } catch (InterruptedException ex) {
                // log error
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateGame(){
        // update objek dalam game
        obs_handler.addObstacle(); // menambah obstacle
        obs_handler.updateObstacle(); // mengupdate kondisi obstacle
        player.update(obs_handler.getObstacles()); // mengupdate kondisi player
    }

    public synchronized void stopGame() {
        // menghentikan game
        try{
            gameThread.join(); // menghentikan thread
            running = false; // set tidak berjalan
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void close() {
        // menutup window
        window.CloseWindow();
    }

    public Player getPlayer(){
        // mengambil player
        return this.player;
    }

    public void setUsername(String username) {
        // set username game
        this.username = username;
    }

    public void setScore(int score, int up, int down) {
        // set skor player di awal game
        this.player.setscoreUp(up);
        this.player.setScoreDown(down);
        this.player.setScoreTotal(score);
    }

    public void saveScore() {
        // menyimpan skor saat game over
        // mainkan backsound game over
        Sound gobgm = new Sound();
        audio = gobgm.playSound(this.audio, "06_over.wav");

        try {
            // tambah atau update data pada tabel
            TableScore tscore = new TableScore();
            Score point = new Score();
            point.setUsername(this.username);
            point.setScore(this.score);
            point.setUp(this.up);
            point.setDown(this.down);
            tscore.insertData(point);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // menampilan panel game over
        JOptionPane.showMessageDialog(null, "Username : " + this.username + "\nscore : " + this.score + "\nup : " + this.up + "\ndown : " + this.down, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
        // berhentikan sound saat panel game over hilang
        gobgm.stopSound(this.audio);
    }

}
