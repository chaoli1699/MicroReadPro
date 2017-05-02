<?php  include 'model/articalClass.php';
header("Content-type: text/html; charset=utf-8");  
/*
 * code = 0 操作成功
 * code =10009 传递参数错误
 * code =10000 默认编码
 * code =10010 查询评论列表为空
 */
function var_json($info='', $code=10000, $com_count=0, $data=array()){

	$out['code'] = $code ?: 0;
	$out['info'] = $info ?: ($out['code'] ? 'error' : 'success');
	$out['com_count']=$com_count ?:$com_count;
	$out['comments'] = $data ?: array();
	header('Content-Type: application/json; charset=utf-8');
	echo json_encode($out, JSON_HEX_TAG);
	$GLOBALS['conn']->close();
	exit(0);
}

function time_to_now($uid, $aid, $tab){

    $begin_time=get_comment_time($uid, $aid, $tab);
	$end_time=date("Y-m-d H:i:s");

	$timediff = strtotime($end_time)-strtotime($begin_time);
	$days = intval( $timediff / 86400 );
	$remain = $timediff % 86400;
	$hours = intval( $remain / 3600 );
	$remain = $remain % 3600;
	$mins = intval( $remain / 60 );
	$secs = $remain % 60;

	if($days>0){
		return $days."天前";
	}

	if($hours>0){
		return $hours."小时前";
	}

	if($mins>0){
		return $mins."分钟前";
	}

	if($secs>0){
		return "刚刚";
	}

	return "发送中";
}

function get_user_name($uid){

	$sql="SELECT username FROM md_user WHERE uid='".$uid."'";
    $result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
    	
    	while ($row=$result->fetch_assoc()) {
    		return $row["username"];
    	}
    }else{
    	var_json("user not exists",10001);
    }
}

function if_artical_exists($detail_path){

	$sql="SELECT aid FROM md_artical WHERE detail_path='".$detail_path."'";
	$result=$GLOBALS['conn']->query($sql);

     //echo "result: ".$result->num_rows;
	if($result->num_rows>0){
		return true;
	}

	return false;
}

function get_artical_com_count($aid){ 

	$sql="SELECT com_count FROM md_artical WHERE aid='".$aid."'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		
		while ($row=$result->fetch_assoc()) {
			return $row["com_count"];	
		}
	}

	return 0;
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

function get_artical_aid($detail_path){
	$sql="SELECT aid FROM md_artical WHERE detail_path='".$detail_path."'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		
		while ($row=$result->fetch_assoc()) {
			return $row["aid"];	
		}
	}

	return null;
}

function get_artical_aid_wacid($acid){
	$sql="SELECT aid FROM md_comment WHERE acid='".$acid."'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		
		while ($row=$result->fetch_assoc()) {
			return $row["aid"];	
		}
	}

	return null;
}

function get_comment_time($uid, $acid, $tab){
	$sql="SELECT com_time FROM ".$tab." WHERE uid='".$uid."' AND acid='".$acid."'";
    $result=$GLOBALS['conn']->query($sql);

    if($result->num_rows>0){

    	while ($row=$result->fetch_assoc()) {
    		return $row["com_time"];
    	}
    }

    return "";
}

function add_moment_item($uid, $comment){   //新增时光轴状态

	$sql="INSERT INTO md_comment(aid, uid, comment) VALUES ('-".$uid."','".$uid."','".$comment."')";
	if ($GLOBALS['conn']->query($sql)===TRUE) {
		get_moment_items();
	}else{
		die ("Could not insert data: ". mysqli_error($GLOBALS['conn']));
	}
}

function get_moment_items(){  //获取时光轴列表
    
    $sql="SELECT acid, uid, comment, com_time FROM md_comment WHERE aid<0 AND can_use='0' ORDER BY com_time DESC";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		
		$arr=array();
		while ($row=$result->fetch_assoc()) {
			$arr[]=array('acid'=>$row["acid"],
			 'username'=>get_user_name($row["uid"]),
			 'comment'=>$row["comment"],
			 'time_to_now'=>time_to_now($row["uid"], $row['acid'], "md_comment") ,
			 'child_com'=>get_childcom_items($row["acid"]));
		}

		var_json("success", 0 , 0, $arr);
	}else{
		var_json("no comments",10010);
	}
}

function move_moment_too_of_trash($acid, $too){ //回收站
	$sql="UPDATE md_comment SET can_use='".$too."' WHERE acid='".$acid."'";

	$retval = mysqli_query($GLOBALS['conn'],$sql);
	get_moment_items();
	if(! $retval )
	{
		die ("Could not update data: " . mysqli_error($GLOBALS['conn']));
	}
}

function get_personal_moment_items($uid, $can_use){  //获取个人时光轴状态/回收站列表

	$sql="SELECT acid, uid, comment, com_time FROM md_comment WHERE aid='-".$uid."' AND can_use='".$can_use."' ORDER BY com_time DESC";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		
		$arr=array();
		while ($row=$result->fetch_assoc()) {		
			$arr[]=array('acid'=>$row["acid"],
				'username'=>get_user_name($row["uid"]),
				'comment'=>$row["comment"],
				'time_to_now'=>time_to_now($row["uid"], $row['acid'], "md_comment") , 
				'child_com'=>get_childcom_items($row["acid"]));
		}
		var_json("success", 0 , 0, $arr);
	}else{
		var_json("no comments",10010);
	}
}

function add_childcom_item($acid, $uid, $comment){  //新增子评论

    $aid=get_artical_aid_wacid($acid);
    update_artical_com_count($aid, get_artical_com_count($aid));

	$sql="INSERT INTO md_childcom(acid, uid, comment) VALUES ('".$acid."','".$uid."','".$comment."')";
	if ($GLOBALS['conn']->query($sql)===TRUE) {
	
		if ($aid<0) {
			get_moment_items();
		}else{
			get_comment_items($aid);
		}
		
	}else{
		die ("Could not insert data: ". mysqli_error($GLOBALS['conn']));
	}
}

function get_childcom_items($acid){  //获取子评论

	$sql="SELECT accid, uid, comment, com_time FROM md_childcom WHERE acid='".$acid."' AND can_use='0' ORDER BY com_time";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		
		$arr=array();
		while ($row=$result->fetch_assoc()) {
			$arr[]=array('accid'=>$row["accid"],'username'=>get_user_name($row["uid"]),'comment'=>$row["comment"], 'time_to_now'=>time_to_now($row["uid"], $row['accid'], "md_childcom"));
		}
		
		return $arr;
	}else{
		return array();
	}
}

function add_comment_item($art, $uid, $comment){  //新增文章评论

	if (!if_artical_exists($art->detail_path)) {
	
		//将被收藏文章加入数据库
        $sql="INSERT INTO md_artical(title, author, source, atid, image_path, detail_path) 
        VALUES ('".$art->title."','".$art->author."','".$art->source."', '".$art->atid."', 
        '".$art->image_path."','".$art->detail_path."')";

        if($GLOBALS['conn']->query($sql)===TRUE){

        }else{
            die ("Could not insert to data: ". mysqli_error($GLOBALS['conn']));
        }
	}

    $aid=get_artical_aid($art->detail_path);
	update_artical_com_count($aid, get_artical_com_count($aid));

	$sql="INSERT INTO md_comment(aid, uid, comment) VALUES ('".$aid."','".$uid."','".$comment."')";
	if ($GLOBALS['conn']->query($sql)===TRUE) {
	
		get_comment_items($aid);
	
	}else{
		die ("Could not insert data: ". mysqli_error($GLOBALS['conn']));
	}
}

function get_comment_items($aid){   //获取文章评论列表

	$sql="SELECT acid, uid, comment, com_time FROM md_comment WHERE aid='".$aid."' AND can_use='0' ORDER BY com_time DESC";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {

		$arr=array();
		while ($row=$result->fetch_assoc()) {
			$arr[]=array('acid'=>$row["acid"],'username'=>get_user_name($row["uid"]),'comment'=>$row["comment"], 'time_to_now'=>time_to_now($row["uid"], $row['acid'], "md_comment")
				, 'child_com'=>get_childcom_items($row["acid"])
				);
		}

		var_json("success", 0, get_artical_com_count($aid), $arr);
	}else{
		var_json("no comments", 10010);
	}
}

$action=empty($_GET['action'])?'':$_GET['action'];
$detail_path=empty($_GET['detail_path'])?'':$_GET['detail_path'];
$artical=empty($_GET['artical'])?'':$_GET['artical'];
$acid=empty($_GET['acid'])?'':$_GET['acid'];
$uid=empty($_GET['uid'])?'':$_GET['uid'];
$comment=empty($_GET['comment'])?'':$_GET['comment'];
$too=empty($_GET['too'])?'':$_GET['too'];
$can_use=empty($_GET['can_use'])?'':$_GET['can_use'];

//Mysqli连接
$conn = mysqli_connect('localhost', 'root', '' , 'md_db');
if (!$conn) {
	die("数据库连接错误" . mysqli_connect_error());
} 

$art=new artical;
$art=json_decode($artical);

switch ($action) {
	case 'query':
	    if (if_artical_exists($detail_path)) {
	    	get_comment_items(get_artical_aid($detail_path));
	    }else {
	    	var_json("no comments",10010);
	    }
	    
		break;
	case 'querym':
	    get_moment_items();
		break;
	case 'querypm':
        get_personal_moment_items($uid, $can_use);
		break;
	case 'add':
	    add_comment_item($art, $uid, $comment);
		break;
	case 'addc':
	    add_childcom_item($acid, $uid, $comment);
		break;
	case 'addm':
        add_moment_item($uid, $comment);
		break;
	case 'mmtot':
	    move_moment_too_of_trash($acid, $too);
        break;
	default:
	    var_json("request error", 10009);
		break;
}
?>