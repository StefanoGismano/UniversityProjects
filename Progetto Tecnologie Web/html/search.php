<?php include "header.php";
if (!isset($_SESSION["username"])) {
    header("Location: /progetto/html/login.php");
} ?>
<link rel="stylesheet" href="/progetto/css/home.css">
<script src="../js/layouts/common.js"></script>
<script src="../js/layouts/searchuser.js"></script>
<main>
    <h1>Search User</h1>
    <form>
        <input type="text" name="username" id="username" placeholder="Search User...">
        <input type="submit" value="Search">
    </form>
    <div id="content"></div>
</main>
<?php include "footer.php"; ?>