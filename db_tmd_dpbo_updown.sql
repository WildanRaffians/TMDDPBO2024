-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 21 Jun 2024 pada 04.24
-- Versi server: 10.4.27-MariaDB
-- Versi PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_tmd_dpbo_updown`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tscore`
--

CREATE TABLE `tscore` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `score` int(11) NOT NULL,
  `up` int(11) NOT NULL,
  `down` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tscore`
--

INSERT INTO `tscore` (`id`, `username`, `score`, `up`, `down`) VALUES
(1, 'wild', 4204, 73, 149),
(3, 'a', 39442, 652, 1024),
(10, 'wildan', 6995, 102, 174),
(11, 'raffians', 4512, 68, 109),
(12, 'wildanHafizh', 6467, 92, 161),
(13, 'awa', 515, 6, 14),
(14, 'NaN', 1211, 20, 27),
(15, 'dan', 79, 1, 2),
(16, 'hallo', 479, 6, 13),
(17, 'hai', 230, 5, 4),
(18, 'danfiz', 54, 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tscore`
--
ALTER TABLE `tscore`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tscore`
--
ALTER TABLE `tscore`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
