<?php 

	$hostname='';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';

	$conexion=mysqli_connect($hostname, $username, $password, $database);

	$nombre=$_POST['Nombre'];
	$apellidos=$_POST['Apellidos'];
	$dni=$_POST['Dni'];
	$tfno=$_POST['Tfno'];
	$correo=$_POST['Correo'];
	$fechaNac=$_POST['FechaNac'];
	$nomUsuario=$_POST['NomUsu'];
	$contrUsuario=$_POST['ContrUsu'];



	$consulta="INSERT INTO clientes (nombre, apellidos, dni, tfno, correo, fechaNac, nomUsuario, contUsuario) VALUES ('".$nombre."','".$apellidos."','".$dni."',".$tfno.",'".$correo."','".$fechaNac."','".$nomUsuario."','".$contrUsuario."')";
	//$consulta="INSERT into clientes values('".$nombre."','".$apellidos."','".$dni."','".$tfno."','".$correo."','".$fechaNac."','".$nomUsuario."','".$contrUsuario."')";
	
	echo $consulta;
	mysqli_query($conexion, $consulta);
	mysqli_close($conexion);

 ?>