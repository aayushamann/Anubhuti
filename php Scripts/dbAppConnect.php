<?php
define('HOST','YourDomain');
define('USER','DatabaseUserID');
define('PASS','DatabasePassword');
define('DB','DatabaseName');

$con = mysqli_connect(HOST,USER,PASS,DB);

// Check connection
if($con === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}
?>
