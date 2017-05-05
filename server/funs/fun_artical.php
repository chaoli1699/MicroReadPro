<?php

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

function update_artical_com_count($aid, $com_count, $fun){ 

    if ($fun>0) {
    	$com_count++;
    }else{
    	if ($com_count>0) {
    		$com_count--;
    	}
    }

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

?>