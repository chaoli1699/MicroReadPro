<?php include 'model/articalClass.php';
header("Content-type: text/html;  charset=utf-8");
/*
 * code = 0 操作成功
 * code =10009 传递参数错误
 * code =10000 默认编码
 * code =10005 尚未收藏
 * code =10006 收藏列表为空
 * code =10007 已收藏
 */
function var_json($info='', $code=10000, $data=array()){

	$out['code'] = $code ?: 0;
	$out['info'] = $info ?: ($out['code'] ? 'error' : 'success');
	$out['collections'] = $data ?: array();
	header('Content-Type: application/json; charset=utf-8');
	echo json_encode($out, JSON_HEX_TAG);
	$GLOBALS['conn']->close();
	exit(0);
}

function time_to_now($uid, $aid){

	$begin_time=get_collection_time($uid, $aid);
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

function if_artical_exists($detail_path){

	$sql="SELECT aid FROM md_artical WHERE detail_path='".$detail_path."'";
	$result=$GLOBALS['conn']->query($sql);

	if($result->num_rows>0){
		return true;
	}

	return false;
}

function get_artical_aid($detail_path){

    $sql="SELECT aid FROM md_artical WHERE detail_path='".$detail_path."'";
    $result=$GLOBALS['conn']->query($sql);

    if($result->num_rows>0){
    	while ($row=$result->fetch_assoc()) {
    		return $row["aid"];
    	}
    }

    return "-1";
}

function if_collection_exists($uid, $aid){

    $sql="SELECT aid FROM md_collection WHERE uid='".$uid."'";
    $result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
    	while ($row=$result->fetch_assoc()) {
    		if ($row["aid"]==$aid) {
    			return true;
    		}
    	}
    }

    return false;
}

function if_collection_abandon($uid, $aid){

	$sql="SELECT can_use FROM md_collection WHERE uid='".$uid."' AND aid='".$aid."'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		while ($row=$result->fetch_assoc()) {
			if ($row["can_use"]==1) {
				return true;
			}
		}
	}

	return false;
}

function get_collection_time($uid, $aid){
	$sql="SELECT col_time FROM md_collection WHERE uid='".$uid."' AND aid='".$aid."'";
    $result=$GLOBALS['conn']->query($sql);

    if($result->num_rows>0){
    	while ($row=$result->fetch_assoc()) {
    		return $row["col_time"];
    	}
    }

    return "";
}

function update_collection_item($uid, $aid, $value){

	$sql="UPDATE md_collection SET can_use='".$value."' WHERE uid='".$uid."' AND aid='".$aid."'";
	$retval = mysqli_query($GLOBALS['conn'],$sql);
	if(! $retval )
	{
		die('Could not update data: ' . mysqli_errno($GLOBALS['conn']));
	}
}

function get_collection_items($uid, $atid){

	$sql="SELECT * FROM md_artical WHERE aid IN (SELECT aid FROM md_collection WHERE uid='".$uid."' AND can_use='0') AND atid='".$atid."' AND can_use='0'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		$arr=array();
		while ( $row=$result->fetch_assoc()) {
	        $arr[]=array('aid'=>$row["aid"], 
	        	'title'=>$row["title"], 
	        	'author'=>$row["author"] , 
	        	'source'=>$row["source"], 
	        	'atid'=>$atid, 
	        	'image_path'=>$row["image_path"], 
	        	'detail_path'=>$row["detail_path"], 
	        	'content'=>$row["content"], 
	        	'com_count'=>$row["com_count"], 
	        	'time_to_now'=>time_to_now($uid, $row["aid"]));
		}

		var_json("success",0,$arr);
	}else{
		var_json("collection empty",10006);
	}
}

function remove_collection_item($uid, $art){

    $aid=get_artical_aid($art->detail_path);
    if (if_collection_exists($uid, $aid)) {

    	if(!if_collection_abandon($uid, $aid)){
            update_collection_item($uid, $aid, 1);
    	}
    }else{
        var_json("not collected",10005);
    }
	
	get_collection_items($uid, $art->atid);
}

function add_collection_item($uid,$art){

    $aid=get_artical_aid($art->detail_path);
	if(if_collection_exists($uid, $aid)){
       
		if(if_collection_abandon($uid, $aid)){
			update_collection_item($uid, $aid, 0);
		}else{
			var_json("has collected",10007);
		}
	}else{

		if(!if_artical_exists($art->detail_path)){
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
		$sql="INSERT INTO md_collection(uid, aid) VALUES ('".$uid."','".$aid."')";
      	if ($GLOBALS['conn']->query($sql) === TRUE) {
			get_collection_items($uid ,$art->atid);
		} else {
			die ("Could not insert to data: ". mysqli_error($GLOBALS['conn']));
		}
	}

	get_collection_items($uid, $art->atid);
}


$action=empty($_GET['action'])?'':$_GET['action'];
$uid=empty($_GET['uid'])?'':$_GET['uid'];
$atid=empty($_GET['atid'])?'':$_GET['atid'];
$artical=empty($_GET['artical'])?'':$_GET['artical'];//json

$art=new artical;
$art=json_decode($artical);

//Mysqli连接
$conn = mysqli_connect('localhost', 'root', '' , 'md_db');
if (!$conn) {
	die("数据库连接错误" . mysqli_connect_error());
} 

switch ($action) {
	case 'query':
		# code...
	    get_collection_items($uid, $atid);
		break;
	case 'add':
		# code...
	    add_collection_item($uid, $art);
		break;
	case 'remove':
		# code...
	    remove_collection_item($uid, $art);
		break;
	default:
		# code...
	    var_json("request error",10009);
		break;
}

?>