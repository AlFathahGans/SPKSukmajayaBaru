package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class koneksi {
    private Connection koneksi;
    public Statement st;

    public Connection getConnection() {
        if (koneksi == null) {
            try {
                String server = "jdbc:mysql://localhost:3306/db_spk_sukamaju_baru";
                String user = "root";
                String password = "";
                Class.forName("com.mysql.jdbc.Driver");
                koneksi = DriverManager.getConnection(server, user, password);
            } catch (Exception x) {
                JOptionPane.showMessageDialog(null, "Koneksi Gagal, Pesan error \n" + x);
            }
        }
        return koneksi;
    }

    public void koneksi() {
        try {
            if (koneksi == null || koneksi.isClosed()) {
                getConnection();
            }
            st = koneksi.createStatement();
        } catch (Exception x) {
            JOptionPane.showMessageDialog(null, "Koneksi ambil Gagal, Pesan error \n" + x);
        }
    }

    public void tutupKoneksi(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (koneksi != null && !koneksi.isClosed()) {
                koneksi.close();
            }
        } catch (Exception x) {
            JOptionPane.showMessageDialog(null, "Tutup Koneksi Gagal, Pesan error \n" + x);
        }
    }

    public ResultSet ambilData(String sql) {
        ResultSet rs = null;
        try {
            koneksi();
            rs = st.executeQuery(sql);
        } catch (Exception x) {
            JOptionPane.showMessageDialog(null, "Ambil Data Gagal, Pesan error : \n" + x);
        }
        return rs;
    }

    public void simpanData(String sql) {
        try {
            koneksi();
            st.executeUpdate(sql);
        } catch (Exception x) {
            //JOptionPane.showMessageDialog(null,"Simpan Data Gagal, Pesan error : \n"+x);
            System.out.print(x);
        }
    }
}
