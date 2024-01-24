<?php include "../header.php"; ?>
<link rel="stylesheet" href="/progetto/css/home.css">
<script src="../../js/comment/submit.js"></script>
<script src="../../js/layouts/common.js"></script>
<script src="../../js/layouts/comments.js"></script>
<main class="container">
    <div id="content"></div>
    <form>
        <textarea id="text" name="text" placeholder="New comment..." rows="4" cols="49"></textarea>
        <input type="submit" value="submit">
    </form>
    <div id="comments"></div>
</main>
<?php include "../footer.php"; ?>