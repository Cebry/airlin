-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 01, 2022 at 10:04 AM
-- Server version: 8.0.28-0ubuntu0.20.04.3
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `HLC-U2-A9`
--
CREATE DATABASE IF NOT EXISTS `HLC-U2-A9` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `HLC-U2-A9`;

-- --------------------------------------------------------

--
-- Table structure for table `billete`
--

DROP TABLE IF EXISTS `billete`;
CREATE TABLE `billete` (
  `id_billete` int NOT NULL,
  `plaza` int NOT NULL,
  `precio` float NOT NULL,
  `fecha` date NOT NULL,
  `id_factura` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `billete`
--

INSERT INTO `billete` (`id_billete`, `plaza`, `precio`, `fecha`, `id_factura`) VALUES
(244, 1, 22.4, '2022-02-25', NULL),
(245, 2, 22.4, '2022-02-25', 132),
(246, 3, 22.4, '2022-02-25', 132),
(247, 4, 22.4, '2022-02-25', NULL),
(248, 5, 22.4, '2022-02-25', NULL),
(249, 6, 22.4, '2022-02-25', 133),
(250, 7, 22.4, '2022-02-25', NULL),
(251, 8, 22.4, '2022-02-25', 135),
(252, 9, 22.4, '2022-02-25', NULL),
(253, 10, 22.4, '2022-02-25', 134),
(254, 1, 22.4, '2022-03-03', 139),
(255, 2, 22.4, '2022-03-03', NULL),
(256, 3, 22.4, '2022-03-03', 140),
(257, 4, 22.4, '2022-03-03', 138),
(258, 5, 22.4, '2022-03-03', 137),
(259, 6, 22.4, '2022-03-03', 139),
(260, 7, 22.4, '2022-03-03', 140),
(261, 8, 22.4, '2022-03-03', 138),
(262, 9, 22.4, '2022-03-03', NULL),
(263, 10, 22.4, '2022-03-03', 138);

-- --------------------------------------------------------

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `id_cliente` int NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellidos` varchar(20) NOT NULL,
  `dni` varchar(9) NOT NULL,
  `telefono` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `nombre`, `apellidos`, `dni`, `telefono`) VALUES
(134, 'John', 'Doe', '31025655N', '785241965'),
(135, 'Barbara', 'Foo', '17478777L', '658247582'),
(136, 'Jim', 'Douglas', '79149554E', '685745825'),
(137, 'Tom', 'Donn', '59342054F', '685248521');

-- --------------------------------------------------------

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
CREATE TABLE `factura` (
  `id_factura` int NOT NULL,
  `fecha` date NOT NULL,
  `id_cliente` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `factura`
--

INSERT INTO `factura` (`id_factura`, `fecha`, `id_cliente`) VALUES
(132, '2022-01-31', 134),
(133, '2022-02-14', 135),
(134, '2022-02-21', 136),
(135, '2022-02-23', 137),
(136, '2022-02-28', 135),
(137, '2022-02-28', 135),
(138, '2022-02-28', 135),
(139, '2022-02-28', 135),
(140, '2022-02-28', 137);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `billete`
--
ALTER TABLE `billete`
  ADD PRIMARY KEY (`id_billete`),
  ADD KEY `FOREIGN_KEY_BILLETE_FACTURA` (`id_factura`);

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Indexes for table `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id_factura`),
  ADD KEY `FOREIGN_KEY_FACTURA_CLIENTE` (`id_cliente`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `billete`
--
ALTER TABLE `billete`
  MODIFY `id_billete` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=264;

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=138;

--
-- AUTO_INCREMENT for table `factura`
--
ALTER TABLE `factura`
  MODIFY `id_factura` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=141;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `billete`
--
ALTER TABLE `billete`
  ADD CONSTRAINT `FOREIGN_KEY_BILLETE_FACTURA` FOREIGN KEY (`id_factura`) REFERENCES `factura` (`id_factura`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Constraints for table `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `FOREIGN_KEY_FACTURA_CLIENTE` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
