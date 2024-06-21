package model;

/**
 *
 * @author Wildan Hafizh Raffianshar
 */

public class Score {
    //Kelas score menampung data/kolom tabel score
    private String username;
    private int score;
    private int up;
    private int down;

    public Score(){

    }

    //setter getter

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getUp() {
        return up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getDown() {
        return down;
    }
}
