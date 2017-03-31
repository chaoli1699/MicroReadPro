-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-03-31 09:15:17
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
  `uid` varchar(8) NOT NULL,
  `username` text NOT NULL,
  `password` text CHARACTER SET utf32 NOT NULL,
  `sex` int(1) NOT NULL,
  `regist_time` datetime NOT NULL,
  `last_login_time` datetime NOT NULL,
  `district` text NOT NULL,
  `introduce` text NOT NULL,
  `can_use` int(1) NOT NULL,
  `role` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `md_user`
--

INSERT INTO `md_user` (`uid`, `username`, `password`, `sex`, `regist_time`, `last_login_time`, `district`, `introduce`, `can_use`, `role`) VALUES
('9999', 'admin', '123456', 1, '2017-03-31 03:10:08', '2017-03-31 08:19:59', '南京', '超级管理员', 0, 0),
('8725', 'lichao', '123456', 1, '2017-03-31 08:13:29', '2017-03-31 08:43:36', '上海', '每天都是好心情！', 0, 0),
('6450', 'gaorui', '123456', 0, '2017-03-31 08:14:02', '0000-00-00 00:00:00', '中卫', '', 0, 0),
('3608', 'lijing', '123456', 0, '2017-03-31 08:14:15', '0000-00-00 00:00:00', '银川', '', 0, 0),
('6304', 'lixu', '123456', 1, '2017-03-31 08:14:22', '0000-00-00 00:00:00', '中卫', '', 0, 0),
('5312', 'zhangwei', '123456', 0, '2017-03-31 08:14:29', '0000-00-00 00:00:00', '中卫', '', 0, 0),
('1129', 'lidahuai', '123456', 1, '2017-03-31 08:14:46', '2017-03-31 08:18:46', '中卫', '', 0, 0),
('9631', 'gaoshulan', '123456', 0, '2017-03-31 08:14:56', '0000-00-00 00:00:00', '中卫', '', 0, 0),
('4211', 'chenlijuan', '123456', 0, '2017-03-31 08:54:07', '0000-00-00 00:00:00', '兰州', '', 0, 0),
('8465', 'shenting', '123456', 0, '2017-03-31 08:54:38', '2017-03-31 09:09:58', '南京', '', 0, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
