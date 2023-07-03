<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/styles/prodotto.css">
</head>
<body>
	<div class="product-container">
		<div class="product-details">
			<h1 class="product-title">${prodotto.nome}</h1>
			<div class="product-image">
				<img
					src="${pageContext.request.contextPath}${initParam['imgProdottiPath']}${prodotto.immagine}"
					alt="Product Image" />
			</div>
			<p class="product-price">${prodotto.prezzo}€</p>
			<p class="product-description">${prodotto.descrizione}</p>
			<form action="cart.jsp" method="post">
				<input type="hidden" name="productId" value="${prodotto.id}">
				<div class="product-quantity">
					<label for="quantity-input">Quantità</label> <input type="number"
						id="quantity-input" name="quantity" min="1" value="1">
				</div>
				<button class="add-to-cart-btn" type="submit">Aggiungi al
					carrello</button>
			</form>
		</div>
	</div>
</body>
</html>