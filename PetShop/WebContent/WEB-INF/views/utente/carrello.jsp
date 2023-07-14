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
	<jsp:include page="../../views/fragments/header.jsp" />
	
	<div id="content">
		<h1>Carrello</h1>
		
		<div id="prodotti"></div>

		<div id="checkout" class="checkout"></div>



	</div>


	<jsp:include page="../../views/fragments/footer.jsp" />
	<jsp:include page="../../views/fragments/toast.jsp" />
	<script>
		let imgProdottiPath = "${pageContext.request.contextPath}${initParam['imgProdottiPath']}";
	</script>
	<script>let contextPath = "${pageContext.request.contextPath}";</script>
	<script src="${pageContext.request.contextPath}/assets/script/lib/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/script/common/ajaxSetup.js"></script>
	<script src="${pageContext.request.contextPath}/assets/script/common/error.js"></script>
	<script src="${pageContext.request.contextPath}/assets/script/common/carrello.js"></script>
	<script src="${pageContext.request.contextPath}/assets/script/async/carrelloAsync.js"></script>
</body>
</html>
