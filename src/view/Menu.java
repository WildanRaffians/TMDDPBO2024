package view;

import model.TableScore;
import viewmodel.Game;
import viewmodel.Sound;

import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wildan Hafizh Raffianshar
 */
public class Menu extends javax.swing.JFrame{

    public Game game; //objek game
    private TableScore tscore; // data tabel
    public Clip audio; // backsound
    private String username; // username


    private JButton playButton;
    private JButton quitButton;
    private JTable tscoreF;
    private JTextField usernameF;
    private JLabel creditlabel;
    private JLabel imagelabel;
    private JLabel soundlabel;
    private JLabel inputusernamelabel;
    private JLabel titlelabel;
    private JPanel menupanel;

    public Menu() {

        this.username = ""; // inisialisasi username

        // Initialize components
        menupanel = new JPanel();
        menupanel.setLayout(new BorderLayout());
        menupanel.setBorder(new EmptyBorder(10, 30, 10, 30)); // Adding margin around the menupanel

        titlelabel = new JLabel("UP DOWN", SwingConstants.CENTER);
        titlelabel.setFont(new Font("Ubuntu", Font.BOLD, 36));
        titlelabel.setForeground(Color.WHITE);

        inputusernamelabel = new JLabel("Input Username", SwingConstants.CENTER);
        inputusernamelabel.setForeground(Color.WHITE);

        usernameF = new JTextField();
        usernameF.setHorizontalAlignment(JTextField.CENTER);

        playButton = new JButton("Play");
        playButton.setBackground(Color.GREEN);
        playButton.setForeground(Color.WHITE);

        quitButton = new JButton("Quit");
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.WHITE);

        creditlabel = new JLabel("Credit:", SwingConstants.CENTER);
        creditlabel.setForeground(Color.WHITE);

        imagelabel = new JLabel("Image by freepik", SwingConstants.CENTER);
        imagelabel.setForeground(Color.WHITE);

        soundlabel = new JLabel("Sound by stocktune", SwingConstants.CENTER);
        soundlabel.setForeground(Color.WHITE);

        tscoreF = new JTable();
        JScrollPane scrollPane = new JScrollPane(tscoreF);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titlelabel, BorderLayout.NORTH);
        titlePanel.setBorder(new EmptyBorder(20, 0, 30, 0)); // Adding margin around the topPanel
        titlePanel.setBackground(new Color(0, 98, 237)); // Set background color of credit panel


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(inputusernamelabel, BorderLayout.CENTER);
        topPanel.add(usernameF, BorderLayout.SOUTH);
        topPanel.setBorder(new EmptyBorder(10, 0, 30, 0)); // Adding margin around the topPanel

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        buttonPanel.add(playButton);
        buttonPanel.add(quitButton);
        buttonPanel.setBorder(new EmptyBorder(30, 0, 40, 0)); // Adding margin around the buttonPanel
        buttonPanel.setBackground(new Color(0, 98, 237)); // Set background color of credit panel

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel creditPanel = new JPanel();
        creditPanel.setLayout(new BoxLayout(creditPanel, BoxLayout.Y_AXIS));
        creditPanel.add(creditlabel);
        creditPanel.add(imagelabel);
        creditPanel.add(soundlabel);

        menupanel.add(topPanel, BorderLayout.NORTH);
        topPanel.setBackground(new Color(0, 98, 237)); // Set background color of credit panel
        menupanel.add(bottomPanel, BorderLayout.CENTER);
        bottomPanel.setBackground(new Color(0, 98, 237)); // Set background color of credit panel
        menupanel.add(creditPanel, BorderLayout.SOUTH); // Add credit panel to menupanel
        creditPanel.setBackground(new Color(0, 98, 237)); // Set background color of credit panel
        menupanel.setBackground(new Color(0, 98, 237)); // Set background color of credit panel

        try {
            // membuat tabel baru
            this.tscore = new TableScore();
            // diisi dgn data dari database
            tscoreF.setModel(tscore.setTable());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // memutar backsound di page menu
        Sound bgm = new Sound();
        audio = bgm.playSound(this.audio, "04_menu.wav");

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Menu.this.username.length() == 0 && usernameF.getText().length() != 0) {
                    // jika nilai username masih kosong
                    // dan text field usernameF ada isinya
                    Menu.this.username = usernameF.getText();
                }
                if(usernameF.getText().length() == 0 && Menu.this.username.length() == 0) {
                    // jika text field kosong
                    // dan nilai username masih kosong (tidak klik dari tabel)
                    JOptionPane.showMessageDialog(Menu.this, "Username tidak boleh kosong!\nPilih user atau masukkan username baru.");
                } else {
                    // sound new game
                    Sound bgm = new Sound();
                    bgm.stopSound(Menu.this.audio);

                    // membuat game baru
                    game = new Game();
                    GameWindow gw = new GameWindow(game); // buat window
                    game.setUsername(Menu.this.username); // set username
                    // cek apakah username sudah ada di database
                    TableScore temp = null;
                    try {
                        temp = new TableScore();
                    } catch (Exception ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, "Failed to initialize TableScore", ex);
                    }
                    temp.getDataTScore(Menu.this.username);
                    int c = 0, tup = 0, tdown = 0, tscoret = 0;
                    try {
                        while(temp.getResult().next()){
                            // jika username sudah ada di database,
                            // ambil nilai score, up dan down nya
                            tscoret = Integer.parseInt(temp.getResult().getString(3));
                            tup = Integer.parseInt(temp.getResult().getString(4));
                            tdown = Integer.parseInt(temp.getResult().getString(5));
                            c++;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, "Error reading from database", ex);
                    }
                    // set score score, up dan down di awal game
                    if(c == 0){
                        // jika user baru, semua bernilai 0
                        game.setScore(0, 0,0);
                    } else {
                        // jika user lama, melanjutkan score yang sudah didapat
                        game.setScore(tscoret, tup, tdown);
                    }

                    // game dimulai
                    game.StartGame(gw);
                    Menu.this.setVisible(false); // window menu dibuat invisible
                    Menu.this.dispose(); // window menu di clear
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu.this.dispose();
                System.exit(0);
            }
        });

        tscoreF.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // jika salah satu record dalam tabel di klik
                int row = tscoreF.getSelectedRow();
                // set username dengan username pada tabel yg di klik
                Menu.this.username = tscoreF.getModel().getValueAt(row, 0).toString();
            }
        });
    }

    public static void main(String[] args) {
        Menu window = new Menu();
        window.setSize(460, 500);   // ukuran window
        window.setTitle("TMD DPBO 2024");        // title window
        window.setLocationRelativeTo(null);      // lokasi window di tengah layar
        window.setResizable(false);              // ukuran tidak dapat diubah
        window.setContentPane(window.menupanel); // set content
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // agar program langsung terclose ketika menekan tombol close di pojok kanan atas
        window.setVisible(true);                 // menampilkan window
    }
}
