import controller.SongController;
import dao.Clones;
import models.Song;

public class App {
    public static void main(String[] args) throws Exception {
        
        // ServiceSong serviceSong = ServiceSong.getInstance();
        // ServiceAlbum serviceAlbum = ServiceAlbum.getInstance();

       //System.out.println(serviceSong.findAll());
      // System.out.println(serviceAlbum.findAll());
      //System.out.println(serviceAlbum.findAllAlbumAndSongs());
        //System.out.println(serviceSong.findById(100L));
       // System.out.println(serviceAlbum.findAlbumBySongName("One"));
      //  System.out.println(serviceAlbum.findById(1L));


        // Clones c = new Clones();
        // Song s = (Song)c.getCloni("song");
        // System.out.println(s.toString());
        // s.setName("la canzone ha un nuovo nome");
        // System.out.println(s);

        SongController songController = new SongController();
        //System.out.println(songController.getSongs());
        System.out.println(songController.findSong(3L));
        
    }
}
