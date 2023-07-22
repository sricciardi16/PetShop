<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>PetShop Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="assets/styles/index.css">
</head>
<body>
	<jsp:include page="WEB-INF/views/fragments/header.jsp" />
	<div id="content">
		<div id="text">
			<h1>Benvenuto!</h1>
			<p>La soluzione completa per il tuo amico a quattro zampe!</p>
		</div>
		<img alt="logo" src="${pageContext.request.contextPath}/assets/img/GUI/logo.png">
	</div>
	<jsp:include page="WEB-INF/views/fragments/toast.jsp" />
	<jsp:include page="WEB-INF/views/fragments/footer.jsp" />
</body>
</html>
