package models;

import java.util.Map;

/*La classe EntityFactory è una fabbrica generica per la creazione di oggetti che implementano
 l'interfaccia IMappable nel caso in cui avessimo scritto: implements IFactory<Entity, Map<String,String>
 ma in questo specifico caso abbiamo dichiarato: implements IFactory<Entity, Map<String,String>>
è quindi una fabbrica generica per la creazione di oggetti che estendono Entity
utilizzando i parametri forniti in una mappa Map<String, String> */
//IFactory è stato dichiarato: interface IFactory<F,P>
//dove F (qui sostituito con Entity): rappresenta il tipo FORMALE di oggetto che sarà restituito dalla factory.
//in questo modo ci diamo la possibilità di creare diversi oggetti(tutti FORMALMENTE Entity) 
//e sfruttare il polimorfismo
//P (qui sostituito con Map<String, String>): rappresenta i parametri utilizzati per creare gli oggetti.
//nel caso infatti della EntitiesFactory vogliamo che produca oggetti usando 
//come valori quelle salvati in una mappa che passiamo come parametro in input
public class EntitiesFactory implements IFactory<Entity, Map<String,String>>{

    //SINGLETON per limitare per EntitiesFactory una sola istanza possibile 
    private static EntitiesFactory instance;

    public static synchronized EntitiesFactory getInstance(){
        if(instance == null){
            instance = new EntitiesFactory();
        }
        return instance;
    }

    private EntitiesFactory(){}

    //IFactory il metodo è dichiarato in questo modo:
    //<T extends F> T make(Class<T> tipo, P... params);
    //ogni classe che implementa questa interfaccia deve dare un corpo al metodo e
    //dichiarare quale deve essere il tipo di F, ovvero quali oggetti possono 
    //essere restituiti dal metodo
    //essendo nella classe EntitiesFactory questa sarà una fabbrica di Entity e non di altro per cui
    //qui dichiariamo che quella F in IFactory sarà una Entity
    //<T extends Entity>: il tipo T deve essere una classe che estende Entity.
    // <T extends Entity> T : Se il generics produce valori (lettura), usiamo extends
    // questo medoto restituirà un elemento, un oggetto T che deve essere figlio di Entity
    //se provassimo a resituire un elemento di altro tipo sarebbe un errore, segnalato 
    //anche dall'ide
    @Override
    public <T extends Entity> T make(Class<T> tipo, Map<String,String>...parametri){
        //in IFactory la firma del metodo era: make(Class<T> tipo, P... params);
        //qui inEntitiesFactory dobbiamo specificare cosa è T e cosa è P
        //il primo parametro accettato in input è accetta un oggetto Class<T>
        // che rappresenta la classe dell'oggetto (nel suo tipo CONCRETO) da costruire.
        //non sappiamo ancora che oggetto verrà creato all'invocazione del metodo make
        //verrà deciso quando qualcuno ne richiederà la creazione
        //(quando al dao servirà un oggetto o song o album)
        //solo a quel punto T avrà una forma specifica, quella della classe passata come primo input
        //per questo motivo lasciamo ancora la dichiarazione del tipo generico
        //Questo metodo permette di costruire dinamicamente un'istanza di T 
        //(che è un sottotipo di F), usando la reflection (Class<T> tipo)
        //il secondo parametro in IFactory è P... params, qui dobbiamo specificare
        //in EntitiesFactory il tipo effettivo di P, in questo caso è un varargs di MAPPE
        //perchè per valorizzare le proprietà dell'istanza creata prenderemo i dati da una mappa
        //usando il varargs ci diamo la libertà di poter inserire da 0 a N mappe

        //Map<String,String> mappa = parametri[0]; 
        Entity e;
        switch (tipo.getSimpleName().toLowerCase()) {
            case "song":
                e = new Song();
            //    e.setId(Long.parseLong(mappa.get("id")));
            //    ((Song)e).setName(mappa.get("name"));
            //    ((Song)e).setDuration(Double.parseDouble(mappa.get("duration"))); 
            e.fromMap(parametri[0]); 
            return (T)e;   
            case "album":
                e = new Album();
                //e.setId(Long.parseLong(mappa.get("id")));
                //((Album)e).setName(mappa.get("name"));
                //((Album)e).setDate_release(mappa.get("date_release"));
                e.fromMap(parametri[0]);
                return (T)e;
        }
        return null;
    }
    
    /* Regola della PECS (Producer Extends, Consumer Super)
    Se il generics produce valori (lettura), usiamo extends
    Se il generics consuma valori (scrittura), usiamo super */
}
