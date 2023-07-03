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
   <jsp:include page="WEB-INF/views/fragments/header.jsp"/>

    <div id="content">
        <h1>Ciao PetShop!</h1>
        
        <p>La soluzione completa per tutte le esigenze del tuo animale domestico</p>
        <h3><a href="prodotti?animale=cane">Prodotti per cani</a></h3>
        <h3><a href="prodotti?animale=gatto">Prodotti per gatti</a></h3>
        <c:choose>
           <c:when test="${empty sessionScope.nomeUtente || empty sessionScope.role}">
              <h2>Non sei loggato. <a href="login">login</a></h2>
           </c:when>
           <c:otherwise>
              <h2>Ciao, ${sessionScope.nomeUtente}! Sei un ${sessionScope.role}. <a href="logout">logout</a></h2>
           </c:otherwise>
        </c:choose>
        <h3><a href="carrello">Carrello</a></h3>
    </div>

   <jsp:include page="WEB-INF/views/fragments/footer.jsp" flush="true"/>
</body>
</html>
