$(document).ready(function() {
	loadProducts();
	elimina();
	quantita();
});

function loadProducts() {
	$.ajax({
		url: contextPath + "/carrello",
		type: "GET",
		dataType: "json",
		success: populateProducts,
		error: handleError
	});
}

function populateProducts(response) {
	$('#prodotti').empty(); 
	let numeroProdotti = response.numeroProdottiCarrello;
	let totale = response.totaleCarrello;
	
	if (numeroProdotti > 0) {
		response.prodottiCarrello.forEach(function(prodotto) {
		
		let prodottoHTML = '<div class="prodotto">' +
		'<img src="'+ imgProdottiPath + prodotto.immagine + '">' +
		'<label class="nome" onclick="window.location.href = \'' +  contextPath +  '/prodotto?id=' + prodotto.id + '\'">' + prodotto.nome + '</label>' +
		'<label class="prezzo">Prezzo: ' + prodotto.prezzo + '&#8364;</label>' +
		'<label class="modificaQuantita" data-id="' + prodotto.id + '">Quantità: ' +
		'<input type="number" class="quantitaProdotto" data-id="' + prodotto.id + '" min="1" value="' + prodotto.quantita + '"></label>' +
		'<label class="totale">Totale: ' + ((prodotto.quantita * prodotto.prezzo).toFixed(2)) + '&#8364;</label>' +
		'<button class="button eliminaProdotto" data-id="' + prodotto.id + '">Elimina</button>' +
		'</div>';

		$('#prodotti').append(prodottoHTML);
		});
		
		$('#checkout').empty(); 
		let checkoutHTML = '<label>Numero articoli: ' + numeroProdotti + '</label><br>' +
		'<label>Totale carrello: ' + (Math.floor(totale * 100) / 100).toFixed(2) + '&#8364;</label><br>' +
		'<button id="checkoutButton" onclick="window.location.href =\'' + contextPath + '/user/checkout\'">' +
		'<b>Procedi al checkout</b>' +
		'</button>';

		$('#checkout').append(checkoutHTML);

	} else {
		$('#contentCarrello').append('<div class="carrelloVuoto" align="center"><h2>Spiacenti, ma il carrello è vuoto</h2><br></div>');
		$('#checkout').empty(); 
	}
}

function elimina() {
	$(document).on('click', '.eliminaProdotto', function() {
		let idProdotto = $(this).data('id');
		aggiornaCarrello('replace', idProdotto, 0, loadProducts);
	});
}

function quantita() {
	$(document).on('change', '.quantitaProdotto', function() {
		let idProdotto = $(this).data('id');
		let quantita = $(this).val();
		aggiornaCarrello('replace', idProdotto, quantita, loadProducts, loadProducts);
	});
}
