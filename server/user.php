<?php include 'model/userClass.php';
header("Content-type: text/html; charset=utf-8");

/*
 * code 0:操作成功
 * code 10009:传递参数错误
 * code 10000:默认code
 * code 10001:用户不存在(登录)
 * code 10002:密码错误
 * code 10003:用户已存在(注册)
 * code 10004:用户已弃用
 */
function var_json($info='', $code=10000, $data=array()){

	$out['code'] = $code ?: 0;
	$out['info'] = $info ?: ($out['code'] ? 'error' : 'success');
	$out['user'] = $data ?: array();
	header('Content-Type: application/json; charset=utf-8');
	echo json_encode($out, JSON_HEX_TAG);
	$GLOBALS['conn']->close();
	exit(0);
}

function if_user_exists($username){
	
	$sql="SELECT username FROM md_user";
	$result=$GLOBALS['conn']->query($sql);
	
	if ($result->num_rows>0) {
		# code...
		while ($row=$result->fetch_assoc()){
		    if ($row["username"]==$username){
			    return true;
		    }
	    }
	}
	return false;
}

function if_user_abandon($username){
	
	$sql="SELECT can_use FROM md_user WHERE username='".$username."'";
	$result=$GLOBALS['conn']->query($sql);
	
	if ($result->num_rows>0) {
		# code...
		while ($row=$result->fetch_assoc()){
		    if ($row["can_use"]==1){
			    return true;
		    }
	    }
	}
	return false;
}

function get_user_info($uid){
	
	$sql="SELECT uid, username, sex, last_login_time, district, introduce, role FROM md_user WHERE uid='".$uid."'";
	$result=$GLOBALS['conn']->query($sql);
	
	if ($result->num_rows>0){
		while ($row=$result->fetch_assoc()){
		    var_json("success",0,$row);
		}
	}else {
// 		echo "0 result.";
		var_json("user not exists",10001,new user);
	}
	
}

function create_user_uid(){

	$pre_uid=rand(1000,9999);
	$sql="SELECT uid FROM md_user LIMIT 0,9000";
	$result=$GLOBALS['conn']->query($sql);
	
	if ($result->num_rows>0) {
		while ($row=$result->fetch_assoc()){
			if ($row["uid"]==$pre_uid){
				return -1;
			}else {
				return $pre_uid;
			}
		}
	}
} 

function update_user_info($uid, $label, $value){
	
	$sql = "UPDATE md_user SET ".$label."='".$value."' WHERE uid='".$uid."'";
	
	$retval = mysqli_query($GLOBALS['conn'],$sql);
	if(! $retval )
	{
		die('Could not update data: ' . mysqli_errno($GLOBALS['conn']));
	}
	
	get_user_info($uid);
// 	echo "Updated data successfully!\n";
}

function verity_user($username,$password){
	
	if (if_user_exists($username)){
		$sql="SELECT uid, password FROM md_user WHERE username='".$username."' AND can_use='0'";
		$result=$GLOBALS['conn']->query($sql);
		
		if ($result->num_rows>0){
			while ($row=$result->fetch_assoc()){
				if ($row["password"]==$password){
					// 				echo "Verity pass!\n";
					$current_time=date("Y-m-d H:i:s");
					update_user_info($row["uid"],"last_login_time",$current_time);
				}else {
					// 				echo "Password wrong!\n";
					var_json("wrong password",10002,new user);
				}
			}
		}
	}else {
// 		echo "User not exists.\n";
		var_json("user not exist",10001,new user);
	}
}

function regist_user($username,$password){
	
	if (if_user_exists($username)){
// 		echo "user exists!"
		if (if_user_abandon($username)){
			var_json("user abandon",10004,new user);
		}else {
			var_json("user exists",10003,new user);
		}
		
	}else {
		$uid=-1;
		while ($uid==-1){
			$uid=create_user_uid();
		}
		
		$sql="INSERT INTO md_user(uid, username, password)
              VALUE('".$uid."','".$username."','".$password."')";

		if ($GLOBALS['conn']->query($sql) === TRUE) {
			get_user_info($uid);
		} else {
			die ("Could not insert to data: ". mysqli_error($GLOBALS['conn']));
		}
	}
}

$uid=empty($_GET['uid'])?'':$_GET['uid'];
$action=empty($_GET['action'])?'':$_GET['action'];
$username=empty($_GET['username'])?'':$_GET['username'];
$password=empty($_GET['password'])?'':$_GET['password'];
$sex=empty($_GET['sex'])?'':$_GET['sex'];
$city=empty($_GET['city'])?'':$_GET['city'];
$sign=empty($_GET['sign'])?'':$_GET['sign'];
$role=empty($_GET['role'])?'':$_GET['role'];

//Mysqli连接
$conn = mysqli_connect('localhost', 'root', '' , 'md_db');
if (!$conn) {
	die("数据库连接错误" . mysqli_connect_error());
} 

switch ($action) {
	case 'login':
		verity_user($username, $password);
		break;
	case 'regist':
		regist_user($username, $password);
		break;
	case 'abandon':
		update_user_info($uid, "can_use", 1);
		break;
	case 'reuse':
		update_user_info($uid, "can_use", 0);
		break;
	case 'chansex':
		update_user_info($uid, "sex", $sex);
		break;
	case 'chancity':
		update_user_info($uid, "district", $city);
		break;
	case 'chansign':
		update_user_info($uid, "introduce", $sign);
		break;
	case 'chanrole':
		update_user_info($uid, "role", $role);
		break;
	case 'channame':
	    update_user_info($uid, "username", $username);
		break;
	case 'chanpwd':
	    update_user_info($uid ,"password" ,$password);
		break;
	default:
		var_json("request error",10009,new user);
		break;
}

?>