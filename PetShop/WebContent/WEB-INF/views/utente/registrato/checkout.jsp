<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Checkout</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/checkoutStyle.css">
</head>

<body>
	<jsp:include page="../../fragments/header.jsp" />
	<div id="content">
		<form action="${pageContext.request.contextPath}/user/checkout" method="post">
			<h4>Indirizzi:</h4>
			<div id="indirizzi">
				<p>L'indirizzo selezionato verrà utilizzato sia come indirizzo personale (per la fattura), sia come indirizzo di spedizione.</p>
				<c:forEach var="indirizzo" items="${indirizzi}" varStatus="status">
					<div class="indirizzo">
						<input type="radio" name="indirizzo" value="${indirizzo.id}" ${status.first ? 'checked' : ''}><br>
						<h2>${indirizzo.alias}</h2>
						<span>${sessionScope.utente.nome} ${sessionScope.utente.cognome}</span><br> <span>${indirizzo.via} ${indirizzo.numero}</span><br> <span>${indirizzo.codicePostale} ${indirizzo.citta}</span><br> <span>${indirizzo.provincia}</span><br> <span>${indirizzo.paese}</span><br>
						<br>
					</div>
				</c:forEach>
				<button type="button" onclick="window.location.href = '${pageContext.request.contextPath}/user/indirizzi/new?redirect=${pageContext.request.contextPath}/user/checkout'" name="addAddress">Aggiungi Un Nuovo Indirizzo</button>
			</div>
			<br>
			<h4>Metodo di spedizione:</h4>
			<div id="metodi-spedizione">
				<c:forEach var="metodoSpedizione" items="${metodiSpedizione}" varStatus="status">
					<div class="metodoSpedizione">
						<input type="radio" name="metodoSpedizione" value="${metodoSpedizione.id}" ${status.first ? 'checked' : ''}> <span>${metodoSpedizione.descrizione}</span> <span>Consegna prevista in ${metodoSpedizione.giorniConsegnaPrevisti} giorni</span> <span><c:choose>
								<c:when test="${metodoSpedizione.prezzo == 0}">
                Gratis
            </c:when>
								<c:otherwise>
                ${metodoSpedizione.prezzo}&euro;
            </c:otherwise>
							</c:choose></span>
					</div>
				</c:forEach>

			</div>
			<h4>Metodo di pagamento:</h4>
			<div id="metodiPagamento">
				<div class="metodoPagamento">
					<input type="radio" name="metodoPagamento" value="carta" onchange="cambiaStatoInputCartaCredito()" required><span>Carta di credito</span>
					<div class="input-container">
						<input type="text" name="numero-carta" placeholder="Numero della carta" disabled> <input type="text" name="data-scadenza" placeholder="MM/AA" disabled> <input type="text" name="CVV" placeholder="CVV" disabled>
					</div>
				</div>
				<div class="metodoPagamento">
					<input id="contanti-radio-button" type="radio" name="metodoPagamento" value="${contanti.id}" onchange="cambiaStatoInputCartaCredito()" required><span>Contanti</span>
				</div>
			</div>
			<h4>Resoconto ordine:</h4>
			<div id="resoconto">
				<h6>${sessionScope.carrello.numeroProdotti}prodotti</h6>
				<h6>Totale Parziale ${sessionScope.carrello.totale}</h6>
				<h6 id="prezzoSpedizione"></h6>
				<h6 id="prezzoTotale"></h6>
				<input type="submit" value="Invia ordine">
			</div>
		</form>
	</div>
	<jsp:include page="../../fragments/toast.jsp" />
	<jsp:include page="../../fragments/footer.jsp" />
	<script src="${pageContext.request.contextPath}/assets/script/async/totaleConSpedizioneAsync.js"></script>
	<script src="${pageContext.request.contextPath}/assets/script/toggleInputCartaCredito.js"></script>
</body>
</html>
