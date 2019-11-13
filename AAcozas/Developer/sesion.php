<?php 

	$hostname='probanditoito.000webhostapp.com:8080';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';

	$json=array();

	if (isset($_GET["usuario"])&& isset($_GET["contrasena"])) {
		
		$usuario=$_GET['usuario'];
		$contrasena=$_GET['contrasena'];
		//$user=$_GET['user'];
		$conexion=mysqli_connect($hostname, $username, $password, $database);

		$consulta="SELECT usuario, contrasena FROM usuarios WHERE usuario ='{$usuario}' AND contrasena='{$contrasena}'";
		$resultado= mysqli_query($conexion, $consulta);

		if($consulta){
			if ($reg=mysqli_fetch_array($resultado)) {
				$json['datos'][]=$reg;
			}
			mysqli_close($conexion);
			echo json_encode($json);
		}


		else{
			$results["usuario"]='';
			$results["contrasena"]='';
			//$results["codCliente"]='';
			$json['datos'][]=$results;
			echo json_encode($json);
		}
	}
	else{
			$results["usuario"]='';
			$results["contrasena"]='';
			//$results["codCliente"]='';
			$json['datos'][]=$results;
			echo json_encode($json);
	}
 ?>