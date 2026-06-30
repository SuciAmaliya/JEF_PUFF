/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Alasan: Package menyatukan class Customer dengan class lain dalam project JEF_PUFF.
package jef_puff;

/**
 *
 * @author amali
 */
// Alasan: Customer menjadi subclass dari Pengguna, sehingga memenuhi konsep inheritance.
public class Customer extends Pengguna{
    // Alasan: Constructor Customer menerima nama user saat objek Customer dibuat.
    public Customer(String nama) {
        // Alasan: super memanggil constructor milik class Pengguna agar atribut nama di superclass ikut terisi.
        super(nama);
    }

    // Alasan: Anotasi ini menandakan method tampilData menimpa method dari superclass Pengguna.
    @Override
    // Alasan: Method ini menampilkan data Customer dengan versi yang lebih spesifik.
    public void tampilData() {
        // Alasan: super memanggil method tampilData milik Pengguna agar data nama tetap ditampilkan.
        super.tampilData();
        // Alasan: Output ini menambahkan keterangan bahwa pengguna yang login adalah Customer.
        System.out.println("Sebagai Customer");
    }

    
}