<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Gestisci Ordini</title>
</head>
<body>
<jsp:include page="../fragments/adminHeader.jsp" />
	<label for="selectClienti">Clienti: </label>
	<select id="selectClienti">
		<option value="">Tutti</option>
		<!-- Options to be filled with AJAX -->
	</select>
	<br>
	<label for="startDate">Data iniziale: </label>
	<input type="date" id="startDate">
	<br>
	<label for="endDate">Data finale: </label>
	<input type="date" id="endDate">
	<br>
	<label for="selectOrdinamento">Ordinamento per: </label>
	<select id="selectOrdinamento">
		<option value="data_ora">Data</option>
		<option value="prezzo">Prezzo</option>
	</select>
	<br>
	<label for="selectCrescenteDecrescente">Ordine: </label>
	<select id="selectCrescenteDecrescente">
		<option value="asc">Crescente</option>
		<option value="desc">Decrescente</option>
	</select>
	<br>
	<table id="tableOrdini">
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
	<jsp:include page="../fragments/toast.jsp" />
	<jsp:include page="../fragments/footer.jsp" />
	<script src="${pageContext.request.contextPath}/assets/script/async/gestisciOrdiniAsync.js"></script>
</body>
</html>
