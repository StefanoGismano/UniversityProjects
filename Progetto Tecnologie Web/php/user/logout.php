<?php
include_once("../include/session.php");
if (isset($_SESSION)) {
    session_unset();
    session_destroy();
}

header("Location: /progetto/html/login.php");
respond(200, "User logged out.");
?>