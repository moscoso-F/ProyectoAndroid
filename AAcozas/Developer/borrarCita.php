<?php 

	$hostname='';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';

	$conexion=mysqli_connect($hostname, $username, $password, $database);

	$idCita=$_POST['idCita'];	
	$dia=$_POST['dia'];
	$hora=$_POST['hora'];
	$tiempoDura=$_POST['servicio'];

	$consulta="DELETE * FROM CITAS WHERE idCita='{$idCita}'";


	$consulta="SELECT orden FROM horas WHERE horaEntrada='{$hora}' AND idDia='{$dia}'";
	$resul= mysqli_query($conexion, $consulta);
	$row= mysqli_fetch_array($consulta);

	$resultado = $row['orden'];

	echo $consulta;
	echo gettype($tiempoDura);

	if($tiempoDura="0"){
		$consulta2="UPDATE horas SET disponible=1 WHERE orden='{$resultado}' AND idDia='{$dia}'";
	}	
	if($tiempoDura="1"){
		$consulta2="UPDATE horas SET disponible=1 WHERE (orden='{$resultado}' OR orden=('{$resultado}'+1)) AND idDia='{$dia}'";
	}
	if($tiempoDura="2"){
		$consulta2="UPDATE horas SET disponible=1 WHERE (orden='{$resultado}' OR orden=('{$resultado}'+1) OR orden=('{$resultado}'+2)) AND idDia='{$dia}'";
	}




	//$consulta="INSERT INTO citas (nomCliente, hora, dia, servicio) VALUES ('".$nombreUs."','".$hora."','".$fechaCita."',".$numServicio."')";
	//$consulta="INSERT into clientes values('".$nombre."','".$apellidos."','".$dni."','".$tfno."','".$correo."','".$fechaNac."','".$nomUsuario."','".$contrUsuario."')";
	
	echo $consulta2;
	mysqli_query($conexion, $consulta2);
	mysqli_close($conexion);

 ?>