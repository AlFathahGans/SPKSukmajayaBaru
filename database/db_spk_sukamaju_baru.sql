-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 12, 2024 at 05:23 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_spk_sukamaju_baru`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_alternatif`
--

CREATE TABLE `tbl_alternatif` (
  `id` int(11) NOT NULL,
  `kode` varchar(20) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `nik` varchar(50) NOT NULL,
  `no_kk` varchar(50) NOT NULL,
  `dusun` varchar(100) NOT NULL,
  `rt` varchar(11) NOT NULL,
  `rw` varchar(11) NOT NULL,
  `no_hp` varchar(15) NOT NULL,
  `no_rekening` varchar(30) NOT NULL,
  `status_seleksi` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `tbl_alternatif`
--

INSERT INTO `tbl_alternatif` (`id`, `kode`, `nama`, `nik`, `no_kk`, `dusun`, `rt`, `rw`, `no_hp`, `no_rekening`, `status_seleksi`) VALUES
(1, 'A1', 'Edi Prasetyo', '3201010520000002', '3201010520000100', 'Pondok Rajeg', '005', '004', '089543334433', '1844811', 'Terseleksi'),
(2, 'A2', 'Karmin', '3201010520000003', '3201010520000016', 'Kancil II', '004', '006', '08966555', '165222', 'Terseleksi'),
(3, 'A3', 'AlFathah', '32010105322234', '32010105322344', 'Kp. Pisang', '005', '003', '089560932201', '16333211', 'Belum Terseleksi'),
(4, 'A4', 'Kodir', '32010104300004', '32010104300023', 'Kp. Sawah', '004', '005', '08564432234', '165453552', 'Belum Terseleksi'),
(5, 'A5', 'Salim', '32010432211000', '32010143555433', 'Citereup', '003', '002', '088988955433', '15333211', 'Belum Terseleksi');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_kriteria`
--

CREATE TABLE `tbl_kriteria` (
  `id` int(11) NOT NULL,
  `kode` varchar(10) NOT NULL,
  `atribut` varchar(100) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `bobot` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `tbl_kriteria`
--

INSERT INTO `tbl_kriteria` (`id`, `kode`, `atribut`, `nama`, `bobot`) VALUES
(1, 'C1', 'Cost', 'Penghasilan Satu Jiwa dalam Keluarga Perbulan', '5'),
(2, 'C2', 'Benefit', 'Jumlah Anggota Keluarga dalam 1 (satu) Rumah', '10'),
(3, 'C3', 'Benefit', 'Pengeluaran Satu Jiwa dalam Keluarga', '15'),
(4, 'C4', 'Benefit', 'Status Kepemilikan Rumah', '20');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_nilai_alternatif`
--

CREATE TABLE `tbl_nilai_alternatif` (
  `id` int(11) NOT NULL,
  `alternatif_id` int(11) DEFAULT NULL,
  `kriteria_id` int(11) DEFAULT NULL,
  `sub_kriteria_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `tbl_nilai_alternatif`
--

INSERT INTO `tbl_nilai_alternatif` (`id`, `alternatif_id`, `kriteria_id`, `sub_kriteria_id`) VALUES
(1, 1, 1, 3),
(2, 1, 2, 6),
(3, 1, 3, 15),
(4, 1, 4, 17),
(5, 2, 1, 1),
(6, 2, 2, 7),
(7, 2, 3, 11),
(8, 2, 4, 18);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_peringkat`
--

CREATE TABLE `tbl_peringkat` (
  `id` int(11) NOT NULL,
  `id_alternatif` int(11) NOT NULL,
  `nik` varchar(50) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `nilai` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `tbl_peringkat`
--

INSERT INTO `tbl_peringkat` (`id`, `id_alternatif`, `nik`, `nama`, `nilai`) VALUES
(1, 2, '3201010520000002', 'Edi Prasetyo', 44.667),
(2, 3, '3201010520000003', 'Karmin', 38.5);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_sub_kriteria`
--

CREATE TABLE `tbl_sub_kriteria` (
  `id` int(11) NOT NULL,
  `kriteria_id` int(11) DEFAULT NULL,
  `sub_nama` varchar(50) NOT NULL,
  `nilai_rating` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `tbl_sub_kriteria`
--

INSERT INTO `tbl_sub_kriteria` (`id`, `kriteria_id`, `sub_nama`, `nilai_rating`) VALUES
(1, 1, 'Rp. < 400 Ribu', 50),
(2, 1, 'Rp. 400 – 700 Ribu', 40),
(3, 1, 'Rp. 700 Ribu – 1 Juta', 30),
(4, 1, 'Rp. 1 Juta  - 1.5 Juta', 20),
(5, 1, '> =  Rp. 1.5 juta', 10),
(6, 2, '1 – 3 Orang', 20),
(7, 2, '4 Orang', 30),
(8, 2, '5 Orang', 40),
(9, 2, '> =  6 Orang', 50),
(10, 3, 'Rp. < 400 Ribu', 10),
(11, 3, 'Rp. 400 – 700 Ribu', 20),
(12, 3, 'Rp. 700 RIbu – 1 Juta', 30),
(15, 3, 'Rp. 1 Juta  - 1.5 Juta', 40),
(16, 3, '> =  Rp. 1.5 juta', 50),
(17, 4, 'Magersari / Pakai Gratis', 50),
(18, 4, 'Sewa < 1 Juta', 40),
(19, 4, 'Milik Orang', 30);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `foto` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `tanggal` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`id`, `nama`, `username`, `password`, `foto`, `email`, `tanggal`) VALUES
(1, 'Admin', 'admin', 'admin', 'C:/Users/Acer/Documents/NetBeansProjects/SPKSukmajayaBaru/src/upload/user_1.png', 'admin@gmail.com', '2024-08-01'),
(2, 'AlFathah', 'alfathah', '123', 'C:\\Users\\Acer\\Documents\\NetBeansProjects\\SPKSukmajayaBaru\\src\\upload\\user_2.png', 'alfathah55@gmail.com', '2024-08-05');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_alternatif`
--
ALTER TABLE `tbl_alternatif`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_kriteria`
--
ALTER TABLE `tbl_kriteria`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_nilai_alternatif`
--
ALTER TABLE `tbl_nilai_alternatif`
  ADD PRIMARY KEY (`id`),
  ADD KEY `alternatif_id` (`alternatif_id`),
  ADD KEY `kriteria_id` (`kriteria_id`),
  ADD KEY `sub_kriteria_id` (`sub_kriteria_id`);

--
-- Indexes for table `tbl_peringkat`
--
ALTER TABLE `tbl_peringkat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_sub_kriteria`
--
ALTER TABLE `tbl_sub_kriteria`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kriteria_id` (`kriteria_id`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_alternatif`
--
ALTER TABLE `tbl_alternatif`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_kriteria`
--
ALTER TABLE `tbl_kriteria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_nilai_alternatif`
--
ALTER TABLE `tbl_nilai_alternatif`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tbl_peringkat`
--
ALTER TABLE `tbl_peringkat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_sub_kriteria`
--
ALTER TABLE `tbl_sub_kriteria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_nilai_alternatif`
--
ALTER TABLE `tbl_nilai_alternatif`
  ADD CONSTRAINT `tbl_nilai_alternatif_ibfk_1` FOREIGN KEY (`alternatif_id`) REFERENCES `tbl_alternatif` (`id`),
  ADD CONSTRAINT `tbl_nilai_alternatif_ibfk_2` FOREIGN KEY (`kriteria_id`) REFERENCES `tbl_kriteria` (`id`),
  ADD CONSTRAINT `tbl_nilai_alternatif_ibfk_3` FOREIGN KEY (`sub_kriteria_id`) REFERENCES `tbl_sub_kriteria` (`id`);

--
-- Constraints for table `tbl_sub_kriteria`
--
ALTER TABLE `tbl_sub_kriteria`
  ADD CONSTRAINT `tbl_sub_kriteria_ibfk_1` FOREIGN KEY (`kriteria_id`) REFERENCES `tbl_kriteria` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
