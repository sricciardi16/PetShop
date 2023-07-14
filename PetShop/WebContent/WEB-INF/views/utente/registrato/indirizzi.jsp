<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/indirizzi.css">
<title>I miei Indirizzi</title>
</head>
<body>
	<jsp:include page="../../../views/fragments/header.jsp" />
	<div id="content">
		<c:forEach var="indirizzo" items="${indirizzi}">
			<div class="indirizzo">
				<h2>${indirizzo.alias}</h2>
				<span>${sessionScope.utente.nome} ${sessionScope.utente.cognome}</span><br> 
				<span>${indirizzo.via} ${indirizzo.numero}</span><br> 
				<span>${indirizzo.codicePostale} ${indirizzo.citta}</span><br> 
				<span>${indirizzo.provincia}</span><br> 
				<span>${indirizzo.paese}</span><br>
				<form method="POST" action="user/indirizzi/delete">
					<input type="hidden" name="id" value="${indirizzo.id}">
					<button type="submit" class="button">Elimina</button>
				</form>
			</div>
		</c:forEach>
		<div class="nuovoIndirizzo">
			<button class="creaNuovoIndirizzo" onclick="window.location.href = '${pageContext.request.contextPath}/user/indirizzi/new'">Crea nuovo indirizzo</button>
		</div>
	</div>
	<jsp:include page="../../../views/fragments/footer.jsp" />
</body>
</html>
