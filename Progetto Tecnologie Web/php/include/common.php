<?php
  function invreq($message) {
    header("HTTP /1.1 400 Invalid Request");
    die("ERROR 400: $message");
  }

function respond($code, $message)
{
    http_response_code($code);
    header("Content-type: application/json");
    die(json_encode(["statusCode" => $code, "message" => $message]));
}

function isvalid_user($username)
{
  $len = strlen($username);
  return $len >= 3 && $len < 21 && ctype_alnum($username);
}

function isvalid_pass($password)
{
  $len = strlen($password);
  return $len >= 8;
}