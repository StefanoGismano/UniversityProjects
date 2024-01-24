<?php
include_once("../include/common.php");
include_once("../include/session.php");
include_once("../include/get.php");
include_once("../db/db.php");

if (!isset($_GET["offset"])) {
    invreq("offset not set.");
}

$db = dbConnect();

$ordertypes = [
    "new" => "ORDER BY post.time DESC",
    "likes" => "ORDER BY likes DESC",
    "comments" => "ORDER BY comments DESC",
];

$getposts = $db->prepare(
    "SELECT post.*,
        COUNT(DISTINCT comment.commentid) comments,
        COUNT(DISTINCT likes.userid) likes,
        COUNT(DISTINCT lk.userid) voted
    FROM post 
    LEFT OUTER JOIN comment ON comment.postid = post.postid 
    LEFT OUTER JOIN likes ON likes.postid = post.postid 
    LEFT OUTER JOIN likes lk ON lk.postid = post.postid
        AND lk.userid = :username
    GROUP BY post.postid
    {$ordertypes[$_GET["order"] ?? "new"]}
    LIMIT 10 OFFSET :offset;"
);

$getposts->bindParam(':username', $_SESSION["username"], PDO::PARAM_STR);
$getposts->bindParam(':offset', $_GET["offset"], PDO::PARAM_INT);

try {
    $getposts->execute();
} catch (PDOException $_) {
    respond(500, "Error fetching posts.");
}

$feed = $getposts->fetchAll();
respond(200, $feed);