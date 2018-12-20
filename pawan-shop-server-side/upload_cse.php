<?php
require_once('dbConnect.php');
$image=$_POST['image'];
$image1=$_POST['image1'];
$image2=$_POST['image2'];
$categoryjava="null"; //$_POST['category'];
$productname=$_POST['product_name'];
$price=$_POST['price'];
$brand=$_POST['brand'];
$modelname=$_POST['model_name'];
$speed=$_POST['speed'];
$storagecapacity=$_POST['capacity'];
$batteryvoltage=$_POST['battery_voltage'];
$os=$_POST['os'];
$usbport=$_POST['usb_port'];
$height=$_POST['height'];
$width=$_POST['width'];
$color=$_POST['color'];
$description=$_POST['description'];
$batterylife="null";//$_POST['battery_life'];

$datee= date("Y-m-d");	
$weight="null";


$screensize="null";//$_POST['screen_size'];
$focusdistance="null";
$opticalzoom="null";
$vehiclecompatibility="null";
$horsepower="null";
$power="null";
$name="uaß";
$sql1="select category_id from category where category_name='categoryjava'";
$r1=mysqli_query($con,$sql1);
$result1=mysqli_fetch_array($r1);
$categoryid=0;
//$image=;

$path ="uploads/$productname.1.png";
$actualpath1 = "http://192.168.43.181/Android/CRUD/$path";
file_put_contents($path,base64_decode($image));

$path = "uploads/$productname.2.png";
$actualpath2 = "http://192.168.43.181/Android/CRUD/$path";
file_put_contents($path,base64_decode($image1));

$path = "uploads/$productname.3.png";
$actualpath3 = "http://192.168.43.181/Android/CRUD/$path";
file_put_contents($path,base64_decode($image2));

$imagepath1=$actualpath1;
$imagepath2=$actualpath2;
$imagepath3=$actualpath3;

$sql3="select max(product_id) as max from product";
$result5=mysqli_query($con,$sql3);
$rr=mysqli_fetch_array($result5);
$id=$rr['max'];
$id=$id+1;

$sql2="insert into product(product_id, category_id, product_name,upload_date, brand, model_name, height, width, weight, color, battery_life, os, battery_voltage, optical_zoom, 
usb_port, storage_capacity, vehicle_compatibility, horse_power, image_path1, image_path2, image_path3, description, price, speed, power)values('$id','$categoryid','$productname',
'$datee','$brand','$modelname','$height','$width','$weight','$color','$batterylife','$os','$batteryvoltage','$opticalzoom','$usbport',
'$storagecapacity','$vehiclecompatibility','$horsepower','$imagepath1','$imagepath2','$imagepath3','$description','$price','$speed','$power')";

mysqli_query($con,$sql2);
echo "successfully done";
?>