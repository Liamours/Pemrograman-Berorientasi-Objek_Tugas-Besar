-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 06, 2025 at 06:07 PM
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
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`barang_id`, `nama_barang`, `deskripsi_barang`, `harga`, `tipe_barang`, `image_url`, `created_at`) VALUES
(1, 'Oat Organik (1kg)', 'Oat utuh organik.', 5.50, 'Makanan', NULL, '2025-05-29 03:44:48'),
(2, 'Air Mineral (500ml x 24)', '24 botol air mineral alami.', 12.00, 'Minuman', NULL, '2025-05-29 03:44:48'),
(3, 'Hand Sanitizer (250ml)', 'Hand sanitizer berbasis alkohol.', 3.75, 'Kebersihan', NULL, '2025-05-29 03:44:48');

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
(6, 0, 'Jl. Sudirman Kav. 1, Jakarta Selatan'),
(7, 1, 'Jl. Thamrin No. 45, Jakarta Pusat'),
(8, 0, 'Jl. Gatot Subroto No. 27, Jakarta Barat');

-- --------------------------------------------------------

--
-- Table structure for table `detail_order`
--

CREATE TABLE `detail_order` (
  `detail_order_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `barang_id` int(11) NOT NULL,
  `jumlah_barang` int(11) NOT NULL CHECK (`jumlah_barang` > 0),
  `harga_per_unit` decimal(10,2) NOT NULL CHECK (`harga_per_unit` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `gudang`
--

CREATE TABLE `gudang` (
  `gudang_id` int(11) NOT NULL,
  `barang_id` int(11) NOT NULL,
  `stok_barang` int(11) NOT NULL DEFAULT 0 CHECK (`stok_barang` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `gudang`
--

INSERT INTO `gudang` (`gudang_id`, `barang_id`, `stok_barang`) VALUES
(1, 1, 100),
(2, 2, 50),
(3, 3, 200);

-- --------------------------------------------------------

--
-- Table structure for table `keranjang`
--

CREATE TABLE `keranjang` (
  `keranjang_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `barang_id` int(11) NOT NULL,
  `jumlah_barang` int(11) NOT NULL CHECK (`jumlah_barang` > 0),
  `waktu_ditambahkan` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `tanggal_order` timestamp NOT NULL DEFAULT current_timestamp(),
  `total_harga` decimal(12,2) NOT NULL CHECK (`total_harga` >= 0),
  `alamat_tujuan` text NOT NULL,
  `status_order` enum('Pending_Client','Pending_Admin','Done') NOT NULL DEFAULT 'Pending_Client'
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
(1, 'admin', 'admin123', 'admin123@growandcheer.com', 'Admin', '2025-05-29 03:44:48', '2025-05-29 03:44:48'),
(2, 'user1', 'password123', 'user1@gmail.com', 'Admin', '2025-05-29 03:44:48', '2025-05-29 03:44:48'),
(3, 'rafiq', 'password123', 'rafiq1@gmail.com', 'Admin', '2025-05-29 03:44:48', '2025-05-29 03:44:48'),
(4, 'rifqi', 'password123', 'rifqi1@gmail.com', 'Admin', '2025-05-29 03:44:48', '2025-05-29 03:44:48'),
(5, 'client_1', 'client123', 'client@mail.com', 'Client', '2025-05-29 03:44:48', '2025-05-29 03:44:48'),
(6, 'client_2', 'client456', 'client2@mail.com', 'Client', '2025-06-06 14:34:00', '2025-06-06 14:34:00'),
(7, 'client_3', 'client789', 'client3@mail.com', 'Client', '2025-06-06 14:34:00', '2025-06-06 14:34:00'),
(8, 'client_4', 'client012', 'client4@mail.com', 'Client', '2025-06-06 14:34:00', '2025-06-06 14:34:00');

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
-- Indexes for table `detail_order`
--
ALTER TABLE `detail_order`
  ADD PRIMARY KEY (`detail_order_id`),
  ADD UNIQUE KEY `uq_order_barang` (`order_id`,`barang_id`),
  ADD KEY `barang_id` (`barang_id`);

--
-- Indexes for table `gudang`
--
ALTER TABLE `gudang`
  ADD PRIMARY KEY (`gudang_id`),
  ADD UNIQUE KEY `barang_id` (`barang_id`);

--
-- Indexes for table `keranjang`
--
ALTER TABLE `keranjang`
  ADD PRIMARY KEY (`keranjang_id`),
  ADD UNIQUE KEY `uq_user_barang` (`user_id`,`barang_id`),
  ADD KEY `barang_id` (`barang_id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `user_id` (`user_id`);

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
-- AUTO_INCREMENT for table `detail_order`
--
ALTER TABLE `detail_order`
  MODIFY `detail_order_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `gudang`
--
ALTER TABLE `gudang`
  MODIFY `gudang_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `keranjang`
--
ALTER TABLE `keranjang`
  MODIFY `keranjang_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `client_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `detail_order`
--
ALTER TABLE `detail_order`
  ADD CONSTRAINT `detail_order_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `detail_order_ibfk_2` FOREIGN KEY (`barang_id`) REFERENCES `barang` (`barang_id`);

--
-- Constraints for table `gudang`
--
ALTER TABLE `gudang`
  ADD CONSTRAINT `gudang_ibfk_1` FOREIGN KEY (`barang_id`) REFERENCES `barang` (`barang_id`) ON DELETE CASCADE;

--
-- Constraints for table `keranjang`
--
ALTER TABLE `keranjang`
  ADD CONSTRAINT `keranjang_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `keranjang_ibfk_2` FOREIGN KEY (`barang_id`) REFERENCES `barang` (`barang_id`) ON DELETE CASCADE;

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
