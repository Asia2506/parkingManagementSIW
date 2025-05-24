-- Questo script popola il database con dati di esempio.
-- Assicurati che le tabelle siano già state create (ad esempio, tramite JPA/Hibernate DDL generation).
-- Le sequenze (es. parkingManagement_seq) dovrebbero essere gestite automaticamente da Hibernate
-- o create manualmente se il tuo setup lo richiede.

-- Elimina i dati esistenti (opzionale, utile per ripristino durante lo sviluppo)
-- Se il tuo database ha vincoli di integrità referenziale, è consigliabile eliminare i dati
-- nell'ordine inverso di creazione (prima operazione, poi tessera, poi cliente/dati_fattura/descrizione_tessera).
-- delete from operazione;
-- delete from tessera;
-- delete from cliente;
-- delete from dati_fattura;
-- delete from descrizione_tessera;


-- 1. Popola la tabella 'dati_fattura'
insert into dati_fattura (id, codice_univoco, pec, partita_iva, ragione_sociale, indirizzo) values (nextval('dati_fattura_seq'), 'ABC12345', 'info@azienda1.pec', 'IT12345678901', 'Azienda Alpha S.r.l.', 'Via del Corso 1, Roma');


-- 2. Popola la tabella 'descrizione_tessera'
insert into descrizione_tessera (id, tipo_tessera, descrizione, importo) values (nextval('descrizione_tessera_seq'), 'FULL', 'Tessera con validità dal primo all ultimo gg del mese', 17.00);
insert into descrizione_tessera (id, tipo_tessera, descrizione, importo) values (nextval('descrizione_tessera_seq'), 'SCALARE', 'Tessera con validità di 100 ore,scala 0.09€/h', 9.00);
insert into descrizione_tessera (id, tipo_tessera, descrizione, importo) values (nextval('descrizione_tessera_seq'), 'FERIALE', 'Tessera con validità dal primo all ultimo gg del mese esclusi i weekend', 12.00);


-- 3. Popola la tabella 'cliente' (con DTYPE per ClienteEsterno e DipendenteCC)
-- CLIENTE ESTERNO (nome, dati_fatturazione_id)
insert into cliente (id, dtype, nome, dati_fatturazione_id) values (nextval('cliente_seq'), 'ClienteEsterno', 'Giuseppe Neri', NULL); -- Cliente Esterno senza dati fattura

-- DIPENDENTE CC (nome, cognome, azienda, targa, dati_fatturazione_id)
insert into cliente (id, dtype, nome, cognome, azienda, targa, dati_fatturazione_id) values (nextval('cliente_seq'), 'DipendenteCC', 'Anna', 'Bianchi', 'FootLocker', 'AB123CD',NULL);
insert into cliente (id, dtype, nome, cognome, azienda, targa, dati_fatturazione_id) values (nextval('cliente_seq'), 'DipendenteCC', 'Carlo', 'Mancini', 'Footlocker', 'EF456GH',NULL);
insert into cliente (id, dtype, nome, cognome, azienda, targa, dati_fatturazione_id) values (nextval('cliente_seq'), 'DipendenteCC', 'Francesca', 'Rizzo', 'Tezenis', 'LM789NO', (select id from dati_fattura where ragione_sociale = 'Azienda Alpha S.r.l.'));


-- 4. Popola la tabella 'tessera'
-- 'numero' è la PK (non generato automaticamente)
insert into tessera (numero, data_emissione, data_scadenza, descrizione_tessera_id, titolare_id) values (1001, '2024-01-01', '2025-01-01', (select id from descrizione_tessera where tipo_tessera = 'FULL'), (select id from cliente where nome = 'Giuseppe Neri' and dtype = 'ClienteEsterno'));
insert into tessera (numero, data_emissione, data_scadenza, descrizione_tessera_id, titolare_id) values (1002, '2025-05-15', '2025-06-15', (select id from descrizione_tessera where tipo_tessera = 'FULL'), (select id from cliente where nome = 'Giuseppe Neri' and dtype = 'ClienteEsterno'));
insert into tessera (numero, data_emissione, data_scadenza, descrizione_tessera_id, titolare_id) values (1003, '2025-05-20', '2025-05-21', (select id from descrizione_tessera where tipo_tessera = 'FULL'), (select id from cliente where nome = 'Giuseppe Neri' and dtype = 'ClienteEsterno'));

insert into tessera (numero, data_emissione, data_scadenza, descrizione_tessera_id, titolare_id) values (2001, '2024-03-01', '2025-03-01', (select id from descrizione_tessera where tipo_tessera = 'FULL'), (select id from cliente where nome = 'Anna' and cognome = 'Bianchi' and dtype = 'DipendenteCC'));
insert into tessera (numero, data_emissione, data_scadenza, descrizione_tessera_id, titolare_id) values (2002, '2025-05-10', '2025-06-10', (select id from descrizione_tessera where tipo_tessera = 'FULL'), (select id from cliente where nome = 'Carlo' and cognome = 'Mancini' and dtype = 'DipendenteCC'));
insert into tessera (numero, data_emissione, data_scadenza, descrizione_tessera_id, titolare_id) values (2003, '2025-04-25', NULL, (select id from descrizione_tessera where tipo_tessera = 'SCALARE'), (select id from cliente where nome = 'Francesca' and cognome = 'Rizzo' and dtype = 'DipendenteCC'));

