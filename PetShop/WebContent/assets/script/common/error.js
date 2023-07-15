function handleError(jqXHR, textStatus, errorThrown) {
    let statusCode = jqXHR.status;
    let errorObj = JSON.parse(jqXHR.responseText);
    showToast("Errore " + statusCode + ": " + errorObj.message, "#ff3a30");
}