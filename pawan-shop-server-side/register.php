<?php
require_once('dbConnect.php');
$name=$_POST['name'];
$email=$_POST['email'];
$password=$_POST['password'];
$phone=$_POST['phone'];
$address=$_POST['address'];
$pincode=$_POST['pincode'];

$sql1="select max(user_id) as max from registration";

$r1=mysqli_query($con,$sql1);
$r2=mysqli_fetch_array($r1);

$max=$r2['max']+1;

$sql2="insert into registration (user_id,name, email, password, address, phone,pincode) values('$max','$name','$email','$password','$address','$phone','$pincode')";
mysqli_query($con,$sql2);
?>