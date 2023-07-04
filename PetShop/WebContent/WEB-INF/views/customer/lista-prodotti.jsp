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
	<style type="text/css">#toast {
    visibility: hidden;
    max-width: 200px;
    height: auto;
    background-color: #333;
    color: #fff;
    text-align: center;
    border-radius: 2px;
    padding: 16px;
    position: fixed;
    z-index: 1;
    left: 50%;
    bottom: 30px;
    font-size: 17px;
    margin-left: -100px;
}

#toast.show {
    visibility: visible;
    -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
    animation: fadein 0.5s, fadeout 0.5s 2.5s;
}

@-webkit-keyframes fadein {
    from {bottom: 0; opacity: 0;} 
    to {bottom: 30px; opacity: 1;}
}

@keyframes fadein {
    from {bottom: 0; opacity: 0;}
    to {bottom: 30px; opacity: 1;}
}

@-webkit-keyframes fadeout {
    from {bottom: 30px; opacity: 1;} 
    to {bottom: 0; opacity: 0;}
}

@keyframes fadeout {
    from {bottom: 30px; opacity: 1;}
    to {bottom: 0; opacity: 0;}
}
	</style>
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
<jsp:include page="../../views/fragments/header.jsp"/>
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
			
			
		</div>
		<div id="toast"></div>
		
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
	<jsp:include page="../../views/fragments/footer.jsp"/>
</body>
</html>
