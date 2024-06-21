package viewmodel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
// mengakses properti
import viewmodel.Game.STATE;
/**
 *
 * @author Wildan Hafizh Raffianshar
 */
public class KeyInputs implements KeyListener{
    // mewarisi interface KeyListener agar bisa menerima input keyboard
    
    // properti game
    private final Game game;

    public KeyInputs(Game game) {
        // konstruktor
        this.game = game; // set game
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // daftar key untuk bergerak 4 arah
            case KeyEvent.VK_UP, KeyEvent.VK_W -> game.getPlayer().setUp(true); // atas
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> game.getPlayer().setLeft(true); // kiri
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> game.getPlayer().setDown(true); //bawah
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> game.getPlayer().setRight(true); // kanan
        }
        
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SPACE) {
            // key untuk menghentikan game
            game.gameState = STATE.GameOver; // ubah state menjadi game over
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> game.getPlayer().setUp(false);
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> game.getPlayer().setLeft(false);
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> game.getPlayer().setDown(false);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> game.getPlayer().setRight(false);
        }
    }
    
}
