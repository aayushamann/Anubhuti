<?php 
 
 //Getting the requested id
 $email = $_GET['email'];
 
 //Importing database
 require_once('dbAppConnect.php');
 
 $sql = "SELECT * FROM student WHERE email=$email";
 
 //getting result 
 $r = mysqli_query($con,$sql) or die('Error in chking'.mysqli_error($con));
 $count=mysqli_num_rows($r);
 
 //pushing result to an array 
 $result = array();
 
 if($count==0) {
	 $row = mysqli_fetch_array($r);
	 array_push($result,array(
	 "fb_id"=>"0",
	 "name"=>"0",
	 "email"=>"0"));
 }
 else {
	 $row = mysqli_fetch_array($r);
	 array_push($result,array(
	 "fb_id"=>$row['fb_id'],
	 "name"=>$row['name'],
	 "email"=>$row['email'],
	 "gender"=>$row['gender'],
	 "contact"=>$row['contact'],
	 "college"=>$row['college'],
	 "year"=>$row['year']
	 ));
 }
 
 //displaying in json format 
 echo json_encode(array('result'=>$result));
 
 mysqli_close($con);
?>	
