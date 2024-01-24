<?php include "../header.php"; ?>
<script src="../../js/layouts/common.js"></script>
<script src="../../js/post/submit.js"></script>
<main>
    <form method="POST" enctype="multipart/form-data">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required>
        <br>
        <label for="text">Text:</label>
        <textarea id="text" name="text" rows="4" cols="49"></textarea>
        <input type="submit" value="submit">
    </form>
    <div id="content"></div>
</main>
<?php include "../footer.php"; ?>