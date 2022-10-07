# Quarkus Artemis JMS

Il progetto mostra come creare un client JMS in Quarkus.
Per scopi di test, l'applicazione produce messaggi il cui *body* è un file XML.

Il client è stato compilato con OpenJDK 11 (openjdk version "11.0.16.1")

## Avvio dell'applicazione

Una volta posizionati della directory del progetto, l'applicazione può essere lanciata attraverso il comando:

```bash
mvn quarkus:dev
```


Si possono quindi invocare le API REST tramite CURL, ad esempio:

```bash
# Scrittura di cinque messaggi
curl localhost:8080/messages/produce/5

# Lettura del primo messaggio in coda
curl localhost:8080/messages/consume/first

# Lettura di 15 messaggi in coda (il client resta pending fino allo scodamento del numero previsto di messaggi)
curl localhost:8080/messages/consume/15
```

## Configurazione

La configurazione presente nel file *application.properties* contiene:
```bash
quarkus.artemis.url
quarkus.artemis.username
quarkus.artemis.password
quarkus.artemis.destination
```
## File XML di esempio
I file XML, che vengono inviati come *body* dei messaggi prodotti dall'applicazione, si trovano nel path:
```bash
src/main/resources/files
```
Il relativo parsing è ora cablato nella classe `JMSXMLProducer`

## Eseguire in modalità nativa

L'applicazione può essere compilata in codice nativo con il comando

`mvn clean install -Pnative`

ed eseguita con:

`./target/jms-xml_message-client-1.0.0-runner`


## TODO
Completamento dei test nella dir */test*
