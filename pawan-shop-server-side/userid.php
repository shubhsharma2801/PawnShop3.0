<?php
require_once('db_config.php');
$username="Ujwal";//$_POST['username'];
$sql1="select user_id from registration where name='$username'";
$r1=mysqli_query($con,$sql1);
$r2=mysqli_fetch_array($r1);
$userid=$r2['user_id'];
echo "$userid";
?>