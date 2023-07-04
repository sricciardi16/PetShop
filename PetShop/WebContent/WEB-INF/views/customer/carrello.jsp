<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Carrello</title>
<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
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

.Cart-Items {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 1em;
	padding: 1em;
	border-bottom: 1px solid #ddd;
}

.image-box img {
	width: 100px;
	height: 100px;
	object-fit: cover;
}

.about {
	width: 25%;
}

.price, .counter, .prices {
	width: 15%;
	text-align: center;
}

.btn {
	margin: 0 1em;
	border: 0;
	background: #f1f1f1;
	padding: 10px;
	cursor: pointer;
}

.prices {
	text-align: right;
}

.amount {
	font-size: 1.2em;
	font-weight: bold;
}

.remove {
	color: red;
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
}</style>
</head>
<body>
<jsp:include page="../../views/fragments/header.jsp"/>

	<div class="CartContainer">
		<div class="Header">
			<h3 class="Heading">Shopping Cart</h3>
			<a href="#" class="Action">Remove all</a>
		</div>

		<c:forEach var="entry" items="${prodottiCarrello}">
			<div class="Cart-Items">
				<div class="image-box">
					<img src="${pageContext.request.contextPath}${initParam['imgProdottiPath']}${entry.key.immagine}" />
				</div>
				<div class="about">
					<h1 class="title">${entry.key.nome}</h1>
				</div>
				<div class="counter">
					<button class="btn">+</button>
					<div class="count">${entry.value}</div>
					<button class="btn">-</button>
				</div>
				<div class="prices">
					<div class="amount">$${entry.key.prezzo * entry.value}</div>
					<a href="#" class="save">Save for later</a>
					<a href="#" class="remove">Remove</a>
				</div>
			</div>
		</c:forEach>

		<hr>
		<div class="checkout">
			<div class="total">
				<div>
					<h4 class="items">${numeroProdottiCarrello} prodotti</h4>
				</div>
				<h2 class="total-amount">${totaleCarrello}€</h2>
			</div>
			<button class="button">Checkout</button>
		</div>
	</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
    $(".btn").click(function(){
        let prodottoId = $(this).parent().parent().find('img').attr('src').split('=')[1];
        let quantita = parseInt($(this).parent().find('.count').text());
        let mode = $(this).text() == '+' ? "increase" : "decrease";
        
        if(mode == "decrease" && quantita == 1) {
            alert("Quantità non può essere inferiore a 1");
            return;
        }

        $.ajax({
            url: "carrello",
            type: "POST",
            data: {
                id: prodottoId,
                quantita: quantita,
                mode: mode
            },
            success: function(response) {
                location.reload();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("Errore: " + textStatus);
            }
        });
    });
});
</script>
</body>
</html>
