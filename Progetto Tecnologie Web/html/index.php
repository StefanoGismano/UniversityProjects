<?php include "header.php";
if (!isset($_SESSION["username"])) {
    header("Location: /progetto/html/login.php");
} ?>
<link rel="stylesheet" href="/progetto/css/home.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
<script src="../js/layouts/feed.js"></script>
<script src="../js/layouts/common.js"></script>
<main class="container">
  <div id="heading">
    <div id="radio">
      <input type="radio" name="ordertype" id="new" value="new" checked>
      <label for="new">New</label>
      <input type="radio" name="ordertype" id="likes" value="likes">
      <label for="likes">Likes</label>
      <input type="radio" name="ordertype" id="comments" value="comments">
      <label for="comments">Comments</label>
    </div>
  </div>
  <div id="content"></div>
  <div id="pager">Pages:</div>
</main>
<?php include "footer.php"; ?>