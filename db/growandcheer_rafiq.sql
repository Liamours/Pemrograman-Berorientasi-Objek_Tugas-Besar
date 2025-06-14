-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 14, 2025 at 04:47 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

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
(2, 'Nabati Suup', 'Nabati Suup adalah snack lezat yang terbuat dari bahan berkualitas tinggi.', 15000.00, 'Makanan', 'https://example.com/images/nabati_suup.jpg', 50, '2025-05-28 20:44:48'),
(4, 'Smartphone XYZ', 'Smartphone dengan fitur AI', 4500000.00, 'Elektronik', 'https://example.com/smartphone.jpg', 30, '2025-06-10 12:17:11'),
(5, 'sendal', 'abcd', 1000000.00, 'Makanan', 'https://example.com/saindal.jpg', 50, '2025-06-10 14:02:21'),
(6, 'Kopi Arabika', 'Kopi Arabika berkualitas premium dari pegunungan.', 75000.00, 'Minuman', 'https://example.com/images/kopi_arabika.jpg', 100, '2025-06-13 16:46:23');

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
(6, 0, 'Jl. Sudirman Kav. 1, Jakarta Selatan'),
(18, 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `keranjang`
--

CREATE TABLE `keranjang` (
  `keranjang_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `waktu_ditambahkan` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `keranjang`
--

INSERT INTO `keranjang` (`keranjang_id`, `user_id`, `order_id`, `waktu_ditambahkan`) VALUES
(1, 5, 1, '2025-06-09 02:45:00'),
(2, 6, 2, '2025-06-09 04:15:00');

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
  `status_order` enum('Pending_Client','Pending_Admin','Done') NOT NULL DEFAULT 'Pending_Client',
  `keranjang_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`order_id`, `barang_id`, `jumlah_barang`, `harga_per_unit`, `tanggal_order`, `alamat_tujuan`, `status_order`, `keranjang_id`, `user_id`) VALUES
(1, 1, 2, 5.50, '2025-06-09 03:00:00', 'Jl. Merdeka No. 123, Jakarta Pusat', 'Pending_Client', NULL, 0),
(2, 2, 1, 12.00, '2025-06-09 04:30:00', 'Jl. Sudirman Kav. 1, Jakarta Selatan', 'Pending_Admin', NULL, 0);

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
(6, 'batanbilek', 'user123', 'client2@mail.com', 'Client', '2025-06-06 07:34:00', '2025-06-06 07:34:00'),
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
  ADD KEY `fk_keranjang_user` (`user_id`),
  ADD KEY `fk_keranjang_order` (`order_id`);

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
  MODIFY `barang_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

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
  ADD CONSTRAINT `fk_keranjang_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE,
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

ALTER TABLE keranjang DROP FOREIGN KEY fk_keranjang_order;
ALTER TABLE keranjang DROP COLUMN order_id;