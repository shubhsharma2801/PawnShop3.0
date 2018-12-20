<?php
require_once('dbConnect.php');
$userid= $_POST['userid'];
$sql1="select product_id,name, price, path1 from product where user_id='$userid'";
$r1= mysqli_query($con,$sql1);
$result=array();
while($r2=mysqli_fetch_array($r1))
{
	$name=$r2['name'];
	$price=$r2['price'];
	$path1=$r2['path1'];
	$id=$r2['product_id'];
	array_push($result, array("name"=>$name));
	array_push($result, array("price"=>$price));
	array_push($result, array("path1"=>$path1));
	array_push($result, array("product_id"=>$id));
	
}
echo json_encode(array('result'=>$result));
?>
