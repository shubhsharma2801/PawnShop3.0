<?php
require_once('dbConnect.php');
$productid=$_POST['product_id'];
$sql1="select name, price, path1, likes from product where product_id='$productid'";
$result=array();
$r1=mysqli_query($con,$sql1);
$r2=mysqli_fetch_array($r1);
$name=$r2['name'];
$price=$r2['price'];
$path=$r2['path1'];
$likes=$r2['likes'];
array_push($result, array("productid"=>$productid));
array_push($result, array("name"=>$name));//;
array_push($result, array("price"=>$price));
array_push($result, array("path"=>$path));
array_push($result, array("views"=>$likes));

echo json_encode(array('result'=>$result));
?>