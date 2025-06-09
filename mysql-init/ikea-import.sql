-- MySQL dump 10.13  Distrib 8.0.41, for macos15 (x86_64)
--
-- Host: 127.0.0.1    Database: ikea_product_catalogue
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `colours`
--

DROP TABLE IF EXISTS `colours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colours` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `hexcode` VARCHAR(7) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idcolor_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colours`
--

LOCK TABLES `colours` WRITE;
/*!40000 ALTER TABLE `colours` DISABLE KEYS */;
INSERT INTO `colours` VALUES 
(1,'Navy Blue','#000066'),
(2,'Crimson Red','#990000'),
(3,'Forest Green','#006600'),
(4,'Jet Black','#000000'),
(5,'Sunflower Yellow','#FFFF66'),
(6,'Soft Pink','#FF9999');
/*!40000 ALTER TABLE `colours` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_colour`
--

DROP TABLE IF EXISTS `product_colour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_colour` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `colour_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_colour_id_idx` (`colour_id`),
  KEY `fk_product_id_idx` (`product_id`),
  CONSTRAINT `fk_colour_id` FOREIGN KEY (`colour_id`) REFERENCES `colours` (`id`),
  CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_colour`
--

LOCK TABLES `product_colour` WRITE;
/*!40000 ALTER TABLE `product_colour` DISABLE KEYS */;
INSERT INTO `product_colour` VALUES 
(1,1,1),(2,2,1),(3,1,2),(4,3,2),(5,1,3),(6,4,3),(7,1,4),(8,5,4),(9,1,5),(10,6,5),
(11,1,6),(12,2,6),(13,1,7),(14,3,7),(15,1,8),(16,4,8),(17,1,9),(18,5,9),(19,1,10),(20,6,10),
(21,1,11),(22,2,11),(23,1,12),(24,3,12),(25,1,13),(26,4,13),(27,1,14),(28,5,14),(29,1,15),(30,6,15),
(31,1,16),(32,2,16),(33,1,17),(34,3,17),(35,1,18),(36,4,18),(37,1,19),(38,5,19),(39,1,20),(40,6,20),
(41,1,21),(42,2,21),(43,1,22),(44,3,22),(45,1,23),(46,4,23),(47,1,24),(48,5,24),(49,1,25),(50,6,25),
(51,1,26),(52,2,26),(53,1,27),(54,3,27),(55,1,28),(56,4,28),(57,1,29),(58,5,29),(59,1,30),(60,6,30),
(61,1,31),(62,2,31),(63,1,32),(64,3,32),(65,1,33),(66,4,33),(67,1,34),(68,5,34),(69,1,35),(70,6,35),
(71,1,36),(72,2,36),(73,1,37),(74,3,37),(75,1,38),(76,4,38),(77,1,39),(78,5,39),(79,1,40),(80,6,40),
(81,1,41),(82,2,41),(83,1,42),(84,3,42),(85,1,43),(86,4,43),(87,1,44),(88,5,44),(89,1,45),(90,6,45),
(91,1,46),(92,2,46),(93,1,47),(94,3,47),(95,1,48),(96,4,48),(97,1,49),(98,5,49),(99,1,50),(100,6,50);
/*!40000 ALTER TABLE `product_colour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_types`
--

DROP TABLE IF EXISTS `product_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_types`
--

LOCK TABLES `product_types` WRITE;
/*!40000 ALTER TABLE `product_types` DISABLE KEYS */;
INSERT INTO `product_types` VALUES 
(1,'Sofa'),
(2,'Chair'),
(3,'Table'),
(4,'Bed'),
(5,'Storage');
/*!40000 ALTER TABLE `product_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `product_type_id` INT DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `product_type_id_idx` (`product_type_id`),
  CONSTRAINT `fk_product_type_id` FOREIGN KEY (`product_type_id`) REFERENCES `product_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES 
(1,'LANDSKRONA Sofa',1,'2024-06-09 19:14:00'),
(2,'POÄNG Chair',2,'2024-06-09 19:14:00'),
(3,'LISABO Table',3,'2024-06-09 19:14:00'),
(4,'MALM Bed',4,'2024-06-09 19:14:00'),
(5,'KALLAX Shelf',5,'2024-06-09 19:14:00'),
(6,'VIMLE Sofa',1,'2024-06-09 19:14:00'),
(7,'STRANDMON Armchair',2,'2024-06-09 19:14:00'),
(8,'NORDLI Bed',4,'2024-06-09 19:14:00'),
(9,'BILLY Bookcase',5,'2024-06-09 19:14:00'),
(10,'INGATORP Table',3,'2024-06-09 19:14:00'),
(11,'KLIPPAN Sofa',1,'2024-06-09 19:14:00'),
(12,'EKTORP Armchair',2,'2024-06-09 19:14:00'),
(13,'HEMNES Bed',4,'2024-06-09 19:14:00'),
(14,'LACK Side Table',3,'2024-06-09 19:14:00'),
(15,'BRIMNES Wardrobe',5,'2024-06-09 19:14:00'),
(16,'FRIHETEN Sofa Bed',1,'2024-06-09 19:14:00'),
(17,'PELLO Chair',2,'2024-06-09 19:14:00'),
(18,'GAMLEHULT Ottoman',5,'2024-06-09 19:14:00'),
(19,'NORDEN Gateleg Table',3,'2024-06-09 19:14:00'),
(20,'ASKVOLL Bed',4,'2024-06-09 19:14:00'),
(21,'SÖDERHAMN Sofa',1,'2024-06-09 19:14:00'),
(22,'TULLSTA Chair',2,'2024-06-09 19:14:00'),
(23,'IVAR Cabinet',5,'2024-06-09 19:14:00'),
(24,'MÖRBYLÅNGA Table',3,'2024-06-09 19:14:00'),
(25,'SLATTUM Bed',4,'2024-06-09 19:14:00'),
(26,'GRÖNLID Sofa',1,'2024-06-09 19:14:00'),
(27,'NOLMYRA Chair',2,'2024-06-09 19:14:00'),
(28,'EKET Wall Storage',5,'2024-06-09 19:14:00'),
(29,'VILHATTEN Table',3,'2024-06-09 19:14:00'),
(30,'SONGESAND Bed',4,'2024-06-09 19:14:00'),
(31,'KIVIK Sofa',1,'2024-06-09 19:14:00'),
(32,'JENNYLUND Armchair',2,'2024-06-09 19:14:00'),
(33,'HAVSTA Cabinet',5,'2024-06-09 19:14:00'),
(34,'GAMLEBY Table',3,'2024-06-09 19:14:00'),
(35,'NEIDEN Bed',4,'2024-06-09 19:14:00'),
(36,'FINNALA Sofa',1,'2024-06-09 19:14:00'),
(37,'BERGMUND Chair',2,'2024-06-09 19:14:00'),
(38,'BESTÅ Storage',5,'2024-06-09 19:14:00'),
(39,'TORNVIKEN Kitchen Island',3,'2024-06-09 19:14:00'),
(40,'TYSSEDAL Bed',4,'2024-06-09 19:14:00'),
(41,'MORABO Sofa',1,'2024-06-09 19:14:00'),
(42,'ADDE Chair',2,'2024-06-09 19:14:00'),
(43,'METOD Wall Cabinet',5,'2024-06-09 19:14:00'),
(44,'LIXHULT Cabinet',5,'2024-06-09 19:14:00'),
(45,'VADHOLMA Kitchen Island',3,'2024-06-09 19:14:00'),
(46,'HÄLLAN Sofa',1,'2024-06-09 19:14:00'),
(47,'FANBYN Chair',2,'2024-06-09 19:14:00'),
(48,'REGISSÖR Bookcase',5,'2024-06-09 19:14:00'),
(49,'DOCKSTA Table',3,'2024-06-09 19:14:00'),
(50,'GJÖRA Bed',4,'2024-06-09 19:14:00');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-09 19:14:00