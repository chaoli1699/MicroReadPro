-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-05-05 17:39:49
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
  `name_eg` varchar(11) NOT NULL,
  `name_cn` varchar(10) NOT NULL,
  `icon_path` text NOT NULL,
  `show_rrow` int(1) NOT NULL,
  `is_button` int(1) NOT NULL,
  `group` varchar(10) NOT NULL,
  `group_top` int(1) NOT NULL,
  `can_use` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ufid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- 转存表中的数据 `md_ufeture`
--

INSERT INTO `md_ufeture` (`ufid`, `name_eg`, `name_cn`, `icon_path`, `show_rrow`, `is_button`, `group`, `group_top`, `can_use`) VALUES
(1, 'acount', '账号', '', 1, 0, '', 1, 0),
(2, 'moment', '时光轴', 'img/icon_moment.png', 1, 0, 'ab_moment', 1, 0),
(3, 'camera', '我的时光', 'img/icon_camera.png', 1, 0, 'ab_moment', 0, 0),
(4, 'trash', '回收站', 'img/icon_trash.png', 1, 0, 'ab_moment', 0, 0),
(5, 'collection', '收藏', 'img/icon_collection.png', 1, 0, 'ab_collect', 1, 0),
(6, 'notify', '消息', 'img/icon_message.png', 1, 0, 'ab_message', 1, 0),
(7, 'setting', '设置', 'img/icon_setting.png', 1, 0, 'ab_setting', 1, 0),
(8, 'change_pwd', '修改密码', '', 1, 0, 'm_setting', 1, 0),
(9, 'clear_cache', '清理缓存', '', 0, 0, 'm_setting', 1, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
