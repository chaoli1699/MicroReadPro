<?php
header("Content-type: text/html; charset=utf-8");
function var_json($info='', $code=10000, $data=array()){
	$out['code']=$code ?: 0;
	$out['info']=$info ?: ($out['code'] ? 'error': 'successs');
	$out['ufetures']=$data ?: array();
	header('Content-type: application/json; charset=utf-8');
	echo json_encode($out, JSON_HEX_TAG);
	$GLOBALS['conn']->close();
	exit(0);
}

function get_user_name_wuid($uid){

	$sql="SELECT username FROM md_user WHERE uid='".$uid."' AND can_use='0'";
    $result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
    	while ($row=$result->fetch_assoc()) {
    		return $row['username'];
    	}
    }

    return "";
}

function get_user_sex_wuid($uid){

	$sql="SELECT sex FROM md_user WHERE uid='".$uid."' AND can_use='0'";
    $result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
    	while ($row=$result->fetch_assoc()) {
    		return $row['sex'];
    	}
    }

    return -1;
}

function get_user_role_wuid($uid){

	$sql="SELECT role FROM md_user WHERE uid='".$uid."' AND can_use='0'";
    $result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
    	while ($row=$result->fetch_assoc()) {
    		return $row['role'];
    	}
    }

    return -1;
}

function get_ufeture_items($uid){
	
	$sql="SELECT * FROM md_ufeture WHERE can_use='0'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {

		$arr=array();
		while ($row=$result->fetch_assoc()) {
			$username="";
            $head_path="";
            $notify_num=0;

			if ($row['ufid']==1) {
				//get head_img form md_user
				$username=get_user_name_wuid($uid);

				if (get_user_sex_wuid($uid)>0) {
					$head_path="/img/male.png";
				}else {
					$head_path="/img/female.png";
				}
			}

			if ($row['ufid']==4) {
				//get notify_num from message
			}

			if (get_user_role_wuid($uid)<5&&$row['name_eg']=="trash") {
				continue;
			}

			$arr[]=array('ufid'=>$row['ufid'],
				'name_eg'=>$row['name_eg'],
				'name_cn'=>$row['name_cn'],
				'head_path'=>$head_path,
				'username'=>$username,
				'icon_path'=>$row['icon_path'],
				'show_rrow'=>$row['show_rrow'],
				'is_button'=>$row['is_button'],
				'notify_num'=>$notify_num,
				'group'=>$row['group'],
				'group_top'=>$row['group_top']);
		}

	    var_json("success", 0, $arr);
	}
	
}

$action=empty($_GET['action'])? "":$_GET['action'];
$uid=empty($_GET['uid'])? "":$_GET['uid'];

$conn = mysqli_connect('localhost', 'root', '', 'md_db');
if (!$conn) {
	die("数据库链接错误".mysqli_connect_error());
}

switch ($action) {
	case 'load':
		get_ufeture_items($uid);
		break;
	
	default:

		break;
}

?>