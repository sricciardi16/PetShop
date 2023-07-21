<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PetShop Home</title>
<!-- <link rel="stylesheet" type="text/css" href="assets/styles/main.css"> -->
</head>
<body>
	<jsp:include page="WEB-INF/views/fragments/header.jsp" />

	<div id="content">
		<h1>Ciao PetShop!</h1>

		<p>La soluzione completa per tutte le esigenze del tuo animale domestico</p>
		<a href="${pageContext.request.contextPath}/user/myAccount">Il Mio Account</a> <a href="${pageContext.request.contextPath}/user/indirizzi">I Miei Indirizzi</a> <a href="${pageContext.request.contextPath}/user/ordini">I Miei Ordini</a> <a href="${pageContext.request.contextPath}/logout">Logout</a>

	</div>
	<jsp:include page="WEB-INF/views/fragments/toast.jsp" />
	<jsp:include page="WEB-INF/views/fragments/footer.jsp" />
</body>
</html>
