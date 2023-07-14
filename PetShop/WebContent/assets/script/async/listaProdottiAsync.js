let page, animale, tipologia, tipologiaIn, order, direction, numeroPagine;
$(document).ready(function() {
	first();
	loadProducts();
	eventiCategoria();
	eventiPaginazione();
});

function first() {
	let reqPar = new URLSearchParams(window.location.search);
	page = reqPar.get("page") || 1;
	animale = reqPar.get("animale");
	tipologia = reqPar.get("tipologia") || 'tutte';
	tipologiaIn = reqPar.get("tipologiaIn") || 'tutte';
	order = reqPar.get("order") || 'in_magazzino';
	direction = reqPar.get("direction") || 'asc';
	loadTipologie();
}

function loadTipologie() {
	$.ajax({
		url: contextPath + "/categoria",
		type: "GET",
		data: { animale: animale },
		dataType: "json",
		success: function(data) {
			$("#tipologia").empty();
			$("#tipologia").append('<option value="tutte">tutte</option>');

			data.tipologie.forEach(function(item) {
				let option = $('<option></option>').attr("value", item).text(item);
				$('#tipologia').append(option);
			});

			$('#tipologia').val(tipologia);

			loadTipologiaIn();
		},
		error: handleError
	});
}

function loadTipologiaIn() {
	if (tipologia == 'tutte') {
		$("#tipologiaIn").empty();
		$("#tipologiaIn").append('<option value="tutte">tutte</option>');
		$("#tipologiaIn").val('tutte');
		return;
	}

	$.ajax({
		url: contextPath + "/categoria",
		type: "GET",
		data: {
			animale: animale,
			tipologia: tipologia
		},
		dataType: "json",
		success: function(data) {
			$("#tipologiaIn").empty();
			$("#tipologiaIn").append('<option value="tutte">tutte</option>');

			data.tipologie.forEach(function(item) {
				let option = $('<option></option>').attr("value", item).text(item);
				$("#tipologiaIn").append(option);
			});

			$('#tipologiaIn').val(tipologiaIn);
		},
		error: handleError
	});
}

function eventiCategoria() {
	$('#tipologia').change(function() {
		page = 1;
		tipologia = $(this).val();
		tipologiaIn = 'tutte';
		loadTipologiaIn();
		loadProducts();
	});

	$('#tipologiaIn').change(function() {
		page = 1;
		tipologiaIn = $(this).val();
		loadProducts();
	});

	$('#order').change(function() {
		page = 1;
		order = $(this).val();
		loadProducts();
	});

	$('#direction').change(function() {
		page = 1;
		direction = $(this).val();
		loadProducts();
	});
}

function eventiPaginazione() {

	$('#primaPagina').click(function() {
		page = 1;
		loadProducts();
	});

	$('#indietro').click(function() {
		if (page > 1) {
			page--;
			loadProducts();
		}
	});

	$('#avanti').click(function() {
		if (page < numeroPagine) {
			page++;
			loadProducts();
		}
	});

	$('#ultimaPagina').click(function() {
		page = numeroPagine;
		loadProducts();
	});
}

function loadProducts() {
	$.ajax({
		url: contextPath + "/prodotti",
		type: "GET",
		data: {
			page: page,
			animale: animale,
			tipologia: tipologia == 'tutte' ? '' : tipologia,
			tipologiaIn: tipologiaIn == 'tutte' ? '' : tipologiaIn,
			order: order,
			direction: direction
		},
		dataType: "json",
		success: populateProducts,
		error: handleError
	});
}

function populateProducts(response) {
	$("#productList").empty();
	numeroPagine = response.numeroPagine;

	response.prodotti.forEach(function(prodotto) {
		let prodottoHTML = '<div class="grid-item">' +
			'<img src="' + imgProdottiPath + prodotto.immagine + '" alt="Immagine del prodotto ' + prodotto.nome + '">' +
			'<h2><a href="' + contextPath +  '/prodotto?id=' + prodotto.id + '">' + prodotto.nome + '</a></h2>' +
			'<p>' + prodotto.prezzo + ' €</p>' +
			'<button type="button" class="aggiungiAlCarrello" data-id= ' + prodotto.id + '>Aggiungi al carrello</button>' +
			'</div>';

		$("#productList").append(prodottoHTML);
	});
	$('#paginaCorrente').html(page);
	$('#ultimaPagina').html(numeroPagine);
	window.scrollTo(0, 0);
	aggiungiAlCarrello();
}


function aggiungiAlCarrello() {
    $('.aggiungiAlCarrello').click(function() {
        let idProdotto = $(this).data('id');
        let quantita = 1;
        aggiornaCarrello('increase', idProdotto, quantita, function(response) {
            showToast("Un Prodotto Aggiunto Al Carrello!", "#34c759");
        });
    });
}

