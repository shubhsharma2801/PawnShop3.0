<?php
require_once('dbConnect.php');
$name=$_POST['name'];
$pincode=$_POST['pincode'];
$first=$pincode-10;
$last=$pincode+10;
$sql1="select product_id, name, price, description,  likes, path1 from product where name like '%$name%' and category_id=1 and (pincode between '$first' and '$last')";
$r1=mysqli_query($con,$sql1);
$result=array();
while($r2= mysqli_fetch_array($r1))
{
	$productid=$r2['product_id'];
	array_push($result, array("productid"=>$productid));
	$name=$r2['name'];
	array_push($result, array("name"=>$name));
	$price=$r2['price'];
	array_push($result, array("price"=>$price));
	
	$views=$r2['likes'];
	array_push($result, array("views"=>$views));
	$path=$r2['path1'];
	array_push($result, array("path"=>$path));
}

echo json_encode(array('result'=>$result));

?>