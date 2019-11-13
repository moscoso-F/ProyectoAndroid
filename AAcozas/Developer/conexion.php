<?php 

	$hostname='localhost';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';

	$conexion= new mysqli($hostname, $username, $password, $database);

	if($conexion->connect_errno){
		echo "Error en la conexión con la BBDD";
	}

 ?>