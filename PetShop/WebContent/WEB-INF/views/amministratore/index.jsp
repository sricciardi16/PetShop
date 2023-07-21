<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pannello di Amministrazione</title>
<link rel="stylesheet" type="text/css" href="styles.css">

</head>
<body>
		<jsp:include page="../fragments/adminHeader.jsp" />
	
		<h1>Ciao ${sessionScope.nomeUtente}, sei un Amministratore</h1>
	
	<nav>
		<ul>
			<li><a href="${pageContext.request.contextPath}/prodotti?animale=cane">Prodotti Cane</a></li>
			<li><a href="${pageContext.request.contextPath}/prodotti?animale=gatto">Prodotti Gatto</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/prodotti/create">Nuovo Prodotto</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/ordini">Gestisci Ordini</a></li>
			<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
		</ul>
	</nav>
			<jsp:include page="../fragments/toast.jsp" />
	<jsp:include page="../fragments/footer.jsp" />
</body>
</html>
