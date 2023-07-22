<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="it">
<head>
<title>Gestisci Ordini</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/gestisciOrdini.css">
</head>
<body>
	<jsp:include page="../../../WEB-INF/views/fragments/adminHeader.jsp" />
	<div class="content">
		<div class="filterContent">
			<div>
				<label for="selectClienti">Clienti: </label> <select id="selectClienti">
					<option value="">Tutti</option>
					<!-- Options to be filled with AJAX -->
				</select>
			</div>

			<div>
				<label for="startDate">Data iniziale: </label> <input type="date" id="startDate">
			</div>

			<div>
				<label for="endDate">Data finale: </label> <input type="date" id="endDate">
			</div>

			<div>
				<label for="selectOrdinamento">Ordinamento per: </label> <select id="selectOrdinamento">
					<option value="data_ora">Data</option>
					<option value="prezzo">Prezzo</option>
				</select>
			</div>

			<div>
				<label for="selectCrescenteDecrescente">Ordine: </label> <select id="selectCrescenteDecrescente">
					<option value="asc">Crescente</option>
					<option value="desc">Decrescente</option>
				</select>
			</div>
		</div>
		<div class="tableContainer">
			<table id="tableOrdini">
			<caption>Tabella ordini</caption>
				<thead>
					<tr>
						<th>Codice</th>
						<th>Nome Utente</th>
						<th>Nome</th>
						<th>Cognome</th>
						<th>Data</th>
						<th>Prezzo Totale</th>
						<th>Stato</th>
						<th>Dettagli</th>
					</tr>
				</thead>

				<tbody>


				</tbody>
			</table>
		</div>
	</div>
	<jsp:include page="../../../WEB-INF/views/fragments/footer.jsp" />

	<jsp:include page="../../views/fragments/toast.jsp" />

	<script src="${pageContext.request.contextPath}/assets/script/async/gestisciOrdiniAsync.js"></script>
</body>
</html>