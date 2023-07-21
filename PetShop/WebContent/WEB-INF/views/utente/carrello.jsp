<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Carrello</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/carrello.css">
</head>

<body>
	<jsp:include page="../fragments/header.jsp" />
	<div id="content">
		<h1>Carrello</h1>

		<div id="prodotti"></div>

		<div id="checkout" class="checkout"></div>



	</div>


	<jsp:include page="../fragments/toast.jsp" />
	<jsp:include page="../fragments/footer.jsp" />
	<script src="${pageContext.request.contextPath}/assets/script/common/carrello.js"></script>
	<script src="${pageContext.request.contextPath}/assets/script/async/carrelloAsync.js"></script>
</body>
</html>
