<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PetShop Products</title>
</head>
<body>
    <div id="content">
        <h1>Our Products</h1>
        <ul id="productList">
            <c:forEach var="prodotto" items="${prodotti}">
                <li>
                    <h2><c:out value="${prodotto.nome}"/></h2>
                    <img src="${pageContext.request.contextPath}${initParam.imgProdottiPath}${prodotto.immagine}" alt="Image of ${prodotto.nome}">
                    <p><c:out value="${prodotto.descrizione}" escapeXml="false"/></p>
                    <p>Prezzo: <c:out value="${prodotto.prezzo}"/></p>
                    <a href="prodotto?id=${prodotto.id}">View Details</a>
                </li>
            </c:forEach>
        </ul>
        <button id="loadMore">Mostra Altro</button>
    </div>

</body>
</html>
