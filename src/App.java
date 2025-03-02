import service.ServiceAlbum;
import service.ServiceSong;

public class App {
    public static void main(String[] args) throws Exception {
        
        ServiceSong serviceSong = ServiceSong.getInstance();
        ServiceAlbum serviceAlbum = ServiceAlbum.getInstance();

       //System.out.println(serviceSong.findAll());
      // System.out.println(serviceAlbum.findAll());
      //System.out.println(serviceAlbum.findAllAlbumAndSongs());
        //System.out.println(serviceSong.findById(100L));
       // System.out.println(serviceAlbum.findAlbumBySongName("One"));
        System.out.println(serviceAlbum.findById(1L));

    }
}
