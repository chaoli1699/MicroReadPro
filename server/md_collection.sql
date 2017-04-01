-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-04-01 07:45:35
-- 服务器版本： 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `md_db`
--

-- --------------------------------------------------------

--
-- 表的结构 `md_collection`
--

CREATE TABLE IF NOT EXISTS `md_collection` (
  `cid` int(8) NOT NULL AUTO_INCREMENT,
  `uid` varchar(20) NOT NULL,
  `detail_path` text NOT NULL,
  `col_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `can_use` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `md_collection`
--

INSERT INTO `md_collection` (`cid`, `uid`, `detail_path`, `col_time`, `can_use`) VALUES
(1, '9999', 'artical/1001.html', '2017-04-01 13:41:06', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
