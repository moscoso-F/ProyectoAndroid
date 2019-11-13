<?php 

	$hostname='';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';

	$conexion=mysqli_connect($hostname, $username, $password, $database);

	$hora=$_POST['hora'];	
	$dia=$_POST['dia'];
	$tiempoDura=$_POST['tiempoDura'];

	$consulta="SELECT orden FROM horas WHERE horaEntrada=$hora AND idDia=$dia";
	$resultado= mysqli_query($conexion, $consulta);

	if($tiempoDura==0){
		$consulta="UPDATE horas SET disponible=0 WHERE orden=$ordenResultado AND idDia=$dia";
	}	
	if($tiempoDura==1){
		$consulta="UPDATE horas SET disponible=0 WHERE (orden=$ordenResultado OR orden=($ordenResultado+1)) AND idDia=$dia";
	}
	if($tiempoDura==2){
		$consulta="UPDATE horas SET disponible=0 WHERE (orden=$ordenResultado OR orden=($ordenResultado+1) OR orden=($ordenResultado+2)) AND idDia=$dia";
	}


	mysqli_query($conexion, $consulta);

	//$consulta="INSERT INTO citas (nomCliente, hora, dia, servicio) VALUES ('".$nombreUs."','".$hora."','".$fechaCita."',".$numServicio."')";
	//$consulta="INSERT into clientes values('".$nombre."','".$apellidos."','".$dni."','".$tfno."','".$correo."','".$fechaNac."','".$nomUsuario."','".$contrUsuario."')";
	
	echo $consulta;
	mysqli_query($conexion, $consulta);
	mysqli_close($conexion);

 ?>