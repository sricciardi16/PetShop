<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
<title>Crea un nuovo prodotto</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/nuovoProdotto.css">
</head>
<body>
	<jsp:include page="../../../WEB-INF/views/fragments/adminHeader.jsp" />
	<form action="${pageContext.request.contextPath}/admin/prodotti/create" method="POST" enctype="multipart/form-data">
		<div class="container">
			<div class="input">
				<label class="boldLabel" for="nome">Nome:</label> <input type="text" id="nome" name="nome" required>
			</div>

			<div class="input">
				<label class="boldLabel" for="descrizione">Descrizione:</label> <input type="text" id="descrizione" name="descrizione" required>
			</div>

			<div class="input">
				<label class="boldLabel" for="prezzo">Prezzo:</label> <input type="number" id="prezzo" name="prezzo" step="0.01" required>
			</div>

			<div class="input">
				<label class="boldLabel" for="inMagazzino">In Magazzino:</label> <input type="number" id="inMagazzino" name="inMagazzino" required>
			</div>

			<div class="input">
				<label class="boldLabel" for="animale">Animale:</label> <select id="animale" name="animale" required>
					<option value="" disabled selected>Seleziona</option>
					<option value="cane">Cane</option>
					<option value="gatto">Gatto</option>
				</select>
			</div>

			<div class="input">
				<label class="boldLabel" for="tipologia">Tipologia:</label> <select id="tipologia" name="tipologia" required>
					<option value="" disabled selected>Seleziona</option>
				</select>
			</div>

			<div class="input">

				<label class="boldLabel" for="tipologiaIn">Sotto Tipologia:</label> <select id="tipologiaIn" name="tipologiaIn" required>
					<option value="seleziona" disabled selected>Seleziona</option>
				</select>

			</div>

			<div class="input-img">
				<label class="boldLabel" for="immagine">Immagine:</label> <input type="file" id="immagine" name="immagine" required><br>
			</div>
			<div class="input">
				<input class="creaProdotto" type="submit" value="Crea prodotto">
			</div>
		</div>
	</form>
	<jsp:include page="../fragments/toast.jsp" />
	<jsp:include page="../../../WEB-INF/views/fragments/footer.jsp" />
	<script src="${pageContext.request.contextPath}/assets/script/async/nuovoProdottoCategoriaAsync.js"></script>

</body>
</html>
