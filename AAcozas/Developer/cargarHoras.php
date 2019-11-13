<?php 

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

	$diaRecibido=$_GET['diaSelec'];
	$servicioSel=$_GET['tiempoServicio'];
	$consulta="SELECT completo FROM dias WHERE dia ='{$diaRecibido}'";
	$resultado= mysqli_query($conexion, $consulta);
	$row = mysqli_fetch_array($resultado);
	$value = $row[0];
	//$resultado=$conexion->query($consulta);
	//echo $value;
	//echo $consulta."      ".$resultado;

	if($value=="0"){
		
		//METER TO LO GORDO PA CARGAR LOS DIAS

	if($servicioSel=="1"){
		$consulta2="SELECT * FROM horas WHERE idDia ='{$diaRecibido}' AND disponible='1' ORDER BY orden ASC";
	}elseif ($servicioSel=="2") {
		$consulta2="SELECT * FROM horas AS h1 INNER JOIN horas AS h2 ON h1.orden=(h2.orden-1) WHERE h1.idDia ='{$diaRecibido}' AND h1.disponible='1' AND h2.disponible='1' ORDER BY h1.orden ASC";
	}elseif ($servicioSel=="3") {
		$consulta2="SELECT * FROM horas AS h1 INNER JOIN horas AS h2 ON h1.orden=(h2.orden-1) INNER JOIN horas AS h3 ON h2.orden=(h3.orden-1) WHERE h1.idDia ='{$diaRecibido}' AND h1.disponible='1' AND h2.disponible='1' AND h3.disponible='1' ORDER BY h1.orden ASC";

	}


//SELECT * FROM horas AS h1 INNER JOIN horas as h2 ON h1.orden=(h2.orden+1) WHERE h1.idDia ='{$diaRecibido}' AND h1.disponible='1' AND h2.disponible='1' ORDER BY h1.orden ASC

	//$consulta2="SELECT * FROM horas WHERE idDia ='{$diaRecibido}' AND disponible='1' ORDER BY orden ASC";

	$resultado2= mysqli_query($conexion, $consulta2);

	while($fila=$resultado2 -> fetch_array()){
		//$fila = mysqli_fetch_array($resultado2);
		//$value2 = $fila[0];
		//$value3 = $fila[1];
		//echo $value2;
		//echo " ";
		//echo $value3;
		//echo " ";
		$producto[]=array_map('utf8_encode', $fila);
	}
	}
	elseif ($value=="1") {
		echo "No está disponible el dia ".$diaRecibido;

	}
	//var_dump($resultado);
	//die();

	echo json_encode($producto);
	mysqli_close($conexion);
 ?>