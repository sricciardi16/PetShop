<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/myAccount.css">
<title>Il Mio Account</title>
</head>
<body>
	<jsp:include page="../../fragments/header.jsp" />
	<div id="container">
		<h2>Dati del Mio Account</h2>
		<div class="elements">
			<div id="nome_utente" class="element">
				<label>Nome utente: </label><span><c:out value="${sessionScope.utente.nomeUtente}" /></span>
			</div>
			<div id="nome" class="element">
				<label>Nome: </label><span><c:out value="${sessionScope.utente.nome}" /></span>
			</div>
			<div id="cognome" class="element">
				<label>Cognome: </label><span><c:out value="${sessionScope.utente.cognome}" /></span>
			</div>
			<div id="data_registrazione" class="element">
				<label>Registrazione: </label><span><c:out value="${sessionScope.utente.dataRegistrazione}" /></span>
			</div>
			<div id="email" class="element">
				<label>Email: </label><span><c:out value="${sessionScope.utente.email}" /></span>
			</div>
			<div id="telefono" class="element">
				<label>Telefono: </label><span><c:out value="${sessionScope.utente.telefono}" /></span>
			</div>
		</div>
		<a href="${pageContext.request.contextPath}/logout">logout</a>
	</div>
	<jsp:include page="../../fragments/toast.jsp" />
	<jsp:include page="../../fragments/footer.jsp" />
</body>
</html>
