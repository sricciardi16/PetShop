<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="it">
<head>
<title>Toast</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/toast.css">
<script src="${pageContext.request.contextPath}/assets/script/toast.js"></script>
</head>
<body>
	<div class="toast"></div>
	<c:choose>
		<c:when test="${status eq 'success'}">
			<script>
				showToast("${message}", "#34c759");
			</script>
		</c:when>
		<c:when test="${status eq 'error'}">
			<script>
				showToast("${message}", "#ff3b30");
			</script>
		</c:when>
		<c:otherwise>
			<c:if test="${not empty message}">
				<script>
					showToast("${message}", "#007bff");
				</script>
			</c:if>
		</c:otherwise>
	</c:choose>
</body>
</html>