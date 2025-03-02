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

    //creo un solo oggetto Database e lo richiamo quando serve senza creare altre istanze
    
    public Database(){
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("controlla la proprietà DRIVER oppure di avere aggiunto "+ 
            " il jar che contiene la libreria JDBC");
        }
        try(Connection connection = DriverManager.getConnection(percorso, user, password);) {
            this.connection = connection;
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
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs != null){
                return rs.getLong(1);
            }
            ps.close();
            if(rs != null)
                rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeConn();
        }
        return 1L;
    }
   
    
    
}
