<?php
include_once("../include/common.php");

function dbConnect() {
    try {
        $db = new PDO('mysql:host=localhost;dbname=bloggify', "root", "");
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
    } catch (PDOException $e) {
        respond(500, "Error connecting to database.");
    }
    return $db;
}