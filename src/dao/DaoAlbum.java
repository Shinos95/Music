package dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import database.Database;
import models.Album;
import models.EntitiesFactory;
import models.Entity;

public class DaoAlbum implements IDao{

    //proprietà
    private Database database;
    private final String INSERT = "INSERT INTO album (name,date_release) VALUES (?,?)"; 
    private final String READ = "SELECT * FROM album";
    private final String READBYID = "SELECT * FROM album WHERE id = ?";
    private final String UPDATE = "UPDATE album SET name = ?, date_release = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM album WHERE id = ?";
    private final String READBYNAME = "SELECT * FROM album WHERE name = ?";
    private final String READBYSONGNAME = "SELECT DISTINCT a.id,a.name,a.date_release\n" + //
                "FROM album a JOIN song s ON a.id = s.album_id\n" + //
                "WHERE s.name LIKE ?";

    private static DaoAlbum istance = null;

    public static synchronized DaoAlbum getInstance() {
        if(istance == null){
            istance = new DaoAlbum();
        }
        return istance;
    }

    //costruttore
    private DaoAlbum(){
        database = Database.getDatabase();
    }

    //metodi
    @Override
    public Long addEntity(Entity e) {
        if(e !=null && e instanceof Album){
           return database.executeUpdate(INSERT, 
           ((Album)e).getName(),String.valueOf(((Album)e).getDate_release())); 
        }
        return 0L;
    }

    @Override
    public Map<Long, Entity> readAll() {
        //"SELECT * FROM album";
        Map<Long,Map<String,String>> result = database.executeDQL(READ);
        Map<Long, Entity> dischi = new HashMap<>();
        Entity e = null;
        for (Entry<Long,Map<String,String>> coppia : result.entrySet()) {
            e = EntitiesFactory.makeEntity("album", coppia.getValue());
            dischi.put(e.getId(), e);
        }
        return dischi;
    }

    @Override
    public Entity readById(Long id) {
        Entity e = null;
        if(id != null && id != 0){
            Map<Long,Map<String,String>> result= database.executeDQL(READBYID, String.valueOf(id));
            if(result.entrySet().size() == 1){
                e = EntitiesFactory.makeEntity("album", result.get(id));
            }
        }
        return e;
    }

    @Override
    public void update(Entity e) {
        //"UPDATE album SET name = ?, date_release = ? WHERE id = ?";
        if(e != null && e instanceof Album a){
            database.executeUpdate(UPDATE,a.getName(), String.valueOf(a.getDate_release()),
                                    String.valueOf(a.getId()));
        }
    }

    @Override
    public void delete(Long id) {
        if(id != null && id != 0){
            database.executeUpdate(DELETE, String.valueOf(id));
        }
    }

    //metodo che cerca un album per titolo
    public Entity readByTitolo(String titolo){
        Entity e = null;
        if(titolo != null){
           Map<Long,Map<String,String>> album = database.executeDQL(READBYNAME, titolo);
            if(album.entrySet().size() == 1){
                for ( Entry<Long,Map<String,String>> coppia : album.entrySet()) {
                    e = EntitiesFactory.makeEntity("album", coppia.getValue());
                }
            }
        }
        return e;
    }

    public Map<Long,Entity> readBySongName(String name){
        Map<Long,Entity> album = new HashMap<>();
        Entity e = null;
        Map<Long,Map<String,String>> result = database.executeDQL(READBYSONGNAME, name);
        if(result.entrySet().size() == 1){
            for (Entry<Long,Map<String,String>> coppia : result.entrySet()) {
                e = EntitiesFactory.makeEntity("album", coppia.getValue());
                album.put(e.getId(), e);
            }
        }
        return album;
    }
    
}
