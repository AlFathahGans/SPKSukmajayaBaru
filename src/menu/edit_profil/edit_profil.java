/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.edit_profil;

import menu.edit_profil.*;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.ImageIcon;
import koneksi.koneksi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import spksukmajayabaru.ImageCellRenderer;
import spksukmajayabaru.exceptionData;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import spksukmajayabaru.UserSession;

/**
 *
 * @author Acer
 */
public class edit_profil extends javax.swing.JPanel {
    
    private Connection conne = new koneksi().getConnection();
    koneksi conn= new koneksi();
    private String fotoPath;

    /**
     * Creates new form profil_saya
     */
    public edit_profil() {
        initComponents();
         loadUserProfile();
         getUserData();
         
         btnSelectPhoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSelectPhotoActionPerformed(evt);
            }
        });
    }
    
    public void getUserData() {
        try {
            UserSession userSession = UserSession.getInstance();
            String userName = userSession.getUserName();
            int userId = userSession.getUserId();

            String getUser = "SELECT foto,nama,email,tanggal FROM tbl_user WHERE id='"+userId+"'";
            ResultSet res = conn.ambilData(getUser);

            String fotoPath = "";        
            String nama = "";
            String email = "";

            if (res.next()) {
                fotoPath = res.getString("foto");            
                nama = res.getString("nama");
                email = res.getString("email");

                // Membuat ImageIcon dari path file
                ImageIcon imageIcon = new ImageIcon(fotoPath);
                jLabelTampilFoto.setIcon(imageIcon); // Set ikon ke JLabel
                jTextField1.setText(nama);            
                jTextField2.setText(email);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Cetak stack trace untuk debug
        } 
    }
    
    private void clear(){
        jLabelPhotoPath.setText("Path :");
    }
    
    public void edit() {
        try {
            UserSession userSession = UserSession.getInstance();
            String userName = userSession.getUserName();
            int userId = userSession.getUserId();
            
            // Tentukan direktori upload dan nama file
            File uploadDir = new File("src/upload");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Buat direktori jika belum ada
            }

            // Nama file dan lokasi baru
            File sourceFile = new File(fotoPath);
            String fileName = sourceFile.getName();
            File destinationFile = new File(uploadDir, fileName);

            try {
                // Salin file dari path sementara ke folder tujuan
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(edit_profil.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Path gambar di database
            String savedFotoPath = destinationFile.getAbsolutePath();
            
            String a = jTextField1.getText();
            String b = jLabelPhotoPath.getText();
            String c = jTextField2.getText();
            
            // Pastikan path tidak mengandung karakter yang salah
            b = b.replace("\\", "/"); // Ubah backslash ke slash jika diperlukan

            //Query SQL
            String sql = "UPDATE tbl_user SET nama='" + a + "', foto='" + b + "', email='" + c + "' WHERE id='" + userId + "'";

            conn.st.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Terupdate");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Terupdate");
            System.out.println(e.getMessage());
        }
        clear();
    }
    
    private void loadUserProfile() {
        // Ambil data dari UserSession
        UserSession userSession = UserSession.getInstance();
        String userName = userSession.getUserName();
        int userId = userSession.getUserId();

        // Tampilkan informasi di UI
       System.out.println("ID: " + userId + "\nNama: " + userName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTampilFoto = new javax.swing.JLabel();
        jLabelTampilNama = new javax.swing.JLabel();
        jLabelTampilEmail = new javax.swing.JLabel();
        jLabelIconNama = new javax.swing.JLabel();
        jLabelIconEmail = new javax.swing.JLabel();
        jLabelIconTanggal = new javax.swing.JLabel();
        jLabelTanggalInfo = new javax.swing.JLabel();
        btnSelectPhoto = new javax.swing.JButton();
        jLabelPhotoPath = new javax.swing.JLabel();
        btn_update = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabelCard = new javax.swing.JLabel();

        setLayout(null);

        jLabelTampilFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTampilFoto.setText("Foto");
        add(jLabelTampilFoto);
        jLabelTampilFoto.setBounds(224, 212, 190, 210);

        jLabelTampilNama.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabelTampilNama.setForeground(new java.awt.Color(16, 184, 255));
        jLabelTampilNama.setText("Nama Lengkap");
        add(jLabelTampilNama);
        jLabelTampilNama.setBounds(550, 230, 121, 50);

        jLabelTampilEmail.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabelTampilEmail.setForeground(new java.awt.Color(16, 184, 255));
        jLabelTampilEmail.setText("Email");
        add(jLabelTampilEmail);
        jLabelTampilEmail.setBounds(550, 290, 120, 50);

        jLabelIconNama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user-profil.png"))); // NOI18N
        jLabelIconNama.setText("jLabel1");
        add(jLabelIconNama);
        jLabelIconNama.setBounds(480, 220, 50, 60);

        jLabelIconEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gmail.png"))); // NOI18N
        jLabelIconEmail.setText("jLabel2");
        add(jLabelIconEmail);
        jLabelIconEmail.setBounds(480, 290, 50, 50);

        jLabelIconTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/download-file.png"))); // NOI18N
        jLabelIconTanggal.setText("jLabel3");
        add(jLabelIconTanggal);
        jLabelIconTanggal.setBounds(480, 350, 50, 60);

        jLabelTanggalInfo.setBackground(new java.awt.Color(16, 184, 255));
        jLabelTanggalInfo.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabelTanggalInfo.setForeground(new java.awt.Color(16, 184, 255));
        jLabelTanggalInfo.setText("Ganti Foto");
        add(jLabelTanggalInfo);
        jLabelTanggalInfo.setBounds(550, 360, 130, 40);

        btnSelectPhoto.setBackground(new java.awt.Color(0, 204, 0));
        btnSelectPhoto.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnSelectPhoto.setForeground(new java.awt.Color(255, 255, 255));
        btnSelectPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/download.png"))); // NOI18N
        btnSelectPhoto.setText("[ Pilih Foto ]");
        btnSelectPhoto.setToolTipText("Klik tombol untuk memilih foto");
        btnSelectPhoto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        btnSelectPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectPhotoActionPerformed(evt);
            }
        });
        add(btnSelectPhoto);
        btnSelectPhoto.setBounds(690, 360, 270, 40);

        jLabelPhotoPath.setBackground(new java.awt.Color(51, 51, 51));
        jLabelPhotoPath.setForeground(new java.awt.Color(16, 184, 255));
        jLabelPhotoPath.setText("Path :");
        jLabelPhotoPath.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(16, 184, 255), 1, true));
        add(jLabelPhotoPath);
        jLabelPhotoPath.setBounds(690, 410, 270, 30);

        btn_update.setBackground(new java.awt.Color(91, 139, 223));
        btn_update.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.setToolTipText("Klik untuk mengupdate data.");
        btn_update.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        add(btn_update);
        btn_update.setBounds(620, 480, 129, 42);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(690, 298, 270, 40);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(690, 238, 270, 40);

        jLabelCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card-profil-2.png"))); // NOI18N
        jLabelCard.setText("jLabel1");
        add(jLabelCard);
        jLabelCard.setBounds(40, 35, 1003, 591);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectPhotoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            fotoPath = selectedFile.getAbsolutePath(); // Simpan path gambar
            jLabelPhotoPath.setText(fotoPath); // Tampilkan path di label
            // Load and display the selected image
            ImageIcon imageIcon = new ImageIcon(fotoPath);
            Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Resize image if needed
            jLabelTampilFoto.setIcon(new ImageIcon(image)); // Set the resized image to JLabel
        }
    }//GEN-LAST:event_btnSelectPhotoActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        edit();
        clear();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelectPhoto;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabelCard;
    private javax.swing.JLabel jLabelIconEmail;
    private javax.swing.JLabel jLabelIconNama;
    private javax.swing.JLabel jLabelIconTanggal;
    private javax.swing.JLabel jLabelPhotoPath;
    private javax.swing.JLabel jLabelTampilEmail;
    private javax.swing.JLabel jLabelTampilFoto;
    private javax.swing.JLabel jLabelTampilNama;
    private javax.swing.JLabel jLabelTanggalInfo;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
