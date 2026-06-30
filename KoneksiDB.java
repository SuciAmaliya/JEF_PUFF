/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Alasan: Package menyatukan class koneksi database dengan class lain dalam aplikasi.
package jef_puff;

// Alasan: Connection dipakai sebagai tipe objek koneksi antara Java dan database.
import java.sql.Connection;
// Alasan: DriverManager dipakai untuk membuka koneksi ke MySQL berdasarkan URL, user, dan password.
import java.sql.DriverManager;

/**
 *
 * @author amali
 */
// Alasan: Class ini dipisahkan khusus untuk mengatur koneksi database agar kode koneksi tidak ditulis berulang.
public class KoneksiDB {
// Alasan: Variabel static menyimpan koneksi database agar bisa dipakai bersama oleh method getConnection.
private static Connection koneksi;

    // Alasan: Method static ini bisa dipanggil langsung lewat KoneksiDB.getConnection() tanpa membuat objek.
    public static Connection getConnection() {

        try {

            // Alasan: URL ini menunjukkan database MySQL lokal dengan nama database jef_puff.
            String url = "jdbc:mysql://localhost:3306/jef_puff";
            // Alasan: User root digunakan untuk login ke MySQL lokal.
            String user = "root";
            // Alasan: Password kosong dipakai karena konfigurasi MySQL lokal biasanya belum diberi password.
            String password = "";

            // Alasan: Driver MySQL dipanggil agar Java mengenali cara berkomunikasi dengan database MySQL.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Alasan: Baris ini membuka koneksi nyata ke database memakai URL, user, dan password.
            koneksi = DriverManager.getConnection(url, user, password);
            // Alasan: Pesan ini membantu mengecek di console apakah koneksi database berhasil.
            System.out.println("Koneksi Berhasil");

        } catch (Exception e) {
            // Alasan: Error koneksi dicetak agar penyebab kegagalan bisa dilihat saat debugging.
            e.printStackTrace();
        }

        // Alasan: Method mengembalikan objek koneksi agar bisa dipakai oleh form lain untuk query database.
        return koneksi;
    }
    
}
