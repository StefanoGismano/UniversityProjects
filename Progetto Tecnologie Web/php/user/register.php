<?php
include_once("../include/common.php");
include_once("../include/session.php");
include_once("../include/post.php");
include_once("../db/db.php");

$data = json_decode(file_get_contents("php://input"), true);

if (!isset($data["username"]) || !isset($data["password"])) {
    invreq("Missing username or password");
}

if (!isvalid_user($data["username"])) {
    invreq("Invalid username.");
}

if (!isvalid_pass($data["password"])) {
    invreq("Invalid password.");
}

if (strcmp($data["password"], $data["confpassword"]) !== 0) {
    invreq("Passwords do not match.");
}

$db = dbConnect();

$lowusername = strtolower($data["username"]);
$checkexist =$db->prepare(
    "SELECT * FROM user WHERE userid = :username;"
);
$checkexist->bindParam(':username', $lowusername, PDO::PARAM_STR);

try {
    $checkexist->execute();
} catch (PDOException $_) {
    respond(500, "Could not check if user already exists.");
}

if ($checkexist->rowCount() > 0) {
    invreq("User already exists.");
}

$lowusername = strtolower($data["username"]);
$encrpass = password_hash($data["password"], PASSWORD_BCRYPT);
$useradd = $db->prepare(
    "INSERT INTO user(userid, password, admin) VALUES (:username, :password, 0);"
);
$useradd->bindParam(':username', $lowusername, PDO::PARAM_STR);
$useradd->bindParam(':password', $encrpass, PDO::PARAM_STR);

try {
    $useradd->execute();
} catch (PDOException $ex) {
    respond(401, "Username or password not correct.");
}

header("HTTP /1.1 201 Created");
?>