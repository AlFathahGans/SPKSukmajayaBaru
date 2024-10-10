/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.subkriteria;

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
import spksukmajayabaru.exceptionData;

/**
 *
 * @author Acer
 */
public class subkriteria extends javax.swing.JPanel {
    private Connection conne = new koneksi().getConnection();
    koneksi conn= new koneksi();
    
    DefaultTableModel tbsk;
    /**
     * Creates new form subkriteria
     */
    public subkriteria() {
        initComponents();
        
        dataListFromKriteria();
        tabelmodelsubkriteria();
        setHeaderTable();
        
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
        
        btn_update.setEnabled(false);       
        btn_hapus.setEnabled(false);
        
        setOpaque(false); 
    }
    
     public void tabelmodelsubkriteria() {
        tbsk = new DefaultTableModel();
        tbsk.addColumn("ID"); // Kolom tambahan untuk ID
        tbsk.addColumn("Kriteria");
        tbsk.addColumn("Sub Kriteria");
        tbsk.addColumn("Bobot");
        jTabelSubKriteria.setModel(tbsk);

        try {
            ResultSet res = conn.ambilData(
                "SELECT "
                + "a.id, " // Menambahkan ID di sini
                + "a.sub_nama, "
                + "a.nilai_rating, "
                + "b.kode, "
                + "b.nama "
                + "FROM tbl_sub_kriteria AS a "
                + "LEFT JOIN tbl_kriteria AS b ON a.kriteria_id = b.id"
            );

            while (res.next()) {
                tbsk.addRow(new Object[]{
                    res.getString(1), // Menyimpan ID di kolom pertama
                    res.getString(5),
                    res.getString(2),
                    res.getString(3)
                });
            }

            jTabelSubKriteria.setModel(tbsk);

            // Menyembunyikan kolom ID
            jTabelSubKriteria.getColumnModel().getColumn(0).setMaxWidth(0);
            jTabelSubKriteria.getColumnModel().getColumn(0).setMinWidth(0);
            jTabelSubKriteria.getColumnModel().getColumn(0).setPreferredWidth(0);
            
            setHeaderTable();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        
        // Mengatur lebar kolom
        TableColumnModel columnModel = jTabelSubKriteria.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(300);  // Lebar kolom Kriteria.
        columnModel.getColumn(2).setPreferredWidth(200); // Lebar kolom Sub Kriteria
        columnModel.getColumn(3).setPreferredWidth(50); // Lebar kolom Bobot
    }

    
    private void setHeaderTable() {
        // Membuat renderer untuk header tabel
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        
        headerRenderer.setBackground(Color.decode("#5b8bdf")); // Mengatur warna latar belakang
        headerRenderer.setForeground(Color.WHITE); // Mengatur warna teks
        
        // Mengatur renderer untuk setiap kolom di header tabel
        for (int i = 0; i < jTabelSubKriteria.getColumnModel().getColumnCount(); i++) {
            jTabelSubKriteria.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        // Mengatur tinggi header tabel
        JTableHeader header = jTabelSubKriteria.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));
    }
    
    private void clear(){
        cb_list_kriteria.setSelectedIndex(0);
        jTextField2.setText((""));
        jTextField3.setText("");
        
        btn_simpan.setEnabled(true);
        btn_update.setEnabled(false);       
        btn_hapus.setEnabled(false);
    }
    
    private void cekKosong() throws exceptionData
    {
       if(cb_list_kriteria.getSelectedIndex() == 0 || jTextField2.getText().isEmpty()==true
               || jTextField2.getText().isEmpty() == true || jTextField3.getText().isEmpty() == true)
            throw new exceptionData();
    }
    
    public void dataListFromKriteria() {                                           
        Connection connect = conn.getConnection();
        try {
            String query = "SELECT * FROM tbl_kriteria";
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);

            cb_list_kriteria.removeAllItems(); // Clear existing items
            cb_list_kriteria.addItem("[Pilih Kriteria]");
            while (rs.next()) {
                System.out.println(rs.getString("id") + " - " + rs.getString("nama"));
                cb_list_kriteria.addItem(rs.getString("id") + " - " + rs.getString("nama")); // Assuming ID and name format
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada.");
            System.out.println(e.getMessage());
        }
    }
    
    public int simpan(){
        try {
            String idKriteria = cb_list_kriteria.getSelectedItem().toString().split(" - ")[0];
            
            PreparedStatement stmt = conne.prepareStatement("INSERT INTO tbl_sub_kriteria(kriteria_id, sub_nama, nilai_rating) values(?,?,?)");
            stmt.setString(1, idKriteria);
            stmt.setString(2, jTextField2.getText());
            stmt.setString(3, jTextField3.getText());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan", "Pesan", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        clear();
        return 0;
    }

    
    public void edit() {
        try {
            // Ambil ID dari baris yang dipilih di tabel
            String id = jTabelSubKriteria.getValueAt(jTabelSubKriteria.getSelectedRow(), 0).toString(); // Kolom ke-0 adalah ID
            String kriteriaId = cb_list_kriteria.getSelectedItem().toString().split(" - ")[0]; // Ambil kriteria_id
            String subNama = jTextField2.getText();
            String nilaiRating = jTextField3.getText();

            // Query SQL
            String sql = "UPDATE tbl_sub_kriteria SET kriteria_id='" + kriteriaId + "', sub_nama='" + subNama + "', nilai_rating='" + nilaiRating + "' WHERE id='" + id + "'";

            conn.st.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Terupdate");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Terupdate");
            System.out.println(e.getMessage());
        }
        clear();
        tabelmodelsubkriteria();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cb_list_kriteria = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        btn_simpan = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabelSubKriteria = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 204, 229));
        setPreferredSize(new java.awt.Dimension(1080, 690));
        setLayout(null);

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Kriteria");
        add(jLabel8);
        jLabel8.setBounds(50, 130, 90, 18);

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Sub Kriteria");
        add(jLabel3);
        jLabel3.setBounds(50, 190, 90, 18);

        jLabel9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Bobot");
        add(jLabel9);
        jLabel9.setBounds(50, 250, 90, 18);

        cb_list_kriteria.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        cb_list_kriteria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih Menu]" }));
        cb_list_kriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_list_kriteriaActionPerformed(evt);
            }
        });
        add(cb_list_kriteria);
        cb_list_kriteria.setBounds(150, 120, 300, 40);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(150, 180, 300, 40);

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        add(jTextField3);
        jTextField3.setBounds(150, 240, 300, 40);

        btn_simpan.setBackground(new java.awt.Color(91, 139, 223));
        btn_simpan.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(255, 255, 255));
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tambah2.png"))); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.setToolTipText("Klik untuk menyimpan data.");
        btn_simpan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        add(btn_simpan);
        btn_simpan.setBounds(40, 310, 129, 42);

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
        btn_update.setBounds(180, 310, 129, 42);

        btn_hapus.setBackground(new java.awt.Color(91, 139, 223));
        btn_hapus.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus.png"))); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.setToolTipText("Klik untuk menghapus data.");
        btn_hapus.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        add(btn_hapus);
        btn_hapus.setBounds(320, 310, 129, 42);

        jScrollPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N

        jTabelSubKriteria.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jTabelSubKriteria.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jTabelSubKriteria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Kriteria", "Sub Kriteria", "Bobot"
            }
        ));
        jTabelSubKriteria.setFillsViewportHeight(true);
        jTabelSubKriteria.setIntercellSpacing(new java.awt.Dimension(2, 2));
        jTabelSubKriteria.setMinimumSize(new java.awt.Dimension(95, 100));
        jTabelSubKriteria.setRowHeight(25);
        jTabelSubKriteria.setSelectionBackground(new java.awt.Color(0, 204, 229));
        jTabelSubKriteria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelSubKriteriaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTabelSubKriteria);

        add(jScrollPane1);
        jScrollPane1.setBounds(480, 120, 570, 456);

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Form Data Sub Kriteria");
        add(jLabel2);
        jLabel2.setBounds(50, 40, 350, 43);

        jLabel7.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tabel Data Sub Kriteria");
        add(jLabel7);
        jLabel7.setBounds(480, 40, 320, 43);
    }// </editor-fold>//GEN-END:initComponents

    private void cb_list_kriteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_list_kriteriaActionPerformed

    }//GEN-LAST:event_cb_list_kriteriaActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        try {
            cekKosong();
        } catch(exceptionData ex) {
            JOptionPane.showMessageDialog(null, ex.showMessageError());
        }
        simpan();
        tabelmodelsubkriteria();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        edit();
        //Field NonDisabled
        cb_list_kriteria.setEnabled(true);
        clear();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Yakin Dihapus?", "Konfirmasi Dialog", JOptionPane.YES_NO_CANCEL_OPTION);
        if (ok == 0) {
            try {
                // Ambil ID dari baris yang dipilih di tabel
                String id = jTabelSubKriteria.getValueAt(jTabelSubKriteria.getSelectedRow(), 0).toString(); // Kolom ke-0 adalah ID

                String sql = "DELETE FROM tbl_sub_kriteria WHERE id='" + id + "'"; // Hapus berdasarkan id
                conn.simpanData(sql);

                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                tabelmodelsubkriteria(); // Tambahkan untuk memperbarui tabel
                clear();
            }catch(HeadlessException e) {
                JOptionPane.showMessageDialog(null, "Data gagal dihapus");
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void jTabelSubKriteriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelSubKriteriaMouseClicked
        // Ambil nama kriteria dari baris yang dipilih di tabel
        int bar = jTabelSubKriteria.getSelectedRow();
        String namaKriteriaTabel = jTabelSubKriteria.getValueAt(bar, 1).toString(); // Ambil nama kriteria dari kolom kedua
        System.out.println("Nama Kriteria dari Tabel: " + namaKriteriaTabel);

        // Set value untuk JTextField
        String subNama = jTabelSubKriteria.getValueAt(bar, 2).toString();
        String nilaiRating = jTabelSubKriteria.getValueAt(bar, 3).toString();

        jTextField2.setText(subNama);
        jTextField3.setText(nilaiRating);

        // Pilih item yang sesuai di cb_list_kriteria
        cb_list_kriteria.setEnabled(true); // Jika ingin mengaktifkan JComboBox
        System.out.println("Loop starts for " + cb_list_kriteria.getItemCount() + " items.");
        for (int i = 0; i < cb_list_kriteria.getItemCount(); i++) {
            String item = cb_list_kriteria.getItemAt(i).toString();
            System.out.println("Item at index " + i + ": " + item);
            String[] parts = item.split(" - ");
            if (parts.length > 1) {
                String namaKriteria = parts[1]; // Mengambil nama kriteria
                System.out.println("Nama Kriteria dari item: " + namaKriteria);
                if (namaKriteria.equals(namaKriteriaTabel)) {
                    cb_list_kriteria.setSelectedIndex(i);
                    System.out.println("Item selected: " + item);
                    break;
                }
            }
        }

        btn_simpan.setEnabled(false);
        btn_update.setEnabled(true);       
        btn_hapus.setEnabled(true);
    }//GEN-LAST:event_jTabelSubKriteriaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> cb_list_kriteria;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabelSubKriteria;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
