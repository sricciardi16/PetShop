<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/registrazione.css">
<script src="${pageContext.request.contextPath}/assets/script/common/validateForm.js"></script>
<title>Registrazione Utente</title>
</head>
<body>
	<jsp:include page="../fragments/header.jsp" />
	<h1>Inserisci i dati per creare un account!</h1>

	<div id="content">
		<form id="regForm" action="${pageContext.request.contextPath}/registrati" method="post">
			<div class="input">
				<div class="label">
					<label>Nome:</label>
				</div>
				<div>
					<input class="field" type="text" name="nome" id="nome" placeholder="Inserisci Nome" onchange="validateFormElement(this, nameOrLastnamePattern, document.getElementById('errorName'), nameErrorMessage)"><span id="errorName"></span>
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>Cognome:</label>
				</div>
				<div>
					<input class="field" type="text" name="cognome" id="cognome" placeholder="Inserisci Cognome" onchange="validateFormElement(this, nameOrLastnamePattern, document.getElementById('errorLastName'),lastnameErrorMessage)"><span id="errorLastName"></span>
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>Email:</label>
				</div>
				<div>
					<input class="field" type="text" name="email" id="email" placeholder="Inserisci Email" onchange="validateFormElement(this, emailPattern, document.getElementById('errorEmail'), emailErrorMessage)"><span id="errorEmail"></span>
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>Nome Utente:</label>
				</div>
				<div>
					<input class="field" type="text" name="nomeUtente" id="nomeUtente" placeholder="Inserisci Nome Utente">
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>Password:</label>
				</div>
				<div>
					<input class="field" type="password" name="password" id="password" placeholder="Inserisci Password">
				</div>
			</div>
			<div class="input">
				<div class="label">
					<label>Telefono:</label>
				</div>
				<div>
					<input class="field" type="text" name="telefono" id="phone" placeholder="##########" onchange="validateFormElement(this, phonePattern, document.getElementById('errorPhone'),nummberErrorMessage)"><span id="errorPhone"></span>
				</div>
			</div>
			<div class="bottoneRegistrazione">
				<div>
					<input type="submit" value="Registrazione"> <input class="reset" type="reset" value="Reset">
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="../fragments/toast.jsp" />
	<jsp:include page="../fragments/footer.jsp" />
	<%-- Script per validare form --%>
</body>
</html>