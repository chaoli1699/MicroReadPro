-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-05-05 15:48:44
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
  `uid` int(8) NOT NULL DEFAULT '1000',
  `username` varchar(10) NOT NULL DEFAULT 'username',
  `password` varchar(15) NOT NULL DEFAULT '123456',
  `sex` int(1) NOT NULL DEFAULT '1',
  `regist_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `district` varchar(8) NOT NULL,
  `introduce` text NOT NULL,
  `can_use` int(1) NOT NULL DEFAULT '0',
  `role` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `md_user`
--

INSERT INTO `md_user` (`uid`, `username`, `password`, `sex`, `regist_time`, `last_login_time`, `district`, `introduce`, `can_use`, `role`) VALUES
(9999, 'admin', '123456', 1, '2017-03-31 00:00:00', '2017-05-05 15:42:39', '北京', '超级管理员', 0, 9),
(3672, 'lichao', 'lichao', 1, '2017-05-03 17:06:49', '2017-05-05 15:42:56', '南京', '每天都是好心情！', 0, 0),
(4139, 'muxiaolan', 'muxiaolan', 0, '2017-05-03 17:19:11', '2017-05-03 17:37:51', '上海', '没啥好说的～', 0, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
