CREATE DATABASE IF NOT EXISTS JEF_PUFF
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE JEF_PUFF;

DROP TABLE IF EXISTS booking_layanan;
DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS layanan;
DROP TABLE IF EXISTS tempat;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE tempat (
    id_tempat INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(150) NOT NULL,
    kategori VARCHAR(80) NOT NULL,
    wilayah VARCHAR(50) NOT NULL,
    alamat VARCHAR(255) NOT NULL,
    rating DECIMAL(2,1) NOT NULL,
    jumlah_ulasan INT NOT NULL,
    telepon VARCHAR(40),
    jam_buka VARCHAR(60),
    deskripsi TEXT
);

CREATE TABLE layanan (
    id_layanan INT AUTO_INCREMENT PRIMARY KEY,
    id_tempat INT NOT NULL,
    nama_layanan VARCHAR(120) NOT NULL,
    harga DECIMAL(12,2) DEFAULT 0,
    FOREIGN KEY (id_tempat) REFERENCES tempat(id_tempat)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE booking (
    id_booking INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    id_tempat INT NOT NULL,
    tanggal_booking DATE NOT NULL,
    jam_booking VARCHAR(25) NOT NULL,
    keluhan TEXT,
    status VARCHAR(30) DEFAULT 'Menunggu',
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (id_tempat) REFERENCES tempat(id_tempat)
);

CREATE TABLE booking_layanan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_booking INT NOT NULL,
    id_layanan INT NOT NULL,
    FOREIGN KEY (id_booking) REFERENCES booking(id_booking)
        ON DELETE CASCADE,
    FOREIGN KEY (id_layanan) REFERENCES layanan(id_layanan)
);

INSERT INTO users(username,password,email) VALUES
('admin','12345','admin@gmail.com'),
('user1','12345','user1@gmail.com');

INSERT INTO tempat
(nama,kategori,wilayah,alamat,rating,jumlah_ulasan,telepon,jam_buka,deskripsi)
VALUES
('Timur AC Jaya','Servis AC','Jakarta Timur','Jl. Raya Kalimalang No.12',4.8,236,'0218601001','08:00-17:00','Servis AC rumah dan kantor.'),
('Rawamangun Elektronik','Elektronik Bekas','Jakarta Timur','Jl. Balai Pustaka No.45',4.7,189,'0214701002','09:00-18:00','Menjual elektronik bekas berkualitas.'),
('Cuci Mesin Duren Sawit','Servis Mesin Cuci','Jakarta Timur','Jl. Pendidikan Raya No.8',4.6,152,'0218601003','08:00-17:00','Servis mesin cuci semua merek.'),
('Cakung TV Service','Servis Televisi','Jakarta Timur','Jl. Raya Bekasi No.27',4.5,118,'0214601004','08:00-17:00','Servis TV LED dan LCD.'),
('Jatinegara Teknik','Servis Elektronik','Jakarta Timur','Jl. Matraman Raya No.76',4.4,97,'0218501005','08:00-17:00','Perbaikan elektronik rumah tangga.'),

('Barat AC Sejuk','Servis AC','Jakarta Barat','Jl. Panjang No.22',4.8,221,'0215302001','08:00-17:00','Servis dan perawatan AC.'),
('Cengkareng Elektro','Elektronik Bekas','Jakarta Barat','Jl. Daan Mogot No.88',4.7,176,'0215402002','09:00-18:00','Toko elektronik bekas.'),
('Mesin Cuci Prima Barat','Servis Mesin Cuci','Jakarta Barat','Jl. Tanjung Duren Raya No.14',4.6,143,'0215602003','08:00-17:00','Servis mesin cuci berbagai merek.'),
('TV Jaya Teknik','Servis Televisi','Jakarta Barat','Jl. Kembangan Raya No.31',4.5,105,'0215802004','08:00-17:00','Perbaikan TV berbagai merek.'),
('Palmerah Service Center','Servis Elektronik','Jakarta Barat','Jl. Palmerah Barat No.19',4.4,89,'0215302005','08:00-17:00','Servis elektronik rumah tangga.'),

('Selatan AC Nyaman','Servis AC','Jakarta Selatan','Jl. Cipete Raya No.20',4.8,248,'0217503001','08:00-17:00','Servis AC rumah dan kantor.'),
('Blok M Elektronik','Elektronik Bekas','Jakarta Selatan','Jl. Panglima Polim No.42',4.7,193,'0217203002','09:00-18:00','Toko elektronik bekas.'),
('Laundry Teknik Selatan','Servis Mesin Cuci','Jakarta Selatan','Jl. Tebet Barat Dalam No.15',4.6,158,'0218303003','08:00-17:00','Perbaikan mesin cuci.'),
('Kemang TV Care','Servis Televisi','Jakarta Selatan','Jl. Kemang Raya No.28',4.5,127,'0217103004','08:00-17:00','Servis TV LED dan LCD.'),
('Pasar Minggu Service Center','Servis Elektronik','Jakarta Selatan','Jl. Raya Pasar Minggu No.61',4.4,101,'0217903005','08:00-17:00','Servis berbagai elektronik.'),

('Pusat AC Mandiri','Servis AC','Jakarta Pusat','Jl. Cempaka Putih Raya No.10',4.8,214,'0214204001','08:00-17:00','Servis dan perawatan AC.'),
('Senen Elektronik','Elektronik Bekas','Jakarta Pusat','Jl. Kramat Raya No.55',4.7,181,'0213904002','09:00-18:00','Elektronik bekas berkualitas.'),
('Cuci Bersih Teknik','Servis Mesin Cuci','Jakarta Pusat','Jl. Bendungan Hilir No.7',4.6,149,'0215704003','08:00-17:00','Servis mesin cuci.'),
('Monas TV Service','Servis Televisi','Jakarta Pusat','Jl. Tanah Abang II No.19',4.5,116,'0213804004','08:00-17:00','Perbaikan TV LED dan Smart TV.'),
('Kemayoran Teknik Elektronik','Servis Elektronik','Jakarta Pusat','Jl. Garuda No.36',4.4,92,'0214204005','08:00-17:00','Servis elektronik rumah tangga.'),

('Utara AC Sejahtera','Servis AC','Jakarta Utara','Jl. Boulevard Barat No.9',4.8,229,'0214505001','08:00-17:00','Servis AC dan pembersihan.'),
('Ancol Elektronik','Elektronik Bekas','Jakarta Utara','Jl. Gunung Sahari No.65',4.7,187,'0216405002','09:00-18:00','Menjual elektronik bekas.'),
('Cuci Bersih Utara','Servis Mesin Cuci','Jakarta Utara','Jl. Pluit Karang Ayu No.11',4.6,154,'0216605003','08:00-17:00','Servis mesin cuci.'),
('TV Marunda Teknik','Servis Televisi','Jakarta Utara','Jl. Marunda Makmur No.24',4.5,121,'0214405004','08:00-17:00','Servis TV LED dan LCD.'),
('Koja Electronic Service','Servis Elektronik','Jakarta Utara','Jl. Bhayangkara No.33',4.4,95,'0214305005','08:00-17:00','Perbaikan elektronik rumah tangga.');

INSERT INTO layanan(id_tempat,nama_layanan,harga)
SELECT id_tempat,'Layanan 1',50000 FROM tempat;
INSERT INTO layanan(id_tempat,nama_layanan,harga)
SELECT id_tempat,'Layanan 2',100000 FROM tempat;
INSERT INTO layanan(id_tempat,nama_layanan,harga)
SELECT id_tempat,'Layanan 3',150000 FROM tempat;

INSERT INTO booking(id_user,id_tempat,tanggal_booking,jam_booking,keluhan,status)
VALUES
(1,8,'2026-07-01','09:00','contoh : Mesin cuci tidak berputar','Menunggu');

INSERT INTO booking_layanan(id_booking,id_layanan)
VALUES (1,22);
