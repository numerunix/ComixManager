-- MySQL dump 10.17  Distrib 10.3.12-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: fumetteria
-- ------------------------------------------------------
-- Server version	10.3.12-MariaDB-1:10.3.12+maria~stretch

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Clienti`
--

DROP TABLE IF EXISTS `Clienti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Clienti` (
  `idCliente` bigint(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) COLLATE utf8_bin NOT NULL,
  `Cognome` varchar(45) COLLATE utf8_bin NOT NULL,
  `Indirizzo` tinytext COLLATE utf8_bin DEFAULT NULL,
  `DataNascita` date NOT NULL,
  `Cap` varchar(5) CHARACTER SET utf8 NOT NULL,
  `Citta` varchar(45) CHARACTER SET utf8 NOT NULL,
  `Provincia` varchar(45) CHARACTER SET utf8 NOT NULL,
  `Telefono` varchar(45) CHARACTER SET utf8 NOT NULL,
  `Fax` varchar(45) CHARACTER SET utf8 NOT NULL,
  `EMail` varchar(45) CHARACTER SET utf8 NOT NULL,
  `SitoWeb` varchar(45) CHARACTER SET utf8 NOT NULL,
  `InizioTesseramento` date NOT NULL,
  `FineTesseramento` date NOT NULL,
  `Note` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `Casella` int(11) NOT NULL,
  `Sconto` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE KEY `Casella_UNIQUE` (`Casella`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Clienti`
--

LOCK TABLES `Clienti` WRITE;
/*!40000 ALTER TABLE `Clienti` DISABLE KEYS */;
/*!40000 ALTER TABLE `Clienti` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-26  1:30:10
