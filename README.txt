SMTP Benchmark
Scritto da Matteo Minardi nel 2017 <minardi.matteo(at)hotmail.it>

BREVE SPIEGAZIONE PROGETTO:
Questo programma è stato progettato con un modello MVC separando i costrutti di modello, vista e controllo.
La vista:
	- Un campo di inserimento mail del mittente (E' necessario che sia una mail gmail.com)
	  Ho creato una mail da utilizzare nel caso non si disponga di una mail google.
	  	mail : test.diennea@gmail.com
	  	pass : test2017
	- Un campo password mittente, criptato durante l'autenticazione.
	- Un campo mail destinatario (consiglio di utilizzare una temp-mail per evitare spam https://temp-mail.org/it/)
	- Un campo che indica su quante mail deve essere eseguito il test (già con 5 mail impiega circa 30 secondi)
	- Un campo che indica la lunghezza del testo nel corpo della mail
	- Un campo che indica la grandezza del messaggio passata come file attachment binario
Il controller:
	- Possiede i listener degli eventi della grafica
	- Gestisce il check degli input e il relativo passaggio al modello
Il model:
	- Possiede tutta la struttura logica e l'implementazione della connessione.
	- Utilizza la libreria JavaMail per connettersi, creare una sessione e autenticarsi in base ai dati forniti.
	- Una volta avuta la connessione genera le mail in base ai dati con o senza attachment (attachment = 0 significa senza attachment)
	- Il modello inoltre gestisce il benchmark che fa media, minimo e massimo di una serie di prove
	- Il modello genera automaticamente degli attachment del size indicato [In MB con valore reale > 0]
	- Calcola uno speedup dopo il completamento rappresentato in una barra calcolato come speedup = [tempo_connessione_diversa/tempo_stessa_connessione]
	- Sta all'utente, gestendo questi valori, indicare come migliora o peggiora in base alla grandezza del testo.

La comunicazione è gestita tramite pattern observer che segnala alle view quando cambia il valore e quando aggiornare la textarea di risultato.
Le mail sono create tramite factory con o senza allegato.

La realizzazione ha impiegato circa 12-15 ore all'interno di una settimana e quindi circa 2 ore al giorno.

Github repo : https://github.com/mino181295/javamail-bench.git

CONFIGURAZIONE:
	1 : 
	- Import the project as maven project. Make sure your java version is 1.7 or higher.
	- Run the project as a Java application.
	2 :
	- Run the jar file