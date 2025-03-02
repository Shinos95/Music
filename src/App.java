import java.util.HashMap;
import java.util.Map;

import models.Entity;
import models.Song;

public class App {
    public static void main(String[] args) throws Exception {
        
        Entity e = new Song(1L,"canzone",2.5);
        System.out.println(e.toMap());

        Map<String,String> mappa = new HashMap<>();
        mappa.put("id", "2");
        mappa.put("name", "canzone2");
        mappa.put("duration", "2.8");

        Entity e2 = new Song();
        e2.fromMap(mappa);
        System.out.println(e2.toString());

    }
}
