<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/login.css">
<title>Login</title>
</head>
<body>
	<jsp:include page="../views/fragments/header.jsp" />
	<h1>Accedi</h1>
	<div id="content">
		<div class="credenziali">
			<form id="loginForm" class="form">
				<label>Nome Utente <input type="text" name="nomeUtente" required/></label><br> <label>Password <input type="password" name="password" required/></label> <div class="button"><input type="submit" value="Login"> <input type="button" 
					onclick="window.location.href='${pageContext.request.contextPath}/registrati'" value="Registrazione"></div> 
			</form>
		</div>
	</div>
	<jsp:include page="../views/fragments/toast.jsp" />
	<jsp:include page="../views/fragments/footer.jsp" />
	
	
	<script src="${pageContext.request.contextPath}/assets/script/async/loginFormAsync.js"></script>
</body>
</html>
