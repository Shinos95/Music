package viste;

import java.io.File;
import java.util.Scanner;

import models.Album;
import models.Entity;
import models.Song;

public class View {

    private String percorsoTemplate;

    public View(String percorsoCartella){
        this.percorsoTemplate = percorsoCartella;
    }

    public String getPercorsoTemplate() {
        return percorsoTemplate;
    }

    public void setPercorsoTemplate(String percorsoTemplate) {
        this.percorsoTemplate = percorsoTemplate;
    }

    public String loadTemplate(String nomeTemplate){
        String templateCaricato = "";
        String percorsoCompleto = percorsoTemplate + "/" + nomeTemplate;
        try (Scanner scanner = new Scanner(new File(percorsoCompleto))) {
            while(scanner.hasNextLine()){
                templateCaricato += scanner.nextLine() +"\n";
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return templateCaricato;
    }
    
    //metodo che renderizza i dati delle entità
    // ==== Entity
    // ID: [ID]
    public String render(Entity e){
        String template = loadTemplate("Entity.txt");
        template = template.replace("[ID]", String.valueOf(e.getId()));
        // if(e instanceof Song){
        //     template += renderSong(e);
        // }else if(e instanceof Album){
        //     template += renderAlbum(e);
        // }
        return template;
    }

    //render di song
    // ___________     Song   _____________
    // Nome ... : [NOME]
    // Durata ... : [DURATA] min
    public String renderSong(Entity song){
        String template = loadTemplate("Song.txt");
        
        if(song instanceof Song s){
            return template.replace("[NOME]", s.getName())
            .replace("[DURATA]", String.valueOf(s.getDuration()));
        }
        return template;
    }

    // ----- Album
    // Nome ... : [NOME]
    // Data di Rilascio: [DATA_RILASCIO]
    // Canzoni: [LISTA_CANZONI]
    public String renderAlbum(Entity album){
        String template = loadTemplate("Album.txt");
        if(album instanceof Album a){
            return template.replace("[NOME]", a.getName())
            .replace("[DATA_RILASCIO]", String.valueOf(a.getDate_release()))
            .replace("[LISTA_CANZONI]", String.valueOf(a.getSongs()));
        }
        return template;
    }

}
