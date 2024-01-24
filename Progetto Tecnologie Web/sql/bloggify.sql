-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 13, 2023 alle 22:34
-- Versione del server: 10.4.21-MariaDB
-- Versione PHP: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bloggify`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `comment`
--

CREATE TABLE `comment` (
  `postid` int(11) NOT NULL,
  `commentid` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `text` text NOT NULL,
  `userid` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `comment`
--

INSERT INTO `comment` (`postid`, `commentid`, `time`, `text`, `userid`) VALUES
(18, 2, '2023-02-13 13:33:42', 'I think it has a very good design, it looks amazing! I wish i could have one...', 'stefanogismano'),
(18, 3, '2023-02-13 13:35:40', 'It looks fine but i wish it had more functions! Maybe incorporate a coffee machine in it next time, i think that would make it way better', 'roccotanica64'),
(16, 4, '2023-02-13 13:36:44', 'I\'m alright, thanks! Congrats on your first post!', 'roccotanica64'),
(16, 5, '2023-02-13 13:39:13', 'I\'m feeling well, thank you very much! See you in Via Palestro!', 'adrianoolivetti'),
(19, 6, '2023-02-13 15:01:57', 'That seems like an issue! Let me see if any of my friends at IBM can help you out with this.', 'adrianoolivetti'),
(20, 7, '2023-02-13 15:06:22', 'Awesome, that\'s just what i needed!', 'roccotanica64'),
(20, 8, '2023-02-13 15:12:06', 'No problem, sir! Can\'t wait for your next product!', 'stefanogismano'),
(21, 9, '2023-02-13 15:24:51', 'I\'m already terrified... can\'t wait to read it!', 'stefanogismano'),
(18, 10, '2023-02-13 15:27:17', 'I write all my books on a Lettera 22! I couldn\'t live without it!', 'stephenking'),
(22, 11, '2023-02-13 15:32:57', 'UPDATE: i figured it out! I just had to hit some monster in its single, big, glowing eye, i don\'t know how i didn\'t think about it earlier!', 'linkofhyrule');

-- --------------------------------------------------------

--
-- Struttura della tabella `commentlikes`
--

CREATE TABLE `commentlikes` (
  `commentid` int(11) NOT NULL,
  `userid` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `commentlikes`
--

INSERT INTO `commentlikes` (`commentid`, `userid`) VALUES
(2, 'roccotanica64'),
(2, 'stephenking'),
(3, 'stephenking'),
(7, 'stefanogismano'),
(8, 'stefanogismano'),
(10, 'stephenking'),
(11, 'stefanogismano');

-- --------------------------------------------------------

--
-- Struttura della tabella `likes`
--

CREATE TABLE `likes` (
  `postid` int(11) NOT NULL,
  `userid` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `likes`
--

INSERT INTO `likes` (`postid`, `userid`) VALUES
(16, 'roccotanica64'),
(16, 'stefanogismano'),
(17, 'stefanogismano'),
(18, 'roccotanica64'),
(18, 'stefanogismano'),
(19, 'adrianoolivetti'),
(19, 'stefanogismano'),
(20, 'roccotanica64'),
(20, 'stefanogismano'),
(21, 'stefanogismano'),
(22, 'linkofhyrule'),
(22, 'stefanogismano');

-- --------------------------------------------------------

--
-- Struttura della tabella `post`
--

CREATE TABLE `post` (
  `postid` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `title` varchar(80) NOT NULL,
  `userid` varchar(20) NOT NULL,
  `text` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `post`
--

INSERT INTO `post` (`postid`, `time`, `title`, `userid`, `text`) VALUES
(16, '2023-02-13 11:22:31', 'First post!', 'stefanogismano', 'Hey, everyone! This is my first post on this site! How are y\'all doing?'),
(17, '2023-02-13 13:20:26', 'I just saw Shpalman!!!', 'roccotanica64', 'Some guy was trying to steal my bike but Shpalman came out of nowhere and saved the day! What a cool dude!'),
(18, '2023-02-13 13:31:45', 'What do you think of our new product?', 'adrianoolivetti', 'We at Olivetti just released a new, innovative typewriter, the Lettera 22! I would like to hear some opinions about it, so that we can improve our future projects even more.'),
(19, '2023-02-13 14:59:23', 'Need an IBM5100 ASAP', 'johntitor', 'If anyone has an original IBM5100, tell me in the comments, please. I will buy it at any price. I come from the future and if i don\'t find one as soon as possible the world might end.'),
(20, '2023-02-13 15:04:51', 'Thanks for your suggestions!', 'adrianoolivetti', 'You people seem to like the Lettera 22 a lot! We heard your feedback and we are happy to announce that our next product, the Lettera 2000, will be able to make your coffee while you work on your next novel or file your taxes!'),
(21, '2023-02-13 15:22:58', 'New book coming soon...', 'stephenking', 'When you are locked in a candy shop and an ominous sound comes from the jars full of chocolate, there\'s only one thing to do...\r\n\"The Monster That Only Scares You If You Have A Very Specific Phobia\", coming to your local bookstore very soon.'),
(22, '2023-02-13 15:31:28', 'Lost in a dungeon, send help', 'linkofhyrule', 'This place is huge and i have no idea where to go! Send the firefighters, please!!! I think i need some weird item to continue but i don\'t have it and i can\'t find the way back.');

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `userid` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`userid`, `password`, `admin`) VALUES
('admin', '$2y$10$OOW4h4CeKZn.efmD/a41/e34efbAFLp9GKXcHOd8MTZxYydfDCnua', 1),
('adrianoolivetti', '$2y$10$SX8Hm9f94LkUAyMff.8gwevzQvpn2ssxbmHquClu5sIdnqRXBALGq', 0),
('johntitor', '$2y$10$e15EFe3JZMtPQ3Qf9xsOMexno4ruQ1mcGycbQme.Yt8wi8nkFVDvu', 0),
('linkofhyrule', '$2y$10$LMa/OLVdlatO/Y0c7MeGkuiSrreDJVFJy3.7zkbCMbCRBGjn.IKlu', 0),
('roccotanica64', '$2y$10$i60iUxUt4HFlD2FCUMvyuO7tri3o4N3ZFSiMuyBL5LTWGeuDD.opC', 0),
('stefanogismano', '$2y$10$izSQSaLMDoiqh2VmMR0Ix.S7.AiGi7p09.bQJql8UKO.9CTngp3Uy', 0),
('stephenking', '$2y$10$fUVDNqmqj3bF1UuYPoyu.uh180NBqMdiT9OHSW6Zlms4NIiwWf3ZS', 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`commentid`),
  ADD KEY `userid` (`userid`),
  ADD KEY `postid` (`postid`);

--
-- Indici per le tabelle `commentlikes`
--
ALTER TABLE `commentlikes`
  ADD PRIMARY KEY (`commentid`,`userid`),
  ADD KEY `userid` (`userid`);

--
-- Indici per le tabelle `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`postid`,`userid`),
  ADD KEY `userid` (`userid`);

--
-- Indici per le tabelle `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`postid`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userid`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `comment`
--
ALTER TABLE `comment`
  MODIFY `commentid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT per la tabella `post`
--
ALTER TABLE `post`
  MODIFY `postid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`postid`) REFERENCES `post` (`postid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `commentlikes`
--
ALTER TABLE `commentlikes`
  ADD CONSTRAINT `commentlikes_ibfk_1` FOREIGN KEY (`commentid`) REFERENCES `comment` (`commentid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `commentlikes_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `likes`
--
ALTER TABLE `likes`
  ADD CONSTRAINT `likes_ibfk_1` FOREIGN KEY (`postid`) REFERENCES `post` (`postid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `likes_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
