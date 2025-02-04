-- phpMyAdmin SQL Dump
-- version 5.2.2-1.el9.remi
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 04, 2025 at 06:40 PM
-- Server version: 10.5.22-MariaDB
-- PHP Version: 8.3.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spr25_csc429_vgoye1`
--

-- --------------------------------------------------------

--
-- Table structure for table `Book`
--

CREATE TABLE `Book` (
  `bookId` int(11) NOT NULL,
  `bookTitle` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
  `pubYear` char(4) NOT NULL,
  `status` enum('Available','Checked Out') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Book`
--

INSERT INTO `Book` (`bookId`, `bookTitle`, `author`, `pubYear`, `status`) VALUES
(1, 'To Kill a Mockingbird', 'Harper Lee', '1960', 'Available'),
(2, '1984', 'George Orwell', '1949', 'Checked Out'),
(3, 'The Great Gatsby', 'F. Scott Fitzgerald', '1925', 'Available'),
(4, 'Pride and Prejudice', 'Jane Austen', '1813', 'Available'),
(5, 'Moby Dick', 'Herman Melville', '1851', 'Checked Out');

-- --------------------------------------------------------

--
-- Table structure for table `Patron`
--

CREATE TABLE `Patron` (
  `patronId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(70) NOT NULL,
  `city` varchar(20) NOT NULL,
  `stateCode` char(2) NOT NULL,
  `zip` char(5) NOT NULL,
  `email` varchar(30) NOT NULL,
  `dateOfBirth` char(12) NOT NULL,
  `status` enum('Active','Inactive') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Patron`
--

INSERT INTO `Patron` (`patronId`, `name`, `address`, `city`, `stateCode`, `zip`, `email`, `dateOfBirth`, `status`) VALUES
(1, 'John Smith', '123 Main St', 'Rochester', 'NY', '14623', 'john.smith@email.com', '1990-05-12', 'Active'),
(2, 'Emily Johnson', '456 Oak Ave', 'Buffalo', 'NY', '14201', 'emily.johnson@email.com', '1985-09-23', 'Inactive'),
(3, 'Michael Brown', '789 Pine Rd', 'Syracuse', 'NY', '13210', 'michael.brown@email.com', '2000-02-17', 'Active'),
(4, 'Sarah Davis', '321 Maple Dr', 'Albany', 'NY', '12207', 'sarah.davis@email.com', '1995-11-08', 'Active'),
(5, 'David Wilson', '654 Elm St', 'New York', 'NY', '10001', 'david.wilson@email.com', '1988-07-30', 'Inactive');

-- --------------------------------------------------------

--
-- Table structure for table `Transaction`
--

CREATE TABLE `Transaction` (
  `transId` int(11) NOT NULL,
  `bookId` int(11) NOT NULL,
  `patronId` int(11) NOT NULL,
  `transType` enum('Rent','Return') NOT NULL,
  `dateOfTrans` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Transaction`
--

INSERT INTO `Transaction` (`transId`, `bookId`, `patronId`, `transType`, `dateOfTrans`) VALUES
(1, 1, 1, 'Rent', '2024-01-10'),
(2, 2, 3, 'Rent', '2024-01-12'),
(3, 2, 3, 'Return', '2024-01-22'),
(4, 4, 2, 'Rent', '2024-02-01'),
(5, 5, 4, 'Rent', '2024-02-05');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Book`
--
ALTER TABLE `Book`
  ADD PRIMARY KEY (`bookId`);

--
-- Indexes for table `Patron`
--
ALTER TABLE `Patron`
  ADD PRIMARY KEY (`patronId`);

--
-- Indexes for table `Transaction`
--
ALTER TABLE `Transaction`
  ADD PRIMARY KEY (`transId`),
  ADD KEY `bookId` (`bookId`),
  ADD KEY `patronId` (`patronId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Book`
--
ALTER TABLE `Book`
  MODIFY `bookId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `Patron`
--
ALTER TABLE `Patron`
  MODIFY `patronId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `Transaction`
--
ALTER TABLE `Transaction`
  MODIFY `transId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Transaction`
--
ALTER TABLE `Transaction`
  ADD CONSTRAINT `Transaction_ibfk_1` FOREIGN KEY (`bookId`) REFERENCES `Book` (`bookId`),
  ADD CONSTRAINT `Transaction_ibfk_2` FOREIGN KEY (`patronId`) REFERENCES `Patron` (`patronId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
