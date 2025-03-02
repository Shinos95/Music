package dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import models.Album;
import models.Entity;
import models.Song;

public class Clones {
    
    private Map<String,Entity>cloni;
    
    public Clones(){
        cloni = new HashMap<>();
        cloni.put("song vuota", new Song());
        cloni.put("song", new Song(0L, "canzone nuova", 3));
        cloni.put("album", new Album(0L, "album nuovo", LocalDate.now(), null));
    } 
    
    public Entity getCloni(String chiave) {
        if(cloni.containsKey(chiave)){
            return (Entity) cloni.get(chiave);
        }
        return null;
    }
}
