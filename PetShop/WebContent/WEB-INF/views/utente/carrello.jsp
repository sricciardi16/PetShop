<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it">
<head>
<title>Carrello</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/carrello.css">
</head>

<body>
	<jsp:include page="../../views/fragments/header.jsp" />

	<div id="content">
		<div id="contentCarrello">
			<h1>Carrello</h1>

			<div id="prodotti"></div>

			<div id="checkout" class="checkout"></div>
		</div>
	</div>


	<jsp:include page="../../views/fragments/footer.jsp" />
	<jsp:include page="../../views/fragments/toast.jsp" />

	<script src="${pageContext.request.contextPath}/assets/script/common/carrello.js"></script>
	<script src="${pageContext.request.contextPath}/assets/script/async/carrelloAsync.js"></script>
</body>
</html>
