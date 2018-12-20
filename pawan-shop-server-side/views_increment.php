<?php

require_once('dbConnect.php');
$username=$_POST['username'];
$productid=$_POST['productid'];

$sql1="select * from likes where user_id='$username' and product_id='$productid'"; 
$r1=mysqli_query($con,$sql1);
$count=mysqli_num_rows($r1);
if($count==0)
{
	$sql2="insert into likes (user_id, product_id) values ('$username','$productid')";
	mysqli_query($con,$sql2);
	
	$sql3="update product set likes=likes+1 where product_id='$productid'";
	mysqli_query($con,$sql3);
}

else
{
	
}
?>
