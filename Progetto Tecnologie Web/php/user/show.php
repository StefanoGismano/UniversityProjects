<?php
include_once("../include/common.php");
include_once("../include/session.php");
include_once("../include/get.php");
include_once("../db/db.php");

if (!isset($_GET["userid"])) {
    invreq("Userid not set.");
}

if (!isset($_GET["offset"])) {
    invreq("offset not set.");
}

$db = dbConnect();

$show_user = $db->prepare(
    "SELECT post.*,
        COUNT(DISTINCT comment.commentid) comments,
        COUNT(DISTINCT likes.userid) likes,
        COUNT(DISTINCT up.userid) voted
    FROM post 
    LEFT OUTER JOIN comment ON comment.postid = post.postid 
    LEFT OUTER JOIN likes ON likes.postid = post.postid 
    LEFT OUTER JOIN likes up ON up.postid = post.postid
        AND up.userid = :username
    WHERE post.userid = :username
    GROUP BY post.postid
    ORDER BY post.time DESC
    LIMIT 10 OFFSET :offset;"
);

$show_user->bindParam(':offset', $_GET["offset"], PDO::PARAM_INT);
$show_user->bindParam(':username', $_GET["userid"], PDO::PARAM_STR);

try {
    $show_user->execute();
} catch (PDOException $_) {
    respond(500, "No post found.");
}

$feed = $show_user->fetchAll();
respond(200, $feed);