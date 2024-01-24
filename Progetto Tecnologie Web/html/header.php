<!DOCTYPE html>
<html lang ="en">

<head>
  <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bloggify</title>
    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
    <link rel="icon" type="image/x-icon" href="../img/icon.ico">
    <link rel="stylesheet" href="/progetto/css/style.css">
</head>

<body>
  <header>
    <div class="navbar">
      <div>
        <a href="/progetto/" id="sitename">Bloggify</a>
        <?php
        if (!isset($_SESSION)) {
          session_start();
        }
        if (isset($_SESSION["username"])) { ?>
        <a href="/progetto/html/post/submit.php" id="submit">Submit</a>
        <?php } ?>
      </div>
      <div id="useroptions">
        <?php 
        if (isset($_SESSION["username"])) {
          $userid = $_SESSION["username"] ?>
          <a href="/progetto/html/search.php">Search</a>
          <a href="/progetto/html/user.php?userid=<?php echo $userid?>"><?php echo $userid?></a>
          <a href="/progetto/php/user/logout.php">Logout</a>
          <?php } else {?>
          <a href="/progetto/html/login.php">Login</a>
          <a href="/progetto/html/register.php">Register</a>
          <?php 
        } ?>
      </div>
    </div>
  </header>
