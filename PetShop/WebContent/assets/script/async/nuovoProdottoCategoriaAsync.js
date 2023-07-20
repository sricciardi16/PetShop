let animale, tipologia, tipologiaIn;

$(document).ready(function() {
	loadTipologie();
	eventiCategoria();
});

function loadTipologie() {
	animale = $("#animale").val();  // qui mi devo prendere il valore dalla select

	$.ajax({
		url: contextPath + "/categoria",
		type: "GET",
		data: { animale: animale },
		dataType: "json",
		success: function(data) {
			$("#tipologia").empty();
			$("#tipologia").append('<option value="seleziona" disabled selected>Seleziona</option>');

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
	if (tipologia == 'seleziona') {
		$("#tipologiaIn").empty();
		$("#tipologiaIn").append('<option value="seleziona" disabled selected>Seleziona</option>');
		$("#tipologiaIn").val('seleziona');
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
			$("#tipologiaIn").append('<option value="seleziona" disabled selected>Seleziona</option>');

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
		tipologia = $(this).val();
		tipologiaIn = 'seleziona';
		loadTipologiaIn();
	});

	$('#tipologiaIn').change(function() {
		tipologiaIn = $(this).val();
	});

	$('#animale').change(function() {
		animale = $(this).val();
		tipologia = 'seleziona';
		tipologiaIn = 'seleziona';
		loadTipologie();
	});
}
