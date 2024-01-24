<?php
include_once("../include/common.php");
include_once("../include/session.php");
include_once("../include/post.php");
include_once("../db/db.php");

$data = json_decode(file_get_contents("php://input"), true);

if (!isset($data["postid"])) {
    invreq("postid not specified.");
}

$db = dbConnect();

$like = $db->prepare(
    "INSERT INTO likes(postid, userid) VALUES (:postid, :userid);"
);

$like->bindParam(':postid', $data["postid"], PDO::PARAM_INT);
$like->bindParam(':userid', $_SESSION["username"], PDO::PARAM_STR);

try {
    $like->execute();
} catch (PDOException $_) {
    respond(500, "Error liking.");
}

respond(200, $data["postid"]);