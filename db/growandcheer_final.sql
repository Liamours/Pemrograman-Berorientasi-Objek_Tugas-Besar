-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 16, 2025 at 06:30 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `growandcheer`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `barang_id` int(11) NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `deskripsi_barang` text DEFAULT NULL,
  `harga` decimal(10,2) NOT NULL CHECK (`harga` >= 0),
  `tipe_barang` enum('Makanan','Minuman','Hygine') NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `stok_barang` int(11) NOT NULL DEFAULT 0 CHECK (`stok_barang` >= 0),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`barang_id`, `nama_barang`, `deskripsi_barang`, `harga`, `tipe_barang`, `image_url`, `stok_barang`, `created_at`) VALUES
(1, 'Biskuit', 'Biskuit gandum dengan selai cokelat, dipacking dalam plastik berkualitas. Terdapat dua biskuit dalam tiap pack. Setiap kardus berisi 20 pack yang cocok untuk konsumsi keluarga atau keperluan usaha kecil.', 40000.00, 'Makanan', '/images/box_biskuit.png', 100, '2025-05-28 20:44:48'),
(2, 'Kripik Pedas', 'Kripik singkong pedas khas Indonesia yang dikemas dalam 25 bungkus per kardus. Tiap bungkus memiliki rasa pedas gurih dan kriuk yang tahan lama. Cocok untuk pecinta camilan pedas.', 75000.00, 'Makanan', '/images/box_hot_kripik.jpg', 85, '2025-05-28 20:44:48'),
(3, 'Jus', 'Paket grosir jus buah dalam botol 250ml, terdiri dari 24 botol dalam 1 kardus. Varian rasa seperti jeruk, apel, dan jambu membuatnya cocok untuk usaha minuman atau acara besar.', 96000.00, 'Minuman', '/images/box_jus.png', 120, '2025-05-28 20:44:48'),
(4, 'Kripik Original', 'Kripik singkong rasa original yang gurih dan renyah, dikemas dalam 30 bungkus per kardus. Camilan sehat tanpa pengawet dan pewarna buatan.', 65000.00, 'Makanan', '/images/box_original_kripik.png', 70, '2025-05-28 20:44:48'),
(5, 'Paket Hygine', 'Paket hygiene berisi sabun mandi cair, pasta gigi, sampo, dan sabun cuci tangan dalam ukuran ekonomis. Terdapat 10 set per kardus, cocok untuk rumah sakit, pesantren, atau kantor.', 230000.00, 'Hygine', '/images/box_paket_hygine.jpg', 45, '2025-05-28 20:44:48'),
(6, 'Paket Sanitasi', 'Paket kebersihan lengkap berisi cairan disinfektan, hand sanitizer, dan sabun cuci tangan dalam kemasan 1 liter. Dalam satu kardus terdapat 6 set, cocok untuk keperluan fasilitas umum.', 270000.00, 'Hygine', '/images/box_paket_sanitasi.jpg', 50, '2025-05-28 20:44:48'),
(7, 'Sabun Mandi', 'Sabun mandi batang dengan aroma herbal alami, dikemas dalam 48 batang per kardus. Sabun ini cocok untuk keluarga besar atau keperluan penginapan.', 144000.00, 'Hygine', '/images/box_sabun.jpg', 60, '2025-05-28 20:44:48'),
(8, 'Sampo', 'Sampo herbal untuk semua jenis rambut, dikemas dalam botol 250ml. Dalam satu kardus terdapat 24 botol, cocok untuk hotel dan salon.', 192000.00, 'Hygine', '/images/box_sampo.png', 55, '2025-05-28 20:44:48'),
(9, 'Smoothie', 'Smoothie buah sehat dalam kemasan botol 300ml, berisi 20 botol dalam 1 kardus. Pilihan rasa alpukat, mangga, dan stroberi. Ideal untuk event atau toko minuman.', 150000.00, 'Minuman', '/images/box_smoothie.png', 48, '2025-05-28 20:44:48'),
(10, 'Susu Manis', 'Susu kental manis dalam sachet ukuran 40g, dikemas dalam 100 sachet per kardus. Cocok untuk warung kopi atau kebutuhan dapur.', 130000.00, 'Minuman', '/images/box_susu.jpg', 80, '2025-05-28 20:44:48'),
(11, 'Teh', 'Teh celup rasa melati dalam kotak isi 25 kantong. Setiap kardus terdiri dari 40 kotak. Produk lokal berkualitas ekspor yang cocok untuk konsumsi rumah tangga dan bisnis.', 200000.00, 'Minuman', '/images/box_teh.png', 90, '2025-05-28 20:44:48'),
(12, 'Tisu', 'Tisu wajah lembut 2 ply dalam kemasan box. Setiap kardus berisi 36 kotak. Cocok untuk kantor, hotel, dan keperluan sehari-hari.', 175000.00, 'Hygine', '/images/box_tisu.jpg', 70, '2025-05-28 20:44:48');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `user_id` int(11) NOT NULL,
  `ismember` tinyint(1) NOT NULL DEFAULT 0,
  `alamat` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`user_id`, `ismember`, `alamat`) VALUES
(5, 1, 'rumah batan'),
(18, 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `keranjang`
--

CREATE TABLE `keranjang` (
  `keranjang_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `waktu_ditambahkan` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `keranjang`
--

INSERT INTO `keranjang` (`keranjang_id`, `user_id`, `waktu_ditambahkan`) VALUES
(1, 5, '2025-06-09 02:45:00'),
(2, 18, '2025-06-11 12:36:37');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL,
  `barang_id` int(11) NOT NULL,
  `jumlah_barang` int(11) NOT NULL CHECK (`jumlah_barang` > 0),
  `harga_per_unit` decimal(10,2) NOT NULL CHECK (`harga_per_unit` >= 0),
  `tanggal_order` timestamp NOT NULL DEFAULT current_timestamp(),
  `alamat_tujuan` text NOT NULL,
  `status_order` enum('Pending','Done') NOT NULL DEFAULT 'Pending',
  `keranjang_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `nama_user` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `peran` enum('Client','Admin') NOT NULL DEFAULT 'Client',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `nama_user`, `password`, `email`, `peran`, `created_at`, `updated_at`) VALUES
(1, 'admin', 'admin123', 'admin@mail.com', 'Admin', '2025-05-28 20:44:48', '2025-05-28 20:44:48'),
(2, 'batanbilek', 'passwordBaru', 'test@mail.com', 'Admin', '2025-05-28 20:44:48', '2025-05-28 20:44:48'),
(5, 'batan', 'user123', 'client@mail.com', 'Client', '2025-05-28 20:44:48', '2025-05-28 20:44:48'),
(9, 'test', 'admin123', 'test3@mail.com', 'Client', '2025-06-11 04:49:20', '2025-06-11 04:49:20'),
(18, 'test', 'admin123', 'test11@mail.com', 'Client', '2025-06-11 12:36:37', '2025-06-11 12:36:37');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`barang_id`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `keranjang`
--
ALTER TABLE `keranjang`
  ADD PRIMARY KEY (`keranjang_id`),
  ADD UNIQUE KEY `unique_user_id` (`user_id`),
  ADD KEY `fk_keranjang_user` (`user_id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `fk_order_barang` (`barang_id`),
  ADD KEY `fk_order_keranjang` (`keranjang_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `barang_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `keranjang`
--
ALTER TABLE `keranjang`
  MODIFY `keranjang_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `fk_client_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `keranjang`
--
ALTER TABLE `keranjang`
  ADD CONSTRAINT `fk_keranjang_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `fk_order_barang` FOREIGN KEY (`barang_id`) REFERENCES `barang` (`barang_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_order_keranjang` FOREIGN KEY (`keranjang_id`) REFERENCES `keranjang` (`keranjang_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
