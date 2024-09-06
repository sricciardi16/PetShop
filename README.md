# PetShop 🛍️🐱🐶

E-commerce per animali domestici, sviluppato come progetto didattico per il corso di Tecnologie Software per il Web.

## Funzionalità

**Per gli utenti:**

- 📝 Registrazione e login
- 🐾 Navigazione del catalogo prodotti, con filtri per categoria, tipologia e prezzo
- 🔎 Visualizzazione dettagliata dei prodotti
- ➕ Aggiunta e gestione dei prodotti nel carrello
- 💳 Procedura di checkout con selezione dell'indirizzo di spedizione, metodo di spedizione e metodo di pagamento
- 📦 Visualizzazione dello storico degli ordini

**Per gli amministratori:**

- 💼 Gestione del catalogo prodotti (aggiunta, modifica, eliminazione)
- 🚚 Gestione degli ordini (visualizzazione, modifica dello stato)
- 👥 Gestione degli utenti

## Tecnologie utilizzate

- **Frontend:** HTML, CSS, JavaScript, jQuery
- **Backend:** Java Servlet, JSP
- **Database:** MySQL
- **Librerie:** Gson (per la serializzazione/deserializzazione JSON)

## Architettura

Client-server con richieste asincrone AJAX per un'esperienza utente fluida.

## Installazione

1. `git clone https://github.com/sricciardi16/PetShop/`
2. Configurare il database in `MainContext.java` (URL, username, password).
3. Deployare il file WAR in Tomcat (`webapps`).
4. Avviare Tomcat. PetShop sarà accessibile a `http://localhost:8080/PetShop/`.

