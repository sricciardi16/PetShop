<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/ordini.css">
<title>I Miei Ordini</title>
</head>
<body>
	<jsp:include page="../../fragments/header.jsp" />
	<div id="content">
		<h3>I Miei ordini</h3>
		<h5>Questi sono gli ordini che hai effettuato dal momento della creazione del tuo account</h5>
		<table>
			<tr>
				<th>Codice</th>
				<th>Data</th>
				<th>Prezzo totale</th>
				<th>Pagamento</th>
				<th>Stato</th>
				<th>Dettagli ordine</th>
			</tr>
			<c:forEach var="ordine" items="${ordini}">
				<tr>
					<td>${ordine.id}</td>
					<td><fmt:formatDate value="${ordine.dataOra}" type="date" pattern="yyyy-MM-dd" /></td>
					<td>${ordine.prezzo}</td>
					<td><c:choose>
							<c:when test="${ordine.idMetodoPagamento == 1}">Contanti</c:when>
							<c:otherwise>Carta</c:otherwise>
						</c:choose></td>
					<td>${ordine.stato}</td>
					<td><a href="${pageContext.request.contextPath}/user/ordine?id=${ordine.id}">Dettagli</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<jsp:include page="../../fragments/toast.jsp" />
	<jsp:include page="../../fragments/footer.jsp" />
</body>
</html>