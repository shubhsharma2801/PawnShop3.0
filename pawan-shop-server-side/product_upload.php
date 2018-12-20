<?php

require_once('dbConnect.php');
$type=$_POST['type'];

$name=$_POST['name'];
$categoryid=$_POST['category'];

$price=$_POST['price'];
$description=$_POST['description'];
$views=0;
$userid=$_POST['userid'];
$pincode=$_POST['pincode'];
$image =$_POST['path1'];
$image1=$_POST['path2'];
$image2=$_POST['path3'];
$image3=$_POST['path4'];
$sql1="select max(product_id) as max from product";
$r1=mysqli_query($con,$sql1);
$r2=mysqli_fetch_array($r1);
$id=$r2['max']+1;

$sql2="select category_id from category where name='$categoryid'";
$r3=mysqli_query($con,$sql2);
$r4=mysqli_fetch_array($r3);
//$categoryid=$r4['category_id'];


$path ="uploads/$name.1.png";
$actualpath1 = "http://192.168.43.181/Olx/$path";
file_put_contents($path,base64_decode($image));

$path = "uploads/$name.2.png";
$actualpath2 = "http://192.168.43.181/Olx/$path";
file_put_contents($path,base64_decode($image1));

$path = "uploads/$name.3.png";
$actualpath3 = "http://192.168.43.181/Olx/$path";
file_put_contents($path,base64_decode($image2)); 

$path = "uploads/$name.4.png";
$actualpath4 = "http://192.168.43.181/Olx/$path";
file_put_contents($path,base64_decode($image3));

$sql3="insert into product(user_id, name,type,category_id, price, description, likes, path1, path2, path3, path4, pincode) 
                              values('$userid','$name','$type','$categoryid','$price','$description','$views','$actualpath1','$actualpath2','$actualpath3','$actualpath4','$pincode')";
mysqli_query($con,$sql3);

?>
