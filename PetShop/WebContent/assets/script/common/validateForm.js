const nameOrLastnamePattern = /^[A-Za-z\s]+$/;
const aliasCittaProvinciaPattern = /^[A-Za-z\s]+$/;
const emailPattern = /^\S+@\S+\.\S+$/;
const phonePattern = /^[0-9]{10}$/;
const capPattern = /^[0-9]{5}$/;
const nameErrorMessage = "Un nome valido puo' contere solo lettere";
const aliasErrorMessage = "Un alias valido puo' contere solo lettere";
const provinciaErrorMessage = "Una provincia valida puo' contere solo lettere";
const cittaErrorMessage = "Una citta' valida puo' contere solo lettere";
const paeseErrorMessage = "Un paese valido puo' contere solo lettere";
const capErrorMessage = "Un cap valido deve contere solo 5 cifre";
const lastnameErrorMessage = "Un congnome valido puo' contenere solo lettere";
const emailErrorMessage = "Un email valida deve essere nel formato username@domain.ext";
const nummberErrorMessage = "Un numero di telfono valido deve essere formato da 10 cifre";

function validateFormElement(formElem, pattern, span, message) {
if (formElem.value.match(pattern)) {
		span.innerHTML = "";
		return true;
	}
	span.innerHTML = message;
	span.style.color = "white";
	return false;
}
