<?php include "header.php" ?>
<script src="../js/user/register.js"></script>
<main>
    <h1>Register</h1>
    <form>
        <input type="text" name="username" id="username" placeholder="Username">
        <input type="password" name="password" id="password" placeholder="Password">
        <input type="password" name="confpassword" id="confpassword" placeholder="Password confirmation">
        <input type="submit" value="Register">
    </form>
    <div id="content"></div>
</main>
<?php include "footer.php"; ?>