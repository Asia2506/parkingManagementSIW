-- Questo script popola il database con dati di esempio.
-- Assicurati che le tabelle siano già state create (ad esempio, tramite JPA/Hibernate DDL generation).
-- Le sequenze (es. parkingManagement_seq, operazione_seq) dovrebbero essere gestite automaticamente da Hibernate
-- o create manualmente se il tuo setup lo richiede.

-- Elimina i dati esistenti (opzionale, utile per ripristino durante lo sviluppo)
-- Se il tuo database ha vincoli di integrità referenziale, è consigliabile eliminare i dati
-- nell'ordine inverso di creazione (prima operazione, poi tessera, poi cliente/dati_fattura/descrizione_tessera).
-- delete from operazione;
-- delete from tessera;
-- delete from dipendentecc;
-- delete from dati_fattura;
-- delete from descrizione_tessera;


-- 1. Popola la tabella 'dati_fattura'
insert into dati_fattura (id, codice_univoco, pec, partita_iva, ragione_sociale, indirizzo) values (nextval('dati_fattura_seq'), 'ABC12345', 'info@azienda1.pec', 'IT12345678901', 'Azienda Alpha S.r.l.', 'Via del Corso 1, Roma');


-- 2. Popola la tabella 'descrizione_tessera'
insert into descrizione_tessera (id, tipo_tessera, descrizione, importo) values (nextval('descrizione_tessera_seq'), 'FULL', 'Tessera con validità dal primo all ultimo gg del mese', 17.00);
insert into descrizione_tessera (id, tipo_tessera, descrizione, importo) values (nextval('descrizione_tessera_seq'), 'SCALARE', 'Tessera con validità di 100 ore,scala 0.09€/h', 9.00);
insert into descrizione_tessera (id, tipo_tessera, descrizione, importo) values (nextval('descrizione_tessera_seq'), 'FERIALE', 'Tessera con validità dal primo all ultimo gg del mese esclusi i weekend', 12.00);


-- 3. Popola la tabella 'dipendentecc'
-- DIPENDENTE CC (nome, cognome, azienda, targa, dati_fatturazione_id)
insert into dipendentecc (id, nome, cognome, azienda, targa, dati_fatturazione_id) values (nextval('dipendentecc_seq'),  'Anna', 'Bianchi', 'FootLocker', 'AB123CD',NULL);
insert into dipendentecc (id, nome, cognome, azienda, targa, dati_fatturazione_id) values (nextval('dipendentecc_seq'),  'Carlo', 'Mancini', 'Footlocker', 'EF456GH',NULL);
insert into dipendentecc (id, nome, cognome, azienda, targa, dati_fatturazione_id) values (nextval('dipendentecc_seq'),  'Francesca', 'Rizzo', 'Tezenis', 'LM789NO', (select id from dati_fattura where ragione_sociale = 'Azienda Alpha S.r.l.'));
insert into dipendentecc (id, nome, cognome, azienda, targa, dati_fatturazione_id) values (nextval('dipendentecc_seq'),  'Mario', 'Rizzo', 'Tezenis', 'LC769hO', (select id from dati_fattura where ragione_sociale = 'Azienda Alpha S.r.l.'));
-- Nuovo dipendente per tessera 2004
insert into dipendentecc (id, nome, cognome, azienda, targa, dati_fatturazione_id) values (nextval('dipendentecc_seq'),  'Giorgia', 'Verdi', 'Zara', 'GV999XY',NULL);

-- 4. Popola la tabella 'tessera'
-- 'numero' è la PK (non generato automaticamente)
insert into tessera (numero, data_emissione, data_scadenza, descrizione_tessera_id, titolare_id, danneggiata, smarrita) values (2001, '2024-03-01', '2025-03-01', (select id from descrizione_tessera where tipo_tessera = 'FULL'), (select id from dipendentecc where nome = 'Anna' and cognome = 'Bianchi'), false, false);
insert into tessera (numero, data_emissione, data_scadenza, descrizione_tessera_id, titolare_id, danneggiata, smarrita) values (2002, '2025-05-10', '2025-06-10', (select id from descrizione_tessera where tipo_tessera = 'FULL'), (select id from dipendentecc where nome = 'Carlo' and cognome = 'Mancini'), false, false);
insert into tessera (numero, data_emissione, data_scadenza, descrizione_tessera_id, titolare_id, danneggiata, smarrita) values (2003, '2025-04-25', NULL, (select id from descrizione_tessera where tipo_tessera = 'SCALARE'), (select id from dipendentecc where nome = 'Francesca' and cognome = 'Rizzo' ), false, false);
-- Nuova Tessera per dimostrare ricarica che estende la scadenza al mese successivo
insert into tessera (numero, data_emissione, data_scadenza, descrizione_tessera_id, titolare_id, danneggiata, smarrita) values (2004, '2025-06-01', '2025-06-30', (select id from descrizione_tessera where tipo_tessera = 'FERIALE'), (select id from dipendentecc where nome = 'Giorgia' and cognome = 'Verdi'), false, false);


-- 5. Popola la tabella 'operazione'
-- Assicurati che 'operazione_seq' sia gestita correttamente dal tuo DB/Hibernate.
-- TipoOperazione: 0=EMISSIONE, 2=RICARICA (basato sull'ordine di dichiarazione nell'enum in Operazione.java)

-- Operazioni di EMISSIONE (una per ogni tessera)
-- Operazione 1: Emissione Tessera 2001 (FULL)
insert into operazione (id, data, tipo_operazione, tipo_pagamento, cauzione, importo, tessera_numero, cliente_id, tipo_tessera_id) values (nextval('operazione_seq'), '2024-03-01', 0, 'contanti', 10.00, (select importo from descrizione_tessera where tipo_tessera = 'FULL'), 2001, (select id from dipendentecc where nome = 'Anna' and cognome = 'Bianchi'), (select descrizione_tessera_id from tessera where numero = 2001));

-- Operazione 2: Emissione Tessera 2002 (FULL)
insert into operazione (id, data, tipo_operazione, tipo_pagamento, cauzione, importo, tessera_numero, cliente_id, tipo_tessera_id) values (nextval('operazione_seq'), '2025-05-10', 0, 'contanti', 10.00, (select importo from descrizione_tessera where tipo_tessera = 'FULL'), 2002, (select id from dipendentecc where nome = 'Carlo' and cognome = 'Mancini'), (select descrizione_tessera_id from tessera where numero = 2002));

-- Operazione 3: Emissione Tessera 2003 (SCALARE)
insert into operazione (id, data, tipo_operazione, tipo_pagamento, cauzione, importo, tessera_numero, cliente_id, tipo_tessera_id) values (nextval('operazione_seq'), '2025-04-25', 0, 'contanti', 10.00, (select importo from descrizione_tessera where tipo_tessera = 'SCALARE'), 2003, (select id from dipendentecc where nome = 'Francesca' and cognome = 'Rizzo'), (select descrizione_tessera_id from tessera where numero = 2003));

-- Operazione 4: Emissione Tessera 2004 (FERIALE)
insert into operazione (id, data, tipo_operazione, tipo_pagamento, cauzione, importo, tessera_numero, cliente_id, tipo_tessera_id) values (nextval('operazione_seq'), '2025-06-01', 0, 'contanti', 10.00, (select importo from descrizione_tessera where tipo_tessera = 'FERIALE'), 2004, (select id from dipendentecc where nome = 'Giorgia' and cognome = 'Verdi'), (select descrizione_tessera_id from tessera where numero = 2004));

-- Operazioni di RICARICA e Aggiornamento della data di scadenza della Tessera
-- RICARICA (assumendo ordinale 1)
-- Operazione 5: Ricarica Tessera 2001 (FULL)
-- Scadenza attuale: 2025-03-01. Data ricarica: 2025-06-15.
-- La scadenza è nel passato rispetto al mese di ricarica, quindi aggiorna a fine mese di ricarica.
insert into operazione (id, data, tipo_operazione, tipo_pagamento, cauzione, importo, tessera_numero, cliente_id, tipo_tessera_id) values (nextval('operazione_seq'), '2025-06-15', 2, 'carta', 0.00, (select importo from descrizione_tessera where tipo_tessera = 'FULL'), 2001, (select id from dipendentecc where nome = 'Anna' and cognome = 'Bianchi'), (select descrizione_tessera_id from tessera where numero = 2001));
UPDATE tessera SET data_scadenza = '2025-06-30' WHERE numero = 2001;

-- Operazione 6: Ricarica Tessera 2002 (FULL)
-- Scadenza attuale: 2025-06-10. Data ricarica: 2025-06-15.
-- La scadenza è nel passato rispetto al mese di ricarica, quindi aggiorna a fine mese di ricarica.
insert into operazione (id, data, tipo_operazione, tipo_pagamento, cauzione, importo, tessera_numero, cliente_id, tipo_tessera_id) values (nextval('operazione_seq'), '2025-06-15', 2, 'carta', 0.00, (select importo from descrizione_tessera where tipo_tessera = 'FULL'), 2002, (select id from dipendentecc where nome = 'Carlo' and cognome = 'Mancini'), (select descrizione_tessera_id from tessera where numero = 2002));
UPDATE tessera SET data_scadenza = '2025-06-30' WHERE numero = 2002;

-- Operazione 7: Ricarica Tessera 2004 (FERIALE)
-- Scadenza attuale: 2025-06-30 (fine mese di emissione). Data ricarica: 2025-06-20.
-- La tessera non è ancora scaduta nel mese di ricarica, quindi estende la scadenza a fine mese successivo.
insert into operazione (id, data, tipo_operazione, tipo_pagamento, cauzione, importo, tessera_numero, cliente_id, tipo_tessera_id) values (nextval('operazione_seq'), '2025-06-20', 2, 'carta', 0.00, (select importo from descrizione_tessera where tipo_tessera = 'FERIALE'), 2004, (select id from dipendentecc where nome = 'Giorgia' and cognome = 'Verdi'), (select descrizione_tessera_id from tessera where numero = 2004));
UPDATE tessera SET data_scadenza = '2025-07-31' WHERE numero = 2004;