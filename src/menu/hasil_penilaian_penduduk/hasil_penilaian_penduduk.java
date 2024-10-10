/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.hasil_penilaian_penduduk;

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
public class hasil_penilaian_penduduk extends javax.swing.JPanel {
    
    private Connection conne = new koneksi().getConnection();
    koneksi conn= new koneksi();
    
    DefaultTableModel tbda;   
    DefaultTableModel tbna;

    /**
     * Creates new form nilai penduduk
     */
    public hasil_penilaian_penduduk() {
        initComponents();
        
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
        
        setSize(1030, 630); // Ukuran panel sesuai kebutuhan
        // Set background panel menjadi transparan
        setOpaque(false); 
        
        tabelmodeldataalternatif();        
        tabelmodelnilaialternatif();
        
        jTabelDataAlternatif.setEnabled(false);        
        jTabelNilaiAlternatif.setEnabled(false);

        
         setHeaderTable();

    }
    
    public void tabelmodeldataalternatif() {
        tbda = new DefaultTableModel();
        tbda.addColumn("Nama");
        tbda.addColumn("NIK");
        tbda.addColumn("Penghasilan Satu Jiwa dalam Keluarga Perbulan");
        tbda.addColumn("Jumlah Anggota Keluarga dalam 1 (satu) Rumah");
        tbda.addColumn("Pengeluaran Satu Jiwa dalam Keluarga");        
        tbda.addColumn("Status Kepemilikan Rumah");

        jTabelDataAlternatif.setModel(tbda); 
        
        try {
            ResultSet res = conn.ambilData(
                "SELECT \n" +
                "    a.nama AS namaAlternatif,\n" +                
                "    a.nik AS nikAlternatif,\n" +
                "    MAX(CASE WHEN k.kode = 'C1' THEN sk.sub_nama END) AS C1,\n" +
                "    MAX(CASE WHEN k.kode = 'C2' THEN sk.sub_nama END) AS C2,\n" +
                "    MAX(CASE WHEN k.kode = 'C3' THEN sk.sub_nama END) AS C3,\n" +
                "    MAX(CASE WHEN k.kode = 'C4' THEN sk.sub_nama END) AS C4\n" +
                "FROM\n" +
                "    tbl_alternatif a\n" +
                "JOIN\n" +
                "    tbl_nilai_alternatif na ON a.id = na.alternatif_id\n" +
                "JOIN\n" +
                "    tbl_kriteria k ON na.kriteria_id = k.id\n" +
                "JOIN\n" +
                "    tbl_sub_kriteria sk ON na.sub_kriteria_id = sk.id\n" +
                "WHERE\n" +
                "    a.status_seleksi ='Terseleksi'\n" +
                "GROUP BY\n" +
                "    a.id, a.nama\n" +
                "ORDER BY\n" +
                "    a.kode;"
            );

            while (res.next()) {
                tbda.addRow(new Object[]{
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),                    
                    res.getString(5),                    
                    res.getString(6)


                });
            }

            jTabelDataAlternatif.setModel(tbda);
            
            setHeaderTable();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        
        // Mengatur lebar kolom
        TableColumnModel columnModel = jTabelDataAlternatif.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);  // Lebar kolom Nama.
        columnModel.getColumn(1).setPreferredWidth(200); // Lebar kolom NIK
        columnModel.getColumn(2).setPreferredWidth(300); // Lebar kolom K1        
        columnModel.getColumn(3).setPreferredWidth(300); // Lebar kolom K2
        columnModel.getColumn(4).setPreferredWidth(300); // Lebar kolom K3
        columnModel.getColumn(5).setPreferredWidth(250); // Lebar kolom K4


    }

    public void tabelmodelnilaialternatif() {
        tbna = new DefaultTableModel();
        tbna.addColumn("Nama");
        tbna.addColumn("NIK");
        tbna.addColumn("Penghasilan Satu Jiwa dalam Keluarga Perbulan");
        tbna.addColumn("Jumlah Anggota Keluarga dalam 1 (satu) Rumah");
        tbna.addColumn("Pengeluaran Satu Jiwa dalam Keluarga");        
        tbna.addColumn("Status Kepemilikan Rumah");
        jTabelNilaiAlternatif.setModel(tbna); 
        
        try {
            ResultSet res = conn.ambilData(
                "SELECT \n" +
                "    a.nama AS nama,\n" +                
                "    a.nik AS nikAlternatif,\n" +
                "    MAX(CASE WHEN k.kode = 'C1' THEN sk.nilai_rating END) AS C1,\n" +
                "    MAX(CASE WHEN k.kode = 'C2' THEN sk.nilai_rating END) AS C2,\n" +
                "    MAX(CASE WHEN k.kode = 'C3' THEN sk.nilai_rating END) AS C3,\n" +
                "    MAX(CASE WHEN k.kode = 'C4' THEN sk.nilai_rating END) AS C4\n" +
                "FROM\n" +
                "    tbl_alternatif a\n" +
                "JOIN\n" +
                "    tbl_nilai_alternatif na ON a.id = na.alternatif_id\n" +
                "JOIN\n" +
                "    tbl_kriteria k ON na.kriteria_id = k.id\n" +
                "JOIN\n" +
                "    tbl_sub_kriteria sk ON na.sub_kriteria_id = sk.id\n" +
                "WHERE\n" +
                "    a.status_seleksi ='Terseleksi'\n" +
                "GROUP BY\n" +
                "    a.id, a.nama\n" +
                "ORDER BY\n" +
                "    a.kode;"
            );

            while (res.next()) {
                tbna.addRow(new Object[]{
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),                    
                    res.getString(5),                 
                    res.getString(6)


                });
            }

            jTabelNilaiAlternatif.setModel(tbna);
            
            setHeaderTable();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        
        // Mengatur lebar kolom
        TableColumnModel columnModel = jTabelNilaiAlternatif.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);  // Lebar kolom Nama.
        columnModel.getColumn(1).setPreferredWidth(200); // Lebar kolom NIK
        columnModel.getColumn(2).setPreferredWidth(300); // Lebar kolom K1        
        columnModel.getColumn(3).setPreferredWidth(300); // Lebar kolom K2
        columnModel.getColumn(4).setPreferredWidth(300); // Lebar kolom K3
        columnModel.getColumn(5).setPreferredWidth(250); // Lebar kolom K4
    }
    
    private void setHeaderTable() {
        // Membuat renderer multiline untuk header tabel
        MultilineHeaderRenderer headerRenderer = new MultilineHeaderRenderer();

        headerRenderer.setBackground(Color.decode("#5b8bdf")); // Mengatur warna latar belakang
        headerRenderer.setForeground(Color.WHITE); // Mengatur warna teks

        // Apply renderer to each column header in jTabelDataAlternatif
        for (int i = 0; i < jTabelDataAlternatif.getColumnModel().getColumnCount(); i++) {
            jTabelDataAlternatif.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Apply renderer to each column header in jTabelNilaiAlternatif
        for (int i = 0; i < jTabelNilaiAlternatif.getColumnModel().getColumnCount(); i++) {
            jTabelNilaiAlternatif.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Adjust header heights
        JTableHeader header = jTabelDataAlternatif.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 40)); // Adjust height as needed

        JTableHeader headerNilai = jTabelNilaiAlternatif.getTableHeader();
        headerNilai.setPreferredSize(new Dimension(headerNilai.getWidth(), 40)); // Adjust height as needed
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabelDataAlternatif = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabelNilaiAlternatif = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 204, 229));
        setLayout(null);

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tabel Nilai Data Penduduk");
        add(jLabel2);
        jLabel2.setBounds(40, 50, 460, 43);

        jScrollPane2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N

        jTabelDataAlternatif.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jTabelDataAlternatif.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jTabelDataAlternatif.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nama", "NIK", "C1", "C2", "C3", "C4"
            }
        ));
        jTabelDataAlternatif.setAutoscrolls(false);
        jTabelDataAlternatif.setIntercellSpacing(new java.awt.Dimension(2, 2));
        jTabelDataAlternatif.setMinimumSize(new java.awt.Dimension(95, 100));
        jTabelDataAlternatif.setRowHeight(25);
        jTabelDataAlternatif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelDataAlternatifMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabelDataAlternatif);

        add(jScrollPane2);
        jScrollPane2.setBounds(40, 120, 1000, 156);

        jScrollPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N

        jTabelNilaiAlternatif.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jTabelNilaiAlternatif.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jTabelNilaiAlternatif.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Alternatif", "Kualitas", "Harga", "Merek", "Warna/Motif"
            }
        ));
        jTabelNilaiAlternatif.setIntercellSpacing(new java.awt.Dimension(2, 2));
        jTabelNilaiAlternatif.setMinimumSize(new java.awt.Dimension(95, 100));
        jTabelNilaiAlternatif.setRowHeight(25);
        jScrollPane1.setViewportView(jTabelNilaiAlternatif);

        add(jScrollPane1);
        jScrollPane1.setBounds(40, 380, 1000, 156);

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tabel Konversi Nilai Data Penduduk");
        add(jLabel3);
        jLabel3.setBounds(40, 310, 600, 43);
    }// </editor-fold>//GEN-END:initComponents

    private void jTabelDataAlternatifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelDataAlternatifMouseClicked

    }//GEN-LAST:event_jTabelDataAlternatifMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabelDataAlternatif;
    private javax.swing.JTable jTabelNilaiAlternatif;
    // End of variables declaration//GEN-END:variables
}
