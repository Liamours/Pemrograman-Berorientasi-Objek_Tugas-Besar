DROP DATABASE IF EXISTS growandcheer;

CREATE DATABASE growandcheer CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE growandcheer;

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` INT AUTO_INCREMENT PRIMARY KEY,
  `nama_user` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `peran` ENUM('Client', 'Admin') NOT NULL DEFAULT 'Client',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `tipe_barang` (
  `tipe_barang_id` INT AUTO_INCREMENT PRIMARY KEY,
  `nama_tipe_barang` VARCHAR(50) NOT NULL UNIQUE,
  `deskripsi_tipe` TEXT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `barang` (
  `barang_id` INT AUTO_INCREMENT PRIMARY KEY,
  `nama_barang` VARCHAR(100) NOT NULL,
  `deskripsi_barang` TEXT NULL,
  `harga` DECIMAL(10, 2) NOT NULL CHECK (`harga` >= 0),
  `tipe_barang_id` INT NOT NULL,
  `image_url` VARCHAR(255) NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`tipe_barang_id`) REFERENCES `tipe_barang`(`tipe_barang_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `gudang` (
  `gudang_id` INT AUTO_INCREMENT PRIMARY KEY,
  `barang_id` INT NOT NULL UNIQUE,
  `stok_barang` INT NOT NULL DEFAULT 0 CHECK (`stok_barang` >= 0),
  FOREIGN KEY (`barang_id`) REFERENCES `barang`(`barang_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `keranjang` (
  `keranjang_id` INT AUTO_INCREMENT PRIMARY KEY,
  `user_id` INT NOT NULL,
  `barang_id` INT NOT NULL,
  `jumlah_barang` INT NOT NULL CHECK (`jumlah_barang` > 0),
  `waktu_ditambahkan` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE,
  FOREIGN KEY (`barang_id`) REFERENCES `barang`(`barang_id`) ON DELETE CASCADE,
  UNIQUE KEY `uq_user_barang` (`user_id`, `barang_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `order` (
  `order_id` INT AUTO_INCREMENT PRIMARY KEY,
  `user_id` INT NOT NULL,
  `tanggal_order` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `total_harga` DECIMAL(12, 2) NOT NULL CHECK (`total_harga` >= 0),
  `alamat_tujuan` TEXT NOT NULL,
  `status_order` ENUM('Pending_Client', 'Pending_Admin', 'Done') NOT NULL DEFAULT 'Pending_Client',
  FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `detail_order` (
  `detail_order_id` INT AUTO_INCREMENT PRIMARY KEY,
  `order_id` INT NOT NULL,
  `barang_id` INT NOT NULL,
  `jumlah_barang` INT NOT NULL CHECK (`jumlah_barang` > 0),
  `harga_per_unit` DECIMAL(10, 2) NOT NULL CHECK (`harga_per_unit` >= 0),
  FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`) ON DELETE CASCADE,
  FOREIGN KEY (`barang_id`) REFERENCES `barang`(`barang_id`) ON DELETE RESTRICT,
  UNIQUE KEY `uq_order_barang` (`order_id`, `barang_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert tipe barang
INSERT INTO `tipe_barang` (`nama_tipe_barang`, `deskripsi_tipe`) VALUES
('Makanan', 'Barang dapat dimakan, termasuk makanan ringan, pokok, dan bahan masak.'),
('Minuman', 'Barang dapat diminum, termasuk jus, soda, dan air mineral.'),
('Kebersihan', 'Barang perawatan pribadi dan pembersih.');

-- Insert user
INSERT INTO `user` (`nama_user`, `password`, `email`, `peran`) VALUES
('admin', 'admin123', 'admin123@growandcheer.com', 'Admin'),
('user1', 'password123', 'user1@gmail.com', 'Admin'),
('rafiq', 'password123', 'rafiq1@gmail.com', 'Admin'),
('rifqi', 'password123', 'rifqi1@gmail.com', 'Admin');
('client_1', 'client123', 'client@mail.com', 'Client');

-- Insert barang dan gudang
INSERT INTO `barang` (`nama_barang`, `deskripsi_barang`, `harga`, `tipe_barang_id`)
SELECT 'Oat Organik (1kg)', 'Oat utuh organik.', 5.50, tipe_barang_id FROM `tipe_barang` WHERE `nama_tipe_barang` = 'Makanan';

INSERT INTO `gudang` (`barang_id`, `stok_barang`)
SELECT barang_id, 100 FROM `barang` WHERE `nama_barang` = 'Oat Organik (1kg)';

INSERT INTO `barang` (`nama_barang`, `deskripsi_barang`, `harga`, `tipe_barang_id`)
SELECT 'Air Mineral (500ml x 24)', '24 botol air mineral alami.', 12.00, tipe_barang_id FROM `tipe_barang` WHERE `nama_tipe_barang` = 'Minuman';

INSERT INTO `gudang` (`barang_id`, `stok_barang`)
SELECT barang_id, 50 FROM `barang` WHERE `nama_barang` = 'Air Mineral (500ml x 24)';

INSERT INTO `barang` (`nama_barang`, `deskripsi_barang`, `harga`, `tipe_barang_id`)
SELECT 'Hand Sanitizer (250ml)', 'Hand sanitizer berbasis alkohol.', 3.75, tipe_barang_id FROM `tipe_barang` WHERE `nama_tipe_barang` = 'Kebersihan';

INSERT INTO `gudang` (`barang_id`, `stok_barang`)
SELECT barang_id, 200 FROM `barang` WHERE `nama_barang` = 'Hand Sanitizer (250ml)';
