package jef_puff;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author amali
 */
// Alasan: Connection dipakai supaya form Booking bisa terhubung ke database MySQL melalui class KoneksiDB.
import java.sql.Connection;
// Alasan: PreparedStatement dipakai agar query database lebih aman dan bisa memakai tanda tanya (?) sebagai parameter.
import java.sql.PreparedStatement;
// Alasan: ResultSet dipakai untuk menampung hasil SELECT dari database sebelum ditampilkan ke tabel.
import java.sql.ResultSet;
// Alasan: DefaultTableModel dipakai untuk membuat struktur kolom dan isi JTable secara dinamis.
import javax.swing.table.DefaultTableModel;

// Alasan: Class Booking adalah tampilan form booking, extends JFrame agar menjadi window GUI, dan implements BookingService agar memenuhi konsep interface.
public class Booking extends javax.swing.JFrame implements BookingService{
    
    // Alasan: Logger disiapkan untuk mencatat error atau aktivitas program pada class Booking.
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Booking.class.getName());
    // Alasan: Variabel ini menyimpan ID booking yang dipilih dari tabel; nilai 0 berarti belum ada data yang dipilih.
    private int idBookingTerpilih = 0;

    /**
     * Creates new form Booking
     */
    // Alasan: Constructor kosong ini dipakai saat form Booking dibuka langsung tanpa membawa data toko dari form lain.
    public Booking() {
     
    // Alasan: Memanggil komponen GUI yang dibuat NetBeans agar semua tombol, label, tabel, dan input muncul di layar.
    initComponents();
    // Alasan: Form diletakkan di tengah layar supaya tampilan lebih rapi saat aplikasi dibuka.
    setLocationRelativeTo(null);
    // Alasan: Ukuran form dikunci agar layout tidak berantakan ketika user memperbesar atau memperkecil window.
    setResizable(false);
    
    // Alasan: Data booking langsung ditampilkan saat form dibuka supaya user bisa melihat data terbaru dari database.
    tampilDataTabel();
    }   
        // Alasan: Constructor ini menerima data dari form Toko, sehingga user tidak perlu mengisi ulang nama toko, layanan, tanggal, jam, dan keluhan.
        public Booking(String namaToko,
               String layanan1,
               String layanan2,
               String layanan3,
               String tanggal,
               String jam,
               String keluhan) {

    // Alasan: Memanggil komponen GUI yang dibuat NetBeans agar semua tombol, label, tabel, dan input muncul di layar.
    initComponents();
    // Alasan: Data booking langsung ditampilkan saat form dibuka supaya user bisa melihat data terbaru dari database.
    tampilDataTabel();
    
    
    // Alasan: Label nama toko diisi dari toko yang dipilih user pada halaman sebelumnya.
    jLabel7.setText(namaToko);


    // Alasan: Label ini menampilkan layanan atau barang pertama yang tersedia pada toko terpilih.
    jLabel4.setText(layanan1);
    // Alasan: Label ini menampilkan layanan atau barang kedua agar informasi toko lebih lengkap.
    jLabel5.setText(layanan2);
    // Alasan: Label ini menampilkan layanan atau barang ketiga jika tersedia di database.
    jLabel6.setText(layanan3);

  
    // Alasan: Isi combo box jam dikosongkan dulu agar pilihan lama/default tidak tercampur dengan pilihan baru.
    jComboBoxJam.removeAllItems();
    
    // Alasan: Menambahkan slot jam booking manual agar user punya pilihan waktu layanan.
    jComboBoxJam.addItem("08:00 - 09:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("09:00 - 10:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("10:00 - 11:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("11:00 - 12:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("12:00 - 13:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("13:00 - 14:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("14:00 - 15:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("15:00 - 16:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("16:00 - 17:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("17:00 - 18:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("18:00 - 19:00");
    // Alasan: Menambahkan slot jam berikutnya sebagai pilihan waktu booking.
    jComboBoxJam.addItem("19:00 - 20:00");
    // Alasan: Menambahkan slot jam terakhir agar user bisa memilih jadwal malam.
    jComboBoxJam.addItem("20:00 - 21:00");

    // Alasan: Jam dari form Toko tetap dimasukkan agar pilihan yang sebelumnya dipilih user tidak hilang.
    jComboBoxJam.addItem(jam);

    // Alasan: Combo box otomatis memilih jam yang dikirim dari halaman Toko.
    jComboBoxJam.setSelectedItem(jam);

   
    // Alasan: Keluhan dari halaman Toko dipindahkan ke textarea agar user bisa melihat atau mengubahnya.
    jTextArea1.setText(keluhan);

    
    // Alasan: Blok try dipakai untuk menjalankan kode yang berpotensi error, misalnya koneksi database atau format tanggal.
    try {
        java.util.Date tgl =
            // Alasan: String tanggal diubah menjadi objek Date agar bisa ditampilkan di komponen JDateChooser.
            new java.text.SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
        // Alasan: Tanggal booking otomatis ditampilkan sesuai data yang dikirim dari form sebelumnya.
        jDateChooser1.setDate(tgl);
    } catch (Exception e) {
        // Alasan: Error dicetak ke console agar penyebab masalah lebih mudah dilacak saat debugging.
        e.printStackTrace();
    }

        }
        
    // Alasan: Method ini bertugas mengambil data booking dari database lalu menampilkannya ke JTable.
    public void tampilDataTabel(){

    // Alasan: Model tabel baru dibuat supaya kolom dan isi tabel bisa diatur ulang dari database.
    DefaultTableModel model = new DefaultTableModel();

    // Alasan: Kolom ID Booking dibuat untuk menampilkan nomor unik setiap data booking.
    model.addColumn("ID Booking");
    // Alasan: Kolom Nama Toko dibuat agar user tahu booking tersebut untuk toko mana.
    model.addColumn("Nama Toko");
    // Alasan: Kolom Tanggal dibuat untuk menampilkan tanggal booking.
    model.addColumn("Tanggal");
    // Alasan: Kolom Jam dibuat untuk menampilkan jam booking yang dipilih.
    model.addColumn("Jam");
    // Alasan: Kolom Keluhan dibuat untuk menampilkan deskripsi masalah user.
    model.addColumn("Keluhan");

    try{

        // Alasan: Mengambil koneksi database dari class KoneksiDB agar tidak perlu menulis ulang koneksi di setiap form.
        Connection conn = KoneksiDB.getConnection();

        // Alasan: Variabel sql dipakai untuk menyimpan perintah database agar query lebih mudah dibaca dan dikelola.
        String sql =
        // Alasan: Query ini mengambil data booking yang ingin ditampilkan pada tabel.
        "SELECT b.id_booking,t.nama,b.tanggal_booking,b.jam_booking,b.keluhan " +
        // Alasan: Tabel booking diberi alias b agar penulisan query lebih ringkas.
        "FROM booking b " +
        // Alasan: JOIN dipakai agar id_tempat di booking bisa dihubungkan dengan nama toko di tabel tempat.
        "JOIN tempat t ON b.id_tempat=t.id_tempat";

        // Alasan: Query disiapkan sebelum dijalankan ke database.
        PreparedStatement pst = conn.prepareStatement(sql);

        // Alasan: executeQuery menjalankan SELECT dan menyimpan hasilnya ke ResultSet.
        ResultSet rs = pst.executeQuery();

        // Alasan: Perulangan ini membaca setiap baris data booking dari database satu per satu.
        while(rs.next()){

            // Alasan: Setiap data dari database dimasukkan sebagai baris baru pada model tabel.
            model.addRow(new Object[]{

                // Alasan: Mengambil ID booking dari ResultSet untuk ditampilkan di kolom pertama.
                rs.getInt("id_booking"),
                // Alasan: Mengambil nama toko dari ResultSet untuk ditampilkan di tabel.
                rs.getString("nama"),
                // Alasan: Mengambil tanggal booking dari ResultSet.
                rs.getDate("tanggal_booking"),
                // Alasan: Mengambil jam booking dari ResultSet.
                rs.getString("jam_booking"),
                // Alasan: Mengambil keluhan user dari ResultSet.
                rs.getString("keluhan")

            });

        }

        // Alasan: Model yang sudah berisi data dipasang ke JTable agar tampil di layar.
        jTable1.setModel(model);
        

    }catch(Exception e){

        // Alasan: Pesan error ditampilkan ke user agar user tahu jika proses gagal.
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBoxJam = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 675));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Nama Toko");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Layanan / Barang");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        jLabel4.setText("lb1");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, -1, -1));

        jLabel5.setText("lb2");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, -1, -1));

        jLabel6.setText("lb3");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, -1, -1));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nama Toko Yang Terpilih");
        jLabel7.setFocusable(false);
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 250, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("ID Booking");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Tanggal Booking");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 110, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Jam Booking");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 110, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("( isi otomatis)");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 110, 40));
        jPanel1.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 130, 210, 40));

        jButton1.setText("            ");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusable(false);
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 343, 90, 30));

        jButton2.setText("               ");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusable(false);
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 336, 100, 40));

        jButton3.setText("                   ");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 336, 120, 40));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Keluhan /Deskripsi");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, -1, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(634, 196, 530, 130));

        jButton4.setText("<");
        jButton4.addActionListener(this::jButton4ActionPerformed);
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 60, 70, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Booking", "Nama Toko", "Tanggal", "Jam", "Keluhan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 1160, 230));

        jComboBoxJam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxJam.addActionListener(this::jComboBoxJamActionPerformed);
        jPanel1.add(jComboBoxJam, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 130, 200, 40));

        jPanel2.setBackground(java.awt.Color.white);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, 60, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GambarBooking.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setPreferredSize(new java.awt.Dimension(1200, 675));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Toko tk = new Toko(1);
        tk.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

try{

        // Alasan: Mengambil koneksi database dari class KoneksiDB agar tidak perlu menulis ulang koneksi di setiap form.
        Connection conn = KoneksiDB.getConnection();

        
        // Alasan: Query ini mencari id_tempat berdasarkan nama toko karena tabel booking membutuhkan id_tempat, bukan hanya nama toko.
        String sqlCari = "SELECT id_tempat FROM tempat WHERE nama=?";

        // Alasan: Query pencarian id_tempat disiapkan dengan PreparedStatement.
        PreparedStatement pstCari = conn.prepareStatement(sqlCari);
        // Alasan: Parameter nama toko diisi dari label yang sedang tampil di form.
        pstCari.setString(1, jLabel7.getText());

        // Alasan: Query pencarian toko dijalankan dan hasilnya disimpan.
        ResultSet rs = pstCari.executeQuery();

        // Alasan: Variabel idTempat disiapkan untuk menyimpan id toko yang ditemukan.
        int idTempat = 0;

        // Alasan: Kondisi ini mengecek apakah data toko ditemukan di database.
        if(rs.next()){
            // Alasan: Jika ditemukan, id_tempat diambil untuk dipakai saat menyimpan booking.
            idTempat = rs.getInt("id_tempat");
        }

        // Alasan: Query INSERT dipakai untuk menyimpan data booking baru ke tabel booking.
        String sql = "INSERT INTO booking(id_user,id_tempat,tanggal_booking,jam_booking,keluhan) VALUES(?,?,?,?,?)";

        // Alasan: Query disiapkan sebelum dijalankan ke database.
        PreparedStatement pst = conn.prepareStatement(sql);

        // Alasan: id_user sementara diisi 1 agar booking tetap punya relasi ke user pada database.
        pst.setInt(1,1); 
        // Alasan: id_tempat diisi dari toko yang dipilih user.
        pst.setInt(2,idTempat);
        // Alasan: Tanggal dari JDateChooser diubah menjadi format SQL Date agar bisa disimpan di database.
        pst.setDate(3,new java.sql.Date(jDateChooser1.getDate().getTime()));
        // Alasan: Jam yang dipilih user dimasukkan ke parameter query.
        pst.setString(4, jComboBoxJam.getSelectedItem().toString());
        // Alasan: Keluhan/deskripsi dari user dimasukkan ke parameter query.
        pst.setString(5,jTextArea1.getText());

        // Alasan: executeUpdate menjalankan INSERT, UPDATE, atau DELETE ke database.
        pst.executeUpdate();
        
        PreparedStatement pstId =
        // Alasan: Query ini mengambil ID booking terakhir agar bisa ditampilkan setelah data disimpan.
        conn.prepareStatement("SELECT MAX(id_booking) AS id FROM booking");

        // Alasan: Query untuk mengambil ID terakhir dijalankan.
        ResultSet rsId = pstId.executeQuery();

        // Alasan: Mengecek apakah ID booking terakhir berhasil ditemukan.
        if(rsId.next()){
            // Alasan: Label ID Booking diisi dengan ID booking terbaru.
            jLabel11.setText(rsId.getString("id"));
        }

        // Alasan: Notifikasi diberikan agar user tahu data berhasil disimpan.
        javax.swing.JOptionPane.showMessageDialog(this,"Booking berhasil");

        // Alasan: Data booking langsung ditampilkan saat form dibuka supaya user bisa melihat data terbaru dari database.
        tampilDataTabel();
        // Alasan: Label ID dikosongkan kembali setelah proses simpan selesai.
        jLabel11.setText("");
        // Alasan: Textarea keluhan dikosongkan agar form siap dipakai untuk input berikutnya.
        jTextArea1.setText("");
        // Alasan: Tanggal dikosongkan agar tidak ada data lama yang tertinggal.
        jDateChooser1.setDate(null);

        // Alasan: Mengecek apakah combo box memiliki item sebelum mengatur pilihan awal agar tidak error.
        if(jComboBoxJam.getItemCount()>0){
            // Alasan: Pilihan jam dikembalikan ke item pertama setelah data disimpan.
            jComboBoxJam.setSelectedIndex(0);
        }

        // Alasan: ID booking terpilih direset supaya program tidak mengira masih ada data yang dipilih.
        idBookingTerpilih = 0;

    }catch(Exception e){

        // Alasan: Pesan error ditampilkan ke user agar user tahu jika proses gagal.
        javax.swing.JOptionPane.showMessageDialog(this,e.getMessage());

    }  // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
//Alasan : Event ini menjalankan proses update data booking yang dipilih dari tabel.
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    if(idBookingTerpilih == 0){
        javax.swing.JOptionPane.showMessageDialog(this,
                "Pilih data pada tabel terlebih dahulu!");
        return;
    }    // TODO add your handling code here:
        
    try{

        Connection conn = KoneksiDB.getConnection();

        String sql = "UPDATE booking SET tanggal_booking=?, jam_booking=?, keluhan=? WHERE id_booking=?";

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setDate(1, new java.sql.Date(jDateChooser1.getDate().getTime()));
        pst.setString(2, jComboBoxJam.getSelectedItem().toString());
        pst.setString(3, jTextArea1.getText());
        pst.setInt(4,idBookingTerpilih);

        pst.executeUpdate();

        javax.swing.JOptionPane.showMessageDialog(this,"Data berhasil diupdate");

         tampilDataTabel();

    }catch(Exception e){

        javax.swing.JOptionPane.showMessageDialog(this,e.getMessage());

    }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
// Alasan: Validasi ini memastikan user sudah memilih data tabel sebelum melakukan update atau hapus.
    if(idBookingTerpilih == 0){
        javax.swing.JOptionPane.showMessageDialog(this,
                "Pilih data pada tabel terlebih dahulu!");
        return;
    }

    int jawab = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus data ini?",
            "Konfirmasi",
            javax.swing.JOptionPane.YES_NO_OPTION);

    if(jawab == javax.swing.JOptionPane.YES_OPTION){

        try{

            Connection conn = KoneksiDB.getConnection();

            String sql = "DELETE FROM booking WHERE id_booking=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, idBookingTerpilih);

            pst.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(this,
                    "Data berhasil dihapus");

            tampilDataTabel();

            idBookingTerpilih = 0;
            jLabel11.setText("");
            jTextArea1.setText("");
            jDateChooser1.setDate(null);

        }catch(Exception e){

            javax.swing.JOptionPane.showMessageDialog(this,e.getMessage());

        }
    }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
// Alasan: Mengambil nomor baris yang dipilih user pada JTable.
int baris = jTable1.getSelectedRow();

// Alasan: ID booking dari tabel disimpan agar bisa dipakai untuk update atau hapus.
idBookingTerpilih = Integer.parseInt(jTable1.getValueAt(baris,0).toString());

// Alasan: Label ID Booking diisi berdasarkan baris tabel yang dipilih.
jLabel11.setText(jTable1.getValueAt(baris,0).toString());
// Alasan: Nama toko di form diisi sesuai data dari tabel.
jLabel7.setText(jTable1.getValueAt(baris,1).toString());

try{
    java.util.Date tgl =
    new java.text.SimpleDateFormat("yyyy-MM-dd")
    // Alasan: Tanggal dari tabel diubah kembali menjadi Date agar bisa masuk ke JDateChooser.
    .parse(jTable1.getValueAt(baris,2).toString());

    // Alasan: Tanggal booking otomatis ditampilkan sesuai data yang dikirim dari form sebelumnya.
    jDateChooser1.setDate(tgl);

}catch(Exception e){}

// Alasan: Combo box jam diatur sesuai jam booking pada baris tabel yang dipilih.
jComboBoxJam.setSelectedItem(
    jTable1.getValueAt(baris,3).toString()
);

// Alasan: Textarea diisi dengan keluhan dari tabel agar data bisa diedit.
jTextArea1.setText(jTable1.getValueAt(baris,4).toString());   // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBoxJamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxJamActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBoxJamActionPerformed

/**
     * @param args the command line arguments
     */
    // Alasan: Method main dipakai saat file ini dijalankan langsung sebagai program Java.
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        // Alasan: Blok try dipakai untuk menjalankan kode yang berpotensi error, misalnya koneksi database atau format tanggal.
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
        // Alasan: Form Booking dijalankan di EventQueue agar GUI Swing berjalan aman dan stabil.
        java.awt.EventQueue.invokeLater(() -> new Booking().setVisible(true));
    }
    
    // Alasan: Anotasi ini menunjukkan method di bawahnya berasal dari interface BookingService.
    @Override
// Alasan: Method ini adalah implementasi dari interface BookingService untuk menyimpan booking.
public void simpanBooking() {
    // Alasan: Method simpanBooking memanggil proses simpan yang sama dengan tombol simpan agar tidak menulis ulang kode.
    jButton1ActionPerformed(null);
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBoxJam;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
