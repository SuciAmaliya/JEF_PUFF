/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Alasan: Package menyatukan class Pengguna dengan class lain dalam project.
package jef_puff;

/**
 *
 * @author amali
 */
// Alasan: Class ini menjadi superclass umum untuk data pengguna.
public class Pengguna {

    // Alasan: Atribut private dipakai agar nama tetap terlindungi tetapi masih bisa diakses subclass seperti Customer.
    private String nama;

    // Alasan: Constructor ini menerima nama saat objek Pengguna atau subclass-nya dibuat.
    public Pengguna(String nama) {
        // Alasan: this membedakan atribut class dengan parameter nama, lalu menyimpan nilainya ke atribut.
        this.nama = nama;
    }
    //getter dan setter
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    // Alasan: Method ini menampilkan data dasar pengguna dan bisa dioverride oleh subclass.
    public void tampilData() {
        // Alasan: Output ini menampilkan nama pengguna ke console.
        System.out.println("Nama: " + nama);
    }
    
}
