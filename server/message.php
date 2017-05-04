<?php
header("Content-type: text/html; charset=utf-8");
function var_json($info='', $code=10000, $data=array()){
	$out['code']=$code ?: 0;
	$out['info']=$info ?: ($out['code'] ? 'error': 'successs');
	$out['messages']=$data ?: array();
	header('Content-type: application/json; charset=utf-8');
	echo json_encode($out, JSON_HEX_TAG);
	$GLOBALS['conn']->close();
	exit(0);
}

?>