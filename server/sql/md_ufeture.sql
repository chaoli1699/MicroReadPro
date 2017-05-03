-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-05-03 17:26:06
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
-- 表的结构 `md_ufeture`
--

CREATE TABLE IF NOT EXISTS `md_ufeture` (
  `ufid` int(8) NOT NULL AUTO_INCREMENT,
  `name_eg` varchar(10) NOT NULL,
  `name_cn` varchar(10) NOT NULL,
  `icon_path` text NOT NULL,
  `show_rrow` int(1) NOT NULL,
  `is_button` int(1) NOT NULL,
  `group` varchar(10) NOT NULL,
  `group_top` int(1) NOT NULL,
  `can_use` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ufid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- 转存表中的数据 `md_ufeture`
--

INSERT INTO `md_ufeture` (`ufid`, `name_eg`, `name_cn`, `icon_path`, `show_rrow`, `is_button`, `group`, `group_top`, `can_use`) VALUES
(1, 'acount', '账号', '', 1, 0, '', 1, 0),
(2, 'moment', '时光轴', '/img/icon_moment.png', 1, 0, 'my_feture', 1, 0),
(3, 'collection', '收藏', '/img/icon_collection.png', 1, 0, 'my_feture', 0, 0),
(4, 'message', '消息', '/img/icon_message.png', 1, 0, 'my_feture', 0, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;