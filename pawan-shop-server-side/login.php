<?php
require_once('dbConnect.php');
$email=$_POST['user1'];
$password=$_POST['pass1'];
$sql="SELECT * FROM registration WHERE email='$email' AND password='$password'";
$r= mysqli_query($con,$sql);
$count= mysqli_num_rows($r);

if($count==1)
{	

$result=array();
$row= mysqli_fetch_array($r);
$user_id=$row['user_id'];
array_push($result, array("message"=>1));
array_push($result, array("user_id"=>$user_id));


echo json_encode(array('result'=>$result));
}
else
{
$result=array();
$row= mysqli_fetch_array($r);
array_push($result, array("message"=>0));
echo json_encode(array('result'=>$result));	
}
mysqli_close($con);
?>























































