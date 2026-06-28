package jef_puff;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author amali
 */
public interface BookingService {
    
    void simpanBooking();

    default void tampilPesan() {
        System.out.println("Booking berhasil.");
    }

    static void infoAplikasi() {
        System.out.println("Jakarta Electronic Finder");
    }

}
