package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private String user;
    private String password;
    private String percorso;
    private Connection connection;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /*Un design pattern è una soluzione astratta, generica e riproducibile 
    a un problema ricorrente di programmazione. Il concetto riprendere quello di 
    pattern (motivo) in architettura, ed è stato popolarizzato dal libro con lo stesso 
    nome del 1994 con particolare riferimento alla programmazione a oggetti.
 
    L'uso e il tempo hanno modificato il significato dato ad alcuni dei pattern, che erano 23, 
    specificati nel libro originale, e ne ha creati altri.
    
    Un design pattern è una soluzione astratta,generica e riproducibile a un problema ricorrente di programmazione. 
    Un pattern non è un pezzo di codice specifico ma un modo di lavorare
    unendo dei pezzi.
	  
    -1. il problema da affrontare, il problema che affronta il pattern
    -2. perché viene utilizzato
    -3. la sua struttura, quali parti lo compongono

    I pattern sono tradizionalmente divisi in famiglie:
    - creazionali
    - strutturali
    - comportmentali
    */

    /*Quello di riportato è il patter CREAZIONALE SINGLETON:
     *1.Problema:
    vogliamo evitare di creare più volte lo stesso oggetto, per ragioni di prestazioni, di sicurezza, o entrambe. Esempi classici: registro (che non dovrebbe essere duplicato), DAO (ne serve tipicamente solo uno per tipo).

    2.Soluzione:
    viene usato per garantire al massimo una istanza di un oggetto. 
    Definito un oggetto di cui non vogliamo ripetizioni modifichiamo la classe relavita dell'oggetto e il suo costruttore in modo da evitare repliche e crearne al massimo uno. Questo passaggio richiede un approccio standard che è il Singleton

    3.Soggetti:
    - il Client: chi ha bisogno dell'oggetto e che quindi usarà il Singleton
    - il Singleton stesso che rappresenta l'oggetto, è l’elemento prodotto di si potrà
    avere una sola istanza

    Ecco i passaggi:
*/
    // un'ISTANZA PRIVATA che è una PROPRIETA' STATICA(della classe), cioè un oggetto, che è anche l'unico oggetto possibile di questo tipo.
    // Il tipo dell’oggetto è anche lo stesso della classe in cui abbiamo inserito il Singleton proprio perché l’oggetto appartiene alla classe.
    // La classe ha come proprietà di classe l’unico oggetto possibile.
    // Per convenzione il nome assegnatogli è instance.
    private static Database instance = null;
    
    // ci deve essere un metodo statico GET public che ritorni il valore di quell'oggetto e che al suo interno abbia un controllo sul valore dell’istanza instance.
    // Alla prima chiamata del metodo instance verrà creato e restituito. Dalla seconda in avanti, verrà sempre restituita l’istanza creata in precedenza
    public synchronized static Database getInstance() {
        // La parola chiave synchronized nel metodo getInstance() assicura che un chiamante per volta entri nel metodo, di modo che non possano esserci due chiamate 
        // contemporanee che porterebbero alla creazione di due oggetti instance separati, di fatto rompendo il meccanismo.
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    //un costruttore privato e vuoto. Privato significa che non può essere usato e  richiamato fuori dalla classe
    private Database(){
        this.user = "root";
        this.password = "root";
        setPercorso(percorso);
    }

    public void setPercorso(String percorso) {
        String url = "jdbc:mysql://127.0.0.1:3306/music2";
        String timezone = "?useSSL=false&serverTimezone=UTC";
        this.percorso = url+ timezone;
    }

    public void openConn(){
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(percorso, user, password);  
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("controlla la proprietà DRIVER oppure di avere aggiunto "+ 
            " il jar che contiene la libreria JDBC");
        }catch(SQLException e){
                e.printStackTrace();
                System.out.println("la connessione non è aperta controlla che il percorso, lo user e la password " +
                " siano corretti");
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public void closeConn(){
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //metodo per un inviare una generica DQL al database
    public Map<Long,Map<String,String>> executeDQL(String query,String... parametri){
        Map<Long,Map<String,String>> result = new HashMap<>();
        openConn();
        try {
            //SELECT * FROM song WHERE name = ?
            PreparedStatement ps = connection.prepareStatement(query);
            for (int i = 0; i < parametri.length; i++) {
                //ps.setString(1,"nomeCanzone");
                ps.setString(i+1, parametri[i]);
                //SELECT * FROM song WHERE name = nomeCanzone
            }
            ResultSet rs = ps.executeQuery();
            Map<String,String> mappa;
            while (rs.next()) {//ci permette di spostarci in verticale
                //scorre un record per volta dal primo all'ultimo della tabella
                mappa = new HashMap<>();
                //le chiavi della mappa saranno i nomi delle colonne
                //i valori associari saranno quelli di quelle colonne
                //al record a cui sta puntando rs
                //devo spostarmi colonna per colonna e prendere i valori
                //in orizzontale
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    mappa.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                }
                result.put(rs.getLong("id"), mappa);
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeConn();
        }
        return result;
    }
    
    //metodo per eseguire qualsiasi DML(INSERT,DELETE,UPDATE)
    public Long executeUpdate(String comando,String... parametri){
        Long ris = 0L;
        openConn();
        String[] colonne = {"id"};
        try {
            PreparedStatement ps = connection.prepareStatement(comando,colonne);
            //INSERT INTO song (name,duration) VALUSE (?,?);
            for (int i = 0; i < parametri.length; i++) {
                ps.setString(i+1, parametri[i]);
                //ps.SetString(1,"nomeCanzone");
                //INSERT INTO song (name,duration) VALUSE ("nomeCanzone",?);
            }
             //INSERT INTO song (name,duration) VALUSE ("nomeCanzone",4.3);
            ris = (long)ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs != null){
                ris = rs.getLong(1);
                rs.close();
                return ris;
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeConn();
        }
        return ris;
    }
   
    
    
}
