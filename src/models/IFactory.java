package models;

public interface IFactory<F,P> {

    //Entity makeEntity(String tipo, Map<String,String> mappa)
    <T extends F> T make(Class<T> tipo, P... params);
    
}
