package model;

import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Wildan Hafizh Raffianshar
 */
public class TableScore extends DB {
    private String tableName; // nama tabel

    public TableScore() throws Exception, SQLException{
        // konstruktor
        super();
        this.tableName = "tscore";
    }

    public void getTScore(){
        // mengeksekusi query untuk mengambil semua data pada tabel
        try {
            String query = "SELECT * from " + this.tableName;
            createQuery(query);
        } catch (Exception e) {
            // menampilkan error
            System.out.println(e.toString());
        }
    }

    public void getDataTScore(String username) {
        // mengeksekusi query untuk mengambil 1 record data berdasarkan username
        try {
            String query = "SELECT * from " + this.tableName +" WHERE username='" + username + "'";
            createQuery(query);
        } catch (Exception e) {
            // menampilkan error
            System.err.println(e.toString());
        }
    }

    public void insertData(Score point){
        // Cek apakah harus ada update
        boolean update = false;
        try {
            TableScore temp = new TableScore();
            temp.getDataTScore(point.getUsername());
            // cek apakah username sudah ada dalam database
            if(temp.getResult().next()) {
                update = true;
            } else {
                update = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if(!update){
            // Lakukan insert
            try {
                String query = "INSERT INTO " + this.tableName + " VALUES(null, '" + point.getUsername() + "', " + Integer.toString(point.getScore()) + ", " + Integer.toString(point.getUp()) + ", " + Integer.toString(point.getDown()) + ")";
                createUpdate(query);
            } catch (Exception e) {
                // menampilkan error
                System.out.println(e.toString());
            }
        }
        else if(update){
            // Lakukan update
            try {
                String query = "UPDATE " + this.tableName + " SET score=" + point.getScore() + ", up=" + point.getUp() + ", down=" + point.getDown() + " WHERE username='" + point.getUsername() + "'";
                createUpdate(query);
            } catch (Exception e) {
                // menampilkan error
                System.out.println(e.getMessage());
            }
        }
    }
    
    // Membuat tabel
    public DefaultTableModel setTable(){
        
        DefaultTableModel dataTable = null;
        try{
            // membuat header tabel
            Object[] column = {"Username", "Score", "Up", "Down"};
            dataTable = new DefaultTableModel(null, column);
            
            // query data untuk menampilkan isi tabel
            String query = "SELECT * from " + this.tableName + " order by score DESC";
            this.createQuery(query);
            // mengambil data per baris
            while(this.getResult().next()){
                Object[] row = new Object[4];
                // mengambil per kolom
                row[0] = this.getResult().getString(2);
                row[1] = this.getResult().getString(3);
                row[2] = this.getResult().getString(4);
                row[3] = this.getResult().getString(5);
                dataTable.addRow(row);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        // mengembalikan data yang sudah diambil
        return dataTable;
    }
}
