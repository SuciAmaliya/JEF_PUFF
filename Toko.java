package jef_puff;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author amali
 */
// Alasan: JOptionPane dipakai untuk menampilkan peringatan dan error pada halaman detail toko.
import javax.swing.JOptionPane;
// Alasan: Connection dipakai untuk mengambil data toko dan layanan dari database.
import java.sql.Connection;
// Alasan: PreparedStatement dipakai untuk menjalankan query detail toko dengan parameter id_tempat.
import java.sql.PreparedStatement;
// Alasan: ResultSet dipakai untuk membaca hasil query detail toko dan layanan.
import java.sql.ResultSet;
// Alasan: Class Toko adalah form GUI yang menampilkan detail satu toko atau tempat layanan.
public class Toko extends javax.swing.JFrame {
    
    // Alasan: Logger dipakai untuk mencatat error pada class Toko.
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Toko.class.getName());

    /**
     * Creates new form Toko
     */
    // Alasan: Variabel wilayah disiapkan untuk menyimpan nama wilayah bila diperlukan.
    private String wilayah;
    // Alasan: Index disiapkan sebagai penanda posisi data jika nanti ada navigasi antar toko.
    private int index = 0;
    // Alasan: ResultSet disimpan sebagai atribut agar hasil data toko bisa diakses dalam class.
    private ResultSet rs;

    // Alasan: Constructor ini menerima id tempat agar form bisa menampilkan detail toko yang dipilih user.
    public Toko(int idTempat) {
    // Alasan: Ukuran form dikunci agar desain halaman toko tetap rapi.
    setResizable(false);
    // Alasan: Memanggil komponen GUI halaman Toko yang dibuat NetBeans.
    initComponents();
    // Alasan: Form Toko ditampilkan di tengah layar.
    setLocationRelativeTo(null);
    // Alasan: Data toko langsung dimuat berdasarkan idTempat yang dikirim dari halaman wilayah.
    tampilData(idTempat);
}
    
// Alasan: Method ini mengambil data detail toko dan layanan dari database.
private void tampilData(int idTempat) {

    try {

        // Alasan: Mengambil koneksi database dari class KoneksiDB.
        Connection conn = KoneksiDB.getConnection();
        
        // Alasan: Menampilkan nilai wilayah ke console untuk membantu debugging.
        System.out.println("Wilayah = " + wilayah);
        // Alasan: Query ini mengambil seluruh data toko berdasarkan id_tempat.
        String sql = "SELECT * FROM tempat WHERE id_tempat=?";

        // Alasan: Query detail toko disiapkan sebelum parameter id diisi.
        PreparedStatement pst = conn.prepareStatement(sql);
        // Alasan: Parameter id_tempat diisi dari toko yang dipilih user.
        pst.setInt(1, idTempat);

        // Alasan: Query dijalankan dan hasil detail toko disimpan ke ResultSet.
        rs = pst.executeQuery();

        // Alasan: Jika data toko ditemukan, isi form akan diisi dari database.
        if(rs.next()){

            // Alasan: Nama toko dari database ditampilkan pada label nama toko.
            jLabelNamaToko.setText(rs.getString("nama"));
            // Alasan: Rating toko ditampilkan agar user bisa menilai kualitas toko.
            jLabelRating.setText(rs.getString("rating"));
            // Alasan: Jumlah ulasan ditampilkan sebagai informasi pendukung rating.
            jLabelUlasan.setText(rs.getString("jumlah_ulasan")+" ulasan");
            // Alasan: Deskripsi toko ditampilkan agar user tahu informasi umum toko.
            jLabelDeskripsi.setText(rs.getString("deskripsi"));
            // Alasan: Kategori toko ditampilkan sebagai jenis layanan atau barang.
            jLabelLayananOrBarang.setText(rs.getString("kategori"));
            // Alasan: Alamat toko ditampilkan agar user tahu lokasi toko.
            jLabelIsiAlamat.setText(rs.getString("alamat"));
            // Alasan: Nomor telepon ditampilkan agar user bisa menghubungi toko.
            jLabelNoTlp.setText(rs.getString("telepon"));
            // Alasan: Jam buka toko ditampilkan agar user tahu waktu layanan.
            jLabelOpen.setText(rs.getString("jam_buka"));
            
            // Alasan: Combo box jam dikosongkan dulu sebelum diisi jadwal dari jam buka toko.
            jComboBoxPilihJam.removeAllItems();

            // Alasan: Jam buka dari database disimpan untuk dipecah menjadi jam mulai dan selesai.
            String jam = rs.getString("jam_buka");

            // Alasan: Jam buka dipecah berdasarkan tanda strip agar dapat menentukan rentang waktu.
            String[] waktu = jam.split("-");

            // Alasan: Dua digit awal jam mulai diubah menjadi angka.
            int mulai = Integer.parseInt(waktu[0].substring(0, 2));
            // Alasan: Dua digit awal jam selesai diubah menjadi angka.
            int selesai = Integer.parseInt(waktu[1].substring(0, 2));

            // Alasan: Perulangan ini membuat pilihan slot jam per satu jam dari jam buka sampai jam tutup.
            for (int i = mulai; i < selesai; i++) {
                // Alasan: Setiap slot jam hasil perulangan dimasukkan ke combo box.
                jComboBoxPilihJam.addItem(
                    // Alasan: Format ini membuat angka jam menjadi dua digit, misalnya 08:00.
                    String.format("%02d:00", i) + " - " +
                    String.format("%02d:00", i + 1)
                );
}
          
            // Alasan: Query ini mengambil daftar layanan yang dimiliki toko terpilih.
            String sqlLayanan = "SELECT nama_layanan FROM layanan WHERE id_tempat = ?";

            // Alasan: Query layanan disiapkan sebelum parameter id toko diisi.
            PreparedStatement pstLayanan = conn.prepareStatement(sqlLayanan);
            // Alasan: Parameter id_tempat diisi agar layanan yang muncul sesuai toko.
            pstLayanan.setInt(1, idTempat);

            // Alasan: Query layanan dijalankan dan hasilnya disimpan di ResultSet.
            ResultSet rsLayanan = pstLayanan.executeQuery();

          
            // Alasan: Label layanan pertama dikosongkan dulu agar data lama tidak tertinggal.
            jLabelNamaLayananOrBarang.setText("");
            // Alasan: Label layanan kedua dikosongkan dulu.
            jLabelNamaLayananOrBarang2.setText("");
            // Alasan: Label layanan ketiga dikosongkan dulu.
            jLabelNamaLayananOrBarang3.setText("");

            // Alasan: Variabel no dipakai untuk menentukan layanan masuk ke label keberapa.
            int no = 0;

            // Alasan: Perulangan ini membaca layanan toko satu per satu.
            while (rsLayanan.next()) {

                // Alasan: Layanan pertama dimasukkan ke label pertama.
                if (no == 0) {
                    // Alasan: Nama layanan pertama ditampilkan pada label layanan pertama.
                    jLabelNamaLayananOrBarang.setText(rsLayanan.getString("nama_layanan"));
                // Alasan: Layanan kedua dimasukkan ke label kedua.
                } else if (no == 1) {
                    // Alasan: Nama layanan kedua ditampilkan pada label layanan kedua.
                    jLabelNamaLayananOrBarang2.setText(rsLayanan.getString("nama_layanan"));
                // Alasan: Layanan ketiga dimasukkan ke label ketiga.
                } else if (no == 2) {
                    // Alasan: Nama layanan ketiga ditampilkan pada label layanan ketiga.
                    jLabelNamaLayananOrBarang3.setText(rsLayanan.getString("nama_layanan"));
                }

                // Alasan: Nomor layanan dinaikkan agar layanan berikutnya masuk ke label berikutnya.
                no++;
}

        }

    } catch(Exception e){

        javax.swing.JOptionPane.showMessageDialog(this,e.getMessage());

    }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelNamaToko = new javax.swing.JLabel();
        jLabelRating = new javax.swing.JLabel();
        jLabelUlasan = new javax.swing.JLabel();
        jLabelDeskripsi = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabelLayananOrBarang = new javax.swing.JLabel();
        jLabelIsiAlamat = new javax.swing.JLabel();
        jLabelNoTlp = new javax.swing.JLabel();
        jLabelOpen = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelNamaLayananOrBarang = new javax.swing.JLabel();
        jLabelNamaLayananOrBarang2 = new javax.swing.JLabel();
        jLabelNamaLayananOrBarang3 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jComboBoxPilihJam = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDeskripsi = new javax.swing.JTextArea();
        jButtonBooking = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jDateChooserTanggal = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 675));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNamaToko.setFont(new java.awt.Font("Segoe UI", 1, 29)); // NOI18N
        jLabelNamaToko.setText("Nama Toko");
        jPanel1.add(jLabelNamaToko, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 370, 60));

        jLabelRating.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelRating.setText("Rating");
        jPanel1.add(jLabelRating, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, -1, -1));

        jLabelUlasan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelUlasan.setText("ulasan");
        jPanel1.add(jLabelUlasan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, -1, -1));

        jLabelDeskripsi.setText("deskripsi");
        jPanel1.add(jLabelDeskripsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 170, 20));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Layanan / Barang");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, -1, 20));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Alamat");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 366, -1, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Telepon");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Jam buka");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, -1, 30));

        jLabelLayananOrBarang.setText("kategori");
        jPanel1.add(jLabelLayananOrBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 320, 110, 20));

        jLabelIsiAlamat.setText("isi alamat");
        jPanel1.add(jLabelIsiAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 366, -1, 20));

        jLabelNoTlp.setText("no tlp");
        jPanel1.add(jLabelNoTlp, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, -1, -1));

        jLabelOpen.setText("jam buka");
        jPanel1.add(jLabelOpen, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 450, -1, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setText("Layanan  / Barang Tersedia");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 520, -1, -1));

        jLabelNamaLayananOrBarang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelNamaLayananOrBarang.setText("nama barang / layanan 1");
        jPanel1.add(jLabelNamaLayananOrBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, -1, 20));

        jLabelNamaLayananOrBarang2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelNamaLayananOrBarang2.setText("nama barang / layanan 2");
        jPanel1.add(jLabelNamaLayananOrBarang2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 570, -1, 20));

        jLabelNamaLayananOrBarang3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelNamaLayananOrBarang3.setText("nama barang / layanan 3");
        jPanel1.add(jLabelNamaLayananOrBarang3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 570, 150, 20));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setText("Booking Layanan/Barang");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 150, -1, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Tanggal");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 220, -1, -1));

        jComboBoxPilihJam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxPilihJam.addActionListener(this::jComboBoxPilihJamActionPerformed);
        jPanel1.add(jComboBoxPilihJam, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 332, 390, 50));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel20.setText("Jam");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 310, 40, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("Keluhan/Deskripsi Barang");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 410, -1, -1));

        jTextAreaDeskripsi.setColumns(20);
        jTextAreaDeskripsi.setRows(5);
        jScrollPane1.setViewportView(jTextAreaDeskripsi);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 430, 390, 120));

        jButtonBooking.setBackground(new java.awt.Color(0, 153, 51));
        jButtonBooking.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonBooking.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBooking.setText("Booking");
        jButtonBooking.addActionListener(this::jButtonBookingActionPerformed);
        jPanel1.add(jButtonBooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 570, 390, 50));

        jButtonBack.setText("<");
        jButtonBack.addActionListener(this::jButtonBackActionPerformed);
        jPanel1.add(jButtonBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 50, 60, -1));
        jPanel1.add(jDateChooserTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 240, 390, 50));

        jButton1.setText(">");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 50, 60, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GambarToko.jpg"))); // NOI18N
        jLabel1.setText("nama barang / layanan 2");
        jLabel1.setPreferredSize(new java.awt.Dimension(1200, 675));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 152, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

// Alasan: Event tombol next ini memberi peringatan jika user belum mengisi data.
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        // Alasan: Pesan peringatan ditampilkan dengan JOptionPane.
        JOptionPane.showMessageDialog(
        this,
        "Isi data terlebih dahulu!",
        "Peringatan",
        JOptionPane.WARNING_MESSAGE
    );
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBoxPilihJamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPilihJamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxPilihJamActionPerformed
// Alasan: Event ini berjalan saat user menekan tombol Booking di halaman Toko.
    private void jButtonBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBookingActionPerformed
        // TODO add your handling code here:
// Alasan: Nama toko yang sedang tampil disimpan untuk dikirim ke form Booking.
     String namaToko = jLabelNamaToko.getText();

        // Alasan: Layanan pertama disimpan untuk ditampilkan kembali di form Booking.
        String layanan1 = jLabelNamaLayananOrBarang.getText();
        // Alasan: Layanan kedua disimpan untuk ditampilkan kembali di form Booking.
        String layanan2 = jLabelNamaLayananOrBarang2.getText();
        // Alasan: Layanan ketiga disimpan untuk ditampilkan kembali di form Booking.
        String layanan3 = jLabelNamaLayananOrBarang3.getText();

        // Alasan: Tanggal dari date chooser diformat menjadi yyyy-MM-dd agar cocok dengan format database.
        String tanggal = new java.text.SimpleDateFormat("yyyy-MM-dd")
                .format(jDateChooserTanggal.getDate());

        // Alasan: Jam booking yang dipilih user disimpan sebagai String.
        String jam = jComboBoxPilihJam.getSelectedItem().toString();

        // Alasan: Keluhan atau deskripsi user diambil dari textarea.
        String keluhan = jTextAreaDeskripsi.getText();

        // Alasan: Form Booking dibuat dengan membawa data dari halaman Toko.
        new Booking(
                namaToko,
                layanan1,
                layanan2,
                layanan3,
                tanggal,
                jam,
                keluhan
        // Alasan: Form Booking yang sudah menerima data langsung ditampilkan.
        ).setVisible(true);

        // Alasan: Form Toko ditutup setelah pindah ke form Booking.
        dispose(); 
    }//GEN-LAST:event_jButtonBookingActionPerformed
// Alasan: Event ini berjalan saat tombol back ditekan pada halaman Toko.
    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
// TODO add your handling code here:
        // Alasan: Objek halaman WilayahJakarta dibuat agar user kembali ke daftar toko.
        WilayahJakarta wilayahJakarta = new WilayahJakarta();
        // Alasan: Halaman WilayahJakarta ditampilkan kembali.
        wilayahJakarta.setVisible(true);
    }//GEN-LAST:event_jButtonBackActionPerformed
/**
     * @param args the command line arguments
     */
    // Alasan: Method main dipakai untuk menjalankan form Toko secara langsung.
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        // Alasan: Form Toko dijalankan dengan contoh idTempat 1 pada EventQueue.
        java.awt.EventQueue.invokeLater(() -> new Toko(1).setVisible(true));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonBooking;
    private javax.swing.JComboBox<String> jComboBoxPilihJam;
    private com.toedter.calendar.JDateChooser jDateChooserTanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDeskripsi;
    private javax.swing.JLabel jLabelIsiAlamat;
    private javax.swing.JLabel jLabelLayananOrBarang;
    private javax.swing.JLabel jLabelNamaLayananOrBarang;
    private javax.swing.JLabel jLabelNamaLayananOrBarang2;
    private javax.swing.JLabel jLabelNamaLayananOrBarang3;
    private javax.swing.JLabel jLabelNamaToko;
    private javax.swing.JLabel jLabelNoTlp;
    private javax.swing.JLabel jLabelOpen;
    private javax.swing.JLabel jLabelRating;
    private javax.swing.JLabel jLabelUlasan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaDeskripsi;
    // End of variables declaration//GEN-END:variables
}
