var elementiInput = document.querySelectorAll(".metodoPagamento input[type='text']");
var bottoneRadioContanti = document.getElementById("contanti-radio-button");

function disabilitaInputCartaCredito() {
  elementiInput.forEach(function(elementoInput) {
    elementoInput.disabled = true;
    elementoInput.value="";
  });
}

function abilitaInputCartaCredito() {
  elementiInput.forEach(function(elementoInput) {
    elementoInput.disabled = false;
  });
}

function cambiaStatoInputCartaCredito() {
  if (bottoneRadioContanti.checked) {
    disabilitaInputCartaCredito();
  } else {
    abilitaInputCartaCredito();
  }
}

window.onload = cambiaStatoInputCartaCredito;
