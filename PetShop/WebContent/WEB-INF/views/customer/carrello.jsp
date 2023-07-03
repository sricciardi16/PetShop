<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Carrello</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #F0F0F0;
}

.CartContainer {
	width: 90%;
	margin: 2em auto;
	padding: 1em;
	background-color: #fff;
	border: 1px solid #ddd;
	border-radius: 3px;
}

.Header {
	display: flex;
	justify-content: space-between;
	margin-bottom: 1em;
}

.Heading, .Action, .title, .subtitle, .Subtotal, .items, .total-amount {
	margin: 0;
}

.Cart-Items {
	display: flex;
	justify-content: space-between;
	margin-bottom: 1em;
}

.image-box img {
	width: 100px;
}

.about {
	width: 30%;
}

.counter {
	display: flex;
}

.btn {
	margin: 0 1em;
}

.prices {
	text-align: right;
}

.amount {
	font-size: 1.2em;
	font-weight: bold;
}

.save, .remove {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

.checkout {
	text-align: right;
}

.button {
	padding: 1em;
	color: #fff;
	background-color: #007BFF;
	border: none;
	border-radius: 3px;
	cursor: pointer;
}

.button:hover {
	background-color: #0056b3;
}
</style>
</head>
<body>
	<div class="CartContainer">
		<div class="Header">
			<h3 class="Heading">Shopping Cart</h3>
			<h5 class="Action">Remove all</h5>
		</div>

		<c:forEach var="entry" items="${carrello}">
			<div class="Cart-Items">
				<div class="image-box">
					<img
						src="${pageContext.request.contextPath}${initParam['imgProdottiPath']}${entry.key.immagine}" />
				</div>
				<div class="about">
					<h1 class="title">${entry.key.nome}</h1>
					<h3 class="subtitle">${entry.key.descrizione}</h3>
				</div>
				<div class="counter">
					<button class="btn">+</button>
					<div class="count">${entry.value}</div>
					<button class="btn">-</button>
				</div>
				<div class="prices">
					<div class="amount">$${entry.key.prezzo * entry.value}</div>
					<div class="save">Save for later</div>
					<div class="remove">Remove</div>
				</div>
			</div>
		</c:forEach>

		<hr>
		<div class="checkout">
			<div class="total">
				<div>
					<h3 class="Subtotal">Sub-Total</h3>
					<h4 class="items">numero tot elementi</h4>
				</div>
				<h2 class="total-amount">prez tot €</h2>
			</div>
			<button class="button">Checkout</button>
		</div>
	</div>
</body>
</html>
