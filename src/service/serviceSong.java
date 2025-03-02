package service;
//classe che implementa la logica di business delle canzoni
//per farlo usare il dao che restituisce i dati delle canzoni 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dao.DaoSong;
import models.Entity;
import models.Song;

public class ServiceSong {
    
    //proprietà
    private DaoSong daoSong;
    private static ServiceSong instance;
    
    public static synchronized ServiceSong getInstance() {
        if(instance == null){
            instance = new ServiceSong();
        }
        return instance;
    }

    //costruttore
    private ServiceSong(){
        //per accedere ai metodi di daoSong devo creare quell'istanza
        daoSong = DaoSong.getInstance();
    }

    //metodi
    public void save(Entity e){
        daoSong.addEntity(e);
    }

    public void save(Entity e,Long idAlbum){
        daoSong.addEntity(e, idAlbum);
    }

    public List<Song> findAll(){
        List<Song> canzoni = new ArrayList<>();
        Map<Long,Entity> result = daoSong.readAll();
        for (Entry<Long,Entity> coppia: result.entrySet()) {
            if(coppia.getValue() instanceof Song){
                canzoni.add((Song)coppia.getValue());
            }
        }
        return canzoni;
    }

    public Song findById(Long id){
        //readById(id) resituisce una Entity che il service casta a Song
        return (Song)daoSong.readById(id);
    }

    public void update(Entity e){
        daoSong.update(e);
    }

    public void update(Entity e, Long idAlbum){
        daoSong.updateAlbumFK(e.getId(), idAlbum);
    }

    public void delete(Long id){
        daoSong.delete(id);
    }

    public Song findByName(String name){
        return (Song)daoSong.readByName(name);
    }

    //metodo che restituisce la lista delle canzoni di un album, sapendo l'id dell'album
    public List<Song> findSongsByIdAlbum(Long idAlbum){
        List<Song> canzoni = new ArrayList<>();
        Map<Long,Entity> result = daoSong.readByIdAlbum(idAlbum);
        for (Entry<Long,Entity> coppia : result.entrySet()) {
            if(coppia.getValue() instanceof Song s){
                canzoni.add(s);
            }
        }
        return canzoni;
    }


}
