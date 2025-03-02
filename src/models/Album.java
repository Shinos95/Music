package models;

import java.time.LocalDate;
import java.util.List;

public class Album extends Entity{

    private String name;
    private LocalDate date_release;
    //aggiungo come proprietà degli ogetti di tipo Album una lista di Song
    //cioè ogni oggetto specifico di tipo Album avrà la gestione della sua lista di canzoni associate 
    private List<Song> songs;

    public Album() {
    }

    public Album(Long id, String name, LocalDate date_release, List<Song> songs) {
        super(id);
        this.name = name;
        this.date_release = date_release;
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate_release() {
        return date_release;
    }

    public void setDate_release(LocalDate date_release) {
        this.date_release = date_release;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Album "+ super.toString() +"[name=" + name +
         ", date_release=" + date_release + ", songs=" + songs +"]";
    }    
    
    
}
