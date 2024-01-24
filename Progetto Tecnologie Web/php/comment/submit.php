<?php
include_once("../include/common.php");
include_once("../include/session.php");
include_once("../include/post.php");
include_once("../db/db.php");

$data = json_decode(file_get_contents("php://input"), true);

if (!isset($data["text"])) {
    invreq("Missing text.");
}

if (!isset($data["postid"])) {
    invreq("Post not specified.");
}

$db = dbConnect();

$insert_comment = $db->prepare(
    "INSERT INTO comment(postid, text, userid) VALUES (:postid, :text, :userid);"
);

$insert_comment->bindParam(':postid', $data["postid"], PDO::PARAM_INT);
$insert_comment->bindParam(':text', $data["text"], PDO::PARAM_STR);
$insert_comment->bindParam(':userid', $_SESSION["username"], PDO::PARAM_STR);

try {
    $insert_comment->execute();
} catch (PDOException $_) {
    respond(500, "Error inserting comment.");
}

$latest_comment = $db->lastInsertId();
respond(200, $latest_comment);