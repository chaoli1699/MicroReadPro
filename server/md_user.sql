-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-04-06 09:27:40
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
-- 表的结构 `md_user`
--

CREATE TABLE IF NOT EXISTS `md_user` (
  `uid` int(8) NOT NULL DEFAULT '10000',
  `username` varchar(15) NOT NULL DEFAULT 'username',
  `password` varchar(15) NOT NULL DEFAULT '123456',
  `sex` int(1) NOT NULL DEFAULT '1',
  `regist_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `district` text NOT NULL,
  `introduce` text NOT NULL,
  `can_use` int(1) NOT NULL DEFAULT '0',
  `role` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `md_user`
--

INSERT INTO `md_user` (`uid`, `username`, `password`, `sex`, `regist_time`, `last_login_time`, `district`, `introduce`, `can_use`, `role`) VALUES
(9999, 'admin', '123456', 1, '2017-03-31 03:10:08', '2017-03-31 08:19:59', '南京', '超级管理员', 0, 9);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
