<?php
header("Content-type: text/html;  charset=utf-8");
/*
 * code = 0 操作成功
 * code =10009 传递参数错误
 * code =10000 默认编码
 * code =10005 尚未收藏
 * code =10006 收藏列表为空
 * code =10007 已收藏
 */

class artical{

	var $title;
	var $author;
	var $source;
	var $image_path;
	var $detail_path;

	function __construct(){}
}

function var_json($info='', $code=10000, $data=array()){

	$out['code'] = $code ?: 0;
	$out['info'] = $info ?: ($out['code'] ? 'error' : 'success');
	$out['collections'] = $data ?: array();
	header('Content-Type: application/json; charset=utf-8');
	echo json_encode($out, JSON_HEX_TAG);
	$GLOBALS['conn']->close();
	exit(0);
}

function if_artical_exists($detail_path){

	$sql="SELECT aid FROM md_artical WHERE detail_path='".$detail_path."'";
	$result=$GLOBALS['conn']->query($sql);

    // echo "result: ".$result->num_rows;
	if($result->num_rows>0){
		return true;
	}

	return false;
}

function if_collection_exists($uid, $detail_path){

    $sql="SELECT detail_path FROM md_collection WHERE uid='".$uid."'";
    $result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
    	# code...
    	while ($row=$result->fetch_assoc()) {
    		# code...
    		if ($row["detail_path"]==$detail_path) {
    			# code...
    			return true;
    		}
    	}
    	// return true;
    }

    return false;
}

function if_collection_abandon($uid, $detail_path){

	$sql="SELECT can_use FROM md_collection WHERE uid='".$uid."' AND detail_path='".$detail_path."'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		# code...
		while ($row=$result->fetch_assoc()) {
			# code...
			if ($row["can_use"]==1) {
				# code...
				// echo $row["can_use"];
				return true;
			}
		}
	}

	return false;
}

function get_collection_items($uid){

	$sql="SELECT * FROM md_artical WHERE detail_path IN (SELECT detail_path FROM md_collection WHERE uid='".$uid."' AND can_use='0') AND can_use='0'";
	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		# code

		$arr=array();
		while ( $row=$result->fetch_assoc()) {
			# code...
			// echo $row["title"]."\n";
	        $arr[]=$row;
		}

		var_json("success",0,$arr);
	}else{
		var_json("collection empty",10006);
	}
}

function update_collection_item($uid, $detail_path, $value){

	$sql="UPDATE md_collection SET can_use='".$value."' WHERE uid='".$uid."' AND detail_path='".$detail_path."'";
	$retval = mysqli_query($GLOBALS['conn'],$sql);
	if(! $retval )
	{
		die('Could not update data: ' . mysqli_errno($GLOBALS['conn']));
	}
}

function remove_collection_item($uid, $art){

    if (if_collection_exists($uid, $art->detail_path)) {
    	# code...
    	if(!if_collection_abandon($uid, $art->detail_path)){
            update_collection_item($uid, $art->detail_path , 1);
    	}
    }else{
        var_json("not collected",10005);
    }
	
	get_collection_items($uid);
}

function add_collection_item($uid,$art){

	if(if_collection_exists($uid, $art->detail_path)){
        # code...
		if(if_collection_abandon($uid, $art->detail_path)){
			update_collection_item($uid, $art->detail_path, 0);
		}else{
			var_json("has collected",10007);
		}
	}else{

		if(!if_artical_exists($art->detail_path)){
			//将被收藏文章加入数据库
            $sql="INSERT INTO md_artical(title,author,source,image_path,detail_path) 
            VALUES ('".$art->title."','".$art->author."','".$art->source."','".$art->image_path."','".$art->detail_path."')";

            if($GLOBALS['conn']->query($sql)===TRUE){

            }else{
            	die ("Could not insert to data: ". mysqli_error($GLOBALS['conn']));
            }
		}

		$sql="INSERT INTO md_collection(uid, detail_path) VALUES ('".$uid."','".$art->detail_path."')";
      	if ($GLOBALS['conn']->query($sql) === TRUE) {
			get_collection_items($uid);
		} else {
			die ("Could not insert to data: ". mysqli_error($GLOBALS['conn']));
		}
	}

	get_collection_items($uid);
}


$action=empty($_GET['action'])?'':$_GET['action'];
$uid=empty($_GET['uid'])?'':$_GET['uid'];
$artical=empty($_GET['artical'])?'':$_GET['artical'];//json

$art=new artical();
$art=json_decode($artical);

//Mysqli连接
$conn = mysqli_connect('localhost', 'root', '' , 'md_db');
if (!$conn) {
	die("数据库连接错误" . mysqli_connect_error());
} 

switch ($action) {
	case 'query':
		# code...
	    get_collection_items($uid);
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