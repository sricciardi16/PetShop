<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/prodottoStyle.css">

</head>
<body>
	<jsp:include page="../../views/fragments/header.jsp" />
	<jsp:include page="../../views/fragments/toast.jsp" />
	<div id="content">
		<div id="img-container">
			<img src="${pageContext.request.contextPath}${initParam['imgProdottiPath']}${prodotto.immagine}">
		</div>
		<div id="data-container">
			<h1>${prodotto.nome}</h1>
			<div id="container-prezzo">
				<label id="prezzo"><fmt:formatNumber value="${prodotto.prezzo}" minFractionDigits="2" maxFractionDigits="2" />€</label>
			</div>
			<div id="container-quantita">
				<h4>Quantità:</h4>
				<input type="number" id="quantitaProdotto" value="1" min="1" width="2%">
			</div>
			<button id="aggiungiAlCarrello" data-id="${prodotto.id}">Aggiungi al carrello</button>
		</div>
		<div id="desc-container">
			<h2>Descrizione:</h2>
			<p id="descrizione">${prodotto.descrizione}</p>
		</div>
	</div>
	<jsp:include page="../fragments/toast.jsp" />
	<jsp:include page="../fragments/footer.jsp" />
	<script src="${pageContext.request.contextPath}/assets/script/common/carrello.js"></script>
	<script src="${pageContext.request.contextPath}/assets/script/async/aggiungiCarrelloDaPaginaProdottoAsync.js"></script>
</body>
</html>