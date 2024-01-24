<?php
if (!isset($_SESSION)) {
    session_start();
}

if (isset($_SESSION["username"])) {
    header("Location: /progetto/html/index.php");
} else {
    header("Location: /progetto/html/login.php");
}
?>