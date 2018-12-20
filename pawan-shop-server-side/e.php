<?php
require_once('dbConnect.php');
$productid=7;//$_POST['productid'];
$sql3= "select user_id from product where product_id='$productid'";
$r5=mysqli_query($con,$sql3);
$r6=mysqli_fetch_array($r5);
$userid=$r6['user_id'];
$sql1="select product_id, name, price, description,  likes, path1, path2, path3, path4 from product where product_id='$productid'";
$r1=mysqli_query($con,$sql1);
$sql2="select name, address, phone from registration where user_id='$userid';";
$r3=mysqli_query($con,$sql2);
$r4=mysqli_fetch_array($r3);
$r6=mysqli_fetch_array($r1);
$seller_name=$r4['name'];
$address=$r4['address'];
$result=array();

	
	$name=$r6['name'];
	array_push($result, array("name"=>$name));
	$price=$r6['price'];
	array_push($result, array("price"=>$price));
	$description=$r6['description'];
	array_push($result, array("description"=>$description));
	$views=$r6['likes'];
	array_push($result, array("views"=>$views));
	$path1=$r6['path1'];
	array_push($result, array("path1"=>$path1));
	$path2=$r6['path2'];
	array_push($result, array("path2"=>$path2));
	$path3=$r6['path3'];
	array_push($result, array("path3"=>$path3));
	$path4=$r6['path4'];
	array_push($result, array("path4"=>$path4));
	$path2=$r6['product_id'];
	array_push($result, array("product_id"=>$productid));
	$path2=$r4['name'];
	array_push($result, array("sellername"=>$path2));
	$path2=$r4['address'];
	array_push($result, array("address"=>$path2));
	$path2=$r4['phone'];
	array_push($result, array("phone"=>$path2));
	
echo json_encode(array('result'=>$result));
?>