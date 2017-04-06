<?php  
header("Content-type: text/html; charset=utf-8");  
/*
 * code = 0 操作成功
 * code =10009 传递参数错误
 * code =10000 默认编码
 * code =10010 查询评论列表为空
 */
function var_json($info='', $code=10000, $data=array()){

	$out['code'] = $code ?: 0;
	$out['info'] = $info ?: ($out['code'] ? 'error' : 'success');
	$out['comments'] = $data ?: array();
	header('Content-Type: application/json; charset=utf-8');
	echo json_encode($out, JSON_HEX_TAG);
	$GLOBALS['conn']->close();
	exit(0);
}

function get_artical_comments($aid){

	$sql="SELECT acid, uid, comment FROM md_artcomment WHERE aid='".$aid."' AND can_use='0'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		# code...
		$arr=array();
		while ($row=$result->fetch_assoc()) {
			# code...
			$arr[]=array('acid'=>$row["acid"],'username'=>get_user_name($row["uid"]),'comment'=>$row["comment"]);
		}

		var_json("success",0,$arr);
	}else{
		var_json("no comments",10010);
	}
}

function add_artical_comment($aid, $uid, $comment){

	update_artical_com_count($aid, get_artical_com_count($aid));

	$sql="INSERT INTO md_artcomment(aid, uid, comment) VALUES ('".$aid."','".$uid."','".$comment."')";
	if ($GLOBALS['conn']->query($sql)===TRUE) {
		# code...
		get_artical_comments($aid);
	}else{
		die ("Could not insert data: ". mysqli_error($GLOBALS['conn']));
	}
}

function update_artical_com_count($aid, $com_count){

    $com_count++;
    $sql="UPDATE md_artical SET com_count='".$com_count."' WHERE aid='".$aid."'";

    $retval = mysqli_query($GLOBALS['conn'],$sql);
	if(! $retval )
	{
		die ("Could not update data: " . mysqli_error($GLOBALS['conn']));
	}
}

function get_artical_com_count($aid){

	$sql="SELECT com_count FROM md_artical WHERE aid='".$aid."'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		# code...
		while ($row=$result->fetch_assoc()) {
			# code...
			return $row["com_count"];	
		}
	}
}

function get_user_name($uid){

	$sql="SELECT username FROM md_user WHERE uid='".$uid."'";
    $result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
    	# code...
    	while ($row=$result->fetch_assoc()) {
    		# code...
    		return $row["username"];
    	}
    }else{
    	var_json("user not exists",10001);
    }
}

$action=empty($_GET['action'])?'':$_GET['action'];
$aid=empty($_GET['aid'])?'':$_GET['aid'];
$uid=empty($_GET['uid'])?'':$_GET['uid'];
$comment=empty($_GET['comment'])?'':$_GET['comment'];

//Mysqli连接
$conn = mysqli_connect('localhost', 'root', '' , 'md_db');
if (!$conn) {
	die("数据库连接错误" . mysqli_connect_error());
} 

switch ($action) {
	case 'query':
		# code...
	    get_artical_comments($aid);
		break;
	case 'add':
		# code...
	    add_artical_comment($aid, $uid, $comment);
		break;
	default:
		# code...
	    var_json("request error", 10009);
		break;
}
?>