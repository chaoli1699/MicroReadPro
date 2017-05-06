<?php

function add_message_item($source, $aim, $acid, $accid){

	$sql="INSERT INTO md_message(source_uid, aim_uid, acid, accid, status) VALUES ('".$source."', '".$aim."' ,'".$acid."', '".$accid."', '1')";

	if ($GLOBALS['conn']->query($sql)===TRUE) {
		
	}else{
		die("Could not insert data: ". mysqli_error($GLOBALS['conn']));
	}
}

function update_message_status($mid){

	$sql="UPDATE md_message SET status=0 WHERE mid='".$mid."'";

	$retval=mysqli_query($GLOBALS['conn'], $sql);
	
	if(! $retval )
	{
		die ("Could not update data: " . mysqli_error($GLOBALS['conn']));
	}
}

function get_message_count($uid){

	$sql="SELECT * FROM md_message WHERE aim_uid='".$uid."' AND status='1' AND can_use='0'";
	$result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
       return $result->num_rows;
    }
}
?>