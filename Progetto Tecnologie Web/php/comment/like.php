<?php
include_once("../include/common.php");
include_once("../include/session.php");
include_once("../include/post.php");
include_once("../db/db.php");

$data = json_decode(file_get_contents("php://input"), true);

if (!isset($data["commentid"])) {
    invreq("commentid not specified.");
}

$db = dbConnect();

$commentlike = $db->prepare(
    "INSERT INTO commentlikes(commentid, userid) VALUES (:commentid,:userid);"
);

$commentlike->bindParam(':commentid', $data["commentid"], PDO::PARAM_INT);
$commentlike->bindParam(':userid', $_SESSION["username"], PDO::PARAM_STR);

try {
    $commentlike->execute();
} catch (PDOException $_) {
    respond(500, "Error liking comment.");
}

respond(200, "Liked.");