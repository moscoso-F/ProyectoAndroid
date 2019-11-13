<?php 

Header('Access-Control-Allow-Origin: *');

	$hostname='';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';
	
	$conexion;
	$conexion=mysqli_connect($hostname, $username, $password, $database);

	if (mysqli_connect_errno()) {
    	printf("Falló la conexión: %s\n", mysqli_connect_error());
    	exit();
	}

	function showerror( )   {
    	die("Se ha producido el siguiente error: " . mysqli_error($connection));
	}
 

	$sql = "SELECT * FROM diasLibres ORDER BY diaLibre";
	$rs = mysqli_query($conexion, $sql);
	$array = array();

		while ($fila = mysqli_fetch_assoc($rs)) {	
			$array[] = array_map('utf8_encode', $fila);
		}
		$res = json_encode($array, JSON_NUMERIC_CHECK);

	mysqli_close($conexion);
    echo $res

?>