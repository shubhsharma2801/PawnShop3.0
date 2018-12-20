<?php


require_once('dbConnect.php');

$sql1="SELECT product_id, name, path1, price, likes FROM product WHERE category_id=0 limit 3";
$sql2="SELECT product_id, name, path1, price, likes FROM product WHERE category_id=1 limit 3";
$sql3="SELECT product_id, name, path1, price, likes FROM product WHERE category_id=2 limit 4";
$output1= mysqli_query($con,$sql1);
$output2= mysqli_query($con,$sql2);
$output3= mysqli_query($con,$sql3);
/*
if($output===FALSE)

{
	die(mysql_error());
}
*/
$result = array();

while($row=mysqli_fetch_array($output1))
	
{
	$id=$row['product_id'];
	array_push($result,array("id"=>$id));
	$name=$row['name'];
	array_push($result,array("name"=>$name));
	$path=$row['path1'];
	array_push($result,array("path"=>$path));
	$price=$row['price'];
	array_push($result,array("price"=>$price));
	$likes=$row['likes'];
	array_push($result,array("views"=>$likes));
	array_push($result,array("catagory"=>0));	
}

while($row=mysqli_fetch_array($output2))
	
{
	$id=$row['product_id'];
	array_push($result,array("id"=>$id));
	$name=$row['name'];
	array_push($result,array("name"=>$name));
	$path=$row['path1'];
	array_push($result,array("path"=>$path));
	$price=$row['price'];
	array_push($result,array("price"=>$price));
	$likes=$row['likes'];
	array_push($result,array("views"=>$likes));
	array_push($result,array("catagory"=>0));
}

while($row=mysqli_fetch_array($output3))
	
{
	$id=$row['product_id'];
	array_push($result,array("id"=>$id));
	$name=$row['name'];
	array_push($result,array("name"=>$name));
	$path=$row['path1'];
	array_push($result,array("path"=>$path));
	$price=$row['price'];
	array_push($result,array("price"=>$price));
	$likes=$row['likes'];
	array_push($result,array("views"=>$likes));
	array_push($result,array("catagory"=>0));
}

echo json_encode(array('result'=>$result));

mysqli_close($con);


?>


