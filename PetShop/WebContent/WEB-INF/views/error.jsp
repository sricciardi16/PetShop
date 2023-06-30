<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PetShop - Error</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/assets/styles/main.css" />">
</head>
<body>
	<%-- <jsp:include page="fragments/header.jsp"/> --%>

	<div id="content">
		<h1>Error</h1>
		<p>${errorMessage}</p>

	</div>

	<%-- <jsp:include page="fragments/footer.jsp"/> --%>
</body>
</html>
