<?php
include_once("../include/common.php");
include_once("../include/session.php");
include_once("../include/get.php");
include_once("../db/db.php");

if (!isset($_GET["postid"])) {
    invreq("Postid not set.");
}

$db = dbConnect();

$show_post = $db->prepare(
    "SELECT post.*,
        COUNT(DISTINCT comment.commentid) comments,
        COUNT(DISTINCT likes.userid) likes,
        COUNT(DISTINCT l.userid) voted,
        CASE
            WHEN post.userid = :username THEN 1
            WHEN us.admin = 1 THEN 1
            ELSE 0
        END AS mine
    FROM post 
    LEFT OUTER JOIN comment ON comment.postid = post.postid 
    LEFT OUTER JOIN likes ON likes.postid = post.postid 
    LEFT OUTER JOIN likes l ON l.postid = post.postid AND l.userid = :username
    LEFT OUTER JOIN user us ON us.userid = :username
    WHERE post.postid = :postid;"
);

$show_post->bindParam(':postid', $_GET["postid"], PDO::PARAM_INT);
$show_post->bindParam(':username', $_SESSION["username"], PDO::PARAM_STR);

try {
    $show_post->execute();
} catch (PDOException $_) {
    respond(500, "No post found.");
}

respond(200, $show_post->fetch());