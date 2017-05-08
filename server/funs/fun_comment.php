<?php

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
		if ($days>5) {
			return  date_format(date_create($begin_time), "Y年m月d日 H:i");
		}
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

function get_comment_time($uid, $acid, $tab){

	if ($tab=="md_comment") {
		$lab="acid";
	}else if ($tab=="md_childcom") {
		$lab="accid";
	}

	$sql="SELECT com_time FROM ".$tab." WHERE uid='".$uid."' AND ".$lab."='".$acid."'";
    $result=$GLOBALS['conn']->query($sql);

    if($result->num_rows>0){

    	while ($row=$result->fetch_assoc()) {
    		return $row["com_time"];
    	}
    }

    return "";
}

function create_childcom_accid(){

	$pre_uid=rand(10000000,99999999);
	$sql="SELECT accid FROM md_childcom LIMIT 10000000,99999999";
	$result=$GLOBALS['conn']->query($sql);
	
	if ($result->num_rows>0) {
		while ($row=$result->fetch_assoc()){
			if ($row["accid"]==$pre_uid){
				return -1;
			}
		}
	}
	return $pre_uid;
} 

function get_comment_waccid($accid){

	$sql="SELECT * FROM md_childcom WHERE accid='".$accid."' AND can_use='0'";

	$result=$GLOBALS['conn']->query($sql);

	if ($result->num_rows>0) {
		while ($row=$result->fetch_assoc()) {
			return $row["comment"];
		}
	}

	return "";
}
?>