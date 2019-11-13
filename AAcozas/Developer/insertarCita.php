<?php 

	$hostname='';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';

	$conexion=mysqli_connect($hostname, $username, $password, $database);

	$nombreUs=$_POST['Nombre'];
	$fechaCita=$_POST['Apellidos'];
	$hora=$_POST['Dni'];
	$numServicio=$_POST['Tfno'];




	$consulta="INSERT INTO citas (nomCliente, hora, dia, servicio) VALUES ('".$nombreUs."','".$fechaCita."','".$hora."',".$numServicio."')";
	//$consulta="INSERT into clientes values('".$nombre."','".$apellidos."','".$dni."','".$tfno."','".$correo."','".$fechaNac."','".$nomUsuario."','".$contrUsuario."')";
	
	echo $consulta;
	mysqli_query($conexion, $consulta);
	mysqli_close($conexion);

 ?>