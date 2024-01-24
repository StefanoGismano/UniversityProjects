<?php
include_once("../include/common.php");
include_once("../include/session.php");
include_once("../include/get.php");
include_once("../db/db.php");

if (!isset($_GET["postid"])) {
    invreq("postid not set.");
}

$db = dbConnect();

$show_comments = $db->prepare(
    "SELECT comment.*,
        COUNT(DISTINCT commentlikes.userid) likes,
        COUNT(DISTINCT cl.userid) voted,
        CASE
            WHEN comment.userid = :username THEN 1
            WHEN us.admin = 1 THEN 1
            ELSE 0
        END AS mine
    FROM comment 
    LEFT OUTER JOIN commentlikes ON commentlikes.commentid = comment.commentid 
    LEFT OUTER JOIN commentlikes cl ON cl.commentid = comment.commentid
        AND cl.userid = :username
    LEFT OUTER JOIN user us ON us.userid = :username
    WHERE postid = :postid
    GROUP BY comment.commentid
    ORDER BY comment.time DESC;"
);

$show_comments->bindParam(':postid', $_GET["postid"], PDO::PARAM_INT);
$show_comments->bindParam(':username', $_SESSION["username"], PDO::PARAM_STR);

try {
    $show_comments->execute();
} catch (PDOException $_) {
    respond(500, "No comment found.");
}

respond(200, $show_comments->fetchAll());