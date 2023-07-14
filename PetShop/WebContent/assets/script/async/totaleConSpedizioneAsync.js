$(document).ready(function() {
	aggiornaPrezzoSpedizione();
	prezzoTotale();
	$("input[name='metodoSpedizione']").change(function() {
		aggiornaPrezzoSpedizione();
		prezzoTotale();
	});
});

function aggiornaPrezzoSpedizione() {
	let prezzoSpedizione = $('input[name="metodoSpedizione"]:checked').closest('.metodoSpedizione').find('span:nth-child(4)').text();
    $('#prezzoSpedizione').text('Spedizione ' + prezzoSpedizione);
}

function prezzoTotale() {
	let idMetodoSpedizione = $('input[name="metodoSpedizione"]:checked').val();
    $.ajax({
		url: contextPath + '/user/checkout',
		type: 'POST',
		data: {
			idMetodoSpedizione: idMetodoSpedizione
		},
		success: getPrezzoTotale,
		error: handleError
	});
}

function getPrezzoTotale() {
	$.ajax({
      url: contextPath + '/user/checkout', 
      type: 'GET',
      success: function(response) {
        $('#prezzoTotale').text('Totale ' + response.totale + '€');
      },
      error: handleError
    });
}