<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pannello di Amministrazione</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/index.css">


</head>
<body>
	<jsp:include page="../fragments/adminHeader.jsp" />
	<div id="content">
		<h1>Ciao ${sessionScope.nomeUtente}, sei un Amministratore</h1>
		<img alt="logo" src="${pageContext.request.contextPath}/assets/img/GUI/logo.png">
	</div>
	<jsp:include page="../fragments/toast.jsp" />
	<jsp:include page="../fragments/footer.jsp" />
</body>
</html>
