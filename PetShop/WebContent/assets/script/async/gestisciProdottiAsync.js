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
	animale = reqPar.get("animale") || null;
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
	$("#productList tbody").empty();
	

	numeroPagine = response.numeroPagine;

	response.prodotti.forEach(function(prodotto) {
		let prodottoHTML = '<tr>' +
			'<td><input type="text" class="nome" value="' + prodotto.nome + '"></td>' +
			'<td><input type="text" class="descrizione" value="' + prodotto.descrizione + '"></td>' +
			'<td><input type="number" class="prezzo" value="' + prodotto.prezzo + '"></td>' +
			'<td>' +
			'<input type="file" class="immagine">' +
			'<img src="' + imgProdottiPath + prodotto.immagine + '" alt="Anteprima dell\'immagine" style="width: 100px; height: 100px;">' +
			'</td>' +
			'<td><input type="number" class="inMagazzino" value="' + prodotto.inMagazzino + '"></td>' +
			'<td><button class="aggiorna" data-id= "' + prodotto.id + '">Aggiorna</button>' +
			'<button class="elimina" data-id= "' + prodotto.id + '">Elimina</button></td>' +
			'</tr><tr></tr>';

		$("#productList tbody").append(prodottoHTML);

	});
	$('#paginaCorrente').html(page);
	$('#ultimaPagina').html(numeroPagine);
	eliminaProdotto();
	modificaProdotto();
	window.scrollTo(0, 0);
}

function eliminaProdotto() {
	$('.elimina').click(function() {
		let idProdotto = $(this).data('id');
		$.ajax({
			url: contextPath + '/admin/prodotti/delete',
			type: 'POST',
			data: {
				id: idProdotto
			},
			success: function(response) {
				if (response.status === 'success') {
					loadProducts();
					showToast("Prodotto Eliminato Con Successo !", "#34c759");
				} else if (response.status === 'error') {
					showToast("Errore: " + response.message, "#ff3a30");
					errorCallback();
				}
			},
			error: handleError
		});
	});
}

function modificaProdotto() {
	$('.aggiorna').click(function() {
		let idProdotto = $(this).data('id');
		let nome = $(this).closest('tr').find('.nome').val();
		let descrizione = $(this).closest('tr').find('.descrizione').val();
		let prezzo = $(this).closest('tr').find('.prezzo').val();
		let inMagazzino = $(this).closest('tr').find('.inMagazzino').val();
		let immagineInput = $(this).closest('tr').find('.immagine')[0];
		let immagine = immagineInput.files.length > 0 ? immagineInput.files[0] : null;

		let formData = new FormData();
		formData.append('id', idProdotto);
		formData.append('nome', nome);
		formData.append('descrizione', descrizione);
		formData.append('prezzo', prezzo);
		formData.append('inMagazzino', inMagazzino);

		if (immagine) {
			formData.append('immagine', immagine);
		}

		$.ajax({
			url: contextPath + '/admin/prodotti/update',
			type: 'POST',
			data: formData,
			processData: false,
			contentType: false,
			success: function(response) {
				if (response.status === 'success') {
					loadProducts();
					showToast("Prodotto Aggiornato Con Successo !", "#34c759");
				} else if (response.status === 'error') {
					showToast("Errore: " + response.message, "#ff3a30");
				}
			},
			error: handleError
		});
	});
}



