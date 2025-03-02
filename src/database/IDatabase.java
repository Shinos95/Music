package database;

import java.util.Map;

public interface IDatabase {

    Map<Long,Map<String,String>> executeDQL(String query,String... parametri);
    Long executeUpdate(String comando,String... parametri);
    
}
