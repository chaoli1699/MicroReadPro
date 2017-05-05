<?php  include 'funs/fun_comment.php';
  include 'funs/fun_user.php';

header("Content-type: text/html; charset=utf-8");
function var_json($info='', $code=10000, $data=array()){
	$out['code']=$code ?: 0;
	$out['info']=$info ?: ($out['code'] ? 'error': 'successs');
	$out['messages']=$data ?: array();
	header('Content-type: application/json; charset=utf-8');
	echo json_encode($out, JSON_HEX_TAG);
	$GLOBALS['conn']->close();
	exit(0);
}

function get_message_items($uid){

	$sql="SELECT * FROM md_message WHERE aim_uid='".$uid."' AND can_use='0'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		
		$arr=array();
		while ($row=$result->fetch_assoc()) {
			$arr[]=array('mid'=>$row["mid"],
			 'acid'=>$row["acid"],
			 'username'=>get_user_name_wuid($row["source_uid"]),
			 'comment'=>get_comment_wacid($row["acid"]),
			 'time_to_now'=>time_to_now($row["source_uid"], $row['acid'], "md_childcom"));
		}

		var_json("success", 0, $arr);
	}
}

$action=empty($_GET['action'])?'':$_GET['action'];
$uid=empty($_GET['uid'])?'':$_GET['uid'];

//Mysqli连接
$conn = mysqli_connect('localhost', 'root', '' , 'md_db');
if (!$conn) {
	die("数据库连接错误" . mysqli_connect_error());
} 

switch ($action) {
	case 'query':
		get_message_items($uid);
		break;
	
	default:
		# code...
		break;
}

?>