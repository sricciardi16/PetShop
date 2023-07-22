<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Oops!</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<jsp:include page="WEB-INF/views/fragments/header.jsp" />
	<div id="content">
		<h1>Oops! Something went wrong...</h1>
		<div class="error-details">
			<h2>Errore ${pageContext.errorData.statusCode}</h2>
			<h3>${requestScope['javax.servlet.error.message']}</h3>
		</div>

		<p>Please try again later, or contact the support team if the problem persists.</p>
	</div>
	<jsp:include page="WEB-INF/views/fragments/toast.jsp" />
	<jsp:include page="WEB-INF/views/fragments/footer.jsp" />
</body>
</html>
