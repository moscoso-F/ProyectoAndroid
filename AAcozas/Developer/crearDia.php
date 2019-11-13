<?php 

	$hostname='';
	$database='id10693935_prueba';
	$username='id10693935_root';
	$password='mosca';

	$conexion=mysqli_connect($hostname, $username, $password, $database);

	$diaRecibido=$_POST['Dia'];


	$consulta="INSERT INTO horas (horaEntrada, horaSalida, orden, disponible, idDia, idHoras) VALUES 
	('10:00','10:15','1','1','".$diaRecibido."','1.".$diaRecibido."'),
	('10:15','10:30','2','1','".$diaRecibido."','2.".$diaRecibido."'),
	('10:30','10:45','3','1','".$diaRecibido."','3.".$diaRecibido."'),
	('10:45','11:00','4','1','".$diaRecibido."','4.".$diaRecibido."'),
	('11:00','11:15','5','1','".$diaRecibido."','5.".$diaRecibido."'),
	('11:15','11:30','6','1','".$diaRecibido."','6.".$diaRecibido."'),
	('11:30','10:45','7','1','".$diaRecibido."','7.".$diaRecibido."'),
	('11:45','12:00','8','1','".$diaRecibido."','8.".$diaRecibido."'),
	('12:00','10:15','9','1','".$diaRecibido."','9.".$diaRecibido."'),
	('12:15','10:15','10','1','".$diaRecibido."','10.".$diaRecibido."'),
	('12:30','10:15','11','1','".$diaRecibido."','11.".$diaRecibido."'),
	('12:45','10:15','12','1','".$diaRecibido."','12.".$diaRecibido."'),
	('13:00','10:15','13','1','".$diaRecibido."','13.".$diaRecibido."'),
	('13:15','10:15','14','1','".$diaRecibido."','14.".$diaRecibido."'),
	('13:30','10:15','15','1','".$diaRecibido."','15.".$diaRecibido."'),
	('13:45','14:00','16','1','".$diaRecibido."','16.".$diaRecibido."'),
	('17:00','17:15','17','1','".$diaRecibido."','17.".$diaRecibido."'),
	('17:15','17:30','18','1','".$diaRecibido."','18.".$diaRecibido."'),
	('17:30','17:45','19','1','".$diaRecibido."','19.".$diaRecibido."'),
	('17:45','18:00','20','1','".$diaRecibido."','20.".$diaRecibido."'),
	('18:00','18:15','21','1','".$diaRecibido."','21.".$diaRecibido."'),
	('18:15','18:35','22','1','".$diaRecibido."','22.".$diaRecibido."'),
	('18:30','18:45','23','1','".$diaRecibido."','23.".$diaRecibido."'),
	('18:45','19:00','24','1','".$diaRecibido."','24.".$diaRecibido."'),
	('19:00','19:15','25','1','".$diaRecibido."','25.".$diaRecibido."'),
	('19:15','19:30','26','1','".$diaRecibido."','26.".$diaRecibido."'),
	('19:30','19:45','27','1','".$diaRecibido."','27.".$diaRecibido."'),
	('19:45','20:00','28','1','".$diaRecibido."','28.".$diaRecibido."'),
	('20:00','20:15','29','1','".$diaRecibido."','29.".$diaRecibido."'),
	('20:15','20:30','30','1','".$diaRecibido."','30.".$diaRecibido."'),
	('20:30','20:45','31','1','".$diaRecibido."','31.".$diaRecibido."'),
	('20:45','21:00','32','1','".$diaRecibido."','32.".$diaRecibido."')";
	//$consulta="INSERT into clientes values('".$nombre."','".$apellidos."','".$dni."','".$tfno."','".$correo."','".$fechaNac."','".$nomUsuario."','".$contrUsuario."')";
	
	echo $consulta;
	mysqli_query($conexion, $consulta);
	$consulta="INSERT INTO dias (dia, completo) VALUES ('".$diaRecibido."','0')";
	mysqli_query($conexion, $consulta);
	mysqli_close($conexion);

 ?>