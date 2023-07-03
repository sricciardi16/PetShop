<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>PetShop Products</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script>
		let imgProdottiPath = "${pageContext.request.contextPath}${initParam['imgProdottiPath']}"
	</script>
	<script>
	$(document).ready(function() {
		$('.add-to-cart').click(function() {
			let prodottoId = $(this).parent().find('a').attr('href').split('=')[1];
			var quantita = 1;

			$.ajax({
				url: "carrello",
				type: "POST",
				data: {
					id: prodottoId,
					quantita: quantita,
					mode: "increase"
				},
				success: function(response) {
					alert("Tutto a perfezione");
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert("Eroror" + textStatus);
				}
			});
		});
	});

	</script>
	<script src="${pageContext.request.contextPath}/assets/script/prodotti.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/main.css">
</head>
<body>
	<div id="content">
		<h1>I nostri prodotti</h1>
		<select id="tipologia" name="tipologia">
			<option value="tutte">tutte</option>
		</select>
		<select id="tipologiaIn" name="tipologiaIn">
			<option value="tutte">tutte</option>
		</select>
		<select id="order" name="order">
			<option value="in_magazzino">Disponibilità</option>
			<option value="prezzo">Prezzo</option>
			<option value="nome">Nome</option>
		</select>
		<select id="direction" name="direction">
			<option value="asc">Crescente</option>
			<option value="desc">Decrescente</option>
		</select>

		<div id="productList">
			<c:forEach var="prodotto" items="${prodotti}">
				<div class="grid-item">
					<img src="${pageContext.request.contextPath}${initParam['imgProdottiPath']}${prodotto.immagine}"
						alt="Immagine del prodotto ${prodotto.nome}">
					<h2><a href="prodotto?id=${prodotto.id}">${prodotto.nome}</a></h2>
					<p>${prodotto.prezzo}€</p>
					<button type="button" class="add-to-cart">Aggiungi al carrello</button>
				</div>
			</c:forEach>
		</div>
		<span>
			<div class="pagination">
				<button class="pagination-btn" id="primaPagina">1</button>
				<button class="pagination-btn" id="indietro">Indietro</button>
				<span class="current-page" id="paginaCorrente">${empty requestScope.page ? '1' : requestScope.page}</span>
				<button class="pagination-btn" id ="avanti">Avanti</button>
				<button class="pagination-btn" id="ultimaPagina">${numeroPagine}</button>
			</div>
		</span>
	</div>
</body>
</html>
