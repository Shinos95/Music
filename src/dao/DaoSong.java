package dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import database.Database;
import models.EntitiesFactory;
import models.Entity;
import models.Song;
//accede ai dati usando l'oggetto database
public class DaoSong implements IDao<Song>{

    //per poter accedere ai dati delle canzoni avrà come dipendenza
    //un oggetto di tipo Database che gli fornisce il ritorno dei metodi
    //executeQuery(), executeUpdate()
    private Database database;
    
    private final String INSERT = "INSERT INTO song (name,duration) VALUES (?,?)"; 
    private final String READ = "SELECT * FROM song";
    private final String DELETE = "DELETE FROM song WHERE id = ?";
    private final String UPDATE = "UPDATE song SET name = ?, duration = ? WHERE id = ?";
    private final String READONE = "SELECT * FROM song WHERE id = ?";
    private final String READBYNAME =  "SELECT * FROM song WHERE name = ?";
    private final String UPDATEFK = "UPDATE song SET album_id = ? WHERE id = ?";

    private static DaoSong instance = null;

    public static synchronized DaoSong getInstance(){
        if(instance == null){
            instance = new DaoSong();
        }
        return instance;
    }

    //costruttore
    private DaoSong(){
        database = Database.getDatabase();
    }

    //metodi
    @Override
    public Long addEntity(Song s) {
        return database.executeUpdate(INSERT, s.getName(),String.valueOf(s.getDuration()));
    }

    public Long addEntity(Entity e, Long idAlbum) {
        Long id = 0L;
        if(e instanceof Song){
            Song s = (Song) e;
            id = database.executeUpdate(INSERT, s.getName(),String.valueOf(s.getDuration()));
            updateAlbumFK(idAlbum, id);
        }
        return id;
    }


    @Override
    public Map<Long, Entity> readAll() {
        Map<Long,Map<String,String>> result = database.executeDQL(READ);
        Map<Long,Entity> canzoni = new HashMap<>();
      //  EntitiesFactory ef = EntitiesFactory.getInstance();
        Entity e = null;
        for (Entry<Long,Map<String,String>> coppia : result.entrySet()) {
            //il daoSong ha bisogno di un oggetto di tipo Song
            //invece di occuparsi lui della creazione dell'istanza,
            //della gestione del suo tipo concreto e dell'assegnazione
            //di valori specifici alle sue proprietà, presi dalla mappa
            //DELEGA la FACTORY che ha il compito di centralizzare e smistare
            //la creazione degli oggetti(dei modelli figli di Entity)
            e = EntitiesFactory.getInstance().make(Song.class, coppia.getValue());
            canzoni.put(coppia.getKey(), e);
        }
        return canzoni;
    }

    @Override
    public Entity readById(Long id) {
        Entity e = null;
        if(id != null && id != 0){
            Map<Long,Map<String,String>> result = database.executeDQL(READONE, String.valueOf(id));
            if(result.entrySet().size() == 1){
                e = EntitiesFactory.getInstance().make(Song.class, result.get(id));
            }
        }
        return e;
    }

    @Override
    public void update(Song s) {
        //"UPDATE song SET name = ?, duration = ? WHERE id = ?";
            database.executeUpdate(UPDATE, s.getName(), String.valueOf(s.getDuration()),
            String.valueOf(s.getId()));
    }

    //metodo che aggiorna il valore di album_id in song
    public void updateAlbumFK(Long idSong,Long idAlbum){
        database.executeUpdate(UPDATEFK, String.valueOf(idAlbum),String.valueOf(idSong));
    }

    @Override
    public void delete(Long id) {
        if(id != null && id != 0){
            database.executeUpdate(DELETE,String.valueOf(id));
        }
    }

    public Entity readByName(String name){
        Entity e = null;
        if(name != null){
            Map<Long,Map<String,String>> canzone = database.executeDQL(READBYNAME, name);
            if (canzone.entrySet().size() == 1) {
                for (Entry<Long,Map<String,String>> coppia : canzone.entrySet()) {
                    e = EntitiesFactory.getInstance().make(Song.class, coppia.getValue());
                }
            }
        }
        return e;
    }
    
    //se voglio gestire l'assegnazione di ogni canzone al suo album 
    //posso creare un metodo in DaoSong che faccia questa associazione
    //sapendo quale sia l'id di un album posso inserire in esso la canzone che gli appartiene
    public Map<Long,Entity> readByIdAlbum(Long idAlbum){
        Map<Long,Entity> listaCanzoni = new HashMap<>();
        Entity s = null;
        //prendo le canzoni di uno specifico album
        String query = "SELECT s.id,s.name,s.duration " +
                        "FROM song s JOIN album a ON s.album_id = a.id WHERE a.id = ?";
        Map<Long,Map<String,String>> canzoni = database.executeDQL(query,String.valueOf(idAlbum));
        for (Entry<Long,Map<String,String>>coppia : canzoni.entrySet()) {
            s = EntitiesFactory.getInstance().make(Song.class, coppia.getValue());
            listaCanzoni.put(coppia.getKey(), s);
        }
        return listaCanzoni;
    }



}
