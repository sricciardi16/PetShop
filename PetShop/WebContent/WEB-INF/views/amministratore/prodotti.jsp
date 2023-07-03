<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Elenco Prodotti</title>
<script>
    
</script>
</head>
<body>
	<h2>Elenco Prodotti</h2>

	<table>
		<tr>
			<th>Nome</th>
			<th>Descrizione</th>
			<th>Prezzo</th>
			<th>Immagine</th>
			<th>In Magazzino</th>
			<th>Animale</th>
			<th>Tipologia</th>
			<th>Sotto Tipologia</th>
		</tr>

		<c:forEach var="entry" items="${prodottiConCategoria}">
			<tr id="row_${entry.key.id}">
				<td><input type="text" id="nome_${entry.key.id}"
					value="${entry.key.nome}" /></td>
				<td><input type="text" id="descrizione_${entry.key.id}"
					value="${entry.key.descrizione}" /></td>
				<td><input type="number" id="prezzo_${entry.key.id}"
					value="${entry.key.prezzo}" /></td>
				<td><img
					src="${pageContext.request.contextPath}${initParam['imgProdottiPath']}${entry.key.immagine}"
					width=100px height=100px><input type="file"
					id="immagine_${entry.key.id}" /></td>
				<td><input type="text" id="inMagazzino_${entry.key.id}"
					value="${entry.key.inMagazzino}" /></td>
				<td>${entry.value.animale}</td>
				<td>${entry.value.tipologia}</td>
				<td>${entry.value.tipologiaIn}</td>
				<td><button onclick="editProduct(${entry.key.id})">Aggiorna</button></td>
				<td><button onclick="deleteProduct(${entry.key.id})">Elimina</button></td>
			</tr>
		</c:forEach>

	</table>


</body>
</html>
