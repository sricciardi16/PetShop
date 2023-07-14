<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
</head>
<jsp:include page="../../views/fragments/header.jsp"/>

<body> 
	<div id="container">
		<h2>Dati personali:</h2>
		<div class="elements">
		<div id="nome_utente" class="element"><label>Nome utente: </label><span><!-- PLACE CODE HERE --></span></div>
		<div id="password" class="element"><label>Password: </label><span><!-- PLACE CODE HERE --></span></div>
		<div id="nome" class="element"><label>Nome: </label><span><!-- PLACE CODE HERE --></span></div>
		<div id="cognome" class="element"><label>Cognome: </label><span><!-- PLACE CODE HERE --></span></div>
		<div id="data_registrazione" class="element"><label>Registrazione: </label><span><!-- PLACE CODE HERE --></span></div>
		<div id="email" class="element"><label>Email: </label><span><!-- PLACE CODE HERE --></span></div>
		<div id="telefono" class="element"><label>Telefono: </label><span><!-- PLACE CODE HERE --></span></div>
		</div>
		<a href="logout">logout</a>
	</div>
</body>
<jsp:include page="../../views/fragments/footer.jsp" />
</html>