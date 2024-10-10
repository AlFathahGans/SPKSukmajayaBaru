/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JFrame;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.swing.Timer;

//INPUT MENU
import menu.dashboard.dashboard;
import menu.profil_saya.profil_saya;
import menu.edit_profil.edit_profil;
import menu.kontrol_admin.kontrol_admin;
import menu.kriteria.kriteria;
import menu.subkriteria.subkriteria;
import menu.penduduk.penduduk;
import menu.hasil_penilaian_penduduk.hasil_penilaian_penduduk;
import menu.hasil_seleksi.hasil_seleksi;
import menu.laporan.laporan;

/**
 *
 * @author Acer
 */
public class menu extends javax.swing.JFrame {
    private SimpleDateFormat dateFormat;
    private JLabel activeLabel = null; 
    
    dashboard dashboard = new dashboard();    
    profil_saya profil_saya = new profil_saya();    
    edit_profil edit_profil = new edit_profil();    
    kontrol_admin kontrol_admin = new kontrol_admin();
    kriteria kriteria = new kriteria();
    subkriteria subkriteria = new subkriteria();   
    penduduk penduduk = new penduduk();    
    hasil_penilaian_penduduk hasil_penilaian_penduduk = new hasil_penilaian_penduduk();    
    hasil_seleksi hasil_seleksi = new hasil_seleksi();    
    laporan laporan = new laporan();

    /**
     * Creates new form menu
     */
    public menu() {
        setTitle("SPK DANA ANGGARAN IURAN RT 007 RW 002 - SUKAMAJU BARU ");
        initComponents();
        setResizable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height); // Mengatur ukuran jendela sesuai dengan ukuran layar
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH); // Menyetel jendela menjadi fullscreen
         addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });
        // Set layout manager untuk panel utama
        jPanelMain.setLayout(new java.awt.CardLayout());

        // Menggunakan layout manager yang sesuai untuk panel
        dashboard.setLayout(new java.awt.BorderLayout());        
        profil_saya.setLayout(new java.awt.BorderLayout());        
        edit_profil.setLayout(new java.awt.BorderLayout());
        kontrol_admin.setLayout(new java.awt.BorderLayout());
        kriteria.setLayout(new java.awt.BorderLayout());
        subkriteria.setLayout(new java.awt.BorderLayout());    
        penduduk.setLayout(new java.awt.BorderLayout());        
        hasil_penilaian_penduduk.setLayout(new java.awt.BorderLayout());        
        hasil_seleksi.setLayout(new java.awt.BorderLayout());        
        laporan.setLayout(new java.awt.BorderLayout());


        // Menambahkan laporan ke jPanelMain dan mengatur panel yang terlihat
        jPanelMain.add(dashboard, "dashboard");        
        jPanelMain.add(profil_saya, "profil_saya");        
        jPanelMain.add(edit_profil, "edit_profil");        
        jPanelMain.add(kontrol_admin, "kontrol_admin");
        jPanelMain.add(kriteria, "kriteria");
        jPanelMain.add(subkriteria, "subkriteria");  
        jPanelMain.add(penduduk, "penduduk");        
        jPanelMain.add(hasil_penilaian_penduduk, "hasil_penilaian_penduduk");        
        jPanelMain.add(hasil_seleksi, "hasil_seleksi");        
        jPanelMain.add(laporan, "laporan");



         // Tambahkan MouseListener untuk efek hover
        addHoverEffect(jlabel_logout);
        addHoverEffect(jlabel_dashboard);
        addHoverEffect(jlabel_profil_saya);
        addHoverEffect(jlabel_edit_profil);
        addHoverEffect(jlabel_kontrol_admin);
        addHoverEffect(jlabel_input_data_penduduk);
        addHoverEffect(jlabel_data_kriteria);        
        addHoverEffect(jlabel_data_sub_kriteria);        
        addHoverEffect(jlabel_hasil_penilaian_penduduk);
        addHoverEffect(jlabel_hasil_seleksi);        
        addHoverEffect(jlabel_laporan);

        
        //Set Dashboard as default active label and visible panel
        setActiveLabel(jlabel_dashboard);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "dashboard"); 
        
        jPanelMain.setOpaque(false);
        
        // Format tanggal dan waktu untuk Waktu Indonesia Barat dengan Locale Indonesia
        dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy : HH:mm:ss", new Locale("id", "ID"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));

        // Update waktu setiap detik
        Timer timer = new Timer(1000, e -> updateDateTime());
        timer.start();
        
        // Set teks awal
        updateDateTime();
    }
    
    private void updateDateTime() {
        // Mendapatkan tanggal dan waktu saat ini
        String currentDateTime = dateFormat.format(new Date());
        // Set teks JLabel
        jlabel_time.setText(currentDateTime);
    }
    
    
    private void resizeComponents() {
        Dimension size = getSize();
        int width = size.width;
        int height = size.height;
    }
    
    private void addHoverEffect(final JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!label.equals(activeLabel)) {
                    label.setForeground(Color.GREEN); // Ganti dengan warna yang diinginkan
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!label.equals(activeLabel)) {
                    label.setForeground(Color.WHITE); // Kembali ke warna awal
                }
            }
        });
    }

    
    private void setActiveLabel(JLabel newActiveLabel) {
        System.out.println("Setting active label: " + newActiveLabel.getText());

        if (activeLabel != null && !activeLabel.equals(newActiveLabel)) {
            System.out.println("Deactivating label: " + activeLabel.getText());
            activeLabel.setForeground(Color.WHITE); // Reset color for the previously active label
        } else if (activeLabel == null) {
            System.out.println("No active label to deactivate.");
        }

        newActiveLabel.setForeground(Color.GREEN); // Set color for the newly active label
        activeLabel = newActiveLabel; // Update the active label reference

        // Force a repaint and revalidate
        revalidate();
        repaint();
    }





    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlabel_logo_time = new javax.swing.JLabel();
        jlabel_time = new javax.swing.JLabel();
        jlabel_logout = new javax.swing.JLabel();
        jlabel_profil_saya = new javax.swing.JLabel();
        jlabel_edit_profil = new javax.swing.JLabel();
        jlabel_kontrol_admin = new javax.swing.JLabel();
        jlabel_input_data_penduduk = new javax.swing.JLabel();
        jlabel_data_kriteria = new javax.swing.JLabel();
        jlabel_hasil_seleksi = new javax.swing.JLabel();
        jlabel_data_sub_kriteria = new javax.swing.JLabel();
        jlabel_hasil_penilaian_penduduk = new javax.swing.JLabel();
        jlabel_dashboard = new javax.swing.JLabel();
        jPanelMain = new javax.swing.JPanel();
        jlabel_laporan = new javax.swing.JLabel();
        jlabel_layout = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jlabel_logo_time.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jlabel_logo_time.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_logo_time.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/jam1.png"))); // NOI18N
        jlabel_logo_time.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_logo_timeMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_logo_time);
        jlabel_logo_time.setBounds(305, 20, 30, 35);

        jlabel_time.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jlabel_time.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_time.setText("Time");
        jlabel_time.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_timeMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_time);
        jlabel_time.setBounds(335, 20, 290, 35);

        jlabel_logout.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_logout.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        jlabel_logout.setText("Logout");
        jlabel_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_logoutMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_logout);
        jlabel_logout.setBounds(20, 680, 210, 40);

        jlabel_profil_saya.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_profil_saya.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_profil_saya.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile.png"))); // NOI18N
        jlabel_profil_saya.setText("Profil Saya");
        jlabel_profil_saya.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_profil_sayaMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_profil_saya);
        jlabel_profil_saya.setBounds(20, 160, 180, 40);

        jlabel_edit_profil.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_edit_profil.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_edit_profil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile-edit.png"))); // NOI18N
        jlabel_edit_profil.setText("Edit Profil");
        jlabel_edit_profil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_edit_profilMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_edit_profil);
        jlabel_edit_profil.setBounds(20, 200, 180, 40);

        jlabel_kontrol_admin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_kontrol_admin.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_kontrol_admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/control-admin2.png"))); // NOI18N
        jlabel_kontrol_admin.setText("Kontrol Admin");
        jlabel_kontrol_admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_kontrol_adminMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_kontrol_admin);
        jlabel_kontrol_admin.setBounds(20, 240, 180, 40);

        jlabel_input_data_penduduk.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_input_data_penduduk.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_input_data_penduduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/input-data-penduduk.png"))); // NOI18N
        jlabel_input_data_penduduk.setText("Input Data Penduduk");
        jlabel_input_data_penduduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_input_data_pendudukMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_input_data_penduduk);
        jlabel_input_data_penduduk.setBounds(20, 440, 210, 40);

        jlabel_data_kriteria.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_data_kriteria.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_data_kriteria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/perhitungan.png"))); // NOI18N
        jlabel_data_kriteria.setText("Data Kriteria");
        jlabel_data_kriteria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_data_kriteriaMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_data_kriteria);
        jlabel_data_kriteria.setBounds(20, 340, 210, 40);

        jlabel_hasil_seleksi.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_hasil_seleksi.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_hasil_seleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hasil-seleksi.png"))); // NOI18N
        jlabel_hasil_seleksi.setText("Hasil Seleksi");
        jlabel_hasil_seleksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_hasil_seleksiMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_hasil_seleksi);
        jlabel_hasil_seleksi.setBounds(20, 540, 210, 40);

        jlabel_data_sub_kriteria.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_data_sub_kriteria.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_data_sub_kriteria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pencil-edit.png"))); // NOI18N
        jlabel_data_sub_kriteria.setText("Data Sub Kriteria");
        jlabel_data_sub_kriteria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_data_sub_kriteriaMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_data_sub_kriteria);
        jlabel_data_sub_kriteria.setBounds(20, 390, 210, 40);

        jlabel_hasil_penilaian_penduduk.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_hasil_penilaian_penduduk.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_hasil_penilaian_penduduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hitung2.png"))); // NOI18N
        jlabel_hasil_penilaian_penduduk.setText("Lihat Penilaian Penduduk");
        jlabel_hasil_penilaian_penduduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_hasil_penilaian_pendudukMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_hasil_penilaian_penduduk);
        jlabel_hasil_penilaian_penduduk.setBounds(20, 490, 210, 40);

        jlabel_dashboard.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_dashboard.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dashboard.png"))); // NOI18N
        jlabel_dashboard.setText("Dashboard");
        jlabel_dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_dashboardMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_dashboard);
        jlabel_dashboard.setBounds(20, 120, 180, 40);

        jPanelMain.setBackground(new java.awt.Color(91, 139, 223));
        jPanelMain.setLayout(new java.awt.CardLayout());
        getContentPane().add(jPanelMain);
        jPanelMain.setBounds(280, 90, 1080, 680);

        jlabel_laporan.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jlabel_laporan.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_laporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit-clipboard.png"))); // NOI18N
        jlabel_laporan.setText("Laporan");
        jlabel_laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel_laporanMouseClicked(evt);
            }
        });
        getContentPane().add(jlabel_laporan);
        jlabel_laporan.setBounds(20, 590, 210, 40);

        jlabel_layout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/layout_menu_warna.png"))); // NOI18N
        jlabel_layout.setText("jLabel1");
        getContentPane().add(jlabel_layout);
        jlabel_layout.setBounds(0, 0, 1362, 768);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jlabel_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_logoutMouseClicked
        dispose();
    }//GEN-LAST:event_jlabel_logoutMouseClicked

    private void jlabel_dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_dashboardMouseClicked
        setActiveLabel(jlabel_dashboard);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "dashboard");

    }//GEN-LAST:event_jlabel_dashboardMouseClicked

    private void jlabel_data_kriteriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_data_kriteriaMouseClicked
         setActiveLabel(jlabel_data_kriteria);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "kriteria");
    }//GEN-LAST:event_jlabel_data_kriteriaMouseClicked

    private void jlabel_data_sub_kriteriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_data_sub_kriteriaMouseClicked
        setActiveLabel(jlabel_data_sub_kriteria);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "subkriteria");
    }//GEN-LAST:event_jlabel_data_sub_kriteriaMouseClicked

    private void jlabel_logo_timeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_logo_timeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlabel_logo_timeMouseClicked

    private void jlabel_timeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_timeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlabel_timeMouseClicked

    private void jlabel_input_data_pendudukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_input_data_pendudukMouseClicked
        setActiveLabel(jlabel_input_data_penduduk);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "penduduk");
    }//GEN-LAST:event_jlabel_input_data_pendudukMouseClicked

    private void jlabel_hasil_penilaian_pendudukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_hasil_penilaian_pendudukMouseClicked
        setActiveLabel(jlabel_hasil_penilaian_penduduk);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "hasil_penilaian_penduduk");
    }//GEN-LAST:event_jlabel_hasil_penilaian_pendudukMouseClicked

    private void jlabel_hasil_seleksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_hasil_seleksiMouseClicked
        setActiveLabel(jlabel_hasil_seleksi);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "hasil_seleksi");
    }//GEN-LAST:event_jlabel_hasil_seleksiMouseClicked

    private void jlabel_kontrol_adminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_kontrol_adminMouseClicked
        setActiveLabel(jlabel_kontrol_admin);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "kontrol_admin");
    }//GEN-LAST:event_jlabel_kontrol_adminMouseClicked

    private void jlabel_profil_sayaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_profil_sayaMouseClicked
        setActiveLabel(jlabel_profil_saya);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "profil_saya");
    }//GEN-LAST:event_jlabel_profil_sayaMouseClicked

    private void jlabel_edit_profilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_edit_profilMouseClicked
        setActiveLabel(jlabel_edit_profil);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "edit_profil");
    }//GEN-LAST:event_jlabel_edit_profilMouseClicked

    private void jlabel_laporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel_laporanMouseClicked
         setActiveLabel(jlabel_laporan);
        ((java.awt.CardLayout) jPanelMain.getLayout()).show(jPanelMain, "laporan");
    }//GEN-LAST:event_jlabel_laporanMouseClicked

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
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JLabel jlabel_dashboard;
    private javax.swing.JLabel jlabel_data_kriteria;
    private javax.swing.JLabel jlabel_data_sub_kriteria;
    private javax.swing.JLabel jlabel_edit_profil;
    private javax.swing.JLabel jlabel_hasil_penilaian_penduduk;
    private javax.swing.JLabel jlabel_hasil_seleksi;
    private javax.swing.JLabel jlabel_input_data_penduduk;
    private javax.swing.JLabel jlabel_kontrol_admin;
    private javax.swing.JLabel jlabel_laporan;
    private javax.swing.JLabel jlabel_layout;
    private javax.swing.JLabel jlabel_logo_time;
    private javax.swing.JLabel jlabel_logout;
    private javax.swing.JLabel jlabel_profil_saya;
    private javax.swing.JLabel jlabel_time;
    // End of variables declaration//GEN-END:variables

}
