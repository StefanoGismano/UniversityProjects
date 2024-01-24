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

$isadmin = $db->prepare(
    "SELECT * FROM user WHERE userid = :userid AND user.admin = 1;"
);

$isadmin->bindParam(':userid', $_SESSION["username"], PDO::PARAM_STR);

try {
    $isadmin->execute();
} catch (PDOException $_) {
    respond(500, "Error checking permissions.");
}

$checkcomment = $db->prepare(
    "SELECT * FROM comment WHERE commentid = :commentid
        AND (userid = :userid OR {$isadmin->rowCount()} = 1);"
);

$checkcomment->bindParam(':commentid', $data["commentid"], PDO::PARAM_STR);
$checkcomment->bindParam(':userid', $_SESSION["username"], PDO::PARAM_STR);

try {
    $checkcomment->execute();
} catch (PDOException $_) {
    respond(500, "No comment found.");
}

if ($checkcomment->rowCount() == 0) {
    invreq("No comment found.");
}

$delcomment = $db->prepare(
    "DELETE FROM comment WHERE commentid = :commentid
        AND (userid = :userid OR {$isadmin->rowCount()} = 1);"
);

$delcomment->bindParam(':commentid', $data["commentid"], PDO::PARAM_STR);
$delcomment->bindParam(':userid', $_SESSION["username"], PDO::PARAM_STR);

try {
    $delcomment->execute();
} catch (PDOException $_) {
    respond(500, "Error deleting comment.");
}

respond(200, "Comment deleted.");