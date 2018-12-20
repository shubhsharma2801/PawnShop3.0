<?php
require_once('dbConnect.php');
$userid=$_POST['userid'];
$productid=$_POST['productid'];
$sql1="delete from product where product_id='$productid' and user_id='$userid'";
mysqli_query($con,$sql1);
?>
