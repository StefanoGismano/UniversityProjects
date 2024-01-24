<?php
include_once("../include/common.php");
include_once("../include/session.php");
include_once("../include/post.php");
include_once("../db/db.php");

if (!isset($_SESSION["username"])) {
    invreq("Not signed in.");
}

if (!isset($_POST["title"])) {
    invreq("Title not specified.");
}

$db = dbConnect();

$insert_post = $db->prepare(
    "INSERT INTO post(title, userid, text) VALUES (:title, :username, :text);"
);

$insert_post->bindParam(':title', $_POST["title"], PDO::PARAM_STR);
$insert_post->bindParam(':username', $_SESSION["username"], PDO::PARAM_STR);
$insert_post->bindParam(':text', $_POST["text"], PDO::PARAM_STR);

try {
    $insert_post->execute();
} catch (PDOException $_) {
    respond(500, "Error inserting post.");
}

$latest_post = $db->lastInsertId();

respond(200, $latest_post);