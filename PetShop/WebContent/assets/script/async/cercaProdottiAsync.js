$(document).ready(function() {
    $("#myInput").keyup(function() {
        let like = $(this).val();
        if (like != "") {
            $.ajax({
                url: contextPath + '/cerca',
                type: 'GET',
                data: {
                    like : like,
                },
                dataType: 'json',
                success: function(data) {
                    updateSearchResults(data.prodotti);
                },
                error: handleError
            });
        } else {
            $('#elements').empty();
        }
    });
});

function updateSearchResults(products) {
    let elementsContainer = $('#elements');
    elementsContainer.empty();

    $.each(products, function(index, prodotto) {
        let element = $('<div/>', { "class": "element" });
        $('<img/>', { src: imgProdottiPath + '/' + prodotto.immagine, alt: "foto" }).appendTo(element);
        let productNameLink = $('<a/>', { href: contextPath + '/prodotto?id=' + prodotto.id, text: prodotto.nome });
        $('<h4/>').append(productNameLink).appendTo(element);
        $('<span/>', { text: prodotto.prezzo + "€" }).appendTo(element);

        elementsContainer.append(element);
    });
}
