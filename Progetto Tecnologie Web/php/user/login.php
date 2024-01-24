<?php
include_once("../include/common.php");
include_once("../include/session.php");
include_once("../include/post.php");
include_once("../db/db.php");

if (isset($_SESSION["username"])) {
    respond(200, "Already signed in.");
}

$data = json_decode(file_get_contents("php://input"), true);

if (!isset($data["username"])) {
    invreq("Username not specified.");
}

if (!isset($data["password"])) {
    invreq("Password not specified.");
}

$db = dbConnect();

$lowusername = strtolower($data["username"]);
if(!isvalid_user($lowusername)) {
    invreq("Username not valid.");
}

$getuser = $db->prepare(
    "SELECT userid, password FROM user WHERE userid = :username;"
);
$getuser->bindParam(':username', $lowusername, PDO::PARAM_STR);

try {
    $getuser->execute();
} catch (PDOException $ex) {
    respond(401, "Username or password not correct.");
}

$userid = $getuser->fetch();

if (password_verify($data["password"], $userid["password"])) {
    $_SESSION["username"] = $lowusername;
    respond(200, "Authenticated.");
} else {
    respond(401, "Username or password not correct.");
}
?>