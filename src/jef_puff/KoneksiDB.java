/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jef_puff;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author amali
 */
public class KoneksiDB {
private static Connection koneksi;

    public static Connection getConnection() {

        try {

            String url = "jdbc:mysql://localhost:3306/jef_puff";
            String user = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");

            koneksi = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi Berhasil");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return koneksi;
    }
    
}
