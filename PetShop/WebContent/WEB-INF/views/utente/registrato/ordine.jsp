<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="it">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/ordine.css">
		<title>Ordine ${ordine.id}</title>
	</head>
	<body>
		<jsp:include page="../../fragments/header.jsp" />
		<div id="content">
			<c:forEach var="elemento" items="${elementi}">
				<div class="prodotto">
					<img alt="ordine" src="${pageContext.request.contextPath}${initParam['imgElementiPath']}${elemento.immagine}">
					<label class="nome">${elemento.nome}</label> 
					<label class="prezzo">${elemento.prezzo} &#8364</label> 
					<label class="modificaQuantita">Quantità: ${elemento.quantita }</label> 
					<label class="totale">Totale: <fmt:formatNumber value="${elemento.quantita * elemento.prezzo}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &#8364</label>
					<button class="vaiAlProdotto" onclick="window.location.href='${pageContext.request.contextPath}/prodotto?id=${elemento.idProdotto}'" ${elemento.idProdotto == 0 ? 'disabled' : ''}>Vai al prodotto</button>
				</div>
			</c:forEach>
			<div class="checkout">
				<label>Numero articoli: ${numeroProdotti}</label><br> 
				<label>Totale ordine: ${totaleOrdine} &#8364 </label><br>
				<button id="checkout" onclick="window.history.back();">
					<b>Indietro</b>
				</button>
			</div>
		</div>
		<jsp:include page="../../fragments/toast.jsp" />
		<jsp:include page="../../fragments/footer.jsp" />
	</body>
</html>
