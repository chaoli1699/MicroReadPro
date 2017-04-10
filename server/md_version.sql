-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-04-06 09:27:48
-- 服务器版本： 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:08";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `md_db`
--

-- --------------------------------------------------------

--
-- 表的结构 `md_version`
--

CREATE TABLE IF NOT EXISTS `md_version` (
  `vid` int(4) NOT NULL AUTO_INCREMENT,
  `version_code` int(2) NOT NULL DEFAULT '0',
  `version_name` varchar(10) NOT NULL,
  `introduce` varchar(30) NOT NULL,
  `download_path` text NOT NULL,
  `can_use` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `md_version`
--

INSERT INTO `md_version` (`vid`, `version_code`, `version_name`, `introduce`, `download_path`, `can_use`) VALUES
(1, 10, 'v 1.0.10', '新增自建后台，修复已知bug.', '/apk/MicroRead(v 1.0.10).apk', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
