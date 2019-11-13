<?php 

	include 'conexion.php';

	$nombre=$_GET['nombre'];
	$apellidos=$_GET['apellidos'];
	$dni=$_GET['dni'];
	$telefono=$_GET['telefono'];
	$correo=$_GET['correo'];
	$fecha=$_GET['fecha'];
	$nomUsuario=$_GET['nomUsuario'];
	$contrasena=$_GET['contrasena'];


	$consulta="insert into NOMBRE TABLA values('".$nombre."','".$apellidos."','".$dni."','".$telefono."','".$correo."','".$fecha."','".$nomUsuario."','".$contrasena"')";
	mysqli_query($conexion, $consulta) or die(mysqli_error());
	mysqli_close($conexion);

 ?>