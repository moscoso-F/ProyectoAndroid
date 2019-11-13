<?php 

	$hostname='';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';

	$conexion=mysqli_connect($hostname, $username, $password, $database);

	$diaLibre=$_GET['diaLibre'];

	$consulta="INSERT INTO diaLibre (diaLibre) VALUES ('".$diaLibre."')";
	//$consulta="INSERT into clientes values('".$nombre."','".$apellidos."','".$dni."','".$tfno."','".$correo."','".$fechaNac."','".$nomUsuario."','".$contrUsuario."')";
	
	echo $consulta;
	mysqli_query($conexion, $consulta);
	mysqli_close($conexion);

 ?>