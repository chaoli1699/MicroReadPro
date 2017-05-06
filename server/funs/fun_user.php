<?php 

function if_user_exists($username){
	
	$sql="SELECT username FROM md_user";
	$result=$GLOBALS['conn']->query($sql);
	
	if ($result->num_rows>0) {
		# code...
		while ($row=$result->fetch_assoc()){
		    if ($row["username"]==$username){
			    return true;
		    }
	    }
	}
	return false;
}

function if_user_abandon($username){
	
	$sql="SELECT can_use FROM md_user WHERE username='".$username."'";
	$result=$GLOBALS['conn']->query($sql);
	
	if ($result->num_rows>0) {
		# code...
		while ($row=$result->fetch_assoc()){
		    if ($row["can_use"]==1){
			    return true;
		    }
	    }
	}
	return false;
}

function create_user_uid(){

	$pre_uid=rand(1000,9999);
	$sql="SELECT uid FROM md_user LIMIT 0,9000";
	$result=$GLOBALS['conn']->query($sql);
	
	if ($result->num_rows>0) {
		while ($row=$result->fetch_assoc()){
			if ($row["uid"]==$pre_uid){
				return -1;
			}else {
				return $pre_uid;
			}
		}
	}
} 

function get_user_name_wuid($uid){

	$sql="SELECT username FROM md_user WHERE uid='".$uid."' AND can_use='0'";
    $result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
    	while ($row=$result->fetch_assoc()) {
    		return $row['username'];
    	}
    }

    return "";
}

function get_user_headpath_wuid($uid){

    $user_head="";
    if (get_user_sex_wuid($uid)>0) {
     	$user_head="img/male.png";
    }else{
        $user_head="img/female.png";
    }

    return $user_head;
}

function get_user_sex_wuid($uid){

	$sql="SELECT sex FROM md_user WHERE uid='".$uid."' AND can_use='0'";
    $result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
    	while ($row=$result->fetch_assoc()) {
    		return $row['sex'];
    	}
    }

    return -1;
}

function get_user_role_wuid($uid){

	$sql="SELECT role FROM md_user WHERE uid='".$uid."' AND can_use='0'";
    $result=$GLOBALS['conn']->query($sql);

    if ($result->num_rows>0) {
    	while ($row=$result->fetch_assoc()) {
    		return $row['role'];
    	}
    }

    return -1;
}

?>