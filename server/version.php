<?php
header("Content-type: text/html; charset=utf-8");  

function var_json($info='', $code=10000, $data=array()){

	$out['code'] = $code ?: 0;
	$out['info'] = $info ?: ($out['code'] ? 'error' : 'success');
	$out['version'] = $data ?: array();
	header('Content-Type: application/json; charset=utf-8');
	echo json_encode($out, JSON_HEX_TAG);
	$GLOBALS['conn']->close();
	exit(0);
}

function get_latest_version_info(){

	$sql="SELECT vid, version_code, version_name, introduce, download_path FROM md_version ORDER BY vid LIMIT 1";
	$result=$GLOBALS['conn']->query($sql);

	if($result->num_rows>0){

		while ($row=$result->fetch_assoc()){
			var_json("success",0,$row);
		}
	} 
}

$action=empty($_GET['action'])?'':$_GET['action'];

//Mysqli连接
$conn = mysqli_connect('localhost', 'root', '' , 'md_db');
if (!$conn) {
	die("数据库连接错误" . mysqli_connect_error());
} 

switch ($action) {
	case 'latest':
		# code...
	    get_latest_version_info();
		break;
	
	default:
		# code...
	    var_json("request error",10009);
		break;
}


?>