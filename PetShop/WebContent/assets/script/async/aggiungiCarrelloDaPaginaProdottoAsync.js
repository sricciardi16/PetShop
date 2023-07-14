$(document).ready(function() {
	aggiungiAlCarrello();
});

function aggiungiAlCarrello() {
    $('#aggiungiAlCarrello').click(function() {
        let idProdotto = $(this).data('id');
        let quantita = $("#quantitaProdotto").val();
        aggiornaCarrello('increase', idProdotto, quantita, function() {
            showToast((quantita == "1" ? "Un Prodotto Aggiunto" : (quantita + " Prodotti Aggiunti")) + " Al Carrello!", "#34c759");
            $("#quantitaProdotto").val(1);
        }, function() {
        	$("#quantitaProdotto").val(1);
        } );
    });
}