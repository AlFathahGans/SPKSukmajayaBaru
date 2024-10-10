/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.penduduk;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import koneksi.koneksi;
import spksukmajayabaru.exceptionData;

//IMPORT PATH PENILAIN
import menu.penduduk.penilaian;
/**
 *
 * @author Acer
 */
public class penduduk extends javax.swing.JPanel {
    private Connection conne = new koneksi().getConnection();
    koneksi conn= new koneksi();
    

    
    DefaultTableModel tbp;
    /**
     * Creates new form penduduk
     */
    public penduduk() {
        initComponents();
        
        tabelmodelpenduduk();
        setHeaderTable();
        
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
        
        setSize(1030, 630); // Ukuran panel sesuai kebutuhan
        // Set background panel menjadi transparan
        setOpaque(false); 
        
        btn_update.setEnabled(false);       
        btn_hapus.setEnabled(false);        
        btn_penilaian.setEnabled(false);

        
        jLabelStatusNama.setVisible(false);        
        jLabelStatusHasil.setVisible(false);

    }
    
    public void tabelmodelpenduduk(){
       tbp =new DefaultTableModel();
       tbp.addColumn("ID"); // Kolom tambahan untuk ID
       tbp.addColumn("No.");
       tbp.addColumn("Nama");       
       tbp.addColumn("NIK");
       tbp.addColumn("No. KK");
       tbp.addColumn("Dusun");       
       tbp.addColumn("RT");
       tbp.addColumn("RW");
       tbp.addColumn("No. HP");
       tbp.addColumn("No. Rek");

       jTabelPenduduk.setModel(tbp); 
       try {
        int no=1;
        ResultSet res = conn.ambilData("SELECT id,nama,nik,no_kk,dusun,rt,rw,no_hp, no_rekening FROM tbl_alternatif ORDER BY nama");
        while (res.next()){
            tbp.addRow(new Object[]{
                res.getString(1),
                no++,
                res.getString(2),
                res.getString(3),               
                res.getString(4),                
                res.getString(5),
                res.getString(6),
                res.getString(7),                
                res.getString(8),                
                res.getString(9)

            });
        } 
      
        jTabelPenduduk.setModel(tbp);
        
        // Menyembunyikan kolom ID
        jTabelPenduduk.getColumnModel().getColumn(0).setMaxWidth(0);
        jTabelPenduduk.getColumnModel().getColumn(0).setMinWidth(0);
        jTabelPenduduk.getColumnModel().getColumn(0).setPreferredWidth(0);
            
        setHeaderTable();
          } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this, ex);
        }
       
        // Mengatur lebar kolom
        TableColumnModel columnModel = jTabelPenduduk.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(50);  // Lebar kolom No.
        columnModel.getColumn(2).setPreferredWidth(180); // Lebar kolom Nama
        columnModel.getColumn(3).setPreferredWidth(170); // Lebar kolom NIK  
        columnModel.getColumn(4).setPreferredWidth(170); // Lebar kolom No. KK
        columnModel.getColumn(5).setPreferredWidth(150); // Lebar kolom Dusun
        columnModel.getColumn(6).setPreferredWidth(50); // Lebar kolom RT
        columnModel.getColumn(7).setPreferredWidth(50); // Lebar kolom RW
        columnModel.getColumn(8).setPreferredWidth(150); // Lebar kolom No. HP
        columnModel.getColumn(9).setPreferredWidth(150); // Lebar kolom No. REK
    }
    
    private void setHeaderTable() {
        // Membuat renderer untuk header tabel
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        
        headerRenderer.setBackground(Color.decode("#5b8bdf")); // Mengatur warna latar belakang
        headerRenderer.setForeground(Color.WHITE); // Mengatur warna teks
        
        // Mengatur renderer untuk setiap kolom di header tabel
        for (int i = 0; i < jTabelPenduduk.getColumnModel().getColumnCount(); i++) {
            jTabelPenduduk.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        // Mengatur tinggi header tabel
        JTableHeader header = jTabelPenduduk.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));
    }
    
    private void clear(){
        jTextField1.setText("");
        jTextField2.setText((""));
        jTextField3.setText("");        
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");

        
        btn_simpan.setEnabled(true);
        btn_update.setEnabled(false);       
        btn_hapus.setEnabled(false);
        btn_penilaian.setEnabled(false);
        
        jLabelStatusNama.setVisible(false);        
        jLabelStatusHasil.setVisible(false);
    }
    
    private void cekKosong() throws exceptionData
    {
       if(jTextField1.getText().isEmpty()==true || jTextField2.getText().isEmpty()==true 
            || jTextField3.getText().isEmpty() == true || jTextField4.getText().isEmpty() == true 
            || jTextField5.getText().isEmpty() == true || jTextField6.getText().isEmpty() == true
            || jTextField7.getText().isEmpty() == true || jTextField8.getText().isEmpty() == true)
         throw new exceptionData();
    }

    public int simpan(){
        try {
             // Query untuk menghitung jumlah data yang ada di tabel tbl_alternatif
            String countQuery = "SELECT COUNT(id) AS total FROM tbl_alternatif";
            ResultSet res = conn.ambilData(countQuery);
            int total = 0;

            if (res.next()) {
                total = res.getInt("total");
            }

            // Tentukan nilai kode berikutnya
            String kode = "A" + (total + 1);
            String status_seleksi = "Belum Terseleksi";
        
            PreparedStatement stmt = conne.prepareStatement("INSERT INTO tbl_alternatif(kode, nama, nik, no_kk, dusun, rt , rw, no_hp, no_rekening,status_seleksi) values(?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, kode);
            stmt.setString(2, jTextField1.getText());
            stmt.setString(3, jTextField2.getText());            
            stmt.setString(4, jTextField3.getText());
            stmt.setString(5, jTextField4.getText());            
            stmt.setString(6, jTextField5.getText());
            stmt.setString(7, jTextField6.getText());
            stmt.setString(8, jTextField7.getText());
            stmt.setString(9, jTextField8.getText());            
            stmt.setString(10, status_seleksi);



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
            String id = jTabelPenduduk.getValueAt(jTabelPenduduk.getSelectedRow(), 0).toString(); 
            
            String a = jTextField1.getText();
            String b = jTextField2.getText();
            String c = jTextField3.getText();            
            String d = jTextField4.getText();
            String e = jTextField5.getText();
            String f = jTextField6.getText();
            String g = jTextField7.getText();
            String h = jTextField8.getText();



            //Query SQL
            String sql = "UPDATE tbl_alternatif SET nama='" + a + "', nik='" + b + "'"
                    + ", no_kk='" + c + "', dusun='" + d + "', rt='" + e + "', rw='" + f + "'"
                    + ", no_hp='" + g + "'"
                    + ", no_rekening='" + h + "'"
                    + " WHERE id='" + id + "'";

            conn.st.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Terupdate");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Terupdate");
            System.out.println(e.getMessage());
        }
        clear();
        tabelmodelpenduduk();
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
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabelStatusHasil = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        btn_simpan = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabelPenduduk = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        btn_penilaian = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabelStatusNama = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 204, 229));
        setLayout(null);

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Form Data Penduduk");
        add(jLabel2);
        jLabel2.setBounds(40, 50, 380, 43);

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nama");
        add(jLabel10);
        jLabel10.setBounds(40, 120, 60, 21);

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("NIK");
        add(jLabel8);
        jLabel8.setBounds(40, 170, 60, 21);

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("No. KK");
        add(jLabel3);
        jLabel3.setBounds(40, 220, 60, 21);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(110, 160, 280, 40);

        jLabelStatusHasil.setBackground(new java.awt.Color(255, 255, 255));
        jLabelStatusHasil.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 15)); // NOI18N
        jLabelStatusHasil.setForeground(new java.awt.Color(255, 51, 0));
        jLabelStatusHasil.setText("Hasil");
        add(jLabelStatusHasil);
        jLabelStatusHasil.setBounds(520, 310, 270, 21);

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        add(jTextField3);
        jTextField3.setBounds(110, 210, 280, 40);

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
        btn_simpan.setBounds(820, 130, 129, 42);

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
        btn_update.setBounds(820, 180, 129, 42);

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
        btn_hapus.setBounds(820, 230, 129, 42);

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tabel Data Penduduk");
        add(jLabel6);
        jLabel6.setBounds(40, 350, 287, 43);

        jScrollPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N

        jTabelPenduduk.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jTabelPenduduk.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jTabelPenduduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "No.", "Nama", "NIK", "No. KK", "Dusun", "RT", "RW", "No. HP", "No. Rek"
            }
        ));
        jTabelPenduduk.setFillsViewportHeight(true);
        jTabelPenduduk.setMinimumSize(new java.awt.Dimension(95, 100));
        jTabelPenduduk.setRowHeight(25);
        jTabelPenduduk.setSelectionBackground(new java.awt.Color(0, 204, 229));
        jTabelPenduduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelPendudukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTabelPenduduk);

        add(jScrollPane1);
        jScrollPane1.setBounds(50, 410, 990, 156);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(110, 110, 280, 40);

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        add(jTextField4);
        jTextField4.setBounds(110, 260, 280, 40);

        jLabel11.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("RT");
        add(jLabel11);
        jLabel11.setBounds(410, 120, 30, 21);

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        add(jTextField5);
        jTextField5.setBounds(510, 110, 280, 40);

        jLabel12.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("RW");
        add(jLabel12);
        jLabel12.setBounds(410, 170, 30, 21);

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        add(jTextField6);
        jTextField6.setBounds(510, 160, 280, 40);

        jLabel13.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("No. HP");
        add(jLabel13);
        jLabel13.setBounds(410, 220, 50, 21);

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        add(jTextField7);
        jTextField7.setBounds(510, 210, 280, 40);

        jLabel14.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("No. Rekening");
        add(jLabel14);
        jLabel14.setBounds(410, 270, 100, 21);

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        add(jTextField8);
        jTextField8.setBounds(510, 260, 280, 40);

        btn_penilaian.setBackground(new java.awt.Color(0, 204, 51));
        btn_penilaian.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btn_penilaian.setForeground(new java.awt.Color(255, 255, 255));
        btn_penilaian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tambah1.png"))); // NOI18N
        btn_penilaian.setText("Penilaian Penduduk");
        btn_penilaian.setToolTipText("Klik untuk penilaian penduduk.");
        btn_penilaian.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        btn_penilaian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_penilaianActionPerformed(evt);
            }
        });
        add(btn_penilaian);
        btn_penilaian.setBounds(440, 350, 240, 42);

        jLabel15.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("*Pilih penduduk pada tabel untuk melakukan proses penilaian*");
        add(jLabel15);
        jLabel15.setBounds(690, 350, 360, 40);

        jLabel16.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Dusun");
        add(jLabel16);
        jLabel16.setBounds(40, 270, 60, 21);

        jLabelStatusNama.setBackground(new java.awt.Color(255, 255, 255));
        jLabelStatusNama.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabelStatusNama.setForeground(new java.awt.Color(255, 255, 255));
        jLabelStatusNama.setText("Status Seleksi :");
        add(jLabelStatusNama);
        jLabelStatusNama.setBounds(410, 310, 110, 21);
    }// </editor-fold>//GEN-END:initComponents

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
        tabelmodelpenduduk();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        edit();
        //Field NonDisabled
        jTextField1.setEnabled(true);
        clear();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Yakin Dihapus ?","Konfirmasi Dialog", JOptionPane.YES_NO_CANCEL_OPTION);
        if(ok == 0){
            // Ambil ID dari baris yang dipilih di tabel
            String id = jTabelPenduduk.getValueAt(jTabelPenduduk.getSelectedRow(), 0).toString(); 
            String sql = "DELETE FROM tbl_alternatif WHERE id='"+id+"'"; //edit menggunakan kode
            System.out.println(sql);
            conn.simpanData(sql);
            //conn.simpanData(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            tabelmodelpenduduk();//tambahkan
            clear();
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void jTabelPendudukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelPendudukMouseClicked
         int bar = jTabelPenduduk.getSelectedRow();

        // Cek apakah baris yang dipilih valid
        if (bar < 0) {
            System.out.println("No row selected.");
            return;
        }

        // Cek apakah jTabelPenduduk dan jTextField tidak null
        if (jTabelPenduduk == null) {
            System.out.println("jTabelPenduduk is null.");
            return;
        }

        if (jTextField1 == null || jTextField2 == null || jTextField3 == null ||
            jTextField4 == null || jTextField5 == null || jTextField6 == null ||
            jTextField7 == null || jTextField8 == null) {
            System.out.println("One or more text fields are null.");
            return;
        }

        try {
            // Ambil nilai dari tabel dengan pengecekan null
            String a = (jTabelPenduduk.getValueAt(bar, 2) != null) ? jTabelPenduduk.getValueAt(bar, 2).toString() : "";
            String b = (jTabelPenduduk.getValueAt(bar, 3) != null) ? jTabelPenduduk.getValueAt(bar, 3).toString() : "";
            String c = (jTabelPenduduk.getValueAt(bar, 4) != null) ? jTabelPenduduk.getValueAt(bar, 4).toString() : "";
            String d = (jTabelPenduduk.getValueAt(bar, 5) != null) ? jTabelPenduduk.getValueAt(bar, 5).toString() : "";
            String e = (jTabelPenduduk.getValueAt(bar, 6) != null) ? jTabelPenduduk.getValueAt(bar, 6).toString() : "";
            String f = (jTabelPenduduk.getValueAt(bar, 7) != null) ? jTabelPenduduk.getValueAt(bar, 7).toString() : "";
            String g = (jTabelPenduduk.getValueAt(bar, 8) != null) ? jTabelPenduduk.getValueAt(bar, 8).toString() : "";
            String h = (jTabelPenduduk.getValueAt(bar, 9) != null) ? jTabelPenduduk.getValueAt(bar, 9).toString() : "";

            // Set nilai ke text fields
            jTextField1.setText(a);
            jTextField2.setText(b);
            jTextField3.setText(c);
            jTextField4.setText(d);
            jTextField5.setText(e);
            jTextField6.setText(f);
            jTextField7.setText(g);
            jTextField8.setText(h);

            // Set status tombol
            btn_simpan.setEnabled(false);
            btn_update.setEnabled(true);
            btn_hapus.setEnabled(true);
            btn_penilaian.setEnabled(true);
            
            String id = jTabelPenduduk.getValueAt(jTabelPenduduk.getSelectedRow(), 0).toString();
            String getStatus = "SELECT status_seleksi FROM tbl_alternatif WHERE id='"+id+"'";
            ResultSet res = conn.ambilData(getStatus);
            
            String status_seleksi = "Status tidak ditemukan"; // Set default jika tidak ada data
            
            if (res.next()) {
                status_seleksi = res.getString("status_seleksi");
                
                if("Terseleksi".equals(status_seleksi)){
                    jLabelStatusHasil.setForeground(Color.GREEN);
                    btn_penilaian.setEnabled(false);
                }else{
                    jLabelStatusHasil.setForeground(Color.RED);
                    btn_penilaian.setEnabled(true);
                }
            }
            
            jLabelStatusNama.setVisible(true);        
            jLabelStatusHasil.setVisible(true);
            jLabelStatusHasil.setText(status_seleksi);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing table data.");
        }
    }//GEN-LAST:event_jTabelPendudukMouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void btn_penilaianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_penilaianActionPerformed
    
        // Ambil baris yang dipilih dari tabel
       int bar = jTabelPenduduk.getSelectedRow();

       // Cek apakah baris yang dipilih valid
       if (bar < 0) {
           JOptionPane.showMessageDialog(null, "Pilih penduduk dari tabel terlebih dahulu.");
           return;
       }

       // Ambil ID dari baris yang dipilih
       String id = jTabelPenduduk.getValueAt(bar, 0).toString();

       // Ambil data penduduk berdasarkan ID
       String query = "SELECT nama, nik FROM tbl_alternatif WHERE id = ?";

       try {
           PreparedStatement stmt = conne.prepareStatement(query);
           stmt.setString(1, id);
           ResultSet rs = stmt.executeQuery();

           if (rs.next()) {
               // Ambil data dari ResultSet
               String nama = rs.getString("nama");
               String nik = rs.getString("nik");

               // Mendapatkan parent JFrame dari JPanel
               JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

               // Membuat instance dari penilaian
               penilaian dialog = new penilaian(parentFrame, true);

               // Set data ke form penilaian
               dialog.setData(nama, nik);

               // Menampilkan dialog
               dialog.pack(); // Sesuaikan ukuran dialog dengan kontennya
               dialog.setLocationRelativeTo(parentFrame); // Posisi dialog di tengah parent frame
               
                // Tambahkan listener untuk menangani dialog ditutup
                dialog.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        // Panggil metode refresh di panel utama
                        clear();
                        tabelmodelpenduduk();
                    }
                });
            
               dialog.setVisible(true);
           } else {
               JOptionPane.showMessageDialog(null, "Data penduduk tidak ditemukan.");
           }
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengambil data penduduk: " + ex.getMessage());
       }
    }//GEN-LAST:event_btn_penilaianActionPerformed
    
//Cara Get Nama Penduduk

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_penilaian;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelStatusHasil;
    private javax.swing.JLabel jLabelStatusNama;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabelPenduduk;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
