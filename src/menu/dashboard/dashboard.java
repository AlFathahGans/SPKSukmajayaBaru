/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.dashboard;

import menu.dashboard.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;
import koneksi.koneksi;

/**
 *
 * @author Acer
 */
public class dashboard extends javax.swing.JPanel {
    private Connection conne = new koneksi().getConnection();
    koneksi conn= new koneksi();
    /**
     * Creates new form dashboard
     */
    public dashboard() {
        initComponents();
        
        setSize(1030, 630); // Ukuran panel sesuai kebutuhan
        setOpaque(false); 
        
        getTotalPenduduk();        
        getTotalKriteria();
        getTotalBelumTerseleksi();
        getTotalTerseleksi();

    }
    
    public void getTotalPenduduk() {
        // Mengambil data dari tabel transaksi
        Connection connect = conn.getConnection();
        boolean hasData = false;
        
        try {
            Statement st = connect.createStatement();
            String query = "SELECT COUNT(id) AS total_penduduk FROM tbl_alternatif";
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()) {
                int total_penduduk = rs.getInt("total_penduduk");
                if (total_penduduk > 0) {
                    hasData = true; // Set hasData ke true jika ada data
                }
                jLabelTotalPenduduk.setText(String.valueOf(total_penduduk));
            } else {
                jLabelTotalPenduduk.setText("0"); // Jika tidak ada data, set nilai label ke 0
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void getTotalKriteria() {
        // Mengambil data dari tabel transaksi
        Connection connect = conn.getConnection();
        boolean hasData = false;
        
        try {
            Statement st = connect.createStatement();
            String query = "SELECT COUNT(id) AS total_kriteria FROM tbl_kriteria";
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()) {
                int total_kriteria = rs.getInt("total_kriteria");
                if (total_kriteria > 0) {
                    hasData = true; // Set hasData ke true jika ada data
                }
                jLabelTotalKriteria.setText(String.valueOf(total_kriteria));
            } else {
                jLabelTotalKriteria.setText("0"); // Jika tidak ada data, set nilai label ke 0
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void getTotalBelumTerseleksi() {
        // Mengambil data dari tabel transaksi
        Connection connect = conn.getConnection();
        boolean hasData = false;
        
        try {
            Statement st = connect.createStatement();
            String query = "SELECT COUNT(id) AS total_belum_terseleksi FROM tbl_alternatif WHERE status_seleksi = 'Belum Terseleksi'";
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()) {
                int total_belum_terseleksi = rs.getInt("total_belum_terseleksi");
                if (total_belum_terseleksi > 0) {
                    hasData = true; // Set hasData ke true jika ada data
                }
                jLabelTotalBelumTerseleksi.setText(String.valueOf(total_belum_terseleksi));
            } else {
                jLabelTotalBelumTerseleksi.setText("0"); // Jika tidak ada data, set nilai label ke 0
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void getTotalTerseleksi() {
        // Mengambil data dari tabel transaksi
        Connection connect = conn.getConnection();
        boolean hasData = false;
        
        try {
            Statement st = connect.createStatement();
            String query = "SELECT COUNT(id) AS total_terseleksi FROM tbl_alternatif WHERE status_seleksi = 'Terseleksi'";
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()) {
                int total_terseleksi = rs.getInt("total_terseleksi");
                if (total_terseleksi > 0) {
                    hasData = true; // Set hasData ke true jika ada data
                }
                jLabelTotalTerseleksi.setText(String.valueOf(total_terseleksi));
            } else {
                jLabelTotalTerseleksi.setText("0"); // Jika tidak ada data, set nilai label ke 0
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTotalPenduduk = new javax.swing.JLabel();
        jLabelTotalBelumTerseleksi = new javax.swing.JLabel();
        jLabelTotalKriteria = new javax.swing.JLabel();
        jLabelTotalTerseleksi = new javax.swing.JLabel();
        jlabel_icon_belum_terseleksi = new javax.swing.JLabel();
        jlabel_icon_terseleksi = new javax.swing.JLabel();
        jlabel_icon_total_penduduk = new javax.swing.JLabel();
        jlabel_icon_total_kriteria = new javax.swing.JLabel();
        jlabel_total_penduduk = new javax.swing.JLabel();
        jlabel_total_kriteria = new javax.swing.JLabel();
        jlabel_belum_terseleksi = new javax.swing.JLabel();
        jlabel_terseleksi = new javax.swing.JLabel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1080, 690));
        setLayout(null);

        jLabelTotalPenduduk.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 36)); // NOI18N
        jLabelTotalPenduduk.setForeground(new java.awt.Color(255, 255, 255));
        add(jLabelTotalPenduduk);
        jLabelTotalPenduduk.setBounds(110, 150, 150, 50);

        jLabelTotalBelumTerseleksi.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 36)); // NOI18N
        jLabelTotalBelumTerseleksi.setForeground(new java.awt.Color(255, 255, 255));
        add(jLabelTotalBelumTerseleksi);
        jLabelTotalBelumTerseleksi.setBounds(110, 430, 150, 50);

        jLabelTotalKriteria.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 36)); // NOI18N
        jLabelTotalKriteria.setForeground(new java.awt.Color(255, 255, 255));
        add(jLabelTotalKriteria);
        jLabelTotalKriteria.setBounds(580, 150, 150, 50);

        jLabelTotalTerseleksi.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 36)); // NOI18N
        jLabelTotalTerseleksi.setForeground(new java.awt.Color(255, 255, 255));
        add(jLabelTotalTerseleksi);
        jLabelTotalTerseleksi.setBounds(580, 430, 150, 50);

        jlabel_icon_belum_terseleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/belum-terseleksi.png"))); // NOI18N
        jlabel_icon_belum_terseleksi.setText("jLabel1");
        add(jlabel_icon_belum_terseleksi);
        jlabel_icon_belum_terseleksi.setBounds(430, 360, 70, 90);

        jlabel_icon_terseleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/terseleksi.png"))); // NOI18N
        jlabel_icon_terseleksi.setText("jLabel1");
        add(jlabel_icon_terseleksi);
        jlabel_icon_terseleksi.setBounds(890, 360, 70, 90);

        jlabel_icon_total_penduduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/penduduk.png"))); // NOI18N
        jlabel_icon_total_penduduk.setText("jLabel1");
        add(jlabel_icon_total_penduduk);
        jlabel_icon_total_penduduk.setBounds(430, 90, 70, 90);

        jlabel_icon_total_kriteria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/kriteria.png"))); // NOI18N
        jlabel_icon_total_kriteria.setText("jLabel1");
        add(jlabel_icon_total_kriteria);
        jlabel_icon_total_kriteria.setBounds(890, 90, 80, 90);

        jlabel_total_penduduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card-penduduk.png"))); // NOI18N
        jlabel_total_penduduk.setText("jLabel2");
        jlabel_total_penduduk.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        add(jlabel_total_penduduk);
        jlabel_total_penduduk.setBounds(80, 60, 450, 256);

        jlabel_total_kriteria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card-total-kriteria.png"))); // NOI18N
        jlabel_total_kriteria.setText("jLabel2");
        jlabel_total_kriteria.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        add(jlabel_total_kriteria);
        jlabel_total_kriteria.setBounds(550, 60, 450, 256);

        jlabel_belum_terseleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card-belum-terseleksi.png"))); // NOI18N
        jlabel_belum_terseleksi.setText("jLabel2");
        jlabel_belum_terseleksi.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        add(jlabel_belum_terseleksi);
        jlabel_belum_terseleksi.setBounds(80, 330, 450, 256);

        jlabel_terseleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card-terseleksi.png"))); // NOI18N
        jlabel_terseleksi.setText("jLabel2");
        jlabel_terseleksi.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        add(jlabel_terseleksi);
        jlabel_terseleksi.setBounds(550, 330, 450, 256);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelTotalBelumTerseleksi;
    private javax.swing.JLabel jLabelTotalKriteria;
    private javax.swing.JLabel jLabelTotalPenduduk;
    private javax.swing.JLabel jLabelTotalTerseleksi;
    private javax.swing.JLabel jlabel_belum_terseleksi;
    private javax.swing.JLabel jlabel_icon_belum_terseleksi;
    private javax.swing.JLabel jlabel_icon_terseleksi;
    private javax.swing.JLabel jlabel_icon_total_kriteria;
    private javax.swing.JLabel jlabel_icon_total_penduduk;
    private javax.swing.JLabel jlabel_terseleksi;
    private javax.swing.JLabel jlabel_total_kriteria;
    private javax.swing.JLabel jlabel_total_penduduk;
    // End of variables declaration//GEN-END:variables
}
