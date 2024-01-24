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

$isadmin = $db->prepare(
    "SELECT * FROM user WHERE userid = :userid AND user.admin = 1;"
);

$isadmin->bindParam(':userid', $_SESSION["username"], PDO::PARAM_STR);

try {
    $isadmin->execute();
} catch (PDOException $_) {
    respond(500, "Error checking permissions.");
}

$checkpost = $db->prepare(
    "SELECT * FROM post WHERE postid = :postid
        AND (userid = :userid OR {$isadmin->rowCount()} = 1);"
);

$checkpost->bindParam(':postid', $data["postid"], PDO::PARAM_STR);
$checkpost->bindParam(':userid', $_SESSION["username"], PDO::PARAM_STR);

try {
    $checkpost->execute();
} catch (PDOException $_) {
    respond(500, "No post found.");
}

if ($checkpost->rowCount() == 0) {
    invreq("No post found.");
}

$delpost = $db->prepare(
    "DELETE FROM post WHERE postid = :postid
        AND (userid = :userid OR {$isadmin->rowCount()} = 1);"
);

$delpost->bindParam(':postid', $data["postid"], PDO::PARAM_STR);
$delpost->bindParam(':userid', $_SESSION["username"], PDO::PARAM_STR);

try {
    $delpost->execute();
} catch (PDOException $_) {
    respond(500, "Error deleting post.");
}

respond(200, "Post deleted.");