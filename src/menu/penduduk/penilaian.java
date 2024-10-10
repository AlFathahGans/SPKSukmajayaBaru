/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.penduduk;

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

import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import spksukmajayabaru.exceptionData;
/**
 *
 * @author Acer
 */
public class penilaian extends javax.swing.JDialog {
    
    private Connection conne = new koneksi().getConnection();
    koneksi conn= new koneksi();
    
    DefaultTableModel tbda;
    
    /**
     * Creates new form penilaian
     */
    public penilaian(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Form Penilaian Penduduk");
        setSize(1300,700);
        // Pusatkan jendela ditengah
        setLocationRelativeTo(null); 
        
        
        dataListFromPenghasilan();        
        dataListFromJumlahKeluarga();
        dataListFromPengeluaran();
        dataListFromStatusRumah();
        
        tabelmodeldataalternatif();

        setHeaderTable();
        
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
        
        btn_update.setEnabled(false);       
        btn_hapus.setEnabled(false);     
        
    }
    
    public void setData(String nama, String nik) {
        System.out.println("Setting Name: " + nama);
        System.out.println("Setting NIK: " + nik);

        // Cek nilai sebelum diatur ke JLabel
        System.out.println("jLabelNama (before setText): " + jLabelNama.getText());
        System.out.println("jLabelNIK (before setText): " + jLabelNIK.getText());

        jLabelNama.setText(nama);
        jLabelNIK.setText(nik);

        // Cek nilai setelah diatur ke JLabel
        System.out.println("jLabelNama (after setText): " + jLabelNama.getText());
        System.out.println("jLabelNIK (after setText): " + jLabelNIK.getText());

        jLabelNama.getParent().revalidate();
        jLabelNama.getParent().repaint();
        
        tabelmodeldataalternatif();
    }

    public void tabelmodeldataalternatif() {
        tbda = new DefaultTableModel();
        tbda.addColumn("C1");
        tbda.addColumn("C2");
        tbda.addColumn("C3");
        tbda.addColumn("C4");
        jTabelDataAlternatif.setModel(tbda);

        // Ambil data dari JLabel
        String namaPenduduk = jLabelNama.getText();
        String nikPenduduk = jLabelNIK.getText();

        System.out.println("Nama Penduduk: " + namaPenduduk);
        System.out.println("NIK Penduduk: " + nikPenduduk);

        try {
            String query = "SELECT " +
                           "    MAX(CASE WHEN k.kode = 'C1' THEN sk.sub_nama END) AS C1, " +
                           "    MAX(CASE WHEN k.kode = 'C2' THEN sk.sub_nama END) AS C2, " +
                           "    MAX(CASE WHEN k.kode = 'C3' THEN sk.sub_nama END) AS C3, " +
                           "    MAX(CASE WHEN k.kode = 'C4' THEN sk.sub_nama END) AS C4 " +
                           "FROM " +
                           "    tbl_alternatif a " +
                           "JOIN " +
                           "    tbl_nilai_alternatif na ON a.id = na.alternatif_id " +
                           "JOIN " +
                           "    tbl_kriteria k ON na.kriteria_id = k.id " +
                           "JOIN " +
                           "    tbl_sub_kriteria sk ON na.sub_kriteria_id = sk.id " +
                           "WHERE a.nama = ? AND a.nik = ? " +
                           "GROUP BY a.id, a.nama " +
                           "ORDER BY a.kode;";
            
            
            PreparedStatement pst = conn.getConnection().prepareStatement(query);
            pst.setString(1, namaPenduduk);
            pst.setString(2, nikPenduduk);

            ResultSet res = pst.executeQuery();
            boolean dataExists = false;
            
            while (res.next()) {
                tbda.addRow(new Object[]{
                    res.getString("C1"),
                    res.getString("C2"),
                    res.getString("C3"),
                    res.getString("C4")
                });
                
                dataExists = true;

            }
            
             if (!dataExists) {
                // Tambahkan baris yang menunjukkan tidak ada data
                tbda.addRow(new Object[]{"", "Tidak ada data...", "", ""});
            }

            jTabelDataAlternatif.setModel(tbda);
            setHeaderTable();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void setHeaderTable() {
        // Membuat renderer untuk header tabel
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        
        headerRenderer.setBackground(Color.decode("#5b8bdf")); // Mengatur warna latar belakang
        headerRenderer.setForeground(Color.WHITE); // Mengatur warna teks
        
        // Mengatur renderer untuk setiap kolom di header tabel
        for (int i = 0; i < jTabelDataAlternatif.getColumnModel().getColumnCount(); i++) {
            jTabelDataAlternatif.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        // Mengatur tinggi header tabel
        JTableHeader header = jTabelDataAlternatif.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));
    }
    
    private void clear(){
        jComboBox1.setSelectedIndex(0);        
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        
        btn_simpan.setEnabled(true);
        btn_update.setEnabled(false);       
        btn_hapus.setEnabled(false);     
    }
    
    private void cekKosong() throws exceptionData
    {
       if(
         jComboBox1.getSelectedIndex() == 0 || 
         jComboBox2.getSelectedIndex() == 0 || 
         jComboBox3.getSelectedIndex() == 0 || 
         jComboBox4.getSelectedIndex() == 0)
            throw new exceptionData();
    }
    

    public int simpan() throws SQLException {
        Connection connect = conn.getConnection();

        String idPenghasilan = jComboBox1.getSelectedItem().toString().split(" - ")[0];        
        String idJumlahKeluarga = jComboBox2.getSelectedItem().toString().split(" - ")[0];
        String idPengeluaran = jComboBox3.getSelectedItem().toString().split(" - ")[0];
        String idStatusRumah = jComboBox4.getSelectedItem().toString().split(" - ")[0];
        
        // Cek apakah semua combo box sudah dipilih
        if (idPenghasilan.equals("[Pilih Penghasilan Jiwa]") || idJumlahKeluarga.equals("[Pilih Jumlah Keluarga]") || idPengeluaran.equals("[Pilih Pengeluaran Jiwa]") || idStatusRumah.equals("[Pilih Status Rumah]")) {
            JOptionPane.showMessageDialog(this, "Silakan pilih semua opsi sebelum menyimpan data.");
            return 0;
        }
        
        System.out.println(idPenghasilan+idJumlahKeluarga+idPengeluaran +idStatusRumah);
        
        String namaPenduduk = jLabelNama.getText();        
        String nikPenduduk = jLabelNIK.getText();

        String query = "SELECT id FROM tbl_alternatif WHERE nama = ? AND nik = ?";
        PreparedStatement pst = connect.prepareStatement(query);
        pst.setString(1, namaPenduduk);        
        pst.setString(2, nikPenduduk);

        ResultSet res = pst.executeQuery();
        
        int alternatifId = 0;
        
        if (res.next()) {
            alternatifId = res.getInt("id");
        } else {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan.");
        }
        
        // Update tbl_alternatif untuk status_seleksi
        String status_seleksi = "Terseleksi";
        String sqlUpdate = "UPDATE tbl_alternatif SET status_seleksi = ? WHERE nama = ? AND nik = ?";
        PreparedStatement stmtUpdate = connect.prepareStatement(sqlUpdate);
        stmtUpdate.setString(1, status_seleksi);
        stmtUpdate.setString(2, namaPenduduk);
        stmtUpdate.setString(3, nikPenduduk);

        stmtUpdate.executeUpdate();


        // Insert into tbl_nilai_kriteria
        String sqlNilaiKriteria = "INSERT INTO tbl_nilai_alternatif(alternatif_id, kriteria_id, sub_kriteria_id) VALUES (?, ?, ?)";
        PreparedStatement stmtNilaiKriteria = connect.prepareStatement(sqlNilaiKriteria);

        // idPenghasilan
        stmtNilaiKriteria.setInt(1, alternatifId);
        stmtNilaiKriteria.setInt(2, 1);  // kriteria_id untuk Penghasilan
        stmtNilaiKriteria.setInt(3, Integer.parseInt(idPenghasilan));
        stmtNilaiKriteria.addBatch();

        // idJumlahKeluarga
        stmtNilaiKriteria.setInt(1, alternatifId);
        stmtNilaiKriteria.setInt(2, 2);  // kriteria_id untuk JumlahKeluarga
        stmtNilaiKriteria.setInt(3, Integer.parseInt(idJumlahKeluarga));
        stmtNilaiKriteria.addBatch();

        // idPengeluaran
        stmtNilaiKriteria.setInt(1, alternatifId);
        stmtNilaiKriteria.setInt(2, 3);  // kriteria_id untuk Pengeluaran
        stmtNilaiKriteria.setInt(3, Integer.parseInt(idPengeluaran));
        stmtNilaiKriteria.addBatch();

        // idStatusRumah
        stmtNilaiKriteria.setInt(1, alternatifId);
        stmtNilaiKriteria.setInt(2, 4);  // kriteria_id untuk StatusRumah
        stmtNilaiKriteria.setInt(3, Integer.parseInt(idStatusRumah));
        stmtNilaiKriteria.addBatch();

        // Execute batch insert
        stmtNilaiKriteria.executeBatch();

        clear();

        return 0;
    }
    
    public int edit() throws SQLException {
        Connection connect = conn.getConnection();

        int selectedRow = jTabelDataAlternatif.getSelectedRow();

        String namaPenduduk = jLabelNama.getText();        
        String nikPenduduk = jLabelNIK.getText();

        String alternatifId = "";

        String query = "SELECT id FROM tbl_alternatif WHERE nama = ? AND nik = ?";
        PreparedStatement pst = connect.prepareStatement(query);
        pst.setString(1, namaPenduduk);       
        pst.setString(2, nikPenduduk);

        ResultSet res = pst.executeQuery();

        if (res.next()) {
            alternatifId = res.getString("id");
        } else {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan.");
        }

        // Ambil nilai dari ComboBox
        String idPenghasilan = jComboBox1.getSelectedItem().toString().split(" - ")[0];        
        String idPengeluaran = jComboBox2.getSelectedItem().toString().split(" - ")[0];
        String idJumlahKeluarga = jComboBox3.getSelectedItem().toString().split(" - ")[0];
        String idStatusRumah = jComboBox4.getSelectedItem().toString().split(" - ")[0];

        System.out.println(idPenghasilan + idPengeluaran + idJumlahKeluarga + idStatusRumah);

        // Update tbl_nilai_alternatif
        String sqlUpdateNilaiKriteria = "UPDATE tbl_nilai_alternatif SET sub_kriteria_id = ? WHERE alternatif_id = ? AND kriteria_id = ?";

        // idPenghasilan
        PreparedStatement stmtUpdatePenghasilan = connect.prepareStatement(sqlUpdateNilaiKriteria);
        stmtUpdatePenghasilan.setInt(1, Integer.parseInt(idPenghasilan));
        stmtUpdatePenghasilan.setString(2, alternatifId);
        stmtUpdatePenghasilan.setInt(3, 1);  // kriteria_id untuk Penghasilan
        stmtUpdatePenghasilan.executeUpdate();

        // idPengeluaran
        PreparedStatement stmtUpdatePengeluaran = connect.prepareStatement(sqlUpdateNilaiKriteria);
        stmtUpdatePengeluaran.setInt(1, Integer.parseInt(idPengeluaran));
        stmtUpdatePengeluaran.setString(2, alternatifId);
        stmtUpdatePengeluaran.setInt(3, 2);  // kriteria_id untuk Pengeluaran
        stmtUpdatePengeluaran.executeUpdate();

        // idJumlahKeluarga
        PreparedStatement stmtUpdateJumlahKeluarga = connect.prepareStatement(sqlUpdateNilaiKriteria);
        stmtUpdateJumlahKeluarga.setInt(1, Integer.parseInt(idJumlahKeluarga));
        stmtUpdateJumlahKeluarga.setString(2, alternatifId);
        stmtUpdateJumlahKeluarga.setInt(3, 3);  // kriteria_id untuk JumlahKeluarga
        stmtUpdateJumlahKeluarga.executeUpdate();

        // idStatusRumah
        PreparedStatement stmtUpdateStatusRumah = connect.prepareStatement(sqlUpdateNilaiKriteria);
        stmtUpdateStatusRumah.setInt(1, Integer.parseInt(idStatusRumah));
        stmtUpdateStatusRumah.setString(2, alternatifId);
        stmtUpdateStatusRumah.setInt(3, 4);  // kriteria_id untuk StatusRumah
        stmtUpdateStatusRumah.executeUpdate();


        tabelmodeldataalternatif();           
        clear();

        return 0;
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
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        btn_simpan = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabelDataAlternatif = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabelNama = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelNIK = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 204, 229));
        setMinimumSize(new java.awt.Dimension(1270, 699));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Form Penilaian Penduduk");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 370, 43));

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nama Penduduk :");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 140, 21));

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Penghasilan Satu Jiwa dalam Keluarga Perbulan");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 320, 21));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih Penghasilan Jiwa]" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 310, 40));

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Jumlah Anggota Keluarga dalam 1 (satu) Rumah");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, 320, 21));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih Jumlah Keluarga]" }));
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 310, 40));

        jLabel11.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Pengeluaran Satu Jiwa dalam Keluarga");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, 310, 21));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih Pengeluaran Jiwa]" }));
        getContentPane().add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, 310, 40));

        jLabel9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Status Kepemilikan Rumah");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, 310, 21));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih Status Rumah]" }));
        getContentPane().add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 590, 310, 40));

        btn_simpan.setBackground(new java.awt.Color(91, 139, 223));
        btn_simpan.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(255, 255, 255));
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tambah2.png"))); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 410, 129, 42));

        btn_update.setBackground(new java.awt.Color(91, 139, 223));
        btn_update.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        getContentPane().add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 470, 129, 42));

        btn_hapus.setBackground(new java.awt.Color(91, 139, 223));
        btn_hapus.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus.png"))); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 530, 129, 42));

        jScrollPane2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N

        jTabelDataAlternatif.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jTabelDataAlternatif.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jTabelDataAlternatif.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "C1", "C2", "C3", "C4"
            }
        ));
        jTabelDataAlternatif.setIntercellSpacing(new java.awt.Dimension(2, 2));
        jTabelDataAlternatif.setMinimumSize(new java.awt.Dimension(95, 100));
        jTabelDataAlternatif.setRowHeight(25);
        jTabelDataAlternatif.setSelectionBackground(new java.awt.Color(0, 204, 229));
        jTabelDataAlternatif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelDataAlternatifMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabelDataAlternatif);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 200, 750, 56));

        jLabel12.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Tabel Penilaian Penduduk");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 132, 410, -1));

        jLabelNama.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 16)); // NOI18N
        jLabelNama.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNama.setText("Hasil");
        getContentPane().add(jLabelNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 190, 21));

        jLabel14.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("NIK                        :");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 140, 21));

        jLabelNIK.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 16)); // NOI18N
        jLabelNIK.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNIK.setText("Hasil");
        getContentPane().add(jLabelNIK, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 260, 190, 21));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card-penilaian.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 400, 100));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/card-dialog.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1310, 690));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        try {
            cekKosong();
        } catch(exceptionData ex) {
            JOptionPane.showMessageDialog(null, ex.showMessageError());
        }

        try {
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            simpan();
        } catch (SQLException ex) {
            Logger.getLogger(penilaian.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelmodeldataalternatif();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        try {
            JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate");

            edit();
            tabelmodeldataalternatif();
            clear();
        } catch (SQLException ex) {
            Logger.getLogger(penilaian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int selectedRow = jTabelDataAlternatif.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus.");
            return;
        }

        int ok = JOptionPane.showConfirmDialog(null, "Yakin Dihapus?", "Konfirmasi Dialog", JOptionPane.YES_NO_CANCEL_OPTION);

        if (ok == 0) {
            try {
                Connection connect = conn.getConnection();
                String alternatifId = "";
                
                // Ambil data dari JLabel
                String namaPenduduk = jLabelNama.getText();
                String nikPenduduk = jLabelNIK.getText();

                String query = "SELECT id FROM tbl_alternatif WHERE nama = ? AND nik = ?";
                PreparedStatement pst = connect.prepareStatement(query);
                pst.setString(1, namaPenduduk);                
                pst.setString(2, nikPenduduk);

                ResultSet res = pst.executeQuery();

                if (res.next()) {
                    alternatifId = res.getString("id");
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan.");
                    return;
                }

                // Hapus dari tbl_nilai_alternatif terlebih dahulu
                String sqlDeleteNilai = "DELETE FROM tbl_nilai_alternatif WHERE alternatif_id = ?";
                PreparedStatement pstDeleteNilai = connect.prepareStatement(sqlDeleteNilai);
                pstDeleteNilai.setString(1, alternatifId);
                pstDeleteNilai.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                tabelmodeldataalternatif();
                clear();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data gagal dihapus");
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void jTabelDataAlternatifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelDataAlternatifMouseClicked
        // Ambil nama kriteria dari baris yang dipilih di tabel
        int bar = jTabelDataAlternatif.getSelectedRow();

        // Ambil nilai dari kolom yang sesuai di tabel
        String penghasilan = jTabelDataAlternatif.getValueAt(bar, 0).toString();
        String jumlahKeluarga = jTabelDataAlternatif.getValueAt(bar, 1).toString();
        String pengeluaran = jTabelDataAlternatif.getValueAt(bar, 2).toString();
        String statusRumah = jTabelDataAlternatif.getValueAt(bar, 3).toString();

        // Pilih item yang sesuai di masing-masing JComboBox
        selectComboBoxItem(jComboBox1, penghasilan);
        selectComboBoxItem(jComboBox2, jumlahKeluarga);
        selectComboBoxItem(jComboBox3, pengeluaran);
        selectComboBoxItem(jComboBox4, statusRumah);

        btn_simpan.setEnabled(false);
        btn_update.setEnabled(true);
        btn_hapus.setEnabled(true);
    }//GEN-LAST:event_jTabelDataAlternatifMouseClicked
    
    private void selectComboBoxItem(JComboBox<String> comboBox, String value) {
        comboBox.setEnabled(true);
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            String item = comboBox.getItemAt(i).toString();
            String[] parts = item.split(" - ");
            if (parts.length > 1) {
                String itemName = parts[1]; // Mengambil nama kriteria
                if (itemName.equals(value)) {
                    comboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    public void dataListFromPenghasilan() {                                           
        Connection connect = conn.getConnection();
        try {
            String query = "SELECT * FROM tbl_sub_kriteria WHERE kriteria_id = 1";
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);

            jComboBox1.removeAllItems(); // Clear existing items
            jComboBox1.addItem("[Pilih Penghasilan Jiwa]");
            while (rs.next()) {
                System.out.println(rs.getString("id") + " - " + rs.getString("sub_nama"));
                jComboBox1.addItem(rs.getString("id") + " - " + rs.getString("sub_nama")); // Assuming ID and name format
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada.");
            System.out.println(e.getMessage());
        }
    }
    
    public void dataListFromJumlahKeluarga() {                                           
        Connection connect = conn.getConnection();
        try {
            String query = "SELECT * FROM tbl_sub_kriteria WHERE kriteria_id = 2";
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);

            jComboBox2.removeAllItems(); // Clear existing items
            jComboBox2.addItem("[Pilih Jumlah Keluarga]");
            while (rs.next()) {
                System.out.println(rs.getString("id") + " - " + rs.getString("sub_nama"));
                jComboBox2.addItem(rs.getString("id") + " - " + rs.getString("sub_nama")); // Assuming ID and name format
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada.");
            System.out.println(e.getMessage());
        }
    }
    
    public void dataListFromPengeluaran() {                                           
        Connection connect = conn.getConnection();
        try {
            String query = "SELECT * FROM tbl_sub_kriteria WHERE kriteria_id = 3";
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);

            jComboBox3.removeAllItems(); // Clear existing items
            jComboBox3.addItem("[Pilih Pengeluaran Jiwa]");
            while (rs.next()) {
                System.out.println(rs.getString("id") + " - " + rs.getString("sub_nama"));
                jComboBox3.addItem(rs.getString("id") + " - " + rs.getString("sub_nama")); // Assuming ID and name format
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada.");
            System.out.println(e.getMessage());
        }
    }
    
    public void dataListFromStatusRumah() {                                           
        Connection connect = conn.getConnection();
        try {
            String query = "SELECT * FROM tbl_sub_kriteria WHERE kriteria_id = 4";
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);

            jComboBox4.removeAllItems(); // Clear existing items
            jComboBox4.addItem("[Pilih Status Rumah]");
            while (rs.next()) {
                System.out.println(rs.getString("id") + " - " + rs.getString("sub_nama"));
                jComboBox4.addItem(rs.getString("id") + " - " + rs.getString("sub_nama")); // Assuming ID and name format
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada.");
            System.out.println(e.getMessage());
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(penilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(penilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(penilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(penilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                penilaian dialog = new penilaian(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelNIK;
    private javax.swing.JLabel jLabelNama;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabelDataAlternatif;
    // End of variables declaration//GEN-END:variables

}
