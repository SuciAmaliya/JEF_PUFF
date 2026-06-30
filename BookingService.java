package jef_puff;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author amali
 */
// Alasan: Interface ini dibuat untuk memenuhi konsep OOP interface dan menjadi kontrak fungsi booking.
public interface BookingService {
    
    // Alasan: Method abstrak ini wajib diimplementasikan oleh class yang memakai interface BookingService.
    void simpanBooking();

    // Alasan: Default method diberi isi langsung sehingga class implementasi boleh memakai tanpa override.
    default void tampilPesan() {
        // Alasan: Pesan ini menjadi contoh output sederhana ketika proses booking berhasil.
        System.out.println("Booking berhasil.");
    }

    // Alasan: Static method bisa dipanggil langsung dari interface tanpa membuat objek.
    static void infoAplikasi() {
        // Alasan: Output ini menampilkan nama aplikasi sebagai informasi umum.
        System.out.println("Jakarta Electronic Finder");
    }

}
