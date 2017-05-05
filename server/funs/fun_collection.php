<?php

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
?>