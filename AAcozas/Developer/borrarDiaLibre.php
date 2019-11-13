<?php 

	header('Access-Control-Allow-Origin: *');
	header("Access-Control-Allow-Headers: X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method");
	header("Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE");
	header("Allow: GET, POST, OPTIONS, PUT, DELETE");
	$method = $_SERVER['REQUEST_METHOD'];
	if($method == "OPTIONS") {
    die();
}



	$hostname='';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';

	$conexion=mysqli_connect($hostname, $username, $password, $database);

	$idCita=$_GET['idCita'];	
	$dia=$_GET['dia'];
	$hora=$_GET['hora'];
	$tiempoDura=$_GET['servicio'];

	$consulta1="DELETE FROM citas WHERE idCita={$idCita}";
	mysqli_query($conexion, $consulta1);

	$consulta="SELECT orden FROM horas WHERE horaEntrada='{$hora}' AND idDia='{$dia}'";
	$resul= mysqli_query($conexion, $consulta);
	$row= mysqli_fetch_array($resul);

	$resultado = $row['orden'];

	echo $consulta;
	//echo gettype($tiempoDura);

	if($tiempoDura="1"){
		$consulta2="UPDATE horas SET disponible=1 WHERE orden='{$resultado}' AND idDia='{$dia}'";
	}	
	if($tiempoDura="2"){
		$consulta2="UPDATE horas SET disponible=1 WHERE (orden='{$resultado}' OR orden=('{$resultado}'+1)) AND idDia='{$dia}'";
	}
	if($tiempoDura="3"){
		$consulta2="UPDATE horas SET disponible=1 WHERE (orden='{$resultado}' OR orden=('{$resultado}'+1) OR orden=('{$resultado}'+2)) AND idDia='{$dia}'";
	}




	//$consulta="INSERT INTO citas (nomCliente, hora, dia, servicio) VALUES ('".$nombreUs."','".$hora."','".$fechaCita."',".$numServicio."')";
	//$consulta="INSERT into clientes values('".$nombre."','".$apellidos."','".$dni."','".$tfno."','".$correo."','".$fechaNac."','".$nomUsuario."','".$contrUsuario."')";
	
	echo $consulta2;
	mysqli_query($conexion, $consulta2);
	mysqli_close($conexion);

 ?>