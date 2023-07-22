<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>PetShop Products</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/styles/gestisciProdotti.css">
</head>

<body>
	<jsp:include page="../../views/fragments/adminHeader.jsp" />
	<div id="content">
		<div id="prodotti">
			<h1>I nostri prodotti</h1>
			<div class="filter-container">
				<h3 id="ordinaPer">Ordina per:</h3>
				<select id="tipologia" name="tipologia">
					<option value="tutte">Tutte</option>
				</select> <select id="tipologiaIn" name="tipologiaIn">
					<option value="tutte">Tutte</option>
				</select> <select id="order" name="order">
					<option value="in_magazzino">Disponibilità</option>
					<option value="prezzo">Prezzo</option>
					<option value="nome">Nome</option>
				</select> <select id="direction" name="direction">
					<option value="asc">Crescente</option>
					<option value="desc">Decrescente</option>
				</select>
			</div>

			<div id="table-container">
				<table id="productList">
				<caption>Lista prodotti</caption>
					<thead>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		<div class="pagination">
			<button class="pagination-btn" id="primaPagina">1</button>
			<button class="pagination-btn" id="indietro">Indietro</button>
			<span class="current-page" id="paginaCorrente"></span>
			<button class="pagination-btn" id="avanti">Avanti</button>
			<button class="pagination-btn" id="ultimaPagina"></button>
		</div>
	</div>

	<jsp:include page="../../views/fragments/footer.jsp" />
	<jsp:include page="../../views/fragments/toast.jsp" />

	<script
		src="${pageContext.request.contextPath}/assets/script/async/gestisciProdottiAsync.js"></script>
</body>
</html>
