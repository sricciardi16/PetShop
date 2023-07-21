<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Pannello di Amministrazione</title>
<link rel="stylesheet" type="text/css" href="index.css">

</head>
<body>
		<jsp:include page="../fragments/adminHeader.jsp" />
		<h1>Ciao ${sessionScope.nomeUtente}, sei un Amministratore</h1>
			<div id="content">
	<div id="text">
	</div>
		<img alt="logo" src="${pageContext.request.contextPath}/assets/img/GUI/logo.png">
    	</div>
		<jsp:include page="../fragments/toast.jsp" />
	<jsp:include page="../fragments/footer.jsp" />
</body>
</html>
