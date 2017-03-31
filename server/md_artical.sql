-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-03-31 10:52:07
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
-- 表的结构 `md_artical`
--

CREATE TABLE IF NOT EXISTS `md_artical` (
  `uid` varchar(8) NOT NULL,
  `title` text NOT NULL,
  `author` text NOT NULL,
  `source` text NOT NULL,
  `type` int(2) NOT NULL,
  `image_path` text NOT NULL,
  `detail_path` text NOT NULL,
  `create_time` datetime NOT NULL,
  `can_show` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `md_artical`
--

INSERT INTO `md_artical` (`uid`, `title`, `author`, `source`, `type`, `image_path`, `detail_path`, `create_time`, `can_show`) VALUES
('9999', '朱自清《背影》原文及赏析', '编辑:lqy', '应届毕业生网', 0, 'http://uploads.yjbys.com/allimg/161117/205P5B64-0.jpg', 'http://wenxue.yjbys.com/beiying/94428.html', '2017-03-31 05:14:11', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
