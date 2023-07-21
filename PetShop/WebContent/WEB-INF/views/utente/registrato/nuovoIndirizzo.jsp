<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/nuovoIndirizzo.css">
<script src="${pageContext.request.contextPath}/assets/script/common/validateForm.js"></script>
<title>Nuovo indirizzo</title>
</head>
<body>
	<jsp:include page="../../fragments/header.jsp" />
	<h2>Inserisci i dati per aggiungere un nuovo indirizzo</h2>

	<div id="content">
		<form id="aggForm" action="new" method="POST">
			<div class="input">
				<div class="label">
					<label>Alias:</label>
				</div>
				<div>
					<input class="field" type="text" name="alias" id="alias" placeholder="Inserisci Alias" onchange="validateFormElement(this, aliasCittaProvinciaPattern, document.getElementById('errorAlias'), aliasErrorMessage)"><span id="errorAlias"></span>
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>Via:</label>
				</div>
				<div>
					<input class="field" type="text" name="via" id="via" placeholder="Inserisci Via">
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>Numero:</label>
				</div>
				<div>
					<input class="field" type="text" name="numero" id="numero" placeholder="Inserisci Numero">
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>Citta':</label>
				</div>
				<div>
					<input class="field" type="text" name="citta" id="citta" placeholder="Inserisci Citta'" onchange="validateFormElement(this, aliasCittaProvinciaPattern, document.getElementById('errorCitta'), cittaErrorMessage)"><span id="errorCitta"></span>
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>CAP:</label>
				</div>
				<div>
					<input class="field" type="text" name="codicePostale" id="codicePostale" placeholder="Inserisci CAP" onchange="validateFormElement(this, capPattern, document.getElementById('errorCap'), capErrorMessage)"><span id="errorCap"></span>
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>Provincia:</label>
				</div>
				<div>
					<input class="field" type="text" name="provincia" id="provincia" placeholder="Inserisci Provincia" onchange="validateFormElement(this, aliasCittaProvinciaPattern, document.getElementById('errorProvincia'), provinciaErrorMessage)"><span id="errorProvincia"></span>
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>Paese:</label>
				</div>
				<div>
					<input class="field" type="text" name="paese" id="paese" placeholder="Inserisci Paese" onchange="validateFormElement(this, aliasCittaProvinciaPattern, document.getElementById('errorPaese'), paeseErrorMessage)"><span id="errorPaese"></span>
				</div>
			</div>
			<div class="aggiungi">
				<div>
					<input type="submit" value="Aggiungi"> <input class="reset" type="reset" value="Reset">
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="../../fragments/toast.jsp" />
	<jsp:include page="../../fragments/footer.jsp" />

	<!-- validare il form -->
</body>
</html>