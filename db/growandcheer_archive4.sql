-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 09, 2025 at 06:35 AM
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
  `tipe_barang` varchar(50) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `stok_barang` int(11) NOT NULL DEFAULT 0 CHECK (`stok_barang` >= 0),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`barang_id`, `nama_barang`, `deskripsi_barang`, `harga`, `tipe_barang`, `image_url`, `stok_barang`, `created_at`) VALUES
(1, 'Oat Organik (1kg)', 'Oat utuh organik.', 5.50, 'Makanan', NULL, 100, '2025-05-28 20:44:48'),
(2, 'Air Mineral (500ml x 24)', '24 botol air mineral alami.', 12.00, 'Minuman', NULL, 50, '2025-05-28 20:44:48'),
(3, 'Hand Sanitizer (250ml)', 'Hand sanitizer berbasis alkohol.', 3.75, 'Kebersihan', NULL, 200, '2025-05-28 20:44:48');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `user_id` int(11) NOT NULL,
  `ismember` tinyint(1) NOT NULL DEFAULT 0,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`user_id`, `ismember`, `alamat`) VALUES
(5, 1, 'Jl. Merdeka No. 123, Jakarta Pusat'),
(6, 0, 'Jl. Sudirman Kav. 1, Jakarta Selatan');

-- --------------------------------------------------------

--
-- Table structure for table `keranjang`
--

CREATE TABLE `keranjang` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- Menambahkan `id` sebagai Primary Key
  `user_id` INT(11) NOT NULL,  -- `user_id` sebagai kolom biasa
  `order_id` INT(11) NOT NULL,  -- `order_id` sebagai kolom biasa
  `waktu_ditambahkan` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `keranjang`
--

INSERT INTO `keranjang` (`user_id`, `order_id`, `waktu_ditambahkan`) VALUES
(5, 1, '2025-06-09 02:45:00'),
(6, 2, '2025-06-09 04:15:00');

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
  `status_order` enum('Pending_Client','Pending_Admin','Done') NOT NULL DEFAULT 'Pending_Client'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`order_id`, `barang_id`, `jumlah_barang`, `harga_per_unit`, `tanggal_order`, `alamat_tujuan`, `status_order`) VALUES
(1, 1, 2, 5.50, '2025-06-09 03:00:00', 'Jl. Merdeka No. 123, Jakarta Pusat', 'Pending_Client'),
(2, 2, 1, 12.00, '2025-06-09 04:30:00', 'Jl. Sudirman Kav. 1, Jakarta Selatan', 'Pending_Admin');

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
(1, 'admin', 'admin123', 'admin123@growandcheer.com', 'Admin', '2025-05-28 20:44:48', '2025-05-28 20:44:48'),
(2, 'user1', 'admin123', 'user1@gmail.com', 'Admin', '2025-05-28 20:44:48', '2025-05-28 20:44:48'),
(5, 'client_1', 'user123', 'client@mail.com', 'Client', '2025-05-28 20:44:48', '2025-05-28 20:44:48'),
(6, 'client_2', 'user123', 'client2@mail.com', 'Client', '2025-06-06 07:34:00', '2025-06-06 07:34:00');

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
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `fk_order_barang` (`barang_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `nama_user` (`nama_user`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `barang_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `client_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `keranjang`
ALTER TABLE `keranjang`
  ADD CONSTRAINT `fk_keranjang_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_keranjang_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE;

-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `fk_order_barang` FOREIGN KEY (`barang_id`) REFERENCES `barang` (`barang_id`) ON DELETE CASCADE;
COMMIT;

-- Constraint for table 'order
ALTER TABLE `order`
  ADD COLUMN `keranjang_id` INT(11),
  ADD CONSTRAINT `fk_order_keranjang` FOREIGN KEY (`keranjang_id`) REFERENCES `keranjang`(`id`);


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
