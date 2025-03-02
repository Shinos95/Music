package models;

import java.util.Map;

public class EntitiesFactory  {

    /* design pattern CREAZIONALE: FACTORY
    1.Problema:
    vogliamo creare un sistema centralizzato e flessibile per istanziare oggetti 
    di un dato tipo.Uno degli obiettivi è garantire il polimorfismo

    2.Soluzione:
    viene usato per delegare la creazione dell'oggetto a un secondo soggetto
    cioè alla factory che sceglierà per noi il tipo concreto a seconda dei parametri
    che le passiamo.
    Nell’esempio fatto in classe, nello specifico come parametro passiamo al metodo make una mappa e in base al suo contenuto il metodo deciderà quale tipo creare
    -> in questo modo il programma resta polimorfico e quindi in grado di gestire  dati diversi che possono arrivare da un file o da un database
    - si evita di far usare new al Client. E’ solo la Factory a farlo.

    - usiamo l'oggetto prodotto dalla Factory per il suo tipo formale  
    - ricordarsi che la Factory potrebbe restituire eccezioni o valori null
   -> prevedere l'errore e gestirlo   

    3.Soggetti: 
    - un Product, un Prodotto cioè il tipo formale dell'oggetto che vogliamo creare,
    spesso abbiamo usato Entity.
    - la Factory, un oggetto, una classe o una interfaccia che è responsabile 
    della produzione del Product, dei prodotti(nel nostro caso delle Entity)
    e della scelta dei loro tipi concreti
    - il Client, che è l'oggetto o la classe che si rivolge alla Factory per
    creare oggetti di tipo Product (Entity) e se necessario eventualmente invia
    i dati necessari per farlo.
     * 
     */
    public static Entity makeEntity(String tipo, Map<String,String> mappa){
        switch (tipo.toLowerCase()) {
            case "song":
            Entity e = new Song();
            //    e.setId(Long.parseLong(mappa.get("id")));
            //    ((Song)e).setName(mappa.get("name"));
            //    ((Song)e).setDuration(Double.parseDouble(mappa.get("duration"))); 
            e.fromMap(mappa); 
            return e;   
            case "album":
                e = new Album();
                //e.setId(Long.parseLong(mappa.get("id")));
                //((Album)e).setName(mappa.get("name"));
                //((Album)e).setDate_release(mappa.get("date_release"));
                e.fromMap(mappa);
                return e;
        }
        return null;
    }

    



    
}
