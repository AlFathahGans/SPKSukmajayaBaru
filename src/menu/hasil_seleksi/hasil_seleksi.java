/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.hasil_seleksi;

import java.awt.Color;
import koneksi.koneksi;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Locale;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import spksukmajayabaru.MultilineHeaderRenderer;

import spksukmajayabaru.exceptionData;

/**
 *
 * @author Acer
 */
public class hasil_seleksi extends javax.swing.JPanel {
    
    private Connection conne = new koneksi().getConnection();
    koneksi conn= new koneksi();
    
    DefaultTableModel tbn;    
    DefaultTableModel tbp;

    /**
     * Creates new form Hasil Seleksi
     */
    public hasil_seleksi() {
        initComponents();
        
        setHeaderTableNormalisasi();        
        setHeaderTablePeringkat();

        
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
        
        setSize(1030, 630); // Ukuran panel sesuai kebutuhan
        // Set background panel menjadi transparan
        setOpaque(false); 
        
        jTable1.setEnabled(false);        
        jTable2.setEnabled(false);
        
    }
    
     public void tabelmodelnormalisasi(){
       tbn =new DefaultTableModel();
       tbn.addColumn("NIK");
       tbn.addColumn("Nama");
       tbn.addColumn("Penghasilan Satu Jiwa dalam Keluarga Perbulan");
       tbn.addColumn("Jumlah Anggota Keluarga dalam 1 (satu) Rumah");
       tbn.addColumn("Pengeluaran Satu Jiwa dalam Keluarga");        
       tbn.addColumn("Status Kepemilikan Rumah");
       jTable1.setModel(tbn);
       
       setHeaderTableNormalisasi();
       
       // Mengatur lebar kolom
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);  // Lebar kolom Nama.
        columnModel.getColumn(1).setPreferredWidth(200); // Lebar kolom NIK
        columnModel.getColumn(2).setPreferredWidth(300); // Lebar kolom K1        
        columnModel.getColumn(3).setPreferredWidth(300); // Lebar kolom K2
        columnModel.getColumn(4).setPreferredWidth(300); // Lebar kolom K3
        columnModel.getColumn(5).setPreferredWidth(250); // Lebar kolom K4
    }
     
     public void tabelmodelperingkat(){
        try {
            tbp =new DefaultTableModel();
            tbp.addColumn("NIK");            
            tbp.addColumn("Nama");
            tbp.addColumn("Nilai");
            tbp.addColumn("Peringkat");
            jTable2.setModel(tbp);
               ResultSet res = conn.ambilData("SELECT nik,nama, nilai FROM tbl_peringkat ORDER BY nilai DESC");
            int rank = 1;
            while (res.next()){
                tbp.addRow(new Object[]{res.getString(1),res.getString(2),res.getString(3),rank});
                rank++;
            }
            
            setHeaderTablePeringkat();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
       }
     
     private void setHeaderTableNormalisasi() {
       // Membuat renderer multiline untuk header tabel
        MultilineHeaderRenderer headerRenderer = new MultilineHeaderRenderer();

        headerRenderer.setBackground(Color.decode("#5b8bdf")); // Mengatur warna latar belakang
        headerRenderer.setForeground(Color.WHITE); // Mengatur warna teks

        // Apply renderer to each column header in jTabelDataAlternatif
        for (int i = 0; i < jTable1.getColumnModel().getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }


        // Adjust header heights
        JTableHeader header = jTable1.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 40)); // Adjust height as needed

     }
     
     private void setHeaderTablePeringkat() {
        // Membuat renderer untuk header tabel
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        
        headerRenderer.setBackground(Color.decode("#5b8bdf")); // Mengatur warna latar belakang
        headerRenderer.setForeground(Color.WHITE); // Mengatur warna teks

        for (int i = 0; i < jTable2.getColumnModel().getColumnCount(); i++) {
            jTable2.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        // Mengatur tinggi header tabel
        JTableHeader header = jTable2.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));
    }
     
    public void normalisasi() {
        try {
            // Query SQL untuk mendapatkan data normalisasi
            String query = "SELECT " +
                    "a.nik AS nikAlternatif, " +                    
                    "a.nama AS namaAlternatif, " +
                    "MAX(CASE WHEN k.kode = 'C1' THEN sk.nilai_rating END) / max_values.max_C1 AS C1, " +
                    "MAX(CASE WHEN k.kode = 'C2' THEN sk.nilai_rating END) / max_values.max_C2 AS C2, " +
                    "MAX(CASE WHEN k.kode = 'C3' THEN sk.nilai_rating END) / max_values.max_C3 AS C3, " +
                    "MAX(CASE WHEN k.kode = 'C4' THEN sk.nilai_rating END) / max_values.max_C4 AS C4 " +
                    "FROM tbl_alternatif a " +
                    "JOIN tbl_nilai_alternatif na ON a.id = na.alternatif_id " +
                    "JOIN tbl_kriteria k ON na.kriteria_id = k.id " +
                    "JOIN tbl_sub_kriteria sk ON na.sub_kriteria_id = sk.id " +
                    "JOIN ( " +
                    "    SELECT " +
                    "        MAX(CASE WHEN k.kode = 'C1' THEN sk.nilai_rating END) AS max_C1, " +
                    "        MAX(CASE WHEN k.kode = 'C2' THEN sk.nilai_rating END) AS max_C2, " +
                    "        MAX(CASE WHEN k.kode = 'C3' THEN sk.nilai_rating END) AS max_C3, " +
                    "        MAX(CASE WHEN k.kode = 'C4' THEN sk.nilai_rating END) AS max_C4 " +
                    "    FROM tbl_alternatif a " +
                    "    JOIN tbl_nilai_alternatif na ON a.id = na.alternatif_id " +
                    "    JOIN tbl_kriteria k ON na.kriteria_id = k.id " +
                    "    JOIN tbl_sub_kriteria sk ON na.sub_kriteria_id = sk.id " +
                    ") AS max_values ON 1=1 " +
                    "GROUP BY a.id, a.nama " +
                    "ORDER BY a.nama";

            // Mengambil data menggunakan koneksi database
            ResultSet res = conn.ambilData(query);
            tabelmodelnormalisasi(); // Mengatur model tabel

            // Menambahkan baris data ke tabel model
            while (res.next()) {
                tbn.addRow(new Object[]{
                    res.getString("nikAlternatif"),                    
                    res.getString("namaAlternatif"),
                    res.getFloat("C1"),
                    res.getFloat("C2"),
                    res.getFloat("C3"),
                    res.getFloat("C4")
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        
    }
    
    public void cariperingkat() {
        try {
            LinkedList<Float> bobot = new LinkedList<>();

            // Ambil bobot kriteria dari tabel
            ResultSet res3 = conn.ambilData("SELECT bobot FROM tbl_kriteria");
            while (res3.next()) {
                bobot.add(res3.getFloat(1)); // Ambil bobot dari kolom pertama
            }

            // Hapus data lama dari tabel peringkat
            String deleteSql = "DELETE FROM tbl_peringkat";
            conn.simpanData(deleteSql);

            // Hitung skor untuk setiap alternatif
            for (int x = 0; x < jTable1.getRowCount(); x++) {
                double r1 = Float.parseFloat(jTable1.getValueAt(x, 2).toString()) * bobot.get(0);
                double r2 = Float.parseFloat(jTable1.getValueAt(x, 3).toString()) * bobot.get(1);
                double r3 = Float.parseFloat(jTable1.getValueAt(x, 4).toString()) * bobot.get(2);
                double r4 = Float.parseFloat(jTable1.getValueAt(x, 5).toString()) * bobot.get(3);

                double totalScore = r1 + r2 + r3 + r4;
                
                // Format untuk angka desimal
                
                DecimalFormat df = new DecimalFormat("#.###");
                
                System.out.println("R1 = " +Float.parseFloat(jTable1.getValueAt(x, 2).toString())+ " * " +bobot.get(0)+ " = " + df.format(r1));
                System.out.println("R2 = " +Float.parseFloat(jTable1.getValueAt(x, 3).toString())+ " * " +bobot.get(1)+ " = " + df.format(r2));
                System.out.println("R3 = " +Float.parseFloat(jTable1.getValueAt(x, 4).toString())+ " * " +bobot.get(2)+ " = " + df.format(r3));
                System.out.println("R4 = " +Float.parseFloat(jTable1.getValueAt(x, 5).toString())+ " * " +bobot.get(3)+ " = " + df.format(r4));
                System.out.println("Proses ke-" + (x+1) + " - Total skor: " + df.format(totalScore));

                // Ambil nama alternatif dari tabel
                String nikAlternatif = jTable1.getValueAt(x, 0).toString();                
                String namaAlternatif = jTable1.getValueAt(x, 1).toString();


                // Ambil id_alternatif dari database berdasarkan kode
                String idQuery = "SELECT id FROM tbl_alternatif WHERE nik = '" + nikAlternatif + "'";
                ResultSet idResult = conn.ambilData(idQuery);
                int idAlternatif = 0;
                if (idResult.next()) {
                    idAlternatif = idResult.getInt("id");
                } else {
                    throw new IllegalArgumentException("NIK alternatif tidak valid: " + nikAlternatif);
                }

                // Simpan skor ke tabel peringkat
                String insertSql = "INSERT INTO tbl_peringkat (id_alternatif, nik, nama, nilai) VALUES ('" + idAlternatif + "', '" + nikAlternatif + "', '" + namaAlternatif + "', '" + totalScore + "')";
                conn.simpanData(insertSql);
            }

            // Update tabel peringkat setelah perhitungan
            tabelmodelperingkat();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
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

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_normalisasi = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 204, 229));
        setLayout(null);

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tahap Normalisasi Nilai Data Penduduk");
        add(jLabel2);
        jLabel2.setBounds(40, 50, 540, 43);

        jScrollPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N

        jTable1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jTable1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "NIK", "Nama", "C1", "C2", "C3", "C4"
            }
        ));
        jTable1.setMinimumSize(new java.awt.Dimension(95, 100));
        jTable1.setRowHeight(25);
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(40, 120, 1005, 175);

        btn_normalisasi.setBackground(new java.awt.Color(0, 204, 0));
        btn_normalisasi.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btn_normalisasi.setForeground(new java.awt.Color(255, 255, 255));
        btn_normalisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hitung1.png"))); // NOI18N
        btn_normalisasi.setText("Lakukan Normalisasi");
        btn_normalisasi.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        btn_normalisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_normalisasiActionPerformed(evt);
            }
        });
        add(btn_normalisasi);
        btn_normalisasi.setBounds(590, 50, 210, 42);

        jScrollPane2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N

        jTable2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jTable2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NIK", "Nama", "Nilai", "Peringkat"
            }
        ));
        jTable2.setIntercellSpacing(new java.awt.Dimension(2, 2));
        jTable2.setMinimumSize(new java.awt.Dimension(95, 100));
        jTable2.setRowHeight(25);
        jScrollPane2.setViewportView(jTable2);

        add(jScrollPane2);
        jScrollPane2.setBounds(120, 400, 850, 156);

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("*tekan tombol untuk proses normalisasi*");
        add(jLabel10);
        jLabel10.setBounds(810, 60, 230, 14);

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Hasil Seleksi");
        add(jLabel4);
        jLabel4.setBounds(440, 320, 230, 43);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_normalisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_normalisasiActionPerformed
        normalisasi();
        cariperingkat();
    }//GEN-LAST:event_btn_normalisasiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_normalisasi;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
