let clienteId, startDate, endDate, ordinamento, ordine;

$(document).ready(function() {
    loadClienti();
    loadOrdini();
    eventiFiltri();
    eventoStato();
});

function loadClienti() {
    $.ajax({
        url: contextPath + "/admin/ordini/utenti",
        type: "GET",
        dataType: "json",
        success: function(data) {
            let select = $("#selectClienti");
            data.clienti.forEach(function(item) {
                select.append(new Option(item.nomeUtente, item.id));
            });
        },
        error: handleError
    });
}

function eventiFiltri() {
    $("#selectClienti, #startDate, #endDate, #selectOrdinamento, #selectCrescenteDecrescente").change(function() {
        clienteId = $("#selectClienti").val();
        startDate = $("#startDate").val();
        endDate = $("#endDate").val();
        ordinamento = $("#selectOrdinamento").val();
        ordine = $("#selectCrescenteDecrescente").val();

        loadOrdini();
    });
}

function loadOrdini() {
    $.ajax({
        url: contextPath + "/admin/ordini",
        type: "GET",
        data: {
            clienteId: clienteId,
            startDate: startDate,
            endDate: endDate,
            ordinamento: ordinamento,
            ordine: ordine
        },
        dataType: "json",
        success: populateOrdini,
        error: handleError
    });
}

function populateOrdini(data) {
    let table = $("#tableOrdini tbody");
    table.empty();
    data.ordini.forEach(function(order) {
        let row = $("<tr>");
        row.append($("<td>").text(order.id));
        row.append($("<td>").text(order.nomeUtente));
        row.append($("<td>").text(order.nome));
        row.append($("<td>").text(order.cognome));
        
row.append($("<td>").text(new Date(order.dataOra).toLocaleDateString('it-IT')));

        row.append($("<td>").text(order.prezzo + ' €'));
        row.append($("<td>").text(order.metodoPagamento));
        row.append($("<td>").append(
            $("<select>").attr("data-id", order.id).append(
                ["In attesa di conferma", "In elaborazione", "Spedito", "Consegnato"].map(function(status) {
                    return $("<option>").attr("selected", status === order.stato).text(status);
                })
            )
        ));
        row.append($("<td>").append(
            $("<a>").attr("href", contextPath + "/admin/ordine?id=" + order.id).text("Dettagli")
        ));
        table.append(row);
        window.scrollTo(0, 0);
    });
}

function eventoStato() {
    $(document).on("change", "select[data-id]", function() {
        let select = $(this);
        let orderId = select.attr("data-id");
        let status = select.val();

        $.ajax({
            url: contextPath + "/admin/ordini",
            type: 'POST',
            data: { id: orderId, stato: status },
            success: function(response) {
                if (response.status == 'success')
                	showToast(response.message, "#34c759");
                else
                	showToast(response.message, "#ff3a30");
            },
            error: handleError
        });
    });
}
