
CREATE TABLE amministratore (
	id INT PRIMARY KEY,
	nome_utente VARCHAR(32),
	password VARCHAR(32)
);

CREATE TABLE utente (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nome_utente VARCHAR(32),
	password VARCHAR(32),
	nome VARCHAR(32),
	cognome VARCHAR(32),
	data_registrazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	email VARCHAR(64),
	telefono CHAR(10)
);

CREATE TABLE metodo_pagamento (
	id INT AUTO_INCREMENT PRIMARY KEY,
	tipo VARCHAR(32)
);

CREATE TABLE metodo_spedizione (
	id INT PRIMARY KEY,
	descrizione VARCHAR(64),
	prezzo DECIMAL(10,2),
	giorni_consegna_previsti INT
);

CREATE TABLE indirizzo (
	id INT AUTO_INCREMENT PRIMARY KEY,
	alias VARCHAR(128),
	via VARCHAR(64),
	numero VARCHAR(8),
	citta VARCHAR(64),
	codice_postale CHAR(5),
	provincia VARCHAR(64),
	paese VARCHAR(64),
	id_utente INT NOT NULL,
	FOREIGN KEY (id_utente) REFERENCES utente(id)
);

CREATE TABLE ordine (
	id INT AUTO_INCREMENT PRIMARY KEY,
	data_ora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	prezzo DECIMAL(10,2),
	stato ENUM('In attesa di conferma', 'In elaborazione', 'Spedito', 'Consegnato') DEFAULT 'In attesa di conferma',
	id_utente INT NOT NULL,
	id_metodo_pagamento INT NOT NULL,
	id_metodo_spedizione INT NOT NULL,
	id_indirizzo INT, -- da modificare, prima era NOT NULL
	FOREIGN KEY (id_utente) REFERENCES utente(id),
	FOREIGN KEY (id_metodo_pagamento) REFERENCES metodo_pagamento(id),
	FOREIGN KEY (id_metodo_spedizione) REFERENCES metodo_spedizione(id),
	FOREIGN KEY (id_indirizzo) REFERENCES indirizzo(id)
);

CREATE TABLE categoria (
	id INT PRIMARY KEY,
	animale ENUM('cane', 'gatto'),
	tipologia VARCHAR(128),
	tipologia_in VARCHAR(128)
);

CREATE TABLE prodotto (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(1024),
	descrizione TEXT,
	prezzo DECIMAL(10,2),
	immagine VARCHAR(64),
	in_magazzino INT,
	id_categoria INT NOT NULL,
	FOREIGN KEY (id_categoria) REFERENCES categoria(id)
);

CREATE TABLE elemento (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(1024),
	descrizione TEXT,
	prezzo DECIMAL(10,2),
	immagine VARCHAR(64),
	quantita INT,
	id_prodotto INT,
	id_ordine INT NOT NULL,
	FOREIGN KEY (id_prodotto) REFERENCES prodotto(id) ON DELETE SET NULL,
	FOREIGN KEY (id_ordine) REFERENCES ordine(id)
);
