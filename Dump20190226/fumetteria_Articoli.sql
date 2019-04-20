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
-- Table structure for table `Articoli`
--

DROP TABLE IF EXISTS `Articoli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Articoli` (
  `idArticolo` bigint(11) NOT NULL AUTO_INCREMENT,
  `Descrizione` mediumtext COLLATE utf8_bin DEFAULT NULL,
  `codiceABarre` varchar(45) COLLATE utf8_bin NOT NULL,
  `Iva` int(11) NOT NULL DEFAULT 22,
  `Note` mediumtext COLLATE utf8_bin DEFAULT NULL,
  `idCollana` int(11) DEFAULT NULL,
  `idCategoria` int(11) DEFAULT NULL,
  `idEditore` int(11) DEFAULT NULL,
  `idFornitore` int(11) DEFAULT NULL,
  `Numero` int(11) DEFAULT NULL,
  `DataPubblicazione` date NOT NULL,
  PRIMARY KEY (`idArticolo`),
  KEY `fk_Articoli_1_idx` (`idCollana`),
  KEY `fk_Articoli_2_idx` (`idCategoria`),
  KEY `fk_Articoli_3_idx` (`idEditore`),
  KEY `fk_Articoli_4_idx` (`idFornitore`),
  CONSTRAINT `fk_Articoli_1` FOREIGN KEY (`idCollana`) REFERENCES `Collana` (`idCollana`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Articoli_2` FOREIGN KEY (`idCategoria`) REFERENCES `Categoria` (`idCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Articoli_3` FOREIGN KEY (`idEditore`) REFERENCES `Editori` (`idEditore`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Articoli_4` FOREIGN KEY (`idFornitore`) REFERENCES `Fornitori` (`idFornitore`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Articoli`
--

LOCK TABLES `Articoli` WRITE;
/*!40000 ALTER TABLE `Articoli` DISABLE KEYS */;
/*!40000 ALTER TABLE `Articoli` ENABLE KEYS */;
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
