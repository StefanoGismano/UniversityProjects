<?php include "header.php";
if (!isset($_SESSION["username"])) {
    header("Location: /progetto/html/login.php");
} ?>
<link rel="stylesheet" href="/progetto/css/home.css">
<script src="../js/layouts/common.js"></script>
<script src="../js/layouts/user.js"></script>
<main class="container">
    <div id="heading"></div>
    <div id="content"></div>
</main>
<?php include "footer.php"; ?>