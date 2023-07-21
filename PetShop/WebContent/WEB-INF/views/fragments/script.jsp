<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
    let imgProdottiPath = "${pageContext.request.contextPath}${initParam['imgProdottiPath']}";
</script>
<script>
    let contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/assets/script/lib/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/script/common/error.js"></script>
<script src="${pageContext.request.contextPath}/assets/script/common/ajaxSetup.js"></script>
</body>
</html>