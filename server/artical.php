<?php
header("Content-type: text/html; charset=utf-8");  
/*
 * code = 0 操作成功
 * code =10009 传递参数错误
 * code =10000 默认编码
 * code =10008 查询结果为空
 */
function var_json($info='', $code=10000, $data=array()){

	$out['code'] = $code ?: 0;
	$out['info'] = $info ?: ($out['code'] ? 'error' : 'success');
	$out['artical'] = $data ?: array();
	header('Content-Type: application/json; charset=utf-8');
	echo json_encode($out, JSON_HEX_TAG);
	$GLOBALS['conn']->close();
	exit(0);
}

function get_latest_articals(){

	$sql="SELECT * FROM md_artical WHERE can_use='0'";
	$result=$GLOBALS['conn']->query($sql);

	if($result->num_rows>0){

        $current_date=date("Y-m-d");
        $arr=array();
		while ($row=$result->fetch_assoc()){

            // echo "sql_date: ".substr($row["create_time"], 0, 10).",current_date: ".$current_date;
			if (substr($row["create_time"], 0, 10)==$current_date) {
				# code...
				$arr[]=$row;
			}
			
		}

		var_json("success",0,$arr);
	} else{
		var_json("empty artcal list",10008);
	}
}

function get_before_articals($date){

	$sql="SELECT * FROM md_artical WHERE can_use='0'";
	$result=$GLOBALS['conn']->query($sql);

	if($result->num_rows>0){

        $date=date("Y-m-d",strtotime($date." -1 days"));
        // echo "date: ".$date;
        $arr=array();
		while ($row=$result->fetch_assoc()){
			if (substr($row["create_time"], 0, 10)==$date) {
				# code...
				$arr[]=$row;
			}
			
		}

		var_json("success",0,$arr);
	} else{
		var_json("empty artcal list",10008);
	}
}

$action=empty($_GET['action'])?'':$_GET['action'];
$date=empty($_GET['date'])?'':$_GET['date'];

//Mysqli连接
$conn = mysqli_connect('localhost', 'root', '' , 'md_db');
if (!$conn) {
	die("数据库连接错误" . mysqli_connect_error());
} 

switch ($action) {
	case 'latest':
		# code...
	    get_latest_articals();
		break;
	case 'before':
		# code...
	    get_before_articals($date);
		break;
	
	default:
		# code...
	    var_json("request error",10009);
		break;
}

?>