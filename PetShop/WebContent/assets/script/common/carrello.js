function aggiornaCarrello(mode, id, quantita, callback, errorCallback) {
    $.ajax({
        url: contextPath + '/carrello',
        type: 'POST',
        data: { 
            mode: mode,
            id: id,
            quantita: quantita
        }, 
        success: function(response) {
            if (response.status === 'success') {
				callback();
			} else if (response.status === 'error') {
				showToast("Errore: " + response.message, "#ff3a30");
				errorCallback();
			}
        },
        error: handleError
    });
}