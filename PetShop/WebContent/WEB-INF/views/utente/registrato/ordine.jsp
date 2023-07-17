<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/ordine.css">
<title>Ordine ${ordine.id}</title>
</head>
<body>
	<jsp:include page="../../../views/fragments/header.jsp" />
	<div id="content">

		<c:forEach var="elemento" items="${elementi}">
			<div class="prodotto">
				<img src="${pageContext.request.contextPath}${initParam['imgElementiPath']}${elemento.immagine}"> <label class="nome">${elemento.nome}</label> <label class="prezzo">${elemento.prezzo} &#8364</label> <label class="modificaQuantita">Quantità: ${elemento.quantita }</label> <label
					class="totale">Totale: ${elemento.quantita * elemento.prezzo} &#8364</label>
				<button class="vaiAlProdotto" onclick="'">Vai al prodotto</button>
			</div>
		</c:forEach>
		<div class="checkout">
			<label>Numero articoli: ${numeroProdotti}</label><br> <label>Totale ordine: ${totaleOrdine} &#8364 </label><br>
			<button id="checkout" onclick="window.location.href = '${pageContext.request.contextPath}/user/ordini'">
				<b>I miei Ordini</b>
			</button>
		</div>
	</div><jsp:include page="../../../views/fragments/footer.jsp" />
</body>
</html>