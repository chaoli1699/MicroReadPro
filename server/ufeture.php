<?php include 'funs/fun_user.php';
  include 'funs/fun_message.php';

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

function get_ufeture_items_wgid($gid){
	$sql="SELECT * FROM md_ufeture WHERE can_use='0' AND group_id=$gid";
	$result=$GLOBALS['conn']->query($sql);
    
	if ($result->num_rows>0) {
		$arr=array();
		while ($row=$result->fetch_assoc()) {
			$arr[]=array('ufid'=>$row['ufid'],
				'name_eg'=>$row['name_eg'],
				'name_cn'=>$row['name_cn'],
				'head_path'=>"",
				'username'=>"",
				'icon_path'=>$row['icon_path'],
				'show_rrow'=>$row['show_rrow'],
				'is_button'=>$row['is_button'],
				'notify_num'=>0,
				'group'=>$row['group_id'],
				'group_top'=>$row['group_top']);
		}

		var_json("success", 0, $arr);
	}
}

function get_ufeture_items_wuid($uid){
	
	$sql="SELECT * FROM md_ufeture WHERE can_use='0' AND group_id<10";
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
			if ($row['ufid']==6) {
				//get notify_num from message
				$notify_num=get_message_count($uid);		   
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
				'group'=>$row['group_id'],
				'group_top'=>$row['group_top']);
		}

	    var_json("success", 0, $arr);
	}
	
}

$action=empty($_GET['action'])? "":$_GET['action'];
$uid=empty($_GET['uid'])? "":$_GET['uid'];
$gid=empty($_GET['gid'])? "":$_GET['gid'];

$conn = mysqli_connect('localhost', 'root', '', 'md_db');
if (!$conn) {
	die("数据库链接错误".mysqli_connect_error());
}

switch ($action) {
	case 'load':
		get_ufeture_items_wuid($uid);
		break;
	case 'group':
		get_ufeture_items_wgid($gid);
		break;
	default:

		break;
}

?>