<?php 

	$hostname='';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';
	
	global $conexion;
	$conexion=mysqli_connect($hostname, $username, $password, $database);

if (mysqli_connect_errno()) {
    printf("Falló la conexión: %s\n", mysqli_connect_error());
    exit();
}

function showerror( )   {
    die("Se ha producido el siguiente error: " . mysqli_error($connection));
}

	$dni=$_GET['dni'];
	$consulta="SELECT * FROM clientes WHERE dni ='{$dni}'";
	$resultado= mysqli_query($conexion, $consulta);

	//$resultado=$conexion->query($consulta);

	//echo $consulta."      ".$resultado;

	while($fila=$resultado -> fetch_array()){
		$producto[]=array_map('utf8_encode', $fila);
	}

	//var_dump($resultado);
	//die();

	echo json_encode($producto);
	mysqli_close($conexion);
 ?>