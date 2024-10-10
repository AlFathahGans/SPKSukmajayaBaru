/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.kontrol_admin;

import java.awt.Color;
import koneksi.koneksi;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Locale;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import spksukmajayabaru.ImageCellRenderer;

import spksukmajayabaru.exceptionData;

/**
 *
 * @author Acer
 */
public class kontrol_admin extends javax.swing.JPanel {
    
    private Connection conne = new koneksi().getConnection();
    koneksi conn= new koneksi();
    
    DefaultTableModel tbk;
    
     private String fotoPath = "";

    /**
     * Creates new form Kontrol Admin
     */
    public kontrol_admin() {
        initComponents();
        
        tabelmodelkontroladmin();
        setHeaderTable();
        
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
        
        setSize(1030, 630); // Ukuran panel sesuai kebutuhan
        // Set background panel menjadi transparan
        setOpaque(false); 
        
        btn_update.setEnabled(false);       
        btn_hapus.setEnabled(false);
        
         btnSelectPhoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSelectPhotoActionPerformed(evt);
            }
        });

    }
    
    public void tabelmodelkontroladmin(){
       tbk =new DefaultTableModel();
       tbk.addColumn("ID");       
       tbk.addColumn("No.");
       tbk.addColumn("Foto");       
       tbk.addColumn("Nama Lengkap");
       tbk.addColumn("Username");
       tbk.addColumn("Email");
       jTabelKontrolAdmin.setModel(tbk); 
       try {
        int no=1;
        ResultSet res = conn.ambilData("SELECT id, foto,nama,username,email FROM tbl_user");
        while (res.next()){
            tbk.addRow(new Object[]{
                res.getString(1),
                no++,
                res.getString(2),
                res.getString(3),               
                res.getString(4),                
                res.getString(5)


            });
        } 
      
        jTabelKontrolAdmin.setModel(tbk);
        
        // Set the custom renderer for the Foto column
        jTabelKontrolAdmin.getColumnModel().getColumn(2).setCellRenderer(new ImageCellRenderer());

        // Menyembunyikan kolom ID
        jTabelKontrolAdmin.getColumnModel().getColumn(0).setMaxWidth(0);
        jTabelKontrolAdmin.getColumnModel().getColumn(0).setMinWidth(0);
        jTabelKontrolAdmin.getColumnModel().getColumn(0).setPreferredWidth(0);
        
         // Menetapkan tinggi baris
        jTabelKontrolAdmin.setRowHeight(100); // Sesuaikan dengan tinggi gambar
        
        setHeaderTable();
          } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this, ex);
        }
       
        // Mengatur lebar kolom
        TableColumnModel columnModel = jTabelKontrolAdmin.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(50);  // Lebar kolom No.
        columnModel.getColumn(2).setPreferredWidth(120); // Lebar kolom Foto
        columnModel.getColumn(3).setPreferredWidth(180); // Lebar kolom Nama
        columnModel.getColumn(4).setPreferredWidth(130); // Lebar kolom Username       
        columnModel.getColumn(5).setPreferredWidth(200); // Lebar kolom Email

    }
    
    private void setHeaderTable() {
        // Membuat renderer untuk header tabel
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        
        headerRenderer.setBackground(Color.decode("#5b8bdf")); // Mengatur warna latar belakang
        headerRenderer.setForeground(Color.WHITE); // Mengatur warna teks
        
        // Mengatur renderer untuk setiap kolom di header tabel
        for (int i = 0; i < jTabelKontrolAdmin.getColumnModel().getColumnCount(); i++) {
            jTabelKontrolAdmin.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
         // Mengatur tinggi header tabel
        JTableHeader header = jTabelKontrolAdmin.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));
    }
    
    private void clear(){
        jTextField1.setText("");        
        jLabelPhotoPreview.setIcon(null);
        jLabelPhotoPath.setText("Path :");
        jTextField3.setText("");
        jTextField4.setText("");        
        jTextField5.setText("");

        
        btn_simpan.setEnabled(true);
        btn_update.setEnabled(false);       
        btn_hapus.setEnabled(false);
    }
    
    private void cekKosong() throws exceptionData
    {
       if(jTextField1.getText().isEmpty()==true || jTextField3.getText().isEmpty() == true 
               || jTextField4.getText().isEmpty() == true || jTextField5.getText().isEmpty() == true)
            throw new exceptionData();
    }

    public int simpan() {
        try {
            // Tentukan direktori upload dan nama file
            File uploadDir = new File("src/upload");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Buat direktori jika belum ada
            }

            // Nama file dan lokasi baru
            File sourceFile = new File(fotoPath);
            String fileName = sourceFile.getName();
            File destinationFile = new File(uploadDir, fileName);

            // Salin file dari path sementara ke folder tujuan
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Path gambar di database
            String savedFotoPath = destinationFile.getAbsolutePath();
            
            // Mendapatkan tanggal hari ini
            LocalDate today = LocalDate.now();
            Date sqlDate = Date.valueOf(today);

            PreparedStatement stmt = conne.prepareStatement("INSERT INTO tbl_user(nama,foto, username, password, email, tanggal) values(?,?,?,?,?,?)");
            stmt.setString(1, jTextField1.getText());
            stmt.setString(2, savedFotoPath);
            stmt.setString(3, jTextField3.getText());
            stmt.setString(4, jTextField4.getText());
            stmt.setString(5, jTextField5.getText());            
            stmt.setDate(6, sqlDate);


            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan", "Pesan", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        clear();
        return 0;
    }

    
    public void edit() {
        try {
            // Ambil ID dari baris yang dipilih di tabel
            String id = jTabelKontrolAdmin.getValueAt(jTabelKontrolAdmin.getSelectedRow(), 0).toString(); // Kolom ke-0 adalah ID
            
            String a = jTextField1.getText();
            String b = jLabelPhotoPath.getText();
            String c = jTextField3.getText();            
            String d = jTextField4.getText();
            String e = jTextField5.getText();
            
            // Pastikan path tidak mengandung karakter yang salah
            b = b.replace("\\", "/"); // Ubah backslash ke slash jika diperlukan

            //Query SQL
            String sql = "UPDATE tbl_user SET nama='" + a + "', foto='" + b + "', username='" + c + "', password='" + d + "', email='" + e + "' WHERE id='" + id + "'";

            conn.st.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Terupdate");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Terupdate");
            System.out.println(e.getMessage());
        }
        clear();
        tabelmodelkontroladmin();
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
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        btn_simpan = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabelKontrolAdmin = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        btnSelectPhoto = new javax.swing.JButton();
        jLabelPhotoPreview = new javax.swing.JLabel();
        jLabelPhotoPath = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(0, 204, 229));
        setLayout(null);

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Form Data Kontrol Admin");
        add(jLabel2);
        jLabel2.setBounds(40, 30, 410, 43);

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Username");
        add(jLabel8);
        jLabel8.setBounds(40, 320, 100, 21);

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Password");
        add(jLabel3);
        jLabel3.setBounds(40, 400, 100, 21);

        jLabel9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Email");
        add(jLabel9);
        jLabel9.setBounds(40, 480, 90, 21);

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 30)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tabel Data Kontrol Admin");
        add(jLabel6);
        jLabel6.setBounds(550, 30, 370, 43);

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nama Lengkap");
        add(jLabel10);
        jLabel10.setBounds(40, 90, 100, 21);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(40, 120, 300, 40);

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        add(jTextField3);
        jTextField3.setBounds(40, 350, 300, 40);

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
        btn_simpan.setBounds(40, 570, 129, 42);

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
        btn_update.setBounds(180, 570, 129, 42);

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
        btn_hapus.setBounds(320, 570, 129, 42);

        jScrollPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N

        jTabelKontrolAdmin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jTabelKontrolAdmin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jTabelKontrolAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "No.", "Foto", "Nama Lengkap", "Username", "Email"
            }
        ));
        jTabelKontrolAdmin.setToolTipText("");
        jTabelKontrolAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabelKontrolAdmin.setFillsViewportHeight(true);
        jTabelKontrolAdmin.setMinimumSize(new java.awt.Dimension(95, 100));
        jTabelKontrolAdmin.setRowHeight(25);
        jTabelKontrolAdmin.setSelectionBackground(new java.awt.Color(0, 204, 229));
        jTabelKontrolAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelKontrolAdminMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTabelKontrolAdmin);

        add(jScrollPane1);
        jScrollPane1.setBounds(470, 110, 580, 500);

        jLabel11.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Foto");
        add(jLabel11);
        jLabel11.setBounds(50, 180, 90, 21);

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        add(jTextField4);
        jTextField4.setBounds(40, 430, 300, 40);

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        add(jTextField5);
        jTextField5.setBounds(40, 510, 300, 40);

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
        btnSelectPhoto.setBounds(50, 210, 260, 40);

        jLabelPhotoPreview.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhotoPreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPhotoPreview.setText("Foto 100x100");
        jLabelPhotoPreview.setToolTipText("");
        jLabelPhotoPreview.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        add(jLabelPhotoPreview);
        jLabelPhotoPreview.setBounds(330, 190, 100, 100);

        jLabelPhotoPath.setBackground(new java.awt.Color(51, 51, 51));
        jLabelPhotoPath.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPhotoPath.setText("Path :");
        jLabelPhotoPath.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        add(jLabelPhotoPath);
        jLabelPhotoPath.setBounds(50, 260, 260, 30);

        jPanel1.setBackground(new java.awt.Color(91, 139, 223));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        add(jPanel1);
        jPanel1.setBounds(40, 170, 400, 140);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

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
        tabelmodelkontroladmin();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        edit();
        //Field NonDisabled
        jTextField1.setEnabled(true);
        clear();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
       int ok = JOptionPane.showConfirmDialog(null, "Yakin Dihapus ?", "Konfirmasi Dialog", JOptionPane.YES_NO_CANCEL_OPTION);
        if (ok == 0) {
            // Ambil ID dari baris yang dipilih di tabel
            String id = jTabelKontrolAdmin.getValueAt(jTabelKontrolAdmin.getSelectedRow(), 0).toString(); // Kolom ke-0 adalah ID

            // Ambil path foto dari database
            String fotoPath = "";
            try {
                String sql = "SELECT foto FROM tbl_user WHERE id='" + id + "'";
                ResultSet rs = conn.ambilData(sql);
                if (rs.next()) {
                    fotoPath = rs.getString("foto"); // Ambil path foto dari hasil query
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengambil data foto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Hapus file foto dari sistem file
            if (!fotoPath.isEmpty()) {
                File fileToDelete = new File(fotoPath);
                if (fileToDelete.exists()) {
                    if (fileToDelete.delete()) {
                        System.out.println("File berhasil dihapus: " + fotoPath);
                    } else {
                        System.out.println("Gagal menghapus file: " + fotoPath);
                    }
                }
            }

            // Hapus data dari database
            String sql = "DELETE FROM tbl_user WHERE id='" + id + "'";
            System.out.println(sql);
            conn.simpanData(sql);

            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            tabelmodelkontroladmin(); // Perbarui tabel
            clear(); // Bersihkan form
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void jTabelKontrolAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelKontrolAdminMouseClicked
        try {
            int bar = jTabelKontrolAdmin.getSelectedRow();
            String a = jTabelKontrolAdmin.getValueAt(bar, 3).toString();
            String b = jTabelKontrolAdmin.getValueAt(bar, 2).toString();
            String c = jTabelKontrolAdmin.getValueAt(bar, 4).toString();
            String d = jTabelKontrolAdmin.getValueAt(bar, 5).toString();

            String id = jTabelKontrolAdmin.getValueAt(jTabelKontrolAdmin.getSelectedRow(), 0).toString();
            String getPassword = "SELECT password FROM tbl_user WHERE id='" + id + "'";
            ResultSet res = conn.ambilData(getPassword);

            String password = "Password tidak ditemukan";
            if (res.next()) {
                password = res.getString("password");
            }

            jTextField1.setText(a);
            jTextField3.setText(c);            
            jTextField4.setText(password);           
            jTextField5.setText(d);
            

            // Load image
            if (b != null && !b.isEmpty()) {
                File imgFile = new File(b);
                if (imgFile.exists()) {
                    ImageIcon imageIcon = new ImageIcon(b);
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    jLabelPhotoPreview.setIcon(new ImageIcon(image));
                    jLabelPhotoPath.setText(b);
                } else {
                    jLabelPhotoPreview.setIcon(null);
                    jLabelPhotoPath.setText("");
                    System.out.println("File tidak ditemukan: " + b);
                }
            } else {
                jLabelPhotoPreview.setIcon(null);
            }

            btn_simpan.setEnabled(false);
            btn_update.setEnabled(true);
            btn_hapus.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(kontrol_admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTabelKontrolAdminMouseClicked

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

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
            jLabelPhotoPreview.setIcon(new ImageIcon(image)); // Set the resized image to JLabel
        }
    }//GEN-LAST:event_btnSelectPhotoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelectPhoto;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelPhotoPath;
    private javax.swing.JLabel jLabelPhotoPreview;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTabelKontrolAdmin;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
