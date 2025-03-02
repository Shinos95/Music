package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dao.DaoAlbum;
import models.Album;
import models.Entity;

public class ServiceAlbum {

    //proprietà
    private DaoAlbum daoAlbum;
    private ServiceSong serviceSong;
    private static ServiceAlbum instance;

    public static synchronized ServiceAlbum getInstance() {
        if(instance == null){
            instance = new ServiceAlbum();
        }
        return instance;
    }

    //costruttore
    private ServiceAlbum(){
        daoAlbum = DaoAlbum.getInstance();
        serviceSong = ServiceSong.getInstance();
    }

    //metodi
    public Long save(Entity e){
        return daoAlbum.addEntity(e);
    }
    
    public List<Album> findAll(){
        List<Album> album = new ArrayList<>();
        Map<Long,Entity> result = daoAlbum.readAll();
        for (Entry<Long,Entity> coppia : result.entrySet()) {
            if(coppia.getValue() instanceof Album al){  
                album.add(al);
            }
        }
        return album;
    }

    //metodo che resituisce gli album e le loro canzoni
    public List<Album> findAllAlbumAndSongs(){
        List<Album> album = findAll();
        for (Album a : album) {
            a.setSongs(serviceSong.findSongsByIdAlbum(a.getId()));
        }
        return album;
    }

    //metodo che restituisce l'album di una canzone
    public Album findAlbumBySongName(String name){
        Map<Long,Entity> result = daoAlbum.readBySongName(name);
        Album a = null;
        if(result.entrySet().size() == 1){
            for (Entry<Long,Entity> coppia : result.entrySet()) {
                a = (Album)coppia.getValue();
            }
        }
        return a;
    }    


    public void delete(Long id){
        daoAlbum.delete(id);
    }

    public Album findById(Long id){
        if(daoAlbum.readById(id) != null && daoAlbum.readById(id) instanceof Album a){
            a.setSongs(serviceSong.findSongsByIdAlbum(id));
            return a;
        }
        return null;
    }

}
