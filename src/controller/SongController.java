package controller;

import models.Song;
import service.ServiceSong;
import viste.View;

public class SongController {

    ServiceSong serviceSong = ServiceSong.getInstance();
    View view = new View("templates");

    public String getSongs(){
        String canzoni = "Elenco canzoni:\n";
        for (Song s : serviceSong.findAll()) {
            canzoni += view.render(s);
            canzoni += view.renderSong(s);
            //motore di renderizzazione
        }
        //restituisce la pagina html renderizzata
        return canzoni;
    }

    public String findSong(Long idSong){
        String canzone = "";
        Song s =serviceSong.findById(idSong);
        canzone = view.render(s);
        canzone +=view.renderSong(s);
        return canzone;
    }


    
}
