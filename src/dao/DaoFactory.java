package dao;

import models.IFactory;

public class DaoFactory implements IFactory<IDao<?>,String>{

    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance(){
        if(instance == null){
            instance = new DaoFactory();
        }
        return instance;
    }

    private DaoFactory(){}

    @Override
    public <T extends IDao<?>> T make(Class<T> tipo, String... params) {
        IDao<?> dao = null;
        switch (tipo.getSimpleName().toLowerCase()) {
            case "daosong":
                dao = DaoSong.getInstance();
                break;
            case "daoalbum":
                dao = DaoAlbum.getInstance();
                break;
            default:
            System.out.println("errore nella creazione del dao");
            break;
        }
        return (T)dao;
    }


    
}
